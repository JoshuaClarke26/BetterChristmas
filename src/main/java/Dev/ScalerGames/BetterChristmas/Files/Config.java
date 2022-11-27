package Dev.ScalerGames.BetterChristmas.Files;

import Dev.ScalerGames.BetterChristmas.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Config {

    static FileConfiguration config;
    static File cFile;

    public static void enableConfig() {
        config = Main.getInstance().getConfig();
        config.options().copyDefaults(true);
        Main.getInstance().saveDefaultConfig();
        cFile = new File(Main.getInstance().getDataFolder(), "config.yml");
    }

}
