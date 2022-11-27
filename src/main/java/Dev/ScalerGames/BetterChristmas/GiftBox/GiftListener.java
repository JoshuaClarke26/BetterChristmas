package Dev.ScalerGames.BetterChristmas.GiftBox;

import Dev.ScalerGames.BetterChristmas.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class GiftListener implements Listener {

    @EventHandler
    public void onClickPresent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("GiftBox.boxes")).getKeys(false).forEach(gift -> {
                if (p.getInventory().getItemInMainHand().getItemMeta() != null && p.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    if (p.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == Main.getInstance().getConfig().getInt("GiftBox.boxes." + gift + ".id")) {
                        event.setCancelled(true);
                        if (p.getInventory().getItemInMainHand().getAmount() == 1) {
                            p.getInventory().remove(p.getInventory().getItemInMainHand());
                        } else {
                            ItemStack item = p.getInventory().getItemInMainHand(); item.setAmount(item.getAmount() - 1);
                            p.getInventory().setItemInMainHand(item);
                        }

                        //Trigger Sound
                        if (Main.getInstance().getConfig().contains("GiftBox.boxes." + gift + ".sound")) {
                            p.playSound(p.getLocation(), Sound.valueOf(Main.getInstance().getConfig().getString("GiftBox.boxes." + gift + ".sound")),
                                    Main.getInstance().getConfig().getInt("DefaultSettings.sound.volume"), Main.getInstance().getConfig().getInt("DefaultSettings.sound.pitch"));
                        }
                        //Give reward
                        GiftRewards.giveReward(p, gift);
                    }
                }
            });
        }
    }

}
