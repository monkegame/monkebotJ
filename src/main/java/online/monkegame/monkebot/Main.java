/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;


public class Main {

    public static Map config;


    public static void main(String[] args) throws LoginException, InterruptedException, IOException {
        long startTime = Instant.now().toEpochMilli();
        ObjectMapper mapper = new ObjectMapper();

        config = mapper.readValue(new File("config.json"), Map.class);
        System.out.println("[monkebot] Config loaded!");

        String token = (String) config.get("token");

        JDA jda = JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES)
            .setActivity(Activity.playing("with monke"))
            .addEventListeners(new CommandHandler())
            .setMemberCachePolicy(MemberCachePolicy.ONLINE)
            .build()
            .awaitReady();

        System.out.println("                       _        _           _   ");
        System.out.println(" _ __ ___   ___  _ __ | | _____| |__   ___ | |_ ");
        System.out.println("| '_ ` _ \\ / _ \\| '_ \\| |/ / _ \\ '_ \\ / _ \\| __|");
        System.out.println("| | | | | | (_) | | | |   <  __/ |_) | (_) | |_ ");
        System.out.println("|_| |_| |_|\\___/|_| |_|_|\\_\\___|_.__/ \\___/ \\__|");
        System.out.println("------------------------------------------------");
        System.out.println("                                                ");
        System.out.println("              welcome to monkebot               ");
        System.out.println("                It's currently: \n         " + Instant.now());
        System.out.println("            Took " + (Instant.now().toEpochMilli() - startTime) +"ms to fully load!\n ");
        System.out.println("------------------------------------------------\n");

    }
}
