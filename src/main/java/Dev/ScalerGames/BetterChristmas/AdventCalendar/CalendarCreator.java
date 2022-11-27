package Dev.ScalerGames.BetterChristmas.AdventCalendar;

import Dev.ScalerGames.BetterChristmas.Files.Calendar;
import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CalendarCreator {

    public static void generate(Player p) {
        Inventory inv = Bukkit.createInventory(null, Calendar.getCalendarConfig().getInt("Calendar.size"), Format.placeholder(p, Calendar.getCalendarConfig().getString("Calendar.name")));
        buttons(inv, p);
        calendarDays(inv, p);
        p.openInventory(inv);
    }

    public static void buttons(Inventory inv, Player p) {
        if (Calendar.getCalendarConfig().contains("Calendar.fillers.exit_button")) {
            ItemStack item = ItemHandler.createItem(Calendar.getCalendarConfig().getString("Calendar.fillers.exit_button.item"));
            ItemHandler.addMeta(item, p, "Calendar.fillers.exit_button");
            inv.setItem(Calendar.getCalendarConfig().getInt("Calendar.fillers.exit_button.slot"), item);
        }

        if (Calendar.getCalendarConfig().contains("Calendar.fillers.gap_fill")) {
            Objects.requireNonNull(Calendar.getCalendarConfig().getConfigurationSection("Calendar.fillers.gap_fill")).getKeys(false).forEach(i -> {
                ItemStack item = ItemHandler.createItem(Calendar.getCalendarConfig().getString("Calendar.fillers.gap_fill." + i +".item"));
                ItemHandler.addMeta(item, p, "Calendar.fillers.gap_fill." + i);
                Calendar.getCalendarConfig().getIntegerList("Calendar.fillers.gap_fill." + i + ".slots").forEach(filler -> inv.setItem(filler, item));
            });
        }
    }

    public static void calendarDays(Inventory inv, Player p) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        Date date = new Date(); int day = Integer.parseInt(format.format(date));
        for (int i = 1; i < Calendar.getCalendarConfig().getInt("Calendar.days") + 1; i++) {

            if (Calendar.getCalendarConfig().getBoolean("Calendar.previous-days")) {
                if (Main.getInstance().calendarStorage.containsKey(p.getUniqueId()) && !Main.getInstance().calendarStorage.get(p.getUniqueId()).isEmpty()
                        && Main.getInstance().calendarStorage.get(p.getUniqueId()).contains(i)) {
                    openedItem(inv, i, p);
                }

                else if (i <= day && Calendar.getCalendarConfig().getInt("Calendar.month") == Integer.parseInt(month.format(date))) {
                    availableItem(inv, i, p);
                }

                else {
                    unavailableItem(inv, i, p);
                }
            }
            else {

                if (Main.getInstance().calendarStorage.containsKey(p.getUniqueId()) && !Main.getInstance().calendarStorage.get(p.getUniqueId()).isEmpty()
                        && Main.getInstance().calendarStorage.get(p.getUniqueId()).contains(i)) {
                    openedItem(inv, i, p);
                }

                else if (i == day && Calendar.getCalendarConfig().getInt("Calendar.month") == Integer.parseInt(month.format(date))) {
                    availableItem(inv, i, p);
                }

                else {
                    unavailableItem(inv, i, p);
                }

            }

        }
    }

    public static void availableItem(Inventory inv, int i, Player p) {
        ItemStack item;
        if (Calendar.getCalendarConfig().contains("Calendar.layout." + i + ".available-item.item")) {
            if (Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".available-item.item")).contains("HEAD:")) {
                item = ItemHandler.customHead(Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".available-item.item")));
            } else {
                item = ItemHandler.createItem(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".available-item.item"));
            }
            ItemHandler.addMeta(item, p, "Calendar.layout." + i + ".available-item");
            ItemMeta meta = item.getItemMeta(); Objects.requireNonNull(meta).setCustomModelData(i); item.setItemMeta(meta);
            inv.setItem(inv.firstEmpty(), item);
        }
    }

    public static void unavailableItem(Inventory inv, int i, Player p) {
        ItemStack item;
        if (Calendar.getCalendarConfig().contains("Calendar.layout." + i + ".unavailable-item.item")) {
            if (Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".unavailable-item.item")).contains("HEAD:")) {
                item = ItemHandler.customHead(Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".unavailable-item.item")));
            } else {
                item = ItemHandler.createItem(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".unavailable-item.item"));
            }
            ItemHandler.addMeta(item, p, "Calendar.layout." + i + ".unavailable-item");
            ItemMeta meta = item.getItemMeta(); Objects.requireNonNull(meta).setCustomModelData(i); item.setItemMeta(meta);
            inv.setItem(inv.firstEmpty(), item);
        }
    }

    public static void openedItem(Inventory inv, int i, Player p) {
        ItemStack item;
        if (Calendar.getCalendarConfig().contains("Calendar.layout." + i + ".opened-item.item")) {
            if (Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".opened-item.item")).contains("HEAD:")) {
                item = ItemHandler.customHead(Objects.requireNonNull(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".opened-item.item")));
            } else {
                item = ItemHandler.createItem(Calendar.getCalendarConfig().getString("Calendar.layout." + i + ".opened-item.item"));
            }
            ItemHandler.addMeta(item, p, "Calendar.layout." + i + ".opened-item");
            ItemMeta meta = item.getItemMeta(); Objects.requireNonNull(meta).setCustomModelData(i); item.setItemMeta(meta);
            inv.setItem(inv.firstEmpty(), item);
        }
    }
}
