/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler.SubCommandHandler;

import me.WASDHelioS.Handler.CommandHandler;
import me.WASDHelioS.Util.Type.PokeType;
import me.WASDHelioS.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Nick
 */
public class PokeHandler extends CommandHandler {

    private String poke = "[Poke] ";

    public PokeHandler(Main plugin) {
        super(plugin);
    }

    /**
     *
     * Handles all possible poke commands (THERE HAS TO BE A BETTER WAY OF DOING
     * THIS, RIGHT?)
     *
     * @param sender - Person who sent the command
     * @param command - The command
     * @param commandLabel - (Not sure what this is.)
     * @param args - The arguments typed after the command.
     */
    public void handlePokeCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length < 1) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                togglePluginState(player);
            } else {
                sender.sendMessage(ChatColor.RED + poke + "This command can only be executed by a Player!");
            }

        } else if (args.length - 1 == 0) {

            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (sender.hasPermission("helios.standardUse")) {

                    if (args[0].equalsIgnoreCase("hard") || args[0].equalsIgnoreCase("h")) {
                        togglePluginState(player, plugin.getType(), PokeType.HARD);
                    } else if (args[0].equalsIgnoreCase("soft") || args[0].equalsIgnoreCase("s")) {
                        togglePluginState(player, plugin.getType(), PokeType.SOFT);
                    } else if (args[0].equalsIgnoreCase("normal") || args[0].equalsIgnoreCase("n")) {
                        togglePluginState(player, plugin.getType(), PokeType.NORMAL);
                    } else if (args[0].equalsIgnoreCase("off")) {
                        if (plugin.getHashMap().containsKey(player)) {
                            if (plugin.getHashMap().get(player)) {
                                plugin.getHashMap().put(player, false);
                                player.sendMessage(ChatColor.RED + poke + "Poking is now disabled.");
                            } else {
                                player.sendMessage(ChatColor.RED + poke + "Poking is already disabled!");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + poke + "Poking is already disabled!");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                        sendPokeHelpMessage(sender);
                    } else {
                        sender.sendMessage(ChatColor.RED + "This command does not exist!");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + poke + "You do not have permission!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + poke + "This command can only be executed by a Player!");
            }

        } else if (args.length - 1 == 1) {
            if (args[0].equalsIgnoreCase("off")) {

                if (sender.hasPermission("helios.manipulationUse")) {


                    Player target = (Bukkit.getServer().getPlayer(args[1]));
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + poke + args[1] + " is not online!");
                    } else {
                        if (plugin.getHashMap().containsKey(target)) {
                            if (plugin.getHashMap().get(target)) {
                                plugin.getHashMap().put(target, false);
                                if (target != sender) {
                                    sender.sendMessage(ChatColor.RED + poke + "Poking is now disabled for " + args[1]);
                                }
                                target.sendMessage(ChatColor.RED + poke + "Poking has been disabled!");
                            } else {
                                sender.sendMessage(ChatColor.RED + poke + "Poking is already disabled for " + args[1]);
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + poke + "Poking is already disabled for " + args[1]);
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + poke + "You do not have permission!");
                }

            } else {
                sender.sendMessage(ChatColor.RED + "This command does not exist!");
            }

        } else if (args.length - 1 == 2) {
            if (args[0].equalsIgnoreCase("set")) {

                if (sender.hasPermission("helios.manipulationUse")) {



                    Player target = (Bukkit.getServer().getPlayer(args[1]));
                    if (target == null) {
                        sender.sendMessage(ChatColor.RED + poke + args[1] + " is not online!");
                    } else {
                        if (args[2].equalsIgnoreCase("off")) {
                            if (plugin.getHashMap().containsKey(target)) {
                                if (plugin.getHashMap().get(target)) {
                                    plugin.getHashMap().put(target, false);
                                    if (target != sender) {
                                        sender.sendMessage(ChatColor.RED + poke + "Poking is now disabled for " + args[1]);
                                    }
                                    target.sendMessage(ChatColor.RED + poke + "Poking has been disabled!");
                                } else {
                                    sender.sendMessage(ChatColor.RED + poke + "Poking is already disabled for " + args[1]);
                                }
                            } else {
                                sender.sendMessage(ChatColor.RED + poke + "Poking is already disabled for " + args[1]);
                            }
                        } else if (args[2].equalsIgnoreCase("soft")) {
                            togglePluginState(target, sender, plugin.getType(), PokeType.SOFT);
                        } else if (args[2].equalsIgnoreCase("normal")) {
                            togglePluginState(target, sender, plugin.getType(), PokeType.NORMAL);
                        } else if (args[2].equalsIgnoreCase("hard")) {
                            togglePluginState(target, sender, plugin.getType(), PokeType.HARD);
                        } else {
                            sender.sendMessage(ChatColor.RED + poke + "This mode does not exist!");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + poke + "You do not have permission!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command does not exist!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command does not exist!");

        }
    }
}
