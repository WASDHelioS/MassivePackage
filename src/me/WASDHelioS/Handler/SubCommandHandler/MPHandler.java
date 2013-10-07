/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler.SubCommandHandler;

import me.WASDHelioS.Handler.CommandHandler;
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
public class MPHandler extends CommandHandler implements CommandExecutor {
    private String MP = "[MassivePackage] ";
    private Main plugin;

    public MPHandler(Main plugin) {
        super();
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
        player.sendMessage(ChatColor.GOLD + "/MassivePackage toggle [pluginname] : toggles a plugin on or off.");
    }

    private boolean togglePlugin(String plugin) {
        
        String path = "enabled."+plugin;
        
        if(this.plugin.getConfiguration().contains(path))
        {
            if(this.plugin.getConfiguration().getBoolean(path)) {
                this.plugin.getConfiguration().set(path, false);
            } else {
                this.plugin.getConfiguration().set(path, true);
            }
            saveConfiguration(this.plugin);
            return true;
        }
        
        return false;
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
            if(args.length < 1) {
                sender.sendMessage(ChatColor.GOLD + MP + "Type /MP Help or /MP ? for more options.");
            } 
            else if (args.length  == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {

                        if (player.hasPermission("helios.opCommands")) {
                            sender.sendMessage(ChatColor.DARK_RED + "[MassivePackage] Reloading..");
                        }
                    }
                    plugin.getConfigHandler().reloadCommands();

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        if (player.hasPermission("helios.opCommands")) {
                            sender.sendMessage(ChatColor.GREEN + MP + "Reloaded!");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                    sendMPHelpMessage(sender);
                } else if (args[0].equalsIgnoreCase("resetconfig")) {
                    plugin.resetConfig();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.hasPermission("helios.opCommands")) {
                            player.sendMessage(ChatColor.GREEN + MP + "Configuration file has been reset!");
                        }
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "This command does not exist!");
                }
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("toggle")) {
                    boolean hasToggled = togglePlugin(args[1]);
                    if(hasToggled) {
                        
                        String enabled = "";
                        if(plugin.getConfiguration().getBoolean("enabled."+args[1])) {
                            enabled = "enabled!";
                        } else {
                            enabled = "disabled!";
                        }
                        sender.sendMessage(ChatColor.GOLD + MP + ChatColor.GREEN +"[" + args[1] + "] is now " + enabled);
                        plugin.getConfigHandler().reloadCommandsAlt();
                        
                    } else {
                        sender.sendMessage(ChatColor.GOLD + MP + ChatColor.RED + "This plugin does not exist!");
                    }
                }
            }
            
            else {
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
