package Dev.ScalerGames.BetterChristmas.Files;

import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Lang {

    private static FileConfiguration langConfig = null;
    private static File langFile = null;

    public static void reloadLang() {
        if (langFile == null)
            langFile = new File(Main.getInstance().getDataFolder(), "lang.yml");

        langConfig = YamlConfiguration.loadConfiguration(langFile);

        InputStream defaultStream = Main.getInstance().getResource("lang.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            langConfig.setDefaults(defaultConfig);
        }
    }

    public static FileConfiguration getLangConfig() {
        if (langConfig == null)
            reloadLang();

        return langConfig;
    }

    public static void saveLang() {
        if (langConfig == null || langFile == null)
            return;

        try {
            getLangConfig().save(langFile);
        } catch (IOException ex) {
            Messages.logger("&4Could not save lang.yml");
        }
    }

    public static void saveDefaultLang() {
        if (langFile == null)
            langFile = new File(Main.getInstance().getDataFolder(), "lang.yml");

        if (!langFile.exists()) {
            Main.getInstance().saveResource("lang.yml", false);
        }
    }

}
