package Dev.ScalerGames.BetterChristmas.Rewards;

import Dev.ScalerGames.BetterChristmas.Files.Calendar;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.entity.Player;

import java.util.Objects;

public class RewardSelector {

    public static void grantReward(Player p, Integer day) {
        if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards")) {
            for (String rewardName : Objects.requireNonNull(Calendar.getCalendarConfig().getConfigurationSection("Calendar.layout." + day + ".rewards")).getKeys(false)) {
                if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".type")) {

                    String type = Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".type");
                    assert type != null;

                    //Sends a message to the player
                    if (type.equalsIgnoreCase("msg") || type.equalsIgnoreCase("message")) {
                        if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".message")) {
                            MessageSender.sendMessage(p, Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".message"));
                        } else {
                            Messages.logger("&4There is no message to send to " + p.getName());
                        }
                    }

                    //Sends a title to the player
                    else if (type.equalsIgnoreCase("title")) {
                        //Display a title and subtitle
                        if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".title")
                                && Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".sub-title")) {
                            MessageSender.sendTitle(p, Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".title"), Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".sub-title"));
                        }
                        //Display just a title
                        else if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".title")) {
                            MessageSender.sendTitle(p, Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".title"), null);
                        }
                        //Error message because there is no message to send
                        else {
                            Messages.logger("&4There is no title to send to " + p.getName());
                        }

                    }

                    else if (type.equalsIgnoreCase("bar")) {
                        if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".bar")) {
                            MessageSender.sendActionBar(p, Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".bar"));
                        }
                    }

                    else if (type.equalsIgnoreCase("item")) {
                        if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".item")) {
                            ItemGranter.give(p, "Calendar.layout." + day + ".rewards." + rewardName);
                        }
                    }

                    else if (type.equalsIgnoreCase("player_cmd")) {
                        if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".command")) {
                            CommandSender.playerCMD(p, Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".command")));
                        }
                    }

                    else if (type.equalsIgnoreCase("console_cmd")) {
                        if (Calendar.getCalendarConfig().contains("Calendar.layout." + day + ".rewards." + rewardName + ".command")) {
                            CommandSender.consoleCMD(p, Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + day + ".rewards." + rewardName + ".command")));
                        }
                    }

                }
            }
        }
    }

}
