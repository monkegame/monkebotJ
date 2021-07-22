/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;

import java.sql.*;
import java.time.Instant;

import static online.monkegame.monkebot.Main.config;

public class Profile {

    private final VariableStorage vs;

    public Profile(){
        this.vs = new VariableStorage();
    }

    public void profileLink(User author, String[] command, MessageChannel channel) {
        if (command.length == 2) {
            channel.sendMessageEmbeds(vs.accountLinkHelp).submit();
        } else if (command.length == 3) {
            String input = command[2];
            if (input.length() != 36 && !input.matches("[a-z0-9]\\w-[a-z0-9]\\w-[a-z0-9]\\w-[a-z0-9]\\w-[a-z0-9]\\w")) {
                channel.sendMessage("Bad UUID!").submit();
            } else {
                String mcUUID = command[2];
                String dcUUID = author.getId();
                String jdbcstuffAccounts = "jdbc:sqlite:" + config.get("databaseLocAccounts");
                try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + config.get("databaseTableAccounts") + "(mcid, iddc) VALUES(?, ?);")) {
                    stmt.setString(1, mcUUID);
                    stmt.setString(2, dcUUID);
                    stmt.executeQuery();
                    channel.sendMessage("Account successfully linked!").submit();
                } catch (SQLException e) {
                    e.printStackTrace();
                    channel.sendMessage(vs.pingHerobrine + "\n Error: " + e).submit();
                }
            }
        }
    }

    public void profileShowUUID(User author, String[] command, MessageChannel channel) {
        String jdbcstuffAccounts = "jdbc:sqlite:" + config.get("databaseLocAccounts");
        String accountQueryResult = "Error!";
        String statsQueryResult = "Error!";
        String usernameFromQuery = "";
        String accountQuery =
                "SELECT mcid" +
                        " FROM " + config.get("databaseTableAccounts") +
                        " WHERE iddc = " + command[2] + ";";

        try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(accountQuery);
            while (rs.next()) {
                accountQueryResult = rs.getString("mcid");
            }
            String statsQuery =
                    " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableHighrise") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableHighrise") + ".uuid" +
                            " UNION" +
                            " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableMuseum") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableMuseum") + ".uuid" +
                            " UNION" +
                            " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableItaly") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableItaly") + ".uuid;";
            ResultSet rs2 = stmt.executeQuery(statsQuery);
            while (rs2.next()) {
                statsQueryResult = rs.getString("killcount");
                usernameFromQuery = rs.getString("username");
                MessageEmbed resultEmbedUserID = new EmbedBuilder()
                        .setTitle("Stats for ``" + usernameFromQuery + "``")
                        .setDescription("\nYou have " + statsQueryResult + " total kills")
                        .setColor(0xb2ced5)
                        .setTimestamp(Instant.now())
                        .setThumbnail(author.getAvatarUrl())
                        .build();
                channel.sendMessageEmbeds(resultEmbedUserID).submit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            channel.sendMessage("That person doesn't have their account linked!").submit();
        }
    }

    public void profileShowPing(User author, String[] command, MessageChannel channel) {
        String user = command[2];
        int userlength = user.length() - 1;
        if (!user.contains("!")) {
            user = user.substring(2, userlength);
        } else {
            user = user.substring(3, userlength);
        }
        String jdbcstuffAccounts = "jdbc:sqlite:" + config.get("databaseLocAccounts");
        String accountQueryResult = "";
        String statsQueryResult = "";
        String usernameFromQuery = "";
        String accountQuery =
                "SELECT mcid" +
                        " FROM " + config.get("databaseTableAccounts") +
                        " WHERE iddc = " + user + ";";

        try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(accountQuery);
            while (rs.next()) {
                accountQueryResult = rs.getString("mcid");
            }
            String statsQuery =
                    " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableHighrise") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableHighrise") + ".uuid" +
                            " UNION" +
                            " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableMuseum") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableMuseum") + ".uuid" +
                            " UNION" +
                            " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableItaly") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableItaly") + ".uuid;";
            ResultSet rs2 = stmt.executeQuery(statsQuery);
            while (rs2.next()) {
                statsQueryResult = rs.getString("killcount");
                usernameFromQuery = rs.getString("username");
                MessageEmbed resultEmbedUserPing = new EmbedBuilder()
                        .setTitle("Stats for ``" + usernameFromQuery + "``")
                        .setDescription("\nYou have " + statsQueryResult + " total kills")
                        .setColor(0xb2ced5)
                        .setTimestamp(Instant.now())
                        .build();
                channel.sendMessageEmbeds(resultEmbedUserPing).submit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            channel.sendMessage("That person doesn't have their account linked!").submit();
        }
    }

    public void profileShowSelf(User author, String[] command, MessageChannel channel) {
        String jdbcstuffAccounts = "jdbc:sqlite:"+ config.get("databaseLocAccounts");
        String accountQueryResult = "";
        int statsQueryResult = 0;
        String usernameFromQuery = "";
        String dcuid = author.getId();
        String accountQuery =
                "SELECT mcid" +
                        " FROM " + config.get("databaseTableAccounts") +
                        " WHERE iddc = " + dcuid + ";";

        try (Connection conn = DriverManager.getConnection(jdbcstuffAccounts);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(accountQuery);
            while (rs.next()) {
                accountQueryResult = rs.getString("mcid");
            }
            String statsQuery =
                    " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableHighrise") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableHighrise") + ".uuid" +
                            " UNION" +
                            " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableMuseum") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableMuseum") + ".uuid" +
                            " UNION" +
                            " SELECT username, killcount" +
                            " FROM " + config.get("databaseTableItaly") +
                            " WHERE '" + accountQueryResult + "' = " + config.get("databaseTableItaly") + ".uuid;";
            ResultSet rs2 = stmt.executeQuery(statsQuery);
            while (rs2.next()) {
                statsQueryResult = statsQueryResult + rs.getInt("killcount");
                usernameFromQuery = rs.getString("username");

            }
            MessageEmbed resultEmbedSelf = new EmbedBuilder()
                    .setTitle("Stats for ``" + usernameFromQuery + "``")
                    .setDescription("\nYou have " + statsQueryResult + " total kills")
                    .setColor(0xb2ced5)
                    .setTimestamp(Instant.now())
                    .build();
            channel.sendMessageEmbeds(resultEmbedSelf).submit();
        } catch (SQLException e) {
            e.printStackTrace();
            channel.sendMessage(vs.pingHerobrine + "\n Error: " + e).submit();
        }
    }
}
