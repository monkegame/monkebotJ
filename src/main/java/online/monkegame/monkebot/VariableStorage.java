/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

public class VariableStorage {

    public static MessageEmbed helpEmbed = new EmbedBuilder()
            .setTitle("Command List")
            .addField("ping", "- shows the ping between the bot and you", false)
            .addField("ip", "- shows you the server IP", false)
            .addField("ranks", "- shows you what ranks there are on the server", false)
            .addField("status", "- shows you the server status.", false)
            .addField("donate", "TBA", false)
            .addField("gay", "- it is a mystery", false)
            .addField("leaderboard", "- gets the top 10 players with the most kills.", false)
            .setDescription("Thanks for using monkebot. The prefix is ``m!``\nExample: ``m!<command>``")
            .setColor(0x00ff75)
            .build();

    public static MessageEmbed onkebotEmbed = new EmbedBuilder()
            .setTitle("monkebot")
            .setDescription("made by mrsherobrine (naomi)#6263")
            .setThumbnail("https://cdn.discordapp.com/attachments/837040023554752543/842867425295990801/communityIcon_8ftkq9sm4qd61.png")
            .setColor(0x5985a4)
            .build();

    public static MessageEmbed offlineEmbed = new EmbedBuilder()
            .setTitle("Server Status")
            .setColor(0xf03737)
            .setDescription("\n\uD83D\uDD34 Server offline! Check back later.")
            .setThumbnail("https://api.mcsrvstat.us/icon/play.monkegame.online")
            .build();

    public static String ranks ="1\u265B\n2<\n3-\n4\n5\n6\n7\n8\n9\n10";

    public static MessageEmbed ipEmbed = new EmbedBuilder()
            .setTitle("Server IP")
            .setDescription("The server's IP is ``" + Main.config.get("serverIp") + "``")
            .setColor(0x63A1E9)
            .build();

    public static MessageEmbed leaderboardMapList = new EmbedBuilder()
            .setTitle("Maps you can choose from")
            .setDescription("map leaderboards: ``leadboard -m <map>``")
            .addField("Italy", "*the most fancy map*\nspecify as ``italy``", false)
            .addField("Museum", "*where you can find the best art*\nspecify as ``museum``", false)
            .addField("Highrise", "*work work work repeat*\nspecify as ``highrise``", false)
            .setColor(0x5985a4)
            .build();

    public static MessageEmbed leaderboardFlags = new EmbedBuilder()
            .setTitle("This command requires flags!")
            .addField("``-m``", "map selector", false)
            .addField("``-a``", "shows total leaderboard", false)
            .setColor(0x5985a4)
            .build();

    public static EmbedBuilder gayEmbed = new EmbedBuilder()
            .setImage("https://media1.tenor.com/images/7bf4ac6933e53b18dc74342fa5b9284e/tenor.gif?itemid=4982350")
            .setColor(0x9400D3);
}
