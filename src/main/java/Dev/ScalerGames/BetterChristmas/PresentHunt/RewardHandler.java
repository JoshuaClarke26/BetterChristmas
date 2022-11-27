package Dev.ScalerGames.BetterChristmas.PresentHunt;

import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Rewards.CommandSender;
import Dev.ScalerGames.BetterChristmas.Rewards.MessageSender;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RewardHandler {

    public static void grantReward(Player p, String path) {
        if (Main.getInstance().getConfig().contains(path)) {
            //Give Item
            if (Main.getInstance().getConfig().contains(path + ".items")) {
                Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection(path + ".items")).getKeys(false).forEach(item -> RewardHandler.itemGranter(p, path + ".items." + item));
            }

            //Send Message
            if (Main.getInstance().getConfig().contains(path + ".message")) {
                MessageSender.sendMessage(p, Main.getInstance().getConfig().getString(path + ".message"));
            }

            if (Main.getInstance().getConfig().contains(path + ".title")
                    && Main.getInstance().getConfig().contains(path + ".sub-title")) {
                MessageSender.sendTitle(p, Main.getInstance().getConfig().getString(path + ".title"),
                        Main.getInstance().getConfig().getString(path + ".sub-title"));
            }

            if (Main.getInstance().getConfig().contains(path + ".title")) {
                MessageSender.sendTitle(p, Main.getInstance().getConfig().getString(path + ".title"), null);
            }

            if (Main.getInstance().getConfig().contains(path + ".bar")) {
                MessageSender.sendActionBar(p, Main.getInstance().getConfig().getString(path + ".bar"));
            }

            if (Main.getInstance().getConfig().contains(path + ".player-cmd")) {
                if (Main.getInstance().getConfig().isString(path + ".player-cmd")) {
                    CommandSender.playerCMD(p, Objects.requireNonNull(Main.getInstance().getConfig().getString(path + ".player-cmd")));
                }
                else if (Main.getInstance().getConfig().isList(path + ".player-cmd")) {
                    Main.getInstance().getConfig().getStringList(path + ".player-cmd").forEach(cmd -> CommandSender.playerCMD(p, cmd));
                }
                else {
                    Messages.logger("&cInvalid format for the present hunt reward command");
                }
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
    public static void itemGranter(Player p, String path) {
        try {
            ItemStack item;
            if (Main.getInstance().getConfig().contains(path + ".quantity")) {
                item = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString(path + ".item")), Main.getInstance().getConfig().getInt(path + ".quantity"));
            } else {
                item = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString(path + ".item")));
            }

            ItemMeta meta = item.getItemMeta();
            if (Main.getInstance().getConfig().contains(path + ".display-name")) {
                Objects.requireNonNull(meta).setDisplayName(Format.placeholder(p, Main.getInstance().getConfig().getString(path + ".display-name")));
            }

            if (Main.getInstance().getConfig().contains(path + ".lore")) {
                List<String> lore = new ArrayList<>();
                Main.getInstance().getConfig().getStringList(path + ".lore").forEach(line -> lore.add(Format.placeholder(p, line)));
                Objects.requireNonNull(meta).setLore(lore);
            }

            if (Main.getInstance().getConfig().contains(path + ".flags")) {
                Main.getInstance().getConfig().getStringList(path + ".flags").forEach(line -> Objects.requireNonNull(meta).addItemFlags(ItemFlag.valueOf(line)));
            }

            if (Main.getInstance().getConfig().contains(path + ".enchants")) {
                try {
                    Main.getInstance().getConfig().getStringList(path + ".enchants").forEach(enchant -> {
                        String[] splitter = enchant.split(":"); String enchantment = splitter[0]; int lvl = Integer.parseInt(splitter[1]);
                        Objects.requireNonNull(meta).addEnchant(Objects.requireNonNull(Enchantment.getByName(enchantment)), lvl, true);
                    });
                } catch (Exception ex) {
                    Messages.logger("&4Could not add enchant for: " + p.getName() + " PresentHunt reward");
                }
            }

            item.setItemMeta(meta);
            p.getInventory().addItem(item);

        } catch (Exception ex) {
            Messages.logger("&4Failed to grant " + p.getName() + " there present hunt reward");
        }
    }

}
