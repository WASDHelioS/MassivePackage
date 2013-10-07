/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler.SubCommandHandler;

import me.WASDHelioS.Util.Type.PokeType;
import me.WASDHelioS.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Nick
 */
public class PokeHandler implements CommandExecutor {

    private String poke = "[Poke] ";
    private Main plugin;

    public PokeHandler(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Sends a Poke help message to the sender.
     *
     * @param player - Sender
     */
    private void sendPokeHelpMessage(CommandSender player) {
        player.sendMessage(ChatColor.GOLD + "---------------Poke Commands---------------");
        player.sendMessage(ChatColor.GOLD + "/Poke: Toggles on or off the plugin.");
        player.sendMessage(ChatColor.GOLD + "/Help | /? : Shows this message dialog.");
        player.sendMessage(ChatColor.GOLD + "/Poke Normal");
        player.sendMessage(ChatColor.GOLD + "/Poke N : Turns on / switches to the normal poke mode.");
        player.sendMessage(ChatColor.GOLD + "/Poke Soft");
        player.sendMessage(ChatColor.GOLD + "/Poke S : Turns on / switches to the soft poke mode.");
        player.sendMessage(ChatColor.GOLD + "/Poke Hard");
        player.sendMessage(ChatColor.GOLD + "/Poke H : Turns on / switches to the hard poke mode.");
        player.sendMessage(ChatColor.GOLD + "/Poke off : turns off any poke mode.");
        player.sendMessage(ChatColor.GOLD + "/Poke off [player] : turns off poke mode for specified player.");
        player.sendMessage(ChatColor.GOLD + "/Poke set [player] [mode] : turns on or off modes for specified player.");
        player.sendMessage(ChatColor.GOLD + "[mode] : OFF, SOFT, NORMAL, HARD");
    }

    /**
     * Toggles the state of the Poke-plugin. If the plugin is enabled, it
     * disables it. if it is disabled, it will enable it in NORMAL mode.
     *
     * @param player - The target.
     */
    protected void togglePluginState(Player player) {
        if (plugin.getHashMap().containsKey(player)) {
            if (plugin.getHashMap().get(player)) {
                plugin.getHashMap().put(player, false);
                plugin.setType(PokeType.OFF);
                player.sendMessage(ChatColor.RED + poke + "poking is now disabled.");
            } else {
                plugin.getHashMap().put(player, true);
                plugin.setType(PokeType.NORMAL);
                player.sendMessage(ChatColor.GOLD + poke + "NORMAL poke is now enabled.");
            }
        } else {
            plugin.getHashMap().put(player, true);
            plugin.setType(PokeType.NORMAL);
            player.sendMessage(ChatColor.GOLD + poke + "NORMAL poke is now enabled.");
        }
    }

    /**
     * Switches the state of the Poke-plugin.
     *
     * @param player - The target.
     * @param oldType - The mode the target is on now.
     * @param newType - The mode to which it needs to switch to.
     */
    protected void togglePluginState(Player player, PokeType oldType, PokeType newType) {
        if (plugin.getHashMap().containsKey(player)) {
            if (plugin.getHashMap().get(player) && oldType != newType) {
                plugin.setType(newType);
                player.sendMessage(ChatColor.GOLD + poke + "Poke has now switched from " + oldType.toString() + " to " + newType.toString() + " poke.");
            } else if (plugin.getHashMap().get(player) && oldType == newType) {
                player.sendMessage(ChatColor.RED + poke + "Poke is already active on " + oldType.toString());
            } else {
                plugin.getHashMap().put(player, true);
                plugin.setType(newType);
                player.sendMessage(ChatColor.GOLD + poke + newType.toString() + " poke is now enabled.");
            }
        } else {
            plugin.getHashMap().put(player, true);
            plugin.setType(newType);
            player.sendMessage(ChatColor.GOLD + poke + newType.toString() + " poke is now enabled.");
        }
    }

    /**
     * Swtiches the state of the plugin. Capable of sending 2 messages: 1 to the
     * target, 1 to the sender. If the target IS the sender, it will only send 1
     * message.
     *
     * @param target - The target(PLAYER)
     * @param sender - The sender.
     * @param oldType - The mode the target is on now.
     * @param newType - The mode to which it needs to switch to.
     */
    protected void togglePluginState(Player target, CommandSender sender, PokeType oldType, PokeType newType) {
        if (plugin.getHashMap().containsKey(target)) {
            if (plugin.getHashMap().get(target) && oldType != newType) {
                plugin.setType(newType);
                target.sendMessage(ChatColor.GOLD + poke + "Poke has now switched from " + oldType.toString() + " to " + newType.toString() + " poke.");
                if (target != sender) {
                    sender.sendMessage(ChatColor.GOLD + poke + "Poke has switched from " + oldType.toString() + " to " + newType.toString() + " poke for " + target.getName() + ".");
                }
            } else if (plugin.getHashMap().get(target) && oldType == newType) {
                sender.sendMessage(ChatColor.RED + poke + "Poke is already active on " + oldType.toString() + " for " + target.getName() + ".");
            } else {
                plugin.getHashMap().put(target, true);
                plugin.setType(newType);
                target.sendMessage(ChatColor.GOLD + poke + newType.toString() + " poke is now enabled.");
                if (target != sender) {
                    sender.sendMessage(ChatColor.GOLD + poke + newType.toString() + " poke is now enabled for " + target.getName() + ".");
                }
            }
        } else {
            plugin.getHashMap().put(target, true);
            plugin.setType(newType);
            target.sendMessage(ChatColor.GOLD + poke + newType.toString() + " poke is now enabled.");
            if (target != sender) {
                sender.sendMessage(ChatColor.GOLD + poke + newType.toString() + " poke is now enabled for " + target.getName() + ".");
            }
        }
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
                    } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("Poke")) {
            if (plugin.isPokeEnabled()) {

                this.handlePokeCommand(sender, cmd, commandLabel, args);
            } else {
                sender.sendMessage(ChatColor.RED + poke + "[Poke] is not able to respond, because it's turned off in the config file.");
            }

            return true;
        }
        return false;
    }
}
