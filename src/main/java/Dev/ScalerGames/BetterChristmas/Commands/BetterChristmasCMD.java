package Dev.ScalerGames.BetterChristmas.Commands;

import Dev.ScalerGames.BetterChristmas.Files.Calendar;
import Dev.ScalerGames.BetterChristmas.Files.Data;
import Dev.ScalerGames.BetterChristmas.Files.Lang;
import Dev.ScalerGames.BetterChristmas.GiftBox.GiftGenerator;
import Dev.ScalerGames.BetterChristmas.Main;
import Dev.ScalerGames.BetterChristmas.Utils.Format;
import Dev.ScalerGames.BetterChristmas.Utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BetterChristmasCMD implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender s, @NotNull Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("betterchristmas") || label.equalsIgnoreCase("bc")) {

            if (args.length == 0) {
                if (CommandCheck.execute(s, "bc.command.version", false)) {
                    Messages.prefix(s, "&2This server is running version " + Main.getInstance().getDescription().getVersion() + " of BetterChristmas");
                }
            }
            else {

                if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
                    if (CommandCheck.execute(s, "bc.command.reload", false)) {
                        try {
                            Main.getInstance().reloadConfig();
                            Data.reloadData();
                            Lang.reloadLang();
                            Calendar.reloadCalendar();
                            Messages.prefix(s, "&2Successfully reloaded BetterChristmas");
                        } catch (Exception ex) {
                            Messages.prefix(s, "&cFailed to reload BetterChristmas");
                        }
                    }
                }

                else if (args[0].equalsIgnoreCase("version") && args.length == 1) {
                    if (CommandCheck.execute(s, "bc.command.version", false)) {
                        Messages.prefix(s, "&2This server is running version " + Main.getInstance().getDescription().getVersion() + " of BetterChristmas");
                    }
                }

                else if (args[0].equalsIgnoreCase("reset") && args.length == 1) {
                    if (CommandCheck.execute(s, "bc.command.reset", false)) {
                        Main.getInstance().calendarStorage.clear();
                        Messages.prefix(s, "&4Reset the Advent Calendar Storage");
                    }
                }

                else if (args[0].equalsIgnoreCase("hunt") && args.length >= 2) {
                    if (CommandCheck.execute(s, "bc.command.hunt", true)) {
                        if (args[1].equalsIgnoreCase("set") && args.length == 3) {
                            if (Format.isInt(args[2]) && Integer.parseInt(args[2]) <= Main.getInstance().getConfig().getInt("PresentHunt.amount")) {
                                Player p = (Player) s; List<String> location = new ArrayList<>(); location.add(Objects.requireNonNull(p.getTargetBlock(null, 200).getLocation().getWorld()).getName());
                                location.add(String.valueOf(p.getTargetBlock(null, 200).getLocation().getBlockX())); location.add(String.valueOf(p.getTargetBlock(null, 200).getLocation().getBlockY()));
                                location.add(String.valueOf(p.getTargetBlock(null, 200).getLocation().getBlockZ()));
                                Main.getInstance().huntStorage.put(Integer.parseInt(args[2]), location);
                                Messages.prefix(s, "&2Set present " + args[2] + " location: [X: " + location.get(1) + " Y: "+ location.get(2) + " Z: " + location.get(3) + "] in " + location.get(0));
                            } else {
                                Messages.prefix(s, "&4You must set the location to a number between 1 and " + Main.getInstance().getConfig().getString("PresentHunt.amount"));
                            }
                        }

                        else if (args[1].equalsIgnoreCase("remove") && args.length == 3) {
                            Main.getInstance().huntStorage.remove(Integer.parseInt(args[2]));
                            Messages.prefix(s, "&2Successfully removed present number " + args[2] + " for the present hunt");
                        }

                        else if (args[1].equalsIgnoreCase("reset") && args.length == 3) {
                            if (args[2].equalsIgnoreCase("locations")) {
                                Main.getInstance().huntStorage.clear();
                                Messages.prefix(s, "&2Successfully reset the locations of the presents");
                            }
                            else if (args[2].equalsIgnoreCase("players")) {
                                Main.getInstance().huntPlayerStorage.clear();
                                Messages.prefix(s, "&2Successfully reset the players data for the PresentHunt");
                            }
                            else {
                                Messages.prefix(s, "&cUsage: /" + label + " hunt reset [locations|players]");
                            }
                        }

                        else {
                            Messages.prefix(s, "&cUsage: /" + label + " hunt [set|remove|reset]");
                        }
                    }
                }

                else if (args[0].equalsIgnoreCase("head")) {
                    if (args.length >= 2) {
                        if (CommandCheck.execute(s, "bc.command.head", true)) {
                            try {
                                ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                                item = Bukkit.getUnsafe().modifyItemStack(item, "{display:{Name:\"{\\\"text\\\":\\\"BetterChristmas Head\\\"}\"},SkullOwner:{Id:[" + "I;1201296705,1414024019,-1385893868,1321399054" + "],Properties:{textures:[{Value:\"" + args[1] + "\"}]}}}");
                                Player p = (Player) s;
                                p.getInventory().addItem(item);
                            } catch (Exception ex) {
                                Messages.prefix(s, "&cInvalid head value");
                            }
                        }
                    } else {
                        Messages.prefix(s, "Usage: /" + label + " head <id>");
                    }
                }

                else if (args[0].equalsIgnoreCase("gift")) {
                    if (CommandCheck.execute(s, "bc.command.gift", false)) {
                        if (args.length == 4) {
                            if (Main.getInstance().getConfig().contains("GiftBox.boxes." + args[1])) {
                                if (Bukkit.getServer().getPlayer(args[2]) != null) {
                                    if (CommandCheck.isInt(args[3])) {
                                        for (int i=0; i < Integer.parseInt(args[3]); i++) {
                                            Objects.requireNonNull(Bukkit.getServer().getPlayer(args[2])).getInventory().addItem(
                                                    GiftGenerator.createGift(Bukkit.getServer().getPlayer(args[2]), args[1]));
                                        }
                                    } else {
                                        Messages.prefix(s, "&cThe quantity needs to be a number");
                                    }
                                } else {
                                    Messages.prefix(s, "&cThe player needs to be online");
                                }
                            } else {
                                Messages.prefix(s, "&cThe gift box was not found");
                            }
                        } else {
                            Messages.prefix(s, "&cUsage: /" + label + " gift <name> <player> <quantity>");
                        }
                    }
                }

                else {
                    Messages.prefix(s, "&cUsage: /" + label + "[reload|version|reset|hunt|head|gift]");
                }

            }

        }
        return false;
    }

    List<String> options = Arrays.asList("reload", "version", "reset", "hunt", "head", "gift");
    List<String> options2 = Arrays.asList("set", "remove", "reset");
    List<String> options3 = Objects.requireNonNull(Main.getInstance().getConfig().getConfigurationSection("GiftBox.boxes")).getKeys(false).stream().toList();
    public List<String> onTabComplete(@NotNull CommandSender s, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 1) {
            List<String> result = new ArrayList<>();
            options.forEach(option -> {
                if (s.hasPermission("bc.command." + option)) {
                    if (option.toLowerCase().startsWith(args[0].toLowerCase()))
                        result.add(option);
                }
            });
            return result;
        }

        if (args[0].equalsIgnoreCase("hunt")) {
            if (args.length == 2) {
                List<String> result2 = new ArrayList<>();
                options2.forEach(option -> {
                    if (option.toLowerCase().startsWith(args[1].toLowerCase()))
                        result2.add(option);
                });
                return result2;
            }
        }

        if (args[0].equalsIgnoreCase("gift") && Main.getInstance().getConfig().contains("GiftBox.boxes")) {
            if (args.length == 2) {
                List<String> result3 = new ArrayList<>();
                options3.forEach(option -> {
                    if (option.toLowerCase().startsWith(args[1].toLowerCase()))
                            result3.add(option);
                });
                return result3;
            }
        }

        return null;
    }

}
