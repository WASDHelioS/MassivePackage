/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler.SubCommandHandler;

import me.WASDHelioS.Handler.ConfigHandler;
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
public class MPHandler implements CommandExecutor {

    private Main plugin;

    public MPHandler(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Sends a MassivePackage help message to the sender.
     *
     * @param player - Sender
     */
    protected void sendMPHelpMessage(CommandSender player) {
        player.sendMessage(ChatColor.GOLD + "---------------MassivePackage Global Commands---------------");
        player.sendMessage(ChatColor.GOLD + "/MassivePackage reload (/mp reload): reloads config file.");
        player.sendMessage(ChatColor.GOLD + "/MassivePackage resetconfig : resets the config file.");
    }

    /**
     *
     * Handles all possible MassivePackage commands (THERE HAS TO BE A BETTER
     * WAY OF DOING THIS, RIGHT?)
     *
     * @param sender - Person who sent the command
     * @param command - The command
     * @param commandLabel - (Not sure what this is.)
     * @param args - The arguments typed after the command.
     */
    public void handleMPCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (sender.hasPermission("helios.opCommands")) {
            if (args.length - 1 == 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {

                        if (player.hasPermission("helios.opCommands")) {
                            sender.sendMessage(ChatColor.DARK_RED + "[MassivePackage] Reloading..");
                        }
                    }
                    plugin.getConfigHandler().reloadCommands();

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        if (player.hasPermission("helios.opCommands")) {
                            sender.sendMessage(ChatColor.GREEN + "[MassivePackage] Reloaded!");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                    sendMPHelpMessage(sender);
                } else if (args[0].equalsIgnoreCase("resetconfig")) {
                    plugin.resetConfig();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.hasPermission("helios.opCommands")) {
                            player.sendMessage(ChatColor.GREEN + "[MassivePackage] Configuration file has been reset!");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "This command does not exist!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command does not exist!");
            }
        } else {
            sender.sendMessage("You do not have permission!");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("MassivePackage") || cmd.getName().equalsIgnoreCase("mp")) {
            this.handleMPCommand(sender, cmd, commandLabel, args);
            return true;
        }
        return false;
    }
}
