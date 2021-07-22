/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static online.monkegame.monkebot.Main.config;

public class Leaderboard {

    public VariableStorage vs;

    public Leaderboard(){
        this.vs = new VariableStorage();
    }

    public void italyLb(MessageChannel channel) {

        ArrayList<String> playerListItaly = new ArrayList<>();
        ArrayList<String> killListItaly = new ArrayList<>();
        killListItaly.add("");
        playerListItaly.add("");
        String jdbcstuffItaly = "jdbc:sqlite:"+ config.get("databaseLocItaly");
        String fetchInfoItaly =
                "SELECT username, killcount " +
                        "FROM " + config.get("databaseTableItaly") +
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
        vs.modificationItaly();
        MessageEmbed leaderboardItaly = new EmbedBuilder()
                .setTitle("Kill Leaderboard")
                .setDescription("This is the top 10. Can you get to #1?\nMap: ``Italy``")
                .addField("#", "```" + vs.ranks + "```", true)
                .addField("Username","```" + playerOutputItaly + "```", true)
                .addField("Kills", "```" + killOutputItaly + "```",true)
                .setColor(0x5985a4)
                .setTimestamp(Instant.now())
                .setFooter("Last updated " + vs.databaseUpdateAgoM + vs.databaseUpdateAgoMS + vs.databaseUpdateAgoH + vs.databaseUpdateAgoS + vs.databaseUpdateAgoD + " ago")
                .build();
        channel.sendMessageEmbeds(leaderboardItaly).submit();
    }

    public void museumLb(MessageChannel channel) {

        ArrayList<String> playerListMuseum = new ArrayList<>();
        ArrayList<String> killListMuseum = new ArrayList<>();
        killListMuseum.add("");
        playerListMuseum.add("");
        String jdbcstuffMuseum = "jdbc:sqlite:"+ config.get("databaseLocMuseum");
        String fetchInfoMuseum =
                "SELECT username, killcount " +
                        "FROM " + config.get("databaseTableMuseum") +
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
        vs.modificationMuseum();
        MessageEmbed leaderboardMuseum = new EmbedBuilder()
                .setTitle("Kill Leaderboard")
                .setDescription("This is the top 10. Can you get to #1?\nMap: ``Museum``")
                .addField("#", "```" + vs.ranks + "```", true)
                .addField("Username","```" + playerOutputMuseum + "```", true)
                .addField("Kills", "```" + killOutputMuseum + "```",true)
                .setColor(0x5985a4)
                .setFooter("Last updated " + vs.databaseUpdateAgoM + vs.databaseUpdateAgoMS + vs.databaseUpdateAgoH + vs.databaseUpdateAgoS + vs.databaseUpdateAgoD + " ago")
                .setTimestamp(Instant.now())
                .build();
        channel.sendMessageEmbeds(leaderboardMuseum).submit();
    }

    public void highriseLb(MessageChannel channel) {

        ArrayList<String> playerListHighrise = new ArrayList<>();
        ArrayList<String> killListHighrise = new ArrayList<>();
        killListHighrise.add("");
        playerListHighrise.add("");
        String jdbcstuffHighrise = "jdbc:sqlite:"+ config.get("databaseLocHighrise");
        String fetchInfoHighrise =
                "SELECT username, killcount " +
                        "FROM " + config.get("databaseTableHighrise") +
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
        vs.modificationHighrise();
        MessageEmbed leaderboardHighrise = new EmbedBuilder()
                .setTitle("Kill Leaderboard")
                .setDescription("This is the top 10. Can you get to #1?\nMap: ``Highrise``")
                .addField("#", "```" + vs.ranks + "```", true)
                .addField("Username","```" + playerOutputHighrise + "```", true)
                .addField("Kills", "```" + killOutputHighrise + "```",true)
                .setColor(0x5985a4)
                .setFooter("Last updated " + vs.databaseUpdateAgoM + vs.databaseUpdateAgoMS + vs.databaseUpdateAgoH + vs.databaseUpdateAgoS + vs.databaseUpdateAgoD + " ago")
                .setTimestamp(Instant.now())
                .build();
        channel.sendMessageEmbeds(leaderboardHighrise).submit();
    }

    public void allLb(MessageChannel channel) {

        ArrayList<String> playerListAll = new ArrayList<>();
        ArrayList<Integer> killListAll = new ArrayList<>();
        killListAll.add(0);
        playerListAll.add("");
        String jdbcstuffHighrise = "jdbc:sqlite:"+ config.get("databaseLocHighrise");
        String fetchInfoAll =
                "SELECT DISTINCT username, SUM(killcount) AS killcount\n" +
                        "FROM (\n" +
                        "SELECT username, killcount\n" +
                        "FROM italy\n" +
                        "UNION \n" +
                        "SELECT username,killcount\n" +
                        "FROM museum\n" +
                        "UNION\n" +
                        "SELECT username,killcount\n" +
                        "FROM highrise\n" +
                        ")\n" +
                        "GROUP BY username\n" +
                        "ORDER BY killcount DESC\n" +
                        "LIMIT 10";

        try (Connection conn = DriverManager.getConnection(jdbcstuffHighrise);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(fetchInfoAll);
            while (rs.next()) {
                playerListAll.add(rs.getString("username"));
                killListAll.add(rs.getInt("killcount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String playerOutputALL = playerListAll.stream().map(Object::toString).collect(Collectors.joining("\n"));
        String killOutputALL = killListAll.stream().map(Object::toString).collect(Collectors.joining("\n"));
        vs.modificationHighrise();
        MessageEmbed leaderboardALL = new EmbedBuilder()
                .setTitle("Kill Leaderboard")
                .setDescription("This is the global top 10. Can you get to #1?")
                .addField("#", "```" + vs.ranks + "```", true)
                .addField("Username","```" + playerOutputALL + "```", true)
                .addField("Kills", "```" + killOutputALL + "```",true)
                .setColor(0x409f99)
                .setFooter("Last updated " + vs.databaseUpdateAgoM + vs.databaseUpdateAgoMS + vs.databaseUpdateAgoH + vs.databaseUpdateAgoS + vs.databaseUpdateAgoD + " ago")
                .setTimestamp(Instant.now())
                .build();
        channel.sendMessageEmbeds(leaderboardALL).submit();
    }
}
