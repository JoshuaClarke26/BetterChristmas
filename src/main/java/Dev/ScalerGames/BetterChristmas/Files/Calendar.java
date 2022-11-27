package Dev.ScalerGames.BetterChristmas.Files;

import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Calendar {

    private static FileConfiguration calendarConfig = null;
    private static File calendarFile = null;

    public static void reloadCalendar() {
        if (calendarFile == null)
            calendarFile = new File(Main.getInstance().getDataFolder(), "calendar.yml");

        calendarConfig = YamlConfiguration.loadConfiguration(calendarFile);

        InputStream defaultStream = Main.getInstance().getResource("calendar.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            calendarConfig.setDefaults(defaultConfig);
        }

    }

    public static FileConfiguration getCalendarConfig() {
        if (calendarConfig == null)
            reloadCalendar();

        return calendarConfig;
    }

    public static void saveCalendar() {
        if (calendarConfig == null || calendarFile == null)
            return;

        try {
            getCalendarConfig().save(calendarFile);
        } catch (IOException ex) {
            Messages.logger("&4Failed to save calendar.yml");
        }
    }

    public static void saveDefaultCalendar() {
        if (calendarFile == null)
            calendarFile = new File(Main.getInstance().getDataFolder(), "calendar.yml");

        if (!calendarFile.exists()) {
            Main.getInstance().saveResource("calendar.yml", false);
        }
    }

}
