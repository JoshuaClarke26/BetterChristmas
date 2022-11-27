package Dev.ScalerGames.BetterChristmas.GiftBox;

import Dev.ScalerGames.BetterChristmas.AdventCalendar.ItemHandler;
import Dev.ScalerGames.BetterChristmas.Main;
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

public class GiftGenerator {

    //Added In Version 1.1.0 - 26/11/22
    public static ItemStack createGift(Player p, String name) {
        try {
            if (Main.getInstance().getConfig().contains("GiftBox.boxes")) {
                if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name)) {

                    ItemStack item;
                    if (Objects.requireNonNull(Main.getInstance().getConfig().getString("GiftBox.boxes." + name + ".item")).contains("HEAD:")) {
                        item = ItemHandler.customHead(Objects.requireNonNull(Main.getInstance().getConfig().getString("GiftBox.boxes." + name + ".item")));
                    } else {
                        item = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("GiftBox.boxes." + name + ".item")));
                    }

                    ItemMeta meta = item.getItemMeta();
                    if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name + ".display-name")) {
                        Objects.requireNonNull(meta).setDisplayName(Format.placeholder(p, Main.getInstance().getConfig().getString("GiftBox.boxes." + name + ".display-name")));
                    }

                    if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name + ".lore")) {
                        List<String> lore = new ArrayList<>();
                        Main.getInstance().getConfig().getStringList("GiftBox.boxes." + name + ".lore").forEach(line -> lore.add(Format.placeholder(p, line)));
                        Objects.requireNonNull(meta).setLore(lore);
                    }

                    if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name + ".flags")) {
                        Main.getInstance().getConfig().getStringList("GiftBox.boxes." + name + ".flags").forEach(flag -> Objects.requireNonNull(meta).addItemFlags(ItemFlag.valueOf(flag)));
                    }

                    if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name + ".enchants")) {
                        try {
                            Main.getInstance().getConfig().getStringList("GiftBox.boxes." + name + ".enchants").forEach(enchant -> {
                                String[] splitter = enchant.split(":"); String enchantment = splitter[0]; int lvl = Integer.parseInt(splitter[1]);
                                Objects.requireNonNull(meta).addEnchant(Objects.requireNonNull(Enchantment.getByName(enchantment)), lvl, true);
                            });
                        } catch (Exception ex) {
                            Messages.logger("&4Could not add enchant for: " + p.getName() + "'s gift box");
                        }
                    }

                    if (Main.getInstance().getConfig().contains("GiftBox.boxes." + name + ".id")) {
                        if (Objects.requireNonNull(Main.getInstance().getConfig().getString("GiftBox.boxes." + name + ".id")).length() == 4) {
                            Objects.requireNonNull(meta).setCustomModelData(Main.getInstance().getConfig().getInt("GiftBox.boxes." + name + ".id"));
                        } else {
                            Messages.logger("&4The ID of the gift box has to be 4 digits long. The gift box will fail");
                        }
                    } else {
                        Messages.logger("&4The gift box dose not have an ID, it will not work");
                    }
                    item.setItemMeta(meta);
                    return item;

                }
            }
        } catch (Exception ex) {
            Messages.logger("&4Creates of the gift box " + name + " failed");
        }
        return null;
    }

}
