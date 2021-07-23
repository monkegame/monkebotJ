/*
 *   copyright 2021
 *   monkegame.online
 *   created mostly by MrsHerobrine (as always)
 */
package online.monkegame.monkebot;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.List;

public class NumberGame {

    public NumberGame(){}

    public boolean gameIsInProgress = false;
    public User a;
    public User b;
    public User c;
    public int counter;

    public void numberGameStart(User author, List<User> pinged, MessageChannel channel) {
        // 2 people
        // count to 100
        // first to get to 100 wins

        a = author;
        b = pinged.get(0);
        if (a != b) {
            gameIsInProgress = true;
            int whoCanStart = (int) (Math.random() * 11);

            if (whoCanStart <= 5) {
                c = a;
            } else {
                c = b;
            }

            User d = c;

            if (d == a) {
                d = b;
            } else {
                d = a;
            }
            channel.sendMessage("Game has been started! " + d.getAsMention() + ", you may start! ``m!g <number>`` or ``m!game <number>``").submit();
        } else if (a.getId().equals(b.getId()) || b.isBot()){
            gameIsInProgress = false;
            channel.sendMessage("Sadly, you can't play a game against yourself or a bot!").submit();
        }
    }

    public void numberGameCore(User author, MessageChannel channel, String[] command) {
        int gameNumber = 0;
        try {
            gameNumber = Integer.parseInt(command[1]);
        } catch (NumberFormatException NFE) {
            channel.sendMessage("bad number! try again!").submit();
        }
        if ((gameNumber > 0 && gameNumber <= 10) && counter <= 99) {
            if (!author.getId().equals(c.getId())) {
                counter = counter + gameNumber;
                c = author;
            } else if (author.getId().equals(c.getId())) {
                channel.sendMessage("Not your turn!").submit();
            }
        } else if (gameNumber > 0 && gameNumber <= 10) {
            channel.sendMessage(c.getAsMention() + " wins! " + author.getAsMention() + " went over 100!").submit();
            gameIsInProgress = false;
            counter = 0;
        } else {
            channel.sendMessage("bad number! try again!").submit();
        }
    }
}
