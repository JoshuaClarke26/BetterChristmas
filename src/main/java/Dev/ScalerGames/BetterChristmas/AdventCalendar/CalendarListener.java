package Dev.ScalerGames.BetterChristmas.AdventCalendar;

import Dev.ScalerGames.BetterChristmas.Files.Calendar;
import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Rewards.RewardSelector;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.SimpleDateFormat;
import java.util.*;

public class CalendarListener implements Listener {

    @EventHandler
    public void onCalendarInteract(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(Format.placeholder((Player) e.getWhoClicked(), Calendar.getCalendarConfig().getString("Calendar.name")))) {
            if (e.getCurrentItem() != null) {
                e.setCancelled(true);

                //Exit Button
                if (Calendar.getCalendarConfig().contains("Calendar.fillers.exit_button.slot") && e.getSlot() == Calendar.getCalendarConfig().getInt("Calendar.fillers.exit_button.slot")) {
                    e.getWhoClicked().closeInventory();
                }

                if (e.getCurrentItem().hasItemMeta() && Objects.requireNonNull(e.getCurrentItem().getItemMeta()).hasCustomModelData()) {
                    Date date = new Date(); SimpleDateFormat format = new SimpleDateFormat("dd"); int day = Integer.parseInt(format.format(date));
                    SimpleDateFormat month = new SimpleDateFormat("MM");
                    //Check if it's the current day
                    if (Calendar.getCalendarConfig().getBoolean("Calendar.previous-days")) {
                        if (e.getCurrentItem().getItemMeta().getCustomModelData() <= day && Calendar.getCalendarConfig().getInt("Calendar.month") == Integer.parseInt(month.format(date))) {
                            if (Main.getInstance().calendarStorage.containsKey(e.getWhoClicked().getUniqueId()) && Main.getInstance().calendarStorage.get(e.getWhoClicked().getUniqueId()).contains(e.getCurrentItem().getItemMeta().getCustomModelData())) {
                                Messages.prefix(e.getWhoClicked(), Lang.getLangConfig().getString("reward-opened"));
                            }
                            //give the reward
                            else {
                                //Add code to give reward
                                Messages.prefix(e.getWhoClicked(), Lang.getLangConfig().getString("reward-claimed"));
                                RewardSelector.grantReward((Player) e.getWhoClicked(), e.getCurrentItem().getItemMeta().getCustomModelData());
                                if (Main.getInstance().calendarStorage.containsKey(e.getWhoClicked().getUniqueId())) {
                                    Main.getInstance().calendarStorage.get(e.getWhoClicked().getUniqueId()).add(e.getCurrentItem().getItemMeta().getCustomModelData());
                                } else {
                                    List<Integer> list = new ArrayList<>(); list.add(e.getCurrentItem().getItemMeta().getCustomModelData());
                                    Main.getInstance().calendarStorage.put(e.getWhoClicked().getUniqueId(), list);
                                }
                                e.getWhoClicked().closeInventory();
                            }
                        } else {
                            Messages.prefix(e.getWhoClicked(), Lang.getLangConfig().getString("reward-unavailable"));
                        }
                    } else {
                        if (e.getCurrentItem().getItemMeta().getCustomModelData() == day && Calendar.getCalendarConfig().getInt("Calendar.month") == Integer.parseInt(month.format(date))) {
                            //if reward has been claimed
                            if (Main.getInstance().calendarStorage.containsKey(e.getWhoClicked().getUniqueId()) && Main.getInstance().calendarStorage.get(e.getWhoClicked().getUniqueId()).contains(e.getCurrentItem().getItemMeta().getCustomModelData())) {
                                Messages.prefix(e.getWhoClicked(), Lang.getLangConfig().getString("reward-opened"));
                            }
                            //give the reward
                            else {
                                //Add code to give reward
                                Messages.prefix(e.getWhoClicked(), Lang.getLangConfig().getString("reward-claimed"));
                                RewardSelector.grantReward((Player) e.getWhoClicked(), e.getCurrentItem().getItemMeta().getCustomModelData());
                                if (Main.getInstance().calendarStorage.containsKey(e.getWhoClicked().getUniqueId())) {
                                    Main.getInstance().calendarStorage.get(e.getWhoClicked().getUniqueId()).add(e.getCurrentItem().getItemMeta().getCustomModelData());
                                } else {
                                    List<Integer> list = new ArrayList<>(); list.add(e.getCurrentItem().getItemMeta().getCustomModelData());
                                    Main.getInstance().calendarStorage.put(e.getWhoClicked().getUniqueId(), list);
                                }
                                e.getWhoClicked().closeInventory();
                            }
                        } else {
                            Messages.prefix(e.getWhoClicked(), Lang.getLangConfig().getString("reward-unavailable"));
                        }
                    }
                }

            }
        }
    }

}
