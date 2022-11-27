package Dev.ScalerGames.BetterChristmas;

import Dev.ScalerGames.BetterChristmas.AdventCalendar.CalendarListener;
import Dev.ScalerGames.BetterChristmas.Commands.AdventCalendarCMD;
import Dev.ScalerGames.BetterChristmas.Commands.BetterChristmasCMD;
import Dev.ScalerGames.BetterChristmas.Commands.PresentHuntCMD;
import Dev.ScalerGames.BetterChristmas.Files.Calendar;
import Dev.ScalerGames.BetterChristmas.Files.Config;
import Dev.ScalerGames.BetterChristmas.Files.Data;
import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.GiftBox.GiftListener;
import Dev.ScalerGames.BetterChristmas.Listeners.onJoin;
import Dev.ScalerGames.BetterChristmas.PresentHunt.PresentListener;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import Dev.ScalerGames.BetterChristmas.Utils.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Main extends JavaPlugin implements Listener {

    public static Main plugin;
    public final Map<UUID, List<Integer>> calendarStorage = new HashMap<>();
    public final Map<Integer, List<String>> huntStorage = new HashMap<>();
    public final Map<UUID, List<Integer>> huntPlayerStorage = new HashMap<>();

    @Override
    public void onEnable() {
        plugin=this;
        enableFiles();
        enableCommands();
        enableListeners();
        enablePlugins();
        getCalendarData();
        getPresentLocations();
        getPresentCount();
    }

    @Override
    public void onDisable() {
        List<String> list = new ArrayList<>();
        calendarStorage.forEach((player, opened) -> list.add(player + ":" + opened));
        Data.getDataConfig().set("Advent-Calendar", list);
        Data.saveData();

        List<String> presents = new ArrayList<>();
        huntStorage.forEach((number, details) -> presents.add(number + ":" + details));
        Data.getDataConfig().set("Present-Locations", presents);
        Data.saveData();

        List<String> present_amount = new ArrayList<>();
        huntPlayerStorage.forEach((player, found) -> present_amount.add(player + ":" + found));
        Data.getDataConfig().set("Present-Hunt", present_amount);
        Data.saveData();
    }

    public static Main getInstance() {return plugin;}

    public void enableFiles() {
        Config.enableConfig();
        Calendar.saveDefaultCalendar();
        Data.saveDefaultData();
        Lang.saveDefaultLang();
    }

    public void enableCommands() {
        Objects.requireNonNull(getCommand("betterchristmas")).setExecutor(new BetterChristmasCMD());
        Objects.requireNonNull(getCommand("betterchristmas")).setTabCompleter(new BetterChristmasCMD());
        Objects.requireNonNull(getCommand("adventcalendar")).setExecutor(new AdventCalendarCMD());
        Objects.requireNonNull(getCommand("presenthunt")).setExecutor(new PresentHuntCMD());
    }

    public void enableListeners() {
        Bukkit.getPluginManager().registerEvents(new CalendarListener(), this);
        if (getConfig().getBoolean("PresentHunt.enabled")) {
            Bukkit.getPluginManager().registerEvents(new PresentListener(), this);
        }
        if (getConfig().getBoolean("ChristmasMessage.enabled")) {
            Bukkit.getPluginManager().registerEvents(new onJoin(), this);
        }
        Bukkit.getPluginManager().registerEvents(new GiftListener(), this);
    }

    public void enablePlugins() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getPluginManager().registerEvents(this, this);
            new Placeholders().register();
            Messages.logger("&2Successfully hooked into PlaceholderAPI");
        }
    }

    public void getCalendarData() {
        Data.getDataConfig().getStringList("Advent-Calendar").forEach(data -> {
            try {
                String[] list = data.split(":"); String uuid = list[0]; String opened = list[1].replace("[", "").replace("]", "");
                String[] numbers = opened.split(", ");
                calendarStorage.put(UUID.fromString(uuid), new ArrayList<>());
                for (String number : numbers) {
                    calendarStorage.get(UUID.fromString(uuid)).add(Integer.parseInt(number));
                }
            } catch (Exception ex) {
                Messages.logger("&4Failed to load Advent Calendar Data");
            }
        });
    }

    public void getPresentLocations() {
        try {
            Data.getDataConfig().getStringList("Present-Locations").forEach(present -> {
                String[] list = present.split(":"); int number = Integer.parseInt(list[0]); String info = list[1].replace("[", "").replace("]", "");
                String[] splitter = info.split(", "); List<String> infoList = new ArrayList<>(); infoList.add(splitter[0]); infoList.add(splitter[1]); infoList.add(splitter[2]); infoList.add(splitter[3]);
                huntStorage.put(number, infoList);
            });
        } catch (Exception ex) {
            Messages.logger("&4Failed to load Present Hunt Location Data");
        }
    }

    public void getPresentCount() {
        try {
            Data.getDataConfig().getStringList("Present-Hunt").forEach(count -> {
                String[] list = count.split(":"); String player = list[0]; String[] splitter = list[1].replace("[", "").replace("]", "").split(", ");
                List<Integer> numbers = new ArrayList<>();
                for (String s : splitter) {
                    numbers.add(Integer.parseInt(s));
                }
                huntPlayerStorage.put(UUID.fromString(player), numbers);
            });
        } catch (Exception ex) {
            Messages.logger("&4Failed to load Present Hunt Count Data");
        }
    }

}
