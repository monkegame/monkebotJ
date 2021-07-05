package online.monkegame.monkebot;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandHandler extends ListenerAdapter {

    MessageEmbed helpEmbed = new EmbedBuilder()
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

    MessageEmbed offlineEmbed = new EmbedBuilder()
        .setTitle("Server Status")
        .setColor(0xf03737)
        .setDescription("\n\uD83D\uDD34 Server offline! Check back later.")
        .setThumbnail("https://api.mcsrvstat.us/icon/play.monkegame.online")
        .build();

    MessageEmbed onkebotEmbed = new EmbedBuilder()
        .setTitle("monkebot")
        .setDescription("made by mrsherobrine (naomi)#6263")
        .setThumbnail("https://cdn.discordapp.com/attachments/837040023554752543/842867425295990801/communityIcon_8ftkq9sm4qd61.png")
        .setColor(0x5985a4)
        .build();

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
            System.out.println("[monkebot] "+ author.getName() + " used command: m!" + Arrays.toString(command));
            // TODO fix it doing stuff like [command, -argument]
            switch (command[0]) {
                case "":
                    channel.sendMessage("You haven't specified a command!").submit();
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
                        channel.sendMessage("haha u gay lol!").submit();
                    } else {
                        List<User> pinged = messageData.getMentionedUsers();
                        for (User u : pinged) {
                            User user = jda.retrieveUserById(u.getId()).complete();
                            String userName = user.getName();
                            String tag = user.getDiscriminator();
                            channel.sendMessage(userName + "#" + tag + " is gay lol!").submit();
                        }
                    }
                    break;
                case "help":
                    channel.sendMessageEmbeds(helpEmbed).submit();
                    break;
                case "status":
                    Map valueMap = null;
                    try {
                        URL j = new URL("https://api.mcsrvstat.us/2/play.monkegame.online");
                        valueMap = mapper.readValue(j, Map.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if ((Boolean) valueMap.get("online") == true) {
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
                        channel.sendMessageEmbeds(offlineEmbed).submit();
                    }
                    break;
                case "onkebot":
                    channel.sendMessageEmbeds(onkebotEmbed).submit();
                    break;
                case "argstest":
                    if (command.length == 2) {
                        switch (command[1]) {
                            case "-italy":
                                channel.sendMessage("TODO leaderboard stuff for italy").submit();
                                channel.sendMessage((String) Main.config.get("databaseLocItaly")).submit();
                                break;
                            case "-museum":
                                channel.sendMessage("TODO leaderboard stuff for museum").submit();
                                break;
                            case "-highrise":
                                channel.sendMessage("TODO leaderboard stuff for highrise").submit();
                                break;
                            default:
                                channel.sendMessage("you should actually like, specify a map").submit();
                        }
                    } else {
                        channel.sendMessage("nothing happened cuz you didn't do anything").submit();
                    }
                    break;
                case "leaderboard":
                    // TODO: do database stuff or else!!!!!!
                    MessageEmbed leaderboard = new EmbedBuilder()
                            .setTitle("Kill Leaderboard")
                            .setDescription("This is the top 10. Can you get to #1?")
                            .addField("#", "```test```", true)
                            .addField("Username","```test```", true)
                            .addField("Kills", "```test```",true)
                            .setColor(0x5985a4)
                            .setFooter("test")
                            .setTimestamp(Instant.now())
                            .build();
                    channel.sendMessageEmbeds(leaderboard).submit();
                    channel.sendMessage("test").submit();
                    break;
                default:
                    channel.sendMessage("Unknown command!").submit();
                    break;
            }
        } else {
            return;
        }
    }
}