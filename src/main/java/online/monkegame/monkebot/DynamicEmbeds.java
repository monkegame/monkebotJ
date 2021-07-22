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

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static online.monkegame.monkebot.Main.config;

public class DynamicEmbeds {

    public VariableStorage vs;

    public DynamicEmbeds() {
        this.vs = new VariableStorage();
    }

    public void pingBot(Message messageData,MessageChannel channel) {
        String pingTime = "Took " + (Instant.now().toEpochMilli() - messageData.getTimeCreated().toInstant().toEpochMilli()) + "ms";
        MessageEmbed pingEmbed = new EmbedBuilder()
                .setTitle("Pong!")
                .setDescription(pingTime)
                .setColor(0x00ff75)
                .build();
        channel.sendMessageEmbeds(pingEmbed).submit();
    }

    public void serverStatus(MessageChannel channel) {
        Map valueMap = null;
        try {
            URL j = new URL("https://api.mcsrvstat.us/2/" + config.get("serverIp"));
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
                    .setThumbnail("https://api.mcsrvstat.us/icon/"+ config.get("serverIp"))
                    .setTimestamp(Instant.now())
                    .build();
            channel.sendMessageEmbeds(onlineEmbed).submit();
        } else {
            channel.sendMessageEmbeds(vs.offlineEmbed).submit();
        }
    }

    public void gay(List<User> pinged, JDA jda, User author, String[] command, MessageChannel channel) {
        if (command.length != 2) {
            channel.sendMessage("haha <@" + author.getId() + "> gay lol!").submit();
        } else {
            try {
                for (User u : pinged) {
                    User user = jda.retrieveUserById(u.getId()).complete();
                    String userName = user.getName();
                    channel.sendMessageEmbeds(vs.gayEmbed.setTitle(userName + " is gay lol!").build()).submit();
                }
            } catch (NoClassDefFoundError error) {
                System.out.println("that's not a user!");
                channel.sendMessage("can't confirm they're gay, sorry!").submit();
            }
        }
    }
}
