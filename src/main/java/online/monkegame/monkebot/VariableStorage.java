/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.io.File;
import java.time.Instant;

public class VariableStorage {

    public VariableStorage() {}

    String pingHerobrine = "<@338608021791965204>";

    MessageEmbed helpEmbed = new EmbedBuilder()
            .setTitle("Command List")
            .addField("ping", "- shows the ping between the bot and you", false)
            .addField("ip", "- shows you the server IP", false)
            .addField("ranks", "- shows you what ranks there are on the server", false)
            .addField("status", "- shows you the server status.", false)
            .addField("donate", "TBA", false)
            .addField("gay", "- it is a mystery", false)
            .addField("leaderboard", "- gets the top 10 players with the most kills.", false)
            .addField("profile", "- gets your stats from the server so you can see them, without having to be in the top10!", false)
            .addField("game", "- the number game, very cool, try it out!", false)
            .setDescription("Thanks for using monkebot. The prefix is ``m!``\nExample: ``m!<command>``")
            .setColor(0x00ff75)
            .build();

    MessageEmbed onkebotEmbed = new EmbedBuilder()
            .setTitle("monkebot")
            .setDescription("made by mrsherobrine (naomi)#6263")
            .setThumbnail("https://avatars.githubusercontent.com/u/86428052?s=200&v=4")
            .setFooter("monkebot, always broken but works as intended.")
            .setColor(0x5985a4)
            .build();

    MessageEmbed offlineEmbed = new EmbedBuilder()
            .setTitle("Server Status")
            .setColor(0xf03737)
            .setDescription("\n\uD83D\uDD34\n - Server offline! Check back later.")
            .setThumbnail("https://api.mcsrvstat.us/icon/" + Main.config.get("serverIp"))
            .build();

    String ranks = "1\u265B\n2<\n3-\n4\n5\n6\n7\n8\n9\n10";

    MessageEmbed ipEmbed = new EmbedBuilder()
            .setTitle("Server IP")
            .setDescription("The server's IP is ``" + Main.config.get("serverIp") + "``")
            .setColor(0x63A1E9)
            .build();

    MessageEmbed leaderboardMapList = new EmbedBuilder()
            .setTitle("Maps you can choose from")
            .setDescription("map leaderboards: ``m!leaderboard -m <map>``")
            .addField("Italy", "*the most fancy map*\nspecify as ``italy``", false)
            .addField("Museum", "*where you can find the best art*\nspecify as ``museum``", false)
            .addField("Highrise", "*work work work repeat*\nspecify as ``highrise``", false)
            .setColor(0x5985a4)
            .build();

    MessageEmbed leaderboardFlags = new EmbedBuilder()
            .setTitle("This command requires flags!")
            .addField("``-m``", "map selector", false)
            .addField("``-a``", "shows global leaderboard", false)
            .setColor(0x5985a4)
            .build();

    EmbedBuilder gayEmbed = new EmbedBuilder()
            .setImage("https://media1.tenor.com/images/7bf4ac6933e53b18dc74342fa5b9284e/tenor.gif?itemid=4982350")
            .setColor(0x9400D3);

    String databaseUpdateAgoH = "";
    String databaseUpdateAgoMS = "";
    String databaseUpdateAgoM = "";
    String databaseUpdateAgoS = "";
    String databaseUpdateAgoD = "";

     void modificationItaly (String configEntry) {
        File database = new File((String) Main.config.get(configEntry));
        long lastMod = (Instant.now().toEpochMilli() - database.lastModified());
        lastMod = lastMod / 1000;
        if (lastMod <= 599 && lastMod >= 60) {
            long lastmod2 = lastMod / 60;
            long lastmod1 = lastMod % 60;
            databaseUpdateAgoMS = lastmod1 + "m" + lastmod2 + "s";
        } else if (lastMod >= 599 && lastMod <= 3600) {
            long lastmod3 = (lastMod / 60) % 60;
            databaseUpdateAgoM = lastmod3 + "m";
        } else if (lastMod >= 3600 && lastMod <= 86399) {
            long lastmod4 = lastMod / 60 / 60;
            databaseUpdateAgoH = lastmod4 + "h";
        } else if (lastMod <= 59) {
            databaseUpdateAgoS = lastMod + "s";
        } else {
            long lastmod5 = lastMod / 86400;
            databaseUpdateAgoD = lastmod5 + "d";
            System.out.println("[monkebot] database last updated over a day ago! do something!!!");
        }
    }

    void modificationMuseum (String configEntry) {
        File database = new File((String) Main.config.get(configEntry));
        long lastMod = (Instant.now().toEpochMilli() - database.lastModified());
        lastMod = lastMod / 1000;
        if (lastMod <= 600 && lastMod >= 60) {
            long lastmod2 = lastMod / 60;
            long lastmod1 = lastMod % 60;
            databaseUpdateAgoMS = lastmod1 + "m" + lastmod2 + "s";
        } else if (lastMod >= 600 && lastMod <= 3599) {
            long lastmod3 = (lastMod / 60) % 60;
            databaseUpdateAgoM = lastmod3 + "m";
        } else if (lastMod >= 3600 && lastMod <= 86399) {
            long lastmod4 = lastMod / 60 / 60;
            databaseUpdateAgoH = lastmod4 + "h";
        } else if (lastMod <= 60) {
            databaseUpdateAgoS = lastMod + "s";
        } else {
            long lastmod5 = lastMod / 86400;
            databaseUpdateAgoD = lastmod5 + "d";
            System.out.println("[monkebot] database last updated over a day ago! do something!!!");
        }
    }

    void modificationHighrise(String configEntry) {

        File database = new File((String) Main.config.get(configEntry));
        long lastMod = (Instant.now().toEpochMilli() - database.lastModified());
        lastMod = lastMod / 1000;
        if (lastMod <= 599 && lastMod >= 60) {
            long lastmod2 = lastMod / 60;
            long lastmod1 = lastMod % 60;
            databaseUpdateAgoMS = lastmod1 + "m" + lastmod2 + "s";
        } else if (lastMod >= 520 && lastMod <= 3599) {
            long lastmod3 = (lastMod / 60) % 60;
            databaseUpdateAgoM = lastmod3 + "m";
        } else if (lastMod >= 3600 && lastMod <= 86399) {
            long lastmod4 = lastMod / 60 / 60;
            databaseUpdateAgoH = lastmod4 + "h";
        } else if (lastMod <= 60) {
            databaseUpdateAgoS = lastMod + "s";
        } else {
            long lastmod5 = lastMod / 86400;
            databaseUpdateAgoD = lastmod5 + "d";
            System.out.println("[monkebot] database last updated over a day ago! do something!!!");
        }
    }

    MessageEmbed statsHelpEmbed = new EmbedBuilder()
        .setTitle("Arguments for m!stats")
        .addField("``link``", "link your mc account to Discord.", false)
        .addField("``show``", "shows your stats", false)
        .setDescription("you can also ping someone or supply their Discord User ID to get their stats")
        .setColor(0x2a9fa0)
        .build();

    MessageEmbed accountLinkHelp = new EmbedBuilder()
        .setTitle("Submit your UUID!")
        .setDescription("""
                How do you get your Minecraft UUID?

                1) Go to https://namemc.com
                2) Fill in your username
                3) Copy the top UUID
                4) Run m!profile link with the UUID you just copied
                You should now be able to run m!profile show!""")
        .setColor(0xaf902f)
        .build();

    MessageEmbed gameHelpEmbed = new EmbedBuilder()
            .setTitle("How to play")
            .setDescription("""
                    There's no game going on right now!
                    You have to ping someone else to start the game.
                    Then, you take turns giving a number from 1 to 10.
                    First to get to exactly 100 wins the game.
                    """)
            .setColor(0xaf7af3)
            .build();
}
