package online.monkegame.monkebot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.time.Instant;


public class Main {


    public static void main(String[] args) throws LoginException, InterruptedException {

        JDA jda = JDABuilder.createLight("ODM4NDk1OTcwNzYwMTk2MTQ3.YI78OQ.0-SbcFzy4zmpYkRcCv-usxN2DEY", GatewayIntent.GUILD_MESSAGES)
            .setActivity(Activity.playing("with monke"))
            .addEventListeners(new Listeners())
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
    }
}
