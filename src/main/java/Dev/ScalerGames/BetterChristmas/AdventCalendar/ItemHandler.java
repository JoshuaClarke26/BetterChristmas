package Dev.ScalerGames.BetterChristmas.AdventCalendar;

import Dev.ScalerGames.BetterChristmas.Files.Calendar;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemHandler {

    public static ItemStack createItem(String material) {
        try {
            return new ItemStack(Material.valueOf(material));
        } catch (Exception e) {
            Messages.logger("&4Failed to create an item: " + material);
        }
        return new ItemStack(Material.BARRIER);
    }

    public static ItemStack customHead(String data) {
        String[] split = data.split(":"); String type = split[1];
        String id="";
        for (HeadStorage head : HeadStorage.values()) {
            if (head.getIdentifier().equals(type.toLowerCase())) {
                id=head.getID();
            }
        }
        if (id.equals("")) {
            id=type;
        }
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        head = Bukkit.getUnsafe().modifyItemStack(head, "{display:{Name:\"{\\\"text\\\":\\\"BetterChristmas\\\"}\"},SkullOwner:{Id:[" + "I;1201296705,1414024019,-1385893868,1321399054" + "],Properties:{textures:[{Value:\"" + id + "\"}]}}}");
        return head;
    }

    public static void addMeta(ItemStack item, Player p, String section) {
        if (Calendar.getCalendarConfig().contains(section + ".name")) {
            displayName(item, p, Calendar.getCalendarConfig().getString(section + ".name"));
        }
        if (Calendar.getCalendarConfig().contains(section + ".lore")) {
            lore(item, p, Calendar.getCalendarConfig().getStringList(section + ".lore"));
        }
        if (Calendar.getCalendarConfig().contains(section + ".enchants")) {
            enchantments(item, p, Calendar.getCalendarConfig().getStringList(section +".enchants"));
        }
        if (Calendar.getCalendarConfig().contains(section + ".flags")) {
            flags(item, p, Calendar.getCalendarConfig().getStringList(section + ".flags"));
        }
    }

    public static void displayName(ItemStack item, Player p, String display_name) {
        try {
            ItemMeta meta = item.getItemMeta();
            Objects.requireNonNull(meta).setDisplayName(Format.placeholder(p, display_name));
            item.setItemMeta(meta);
        } catch (Exception ex) {
            Messages.logger("&4Failed to set the display name on " + item.getType() + " in " + p + "'s calendar");
        }
    }

    public static void lore(ItemStack item, Player p, List<String> lore) {
        try {
            ItemMeta meta = item.getItemMeta();
            List<String> lore_list = new ArrayList<>();
            lore.forEach(line -> lore_list.add(Format.placeholder(p, line)));
            Objects.requireNonNull(meta).setLore(lore_list);
            item.setItemMeta(meta);
        } catch (Exception ex) {
            Messages.logger("&4Failed to set the lore on " + item.getType() + " in " + p + "'s calendar");
        }
    }

    public static void enchantments(ItemStack item, Player p, List<String> enchants) {
        try {
            ItemMeta meta = item.getItemMeta();
            enchants.forEach(enchant -> {
                String[] splitter = enchant.split(":"); String type = splitter[0]; int lvl = Integer.parseInt(splitter[1]);
                Objects.requireNonNull(meta).addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(type))), lvl, true);
            });
        } catch (Exception ex) {
            Messages.logger("&4Failed to set enchants on " + item.getType() + " in " + p + "'s calendar");
        }
    }

    public static void flags(ItemStack item, Player p, List<String> flags) {
        try {
            ItemMeta meta = item.getItemMeta();
            flags.forEach(flag -> Objects.requireNonNull(meta).addItemFlags(ItemFlag.valueOf(flag)));
        } catch (Exception ex) {
            Messages.logger("&4Failed to set enchants on " + item.getType() + " in " + p + "'s calendar");
        }
    }

}
