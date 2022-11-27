package Dev.ScalerGames.BetterChristmas.PresentHunt;

import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class PresentListener implements Listener {

    @EventHandler
    public void onPresentClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand() == EquipmentSlot.HAND) {
            Main.getInstance().huntStorage.forEach((number, loc) -> {
                if (event.getClickedBlock() != null && event.getClickedBlock().getLocation().getBlockX() == Integer.parseInt(loc.get(1)) &&
                event.getClickedBlock().getLocation().getBlockY() == Integer.parseInt(loc.get(2)) && event.getClickedBlock().getLocation().getBlockZ() ==
                Integer.parseInt(loc.get(3))) {
                    if (Main.getInstance().huntPlayerStorage.containsKey(event.getPlayer().getUniqueId())
                            && Main.getInstance().huntPlayerStorage.get(event.getPlayer().getUniqueId()).contains(number)) {
                        if (Main.getInstance().huntPlayerStorage.get(event.getPlayer().getUniqueId()).size() == Main.getInstance().getConfig().getInt("PresentHunt.amount")) {
                            event.getPlayer().sendMessage(Format.placeholder(event.getPlayer(), Lang.getLangConfig().getString("present-hunt-complete")));
                        } else {
                            event.getPlayer().sendMessage(Format.placeholder(event.getPlayer(), Lang.getLangConfig().getString("present-found-already")));
                        }
                    } else {
                        if (Main.getInstance().huntPlayerStorage.containsKey(event.getPlayer().getUniqueId())) {
                            List<Integer> found = Main.getInstance().huntPlayerStorage.get(event.getPlayer().getUniqueId());
                            found.add(number);
                            Main.getInstance().huntPlayerStorage.replace(event.getPlayer().getUniqueId(), found);
                            if (found.size() == Main.getInstance().getConfig().getInt("PresentHunt.amount")) {
                                event.getPlayer().sendMessage(Format.placeholder(event.getPlayer(), Lang.getLangConfig().getString("all-present-found")));
                                RewardHandler.grantReward(event.getPlayer(), "PresentHunt.rewards");
                            }

                            if (Main.getInstance().getConfig().contains("PresentHunt.individual-rewards." + number)) {
                                RewardHandler.grantReward(event.getPlayer(), "PresentHunt.individual-rewards." + number);
                            }

                        } else {
                            List<Integer> found = new ArrayList<>(); found.add(number);
                            Main.getInstance().huntPlayerStorage.put(event.getPlayer().getUniqueId(), found);
                            if (Main.getInstance().getConfig().contains("PresentHunt.individual-rewards." + number)) {
                                RewardHandler.grantReward(event.getPlayer(), "PresentHunt.individual-rewards." + number);
                            }
                        }
                        event.getPlayer().sendMessage(Format.placeholder(event.getPlayer(), Lang.getLangConfig().getString("present-found")).replace("{present}",  String.valueOf(number)));
                    }
                }
            });
        }
    }

}
