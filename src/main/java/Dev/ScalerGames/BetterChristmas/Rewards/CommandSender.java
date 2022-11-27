package Dev.ScalerGames.BetterChristmas.Rewards;

import Dev.ScalerGames.BetterChristmas.Utils.Format;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandSender {

    public static void playerCMD(Player p, String cmd) {
        p.performCommand(Format.placeholder(p, cmd.replace("{player}", p.getName())));
    }

    public static void consoleCMD(Player p, String cmd) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Format.placeholder(p, cmd.replace("{player}", p.getName())));
    }

}
