package Dev.ScalerGames.BetterChristmas.Listeners;

import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Rewards.MessageSender;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class onJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //Added for version 1.1.0 - 26/11/22
        SimpleDateFormat sdf = new SimpleDateFormat("ddMM"); String christmas = "2512"; String current = sdf.format(new Date());
        if (Main.getInstance().getConfig().getBoolean("ChristmasMessage.enabled") && current.equals(christmas)) {
            if (Main.getInstance().getConfig().contains("ChristmasMessage.message")) {
                MessageSender.sendMessage(event.getPlayer(), Main.getInstance().getConfig().getString("ChristmasMessage.message"));
            }

            else if (Main.getInstance().getConfig().contains("ChristmasMessage.title") && Main.getInstance().getConfig().contains("ChristmasMessage.sub-title")) {
                MessageSender.sendTitle(event.getPlayer(), Main.getInstance().getConfig().getString("ChristmasMessage.title"), Main.getInstance().getConfig().getString("ChristmasMessage.sub-title"));
            }

            else if (Main.getInstance().getConfig().contains("ChristmasMessage.title")) {
                MessageSender.sendTitle(event.getPlayer(), Main.getInstance().getConfig().getString("ChristmasMessage.title"), null);
            }

            else if (Main.getInstance().getConfig().contains("ChristmasMessage.bar")) {
                MessageSender.sendActionBar(event.getPlayer(), Main.getInstance().getConfig().getString("Christmas.bar"));
            }

            else {
                Messages.logger("&cThere is no christmas message to send!");
            }
        }
    }

}
