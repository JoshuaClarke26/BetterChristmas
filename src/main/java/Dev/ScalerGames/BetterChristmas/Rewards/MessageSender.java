package Dev.ScalerGames.BetterChristmas.Rewards;

import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MessageSender {

    public static void sendMessage (Player p, String msg) {
        p.sendMessage(Format.placeholder(p, msg).replace("[prefix]", Objects.requireNonNull(Lang.getLangConfig().getString("prefix"))));
    }

    public static void sendTitle(Player p, String title, String subtitle) {
        try {
            if (subtitle == null) {
                p.sendTitle(Format.placeholder(p, title), null, Main.getInstance().getConfig().getInt("DefaultSettings.title.fadeIn")*20,
                        Main.getInstance().getConfig().getInt("DefaultSettings.title.showTitle")*20, Main.getInstance().getConfig().getInt("DefaultSettings.title.fadeOut"));
            } else {
                p.sendTitle(Format.placeholder(p, title), Format.placeholder(p, subtitle), Main.getInstance().getConfig().getInt("DefaultSettings.title.fadeIn")*20,
                        Main.getInstance().getConfig().getInt("DefaultSettings.title.showTitle")*20, Main.getInstance().getConfig().getInt("DefaultSettings.title.fadeOut"));
            }
        } catch (Exception ex) {
            Messages.logger("&4Failed to send a Title to: " + p.getName());
        }
    }

    public static void sendActionBar(Player p, String bar) {
        try {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Format.placeholder(p, bar)));
        } catch (Exception ex) {
            Messages.logger("&4Failed to send an action bar to: " + p.getName());
        }
    }

}
