package Dev.ScalerGames.BetterChristmas.GiftBox;

import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.PresentHunt.RewardHandler;
import Dev.ScalerGames.BetterChristmas.Rewards.CommandSender;
import Dev.ScalerGames.BetterChristmas.Rewards.MessageSender;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GiftRewards {

    public static void giveReward(Player p, String name) {
        if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name + ".rewards")) {
            List<String> rewards = Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("GiftBox.boxes." + name + ".rewards")).getKeys(false).stream().toList();
            Random num = new Random(); int selected = num.nextInt(Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("GiftBox.boxes." + name + ".rewards")).getKeys(false).size());
            String reward = rewards.get(selected);
            if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name + ".rewards." + reward)) {
                String path = "GiftBox.boxes." + name + ".rewards." + reward;

                if (Main.getInstance().getConfig().contains(path + ".items")) {
                    Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection(path + ".items")).getKeys(false).forEach(item -> RewardHandler.itemGranter(p, path + ".items." + item));
                }

                if (Main.getInstance().getConfig().contains(path + ".message")) {
                    MessageSender.sendMessage(p, Main.getInstance().getConfig().getString(path + ".message"));
                }

                if (Main.getInstance().getConfig().contains(path + ".console-cmd")) {
                    if (Main.getInstance().getConfig().isString(path + ".console-cmd")) {
                        CommandSender.consoleCMD(p, Objects.requireNonNull(Main.getInstance().getConfig().getString(path + ".console-cmd")));
                    }
                    else if (Main.getInstance().getConfig().isList(path + ".console-cmd")) {
                        Main.getInstance().getConfig().getStringList(path + ".console-cmd").forEach(cmd -> CommandSender.consoleCMD(p, cmd));
                    }
                    else {
                        Messages.logger("&cInvalid format for the present hunt reward commands");
                    }
                }

            }
        }
    }

}
