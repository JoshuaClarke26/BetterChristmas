package Dev.ScalerGames.BetterChristmas.Utils;

import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messages {

    public static void prefix(CommandSender s, String msg) {
        if (s instanceof Player) {
            s.sendMessage(Format.placeholder((Player) s, Lang.getLangConfig().getString("prefix") + msg));
        } else {
            s.sendMessage(Format.color(Lang.getLangConfig().getString("prefix") + msg));
        }
    }

    public static void logger(String log) {
        Bukkit.getConsoleSender().sendMessage("[" + Main.getInstance().getDescription().getName() + "] " + Format.color(log));
    }

}
