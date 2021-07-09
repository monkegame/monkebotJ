/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.StackTrace;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CommandHandler extends ListenerAdapter {



    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        JDA jda = event.getJDA();
        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();
        Message messageData = event.getMessage();
        String message = event.getMessage().getContentRaw().toLowerCase();
        List<User> pinged = messageData.getMentionedUsers();
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
                    String pingTime = "Took " + (Instant.now().toEpochMilli() - messageData.getTimeCreated().toInstant().toEpochMilli()) + "ms";
                    MessageEmbed pingEmbed = new EmbedBuilder()
                        .setTitle("Pong!")
                        .setDescription(pingTime)
                        .setColor(0x00ff75)
                        .build();
                    channel.sendMessageEmbeds(pingEmbed).submit();
                    break;
                case "gay":
                    if (command.length != 2) {
                        channel.sendMessage("haha <@" + author.getId() + "> gay lol!").submit();
                    } else {
                        try {
                            for (User u : pinged) {
                                User user = jda.retrieveUserById(u.getId()).complete();
                                String userName = user.getName();
                                channel.sendMessageEmbeds(VariableStorage.gayEmbed.setTitle(userName + " is gay lol!").build()).submit();
                            }
                        } catch (NoClassDefFoundError error) {
                                System.out.println("that's not a user!");
                                channel.sendMessage("can't confirm they're gay, sorry!").submit();
                        }
                    }
                    break;
                case "status":
                    Map valueMap = null;
                    try {
                        URL j = new URL("https://api.mcsrvstat.us/2/" + Main.config.get("serverIp"));
                        ObjectMapper mapper = new ObjectMapper();
                        valueMap = mapper.readValue(j, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if ((Boolean) valueMap.get("online")) {
                        Map players = (Map) valueMap.get("players");
                        int minOnline = (int) players.get("online");
                        int maxOnline = (int) players.get("max");
                        MessageEmbed onlineEmbed = new EmbedBuilder()
                            .setTitle("Server Status")
                            .setColor(0x00ff9f)
                            .setDescription("\n\uD83D\uDFE2 - Server is up!\n People online: " + minOnline + "/" + maxOnline)
                            .setThumbnail("https://api.mcsrvstat.us/icon/"+ Main.config.get("serverIp"))
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
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + VariableStorage.databaseUpdateAgoD + " ago")
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
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + VariableStorage.databaseUpdateAgoD + " ago")
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
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + VariableStorage.databaseUpdateAgoD + " ago")
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
                                MessageEmbed leaderboardALL = new EmbedBuilder()
                                        .setTitle("Kill Leaderboard")
                                        .setDescription("This is the global top 10. Can you get to #1?")
                                        .addField("#", "```" + VariableStorage.ranks + "```", true)
                                        .addField("Username","```" + playerOutputALL + "```", true)
                                        .addField("Kills", "```" + killOutputALL + "```",true)
                                        .setColor(0x409f99)
                                        .setFooter("Last updated " + VariableStorage.databaseUpdateAgoM + VariableStorage.databaseUpdateAgoMS + VariableStorage.databaseUpdateAgoH + VariableStorage.databaseUpdateAgoS + VariableStorage.databaseUpdateAgoD + " ago")
                                        .setTimestamp(Instant.now())
                                        .build();
                                channel.sendMessageEmbeds(leaderboardALL).submit();
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
                case "profile":
                    if (command.length >= 2) {
                        switch (command[1]) {
                            case "link":
                                if (command.length == 2) {
                                    channel.sendMessageEmbeds(VariableStorage.accountLinkHelp).submit();
                                } else if (command.length == 3) {
                                    String input = command[2];
                                    if (input.length() != 36) {
                                        channel.sendMessage("Bad UUID!").submit();
                                    } else {
                                        String mcUUID = command[2];
                                        String dcUUID = author.getId();
                                        String jdbcstuffAccounts = "jdbc:sqlite:"+ Main.config.get("databaseLocAccounts");
                                        String fetchInfoAccounts =
                                                "INSERT INTO " + Main.config.get("databaseTableAccounts") + "(mcid, iddc)" +
                                                        "VALUES('" + mcUUID + "', '" + dcUUID + "');";
                                        try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
                                             Statement stmt = conn.createStatement()) {
                                            stmt.execute(fetchInfoAccounts);
                                            channel.sendMessage("Account successfully linked!").submit();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                            channel.sendMessage(VariableStorage.pingHerobrine + "\n Error: " + e).submit();
                                        }
                                    }
                                }
                                break;
                            case "show":
                                if (command.length == 2) {
                                    String jdbcstuffAccounts = "jdbc:sqlite:"+ Main.config.get("databaseLocAccounts");
                                    String accountQueryResult = "";
                                    String statsQueryResult = "";
                                    String usernameFromQuery = "";
                                    String dcuid = author.getId();
                                    String accountQuery =
                                        "SELECT mcid" +
                                        " FROM " + Main.config.get("databaseTableAccounts") +
                                        " WHERE iddc = " + dcuid + ";";

                                    try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
                                         Statement stmt = conn.createStatement()) {
                                        ResultSet rs = stmt.executeQuery(accountQuery);
                                        while (rs.next()) {
                                            accountQueryResult = rs.getString("mcid");
                                        }
                                        channel.sendMessage("Got account info...").submit();
                                        String statsQuery =
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableHighrise") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableHighrise") + ".uuid" +
                                            " UNION" +
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableMuseum") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableMuseum") + ".uuid" +
                                            " UNION" +
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableItaly") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableItaly") + ".uuid;";
                                        ResultSet rs2 = stmt.executeQuery(statsQuery);
                                        while (rs2.next()) {
                                            statsQueryResult = rs.getString("killcount");
                                            usernameFromQuery = rs.getString("username");
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        channel.sendMessage(VariableStorage.pingHerobrine + "\n Error: " + e).submit();
                                    }

                                    MessageEmbed resultEmbedUserID = new EmbedBuilder()
                                            .setTitle("Stats for ``" + usernameFromQuery + "``")
                                            .setDescription("\nYou have " + statsQueryResult + " total kills")
                                            .setColor(0xb2ced5)
                                            .setTimestamp(Instant.now())
                                            .build();
                                    channel.sendMessageEmbeds(resultEmbedUserID).submit();

                                } else if (command.length == 3 && command[2].contains("<@")) {
                                    String user = command[2];
                                    int userlength = user.length() - 1;
                                    if (!user.contains("!")) {
                                        user = user.substring(2, userlength);
                                    } else {
                                        user = user.substring(3, userlength);
                                    }
                                    String jdbcstuffAccounts = "jdbc:sqlite:"+ Main.config.get("databaseLocAccounts");
                                    String accountQueryResult = "";
                                    String statsQueryResult = "";
                                    String usernameFromQuery = "";
                                    String accountQuery =
                                        "SELECT mcid" +
                                        " FROM " + Main.config.get("databaseTableAccounts") +
                                        " WHERE iddc = " + user + ";";

                                    try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
                                         Statement stmt = conn.createStatement()) {
                                        ResultSet rs = stmt.executeQuery(accountQuery);
                                        while (rs.next()) {
                                            accountQueryResult = rs.getString("mcid");
                                        }
                                        channel.sendMessage("Got account info...").submit();
                                        String statsQuery =
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableHighrise") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableHighrise") + ".uuid" +
                                            " UNION" +
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableMuseum") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableMuseum") + ".uuid" +
                                            " UNION" +
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableItaly") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableItaly") + ".uuid;";
                                        ResultSet rs2 = stmt.executeQuery(statsQuery);
                                        while (rs2.next()) {
                                            statsQueryResult = rs.getString("killcount");
                                            usernameFromQuery = rs.getString("username");
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        channel.sendMessage("That person doesn't have their account linked!").submit();
                                    }

                                    MessageEmbed resultEmbedUserID = new EmbedBuilder()
                                            .setTitle("Stats for ``" + usernameFromQuery + "``")
                                            .setDescription("\nYou have " + statsQueryResult + " total kills")
                                            .setColor(0xb2ced5)
                                            .setTimestamp(Instant.now())
                                            .build();
                                    channel.sendMessageEmbeds(resultEmbedUserID).submit();

                                } else {

                                    String jdbcstuffAccounts = "jdbc:sqlite:" + Main.config.get("databaseLocAccounts");
                                    String accountQueryResult = "Error!";
                                    String statsQueryResult = "Error!";
                                    String usernameFromQuery = "";
                                    String accountQuery =
                                        "SELECT mcid" +
                                        " FROM " + Main.config.get("databaseTableAccounts") +
                                        " WHERE iddc = " + command[2] + ";";

                                    try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
                                         Statement stmt = conn.createStatement()) {
                                        ResultSet rs = stmt.executeQuery(accountQuery);
                                        while (rs.next()) {
                                            accountQueryResult = rs.getString("mcid");
                                        }
                                        channel.sendMessage("Got account info...").submit();
                                        String statsQuery =
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableHighrise") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableHighrise") + ".uuid" +
                                            " UNION" +
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableMuseum") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableMuseum") + ".uuid" +
                                            " UNION" +
                                            " SELECT username, killcount" +
                                            " FROM " + Main.config.get("databaseTableItaly") +
                                            " WHERE '" + accountQueryResult + "' = " + Main.config.get("databaseTableItaly") + ".uuid;";
                                        ResultSet rs2 = stmt.executeQuery(statsQuery);
                                        while (rs2.next()) {
                                            statsQueryResult = rs.getString("killcount");
                                            usernameFromQuery = rs.getString("username");
                                        }


                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                        channel.sendMessage("That person doesn't have their account linked!").submit();
                                    }
                                    MessageEmbed resultEmbedUserID = new EmbedBuilder()
                                            .setTitle("Stats for ``" + usernameFromQuery + "``")
                                            .setDescription("\nYou have " + statsQueryResult + " total kills")
                                            .setColor(0xb2ced5)
                                            .setTimestamp(Instant.now())
                                            .build();
                                    channel.sendMessageEmbeds(resultEmbedUserID).submit();
                                }
                                break;
                            default:
                                channel.sendMessageEmbeds(VariableStorage.statsHelpEmbed).submit();
                                break;
                        }
                    } else {
                        channel.sendMessageEmbeds(VariableStorage.statsHelpEmbed).submit();
                    }
                    break;
                default:
                    channel.sendMessage("Unknown command!").submit();
                    break;
            }
        }
    }
}