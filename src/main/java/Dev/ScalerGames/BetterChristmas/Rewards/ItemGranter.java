package Dev.ScalerGames.BetterChristmas.Rewards;

import Dev.ScalerGames.BetterChristmas.Files.Calendar;
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

public class ItemGranter {

    public static void give(Player p, String path) {
        try {
            ItemStack item;
            if (Calendar.getCalendarConfig().contains(path + ".quantity")) {
                item = new ItemStack(Material.valueOf(Calendar.getCalendarConfig().getString(path + ".item")), Calendar.getCalendarConfig().getInt(path + ".quantity"));
            } else {
                item = new ItemStack(Material.valueOf(Calendar.getCalendarConfig().getString(path + ".item")));
            }
            ItemMeta meta = item.getItemMeta();

            if (Calendar.getCalendarConfig().contains(path + ".display-name")) {
                Objects.requireNonNull(meta).setDisplayName(Format.placeholder(p, Calendar.getCalendarConfig().getString(path + ".display-name")));
            }

            if (Calendar.getCalendarConfig().contains(path + ".lore")) {
                List<String> lore = new ArrayList<>();
                Calendar.getCalendarConfig().getStringList(path + ".lore").forEach(line -> lore.add(Format.placeholder(p, line)));
                Objects.requireNonNull(meta).setLore(lore);
            }

            if (Calendar.getCalendarConfig().contains(path + ".flags")) {
                Calendar.getCalendarConfig().getStringList(path + ".flags").forEach(line -> Objects.requireNonNull(meta).addItemFlags(ItemFlag.valueOf(line)));
            }

            if (Calendar.getCalendarConfig().contains(path + ".enchants")) {
                try {
                    Calendar.getCalendarConfig().getStringList(path + ".enchants").forEach(enchant -> {
                        String[] splitter = enchant.split(":"); String enchantment = splitter[0]; int lvl = Integer.parseInt(splitter[1]);
                        Objects.requireNonNull(meta).addEnchant(Objects.requireNonNull(Enchantment.getByName(enchantment)), lvl, true);
                    });
                } catch (Exception ex) {
                    Messages.logger("&4Could not add enchant for: " + p.getName());
                }
            }

            item.setItemMeta(meta);
            p.getInventory().addItem(item);


        } catch (Exception ex) {
            Messages.logger("&4Could not create item for: " + p.getName());
        }
    }

}
