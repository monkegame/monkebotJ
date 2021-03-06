/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class CommandHandler extends ListenerAdapter {

    private final VariableStorage vs;
    private final Profile profile;
    private final Leaderboard lb;
    public DynamicEmbeds de;
    public NumberGame ng;

    public CommandHandler() {
        this.ng = new NumberGame();
        this.profile = new Profile();
        this.lb = new Leaderboard();
        this.de = new DynamicEmbeds();
        this.vs = new VariableStorage();
    }

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
                    channel.sendMessageEmbeds(vs.helpEmbed).submit();
                    break;
                case "ip":
                    channel.sendMessageEmbeds(vs.ipEmbed).submit();
                    break;
                case "ping":
                    de.pingBot(messageData, channel);
                    break;
                case "gay":
                    de.gay(pinged, jda, author, command, channel);
                    break;
                case "status":
                    de.serverStatus(channel);
                    break;
                case "onkebot":
                    channel.sendMessageEmbeds(vs.onkebotEmbed).submit();
                    break;
                case "leaderboard":
                    if (command.length == 3 && command[1].equals("-m")) {
                        switch (command[2]) {
                            case "italy" -> lb.italyLb(channel);
                            case "museum" -> lb.museumLb(channel);
                            case "highrise" -> lb.highriseLb(channel);
                            default -> {
                                channel.sendMessage("Map not found! Choose from these:").submit();
                                channel.sendMessageEmbeds(vs.leaderboardMapList).submit();
                            }
                        }
                    } else if (command.length == 2) {
                        switch (command[1]) {
                            case "-m" -> channel.sendMessageEmbeds(vs.leaderboardMapList).submit();
                            case "-a" -> lb.allLb(channel);
                            default -> channel.sendMessage("Unknown flag!").submit();
                        }
                    } else if (command.length == 1) {
                        channel.sendMessageEmbeds(vs.leaderboardFlags).submit();
                    }
                    break;
                case "hiddencommandverysecretdebug":
                    channel.sendMessage("how'd you find this lol?").submit();
                    break;
                case "profile":
                    if (command.length >= 2) {
                        switch (command[1]) {
                            case "link":
                                profile.profileLink(author, command, channel);
                                break;
                            case "show":
                                if (command.length == 2) {
                                    profile.profileShowSelf(author, channel);
                                } else if (command.length == 3 && command[2].contains("<@")) {
                                    profile.profileShowPing(command, channel);
                                } else if (command[2].matches("[0-9]\\w")){
                                    profile.profileShowUUID(author, command, channel);
                                }
                                break;
                            default:
                                channel.sendMessageEmbeds(vs.statsHelpEmbed).submit();
                                break;
                        }
                    } else {
                        channel.sendMessageEmbeds(vs.statsHelpEmbed).submit();
                    }
                    break;
                case "g":
                case "game":
                    if (pinged.isEmpty() && !ng.gameIsInProgress) {
                        channel.sendMessageEmbeds(vs.gameHelpEmbed).submit();
                    } else if (!pinged.isEmpty() && !ng.gameIsInProgress) {
                        ng.numberGameStart(author, pinged, channel);
                    } else if (command.length == 2) {
                        if (command[1].contains("cancel") || command[1].contains("stop") || command[1].contains("quit")) {
                            ng.gameIsInProgress = false;
                            ng.counter = 0;
                            channel.sendMessage("Game ended.").submit();
                        } else if (author.getId().equals(ng.a.getId()) || author.getId().equals(ng.b.getId())) {
                            ng.numberGameCore(author, channel, command);
                        } else {
                            channel.sendMessage(author.getAsMention() + ", this isn't your game!").submit();
                        }
                    } else {
                        channel.sendMessage("????").submit();
                    }
                    break;
                default:
                    channel.sendMessage("Unknown command!").submit();
                    break;
            }
        }
    }
}