package Dev.ScalerGames.BetterChristmas.Utils;

import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "ScalerGames";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "betterchristmas";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, String identifier) {

        if (identifier.equals("presents_collected")) {
            try {
                List<Integer> presents = Main.getInstance().huntPlayerStorage.get(p.getUniqueId());
                return String.valueOf(presents.size());
            } catch (Exception ex) {
                return "0";
            }
        }

        if (identifier.equals("presents_total")) {
            return Main.getInstance().getConfig().getString("PresentHunt.amount");
        }

        if (identifier.equals("prefix")) {
            return Lang.getLangConfig().getString("prefix");
        }

        return "";
    }

}
