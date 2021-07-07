/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler extends ListenerAdapter {

    ObjectMapper mapper = new ObjectMapper();


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        JDA jda = event.getJDA();
        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();
        Message messageData = event.getMessage();
        String message = event.getMessage().getContentRaw().toLowerCase();
        if (message.startsWith("m!") && !author.isBot()) {
            String[] command = message.substring(2).split(" ");
            System.out.println("[monkebot] "+ author.getName() + " used command: " + message);
            switch (command[0]) {
                case "":
                    channel.sendMessage("You haven't specified a command!").submit();
                case "help":
                    channel.sendMessageEmbeds(VariableStorage.helpEmbed).submit();
                    break;
                case "ip":
                    channel.sendMessageEmbeds(VariableStorage.ipEmbed).submit();
                    break;
                case "ping":
                    String pingTime = "Took " + (ZonedDateTime.now().toInstant().toEpochMilli() - messageData.getTimeCreated().toInstant().toEpochMilli()) + "ms";
                    MessageEmbed pingEmbed = new EmbedBuilder()
                        .setTitle("Pong!")
                        .setDescription(pingTime)
                        .setColor(0x00ff75)
                        .build();
                    channel.sendMessageEmbeds(pingEmbed).submit();
                    break;
                case "gay":
                    if (command.length != 2) {
                        channel.sendMessage("haha gay lol!").submit();
                    } else {
                        List<User> pinged = messageData.getMentionedUsers();
                        for (User u : pinged) {
                            User user = jda.retrieveUserById(u.getId()).complete();
                            String userName = user.getName();
                            channel.sendMessageEmbeds(VariableStorage.gayEmbed.setTitle(userName + " is gay lol!").build()).submit();
                        }
                    }
                    break;
                case "status":
                    Map valueMap = null;
                    try {
                        URL j = new URL("https://api.mcsrvstat.us/2/play.monkegame.online");
                        valueMap = mapper.readValue(j, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if ((Boolean) valueMap.get("online")) {
                        String minOnline = (String) valueMap.get("players.online");
                        String maxOnline = (String) valueMap.get("players.max");
                        MessageEmbed onlineEmbed = new EmbedBuilder()
                            .setTitle("Server Status")
                            .setColor(0x00ff9f)
                            .setDescription("\n\uD83D\uDFE2 Players: " + minOnline + "/" + maxOnline)
                            .setTimestamp(Instant.now())
                            .build();
                        channel.sendMessageEmbeds(onlineEmbed).submit();
                    } else {
                        channel.sendMessageEmbeds(VariableStorage.offlineEmbed).submit();
                    }
                    break;
                case "onkebot":
                    channel.sendMessageEmbeds(VariableStorage.onkebotEmbed).submit();
                    break;
                case "leaderboard":
                    if (command.length == 3 && command[1].equals("-m")) {
                        switch (command[2]) {
                            case "italy":
                                ArrayList<String> playerListItaly = new ArrayList<>();
                                ArrayList<String> killListItaly = new ArrayList<>();
                                killListItaly.add("");
                                playerListItaly.add("");
                                String jdbcstuffItaly = "jdbc:sqlite:"+ Main.config.get("databaseLocItaly");
                                String fetchInfoItaly =
                                    "SELECT username, killcount " +
                                    "FROM " + Main.config.get("databaseTableItaly") +
                                    " ORDER BY killcount DESC " +
                                    "LIMIT 10";

                                try (Connection conn = DriverManager.getConnection(jdbcstuffItaly);
                                     Statement stmt = conn.createStatement()) {
                                    ResultSet rs = stmt.executeQuery(fetchInfoItaly);
                                    while (rs.next()) {
                                        playerListItaly.add(rs.getString("username"));
                                        killListItaly.add(rs.getString("killcount"));
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                String playerOutputItaly = playerListItaly.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                String killOutputItaly = killListItaly.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                VariableStorage.modificationItaly();
                                MessageEmbed leaderboardItaly = new EmbedBuilder()
                                        .setTitle("Kill Leaderboard")
                                        .setDescription("This is the top 10. Can you get to #1?\nMap: ``Italy``")
                                        .addField("#", "```" + VariableStorage.ranks + "```", true)
                                        .addField("Username","```" + playerOutputItaly + "```", true)
                                        .addField("Kills", "```" + killOutputItaly + "```",true)
                                        .setColor(0x5985a4)
                                        .setTimestamp(Instant.now())
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + " ago")
                                        .build();
                                channel.sendMessageEmbeds(leaderboardItaly).submit();
                                break;
                            case "museum":
                                ArrayList<String> playerListMuseum = new ArrayList<>();
                                ArrayList<String> killListMuseum = new ArrayList<>();
                                killListMuseum.add("");
                                playerListMuseum.add("");
                                String jdbcstuffMuseum = "jdbc:sqlite:"+ Main.config.get("databaseLocMuseum");
                                String fetchInfoMuseum =
                                        "SELECT username, killcount " +
                                                "FROM " + Main.config.get("databaseTableMuseum") +
                                                " ORDER BY killcount DESC " +
                                                "LIMIT 10";

                                try (Connection conn = DriverManager.getConnection(jdbcstuffMuseum);
                                     Statement stmt = conn.createStatement()) {
                                    ResultSet rs = stmt.executeQuery(fetchInfoMuseum);
                                    while (rs.next()) {
                                        playerListMuseum.add(rs.getString("username"));
                                        killListMuseum.add(rs.getString("killcount"));
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                String playerOutputMuseum = playerListMuseum.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                String killOutputMuseum = killListMuseum.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                VariableStorage.modificationMuseum();
                                MessageEmbed leaderboardMuseum = new EmbedBuilder()
                                        .setTitle("Kill Leaderboard")
                                        .setDescription("This is the top 10. Can you get to #1?\nMap: ``Museum``")
                                        .addField("#", "```" + VariableStorage.ranks + "```", true)
                                        .addField("Username","```" + playerOutputMuseum + "```", true)
                                        .addField("Kills", "```" + killOutputMuseum + "```",true)
                                        .setColor(0x5985a4)
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + " ago")
                                        .setTimestamp(Instant.now())
                                        .build();
                                channel.sendMessageEmbeds(leaderboardMuseum).submit();
                                break;
                            case "highrise":
                                ArrayList<String> playerListHighrise = new ArrayList<>();
                                ArrayList<String> killListHighrise = new ArrayList<>();
                                killListHighrise.add("");
                                playerListHighrise.add("");
                                String jdbcstuffHighrise = "jdbc:sqlite:"+ Main.config.get("databaseLocHighrise");
                                String fetchInfoHighrise =
                                        "SELECT username, killcount " +
                                                "FROM " + Main.config.get("databaseTableHighrise") +
                                                " ORDER BY killcount DESC " +
                                                "LIMIT 10";

                                try (Connection conn = DriverManager.getConnection(jdbcstuffHighrise);
                                     Statement stmt = conn.createStatement()) {
                                    ResultSet rs = stmt.executeQuery(fetchInfoHighrise);
                                    while (rs.next()) {
                                        playerListHighrise.add(rs.getString("username"));
                                        killListHighrise.add(rs.getString("killcount"));
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                String playerOutputHighrise = playerListHighrise.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                String killOutputHighrise = killListHighrise.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                VariableStorage.modificationHighrise();
                                MessageEmbed leaderboardHighrise = new EmbedBuilder()
                                        .setTitle("Kill Leaderboard")
                                        .setDescription("This is the top 10. Can you get to #1?\nMap: ``Highrise``")
                                        .addField("#", "```" + VariableStorage.ranks + "```", true)
                                        .addField("Username","```" + playerOutputHighrise + "```", true)
                                        .addField("Kills", "```" + killOutputHighrise + "```",true)
                                        .setColor(0x5985a4)
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + " ago")
                                        .setTimestamp(Instant.now())
                                        .build();
                                channel.sendMessageEmbeds(leaderboardHighrise).submit();
                                break;
                            default:
                                channel.sendMessage("Map not found! Choose from these:").submit();
                                channel.sendMessageEmbeds(VariableStorage.leaderboardMapList).submit();
                        }
                    } else if (command.length == 2) {
                        switch (command[1]) {
                            case "-m":
                                channel.sendMessageEmbeds(VariableStorage.leaderboardMapList).submit();
                                break;
                            //TODO fix -a
                            //working on it aaa
                            case "-a":
                                ArrayList<String> playerListALL = new ArrayList<>();
                                ArrayList<String> killListALL = new ArrayList<>();
                                killListALL.add("");
                                playerListALL.add("");
                                String jdbcstuffHighrise = "jdbc:sqlite:"+ Main.config.get("databaseLocHighrise");
                                String fetchInfoALL =
                                                "SELECT username, killcount" +
                                                " FROM " + Main.config.get("databaseTableHighrise") +
                                                " UNION" +
                                                " SELECT username, killcount" +
                                                " FROM " + Main.config.get("databaseTableMuseum") +
                                                " UNION" +
                                                " SELECT username, killcount" +
                                                " FROM " + Main.config.get("databaseTableItaly") +
                                                " ORDER BY killcount DESC" +
                                                " LIMIT 10";

                                try (Connection conn = DriverManager.getConnection(jdbcstuffHighrise);
                                     Statement stmt = conn.createStatement()) {
                                    ResultSet rs = stmt.executeQuery(fetchInfoALL);
                                    while (rs.next()) {
                                        playerListALL.add(rs.getString("username"));
                                        killListALL.add(rs.getString("killcount"));
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                String playerOutputALL = playerListALL.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                String killOutputALL = killListALL.stream().map(Object::toString).collect(Collectors.joining("\n"));
                                VariableStorage.modificationHighrise();
                                MessageEmbed leaderboardHighrise = new EmbedBuilder()
                                        .setTitle("Kill Leaderboard")
                                        .setDescription("This is the top 10. Can you get to #1?")
                                        .addField("#", "```" + VariableStorage.ranks + "```", true)
                                        .addField("Username","```" + playerOutputALL + "```", true)
                                        .addField("Kills", "```" + killOutputALL + "```",true)
                                        .setColor(0x5985a4)
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + " ago")
                                        .setTimestamp(Instant.now())
                                        .build();
                                channel.sendMessageEmbeds(leaderboardHighrise).submit();
                                channel.sendMessage("this should show the total of all maps, TODO").submit();
                                break;
                            default:
                                channel.sendMessage("Unknown flag!").submit();
                                break;
                        }
                    } else if (command.length == 1) {
                        channel.sendMessageEmbeds(VariableStorage.leaderboardFlags).submit();
                    }
                    break;
                case "hiddencommandverysecretdebug":
                    channel.sendMessage("how'd you find this lol?").submit();
                    break;
                default:
                    channel.sendMessage("Unknown command!").submit();
                    break;
            }
        }
    }
}