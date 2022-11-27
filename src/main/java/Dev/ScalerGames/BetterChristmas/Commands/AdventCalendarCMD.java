package Dev.ScalerGames.BetterChristmas.Commands;

import Dev.ScalerGames.BetterChristmas.AdventCalendar.CalendarCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AdventCalendarCMD implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("adventcalendar") || label.equalsIgnoreCase("ac")) {

            if (args.length == 0) {
                if (CommandCheck.execute(s, "bc.adventcalendar.open", true)) {
                    CalendarCreator.generate((Player) s);
                }
            }

        }
        return false;
    }

}
