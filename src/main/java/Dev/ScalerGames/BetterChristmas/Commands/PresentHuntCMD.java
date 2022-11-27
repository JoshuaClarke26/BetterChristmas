package Dev.ScalerGames.BetterChristmas.Commands;

import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PresentHuntCMD implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("presenthunt") || label.equalsIgnoreCase("ph")) {
            if (CommandCheck.execute(s, "bc.presenthunt.command", true)) {
                Player p = (Player) s;
                p.sendMessage(Format.placeholder(p, Lang.getLangConfig().getString("present-command")));
            }
        }
        return false;
    }

}
