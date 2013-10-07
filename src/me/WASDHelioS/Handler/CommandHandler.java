/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler;

import me.WASDHelioS.Handler.SubCommandHandler.CEditHandler;
import me.WASDHelioS.Handler.SubCommandHandler.EwabHandler;
import me.WASDHelioS.Handler.SubCommandHandler.MPHandler;
import me.WASDHelioS.Handler.SubCommandHandler.PokeHandler;
import me.WASDHelioS.Util.Type.PokeType;
import me.WASDHelioS.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Nick
 */
public class CommandHandler {

    protected final Main plugin;
    private MPHandler mp;
    private EwabHandler ew;
    private PokeHandler po;
    private CEditHandler ce;
    
    private String poke = "[Poke] ";

    public CommandHandler(Main main) {
        this.plugin = main;
    }
    
    public void setMPHandler(MPHandler h) {
        mp = h;
    }
    
    public void setEwabHandler(EwabHandler e) {
        ew = e;
    }
    
    public void setPokeHandler(PokeHandler p) {
        po = p;
    }
    public void setCEditHandler(CEditHandler ce) {
        this.ce = ce;
    }

    /**
     * Sends a MassivePackage help message to the sender.
     * @param player - Sender
     */
    protected void sendMPHelpMessage(CommandSender player) {
        player.sendMessage(ChatColor.GOLD + "---------------MassivePackage Global Commands---------------");
        player.sendMessage(ChatColor.GOLD + "/MassivePackage reload (/mp reload): reloads config file.");
        player.sendMessage(ChatColor.GOLD + "/MassivePackage resetconfig : resets the config file.");
    }
    /**
     * Sends a Poke help message to the sender.
     * @param player - Sender
     */
    protected void sendPokeHelpMessage(CommandSender player) {
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
    
    protected void sendCEditHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "---------------CEdit Commands---------------");
        sender.sendMessage(ChatColor.GOLD + "/CEdit : sends a useless message.");
        sender.sendMessage(ChatColor.GOLD + "/CEdit help : Shows this help message.");
        sender.sendMessage(ChatColor.GOLD + "/CEdit ? : Alias for help/");
        sender.sendMessage(ChatColor.GOLD + "/CEdit list : returns a list of all from and to commands.");
        sender.sendMessage(ChatColor.GOLD + "/CEdit add fromc [command] toc [command] : Adds a command.");
        sender.sendMessage(ChatColor.GOLD + "/CEdit edit fromc [command] toc [command] newfromc [command] newtoc [command] : edits a command. first from and to : "
                + "the registered commands. second from and to : the command to replace them with.");
        sender.sendMessage(ChatColor.GOLD + "/CEdit remove fromc [fromCommand] : removes the command based on the fromcommand.");
        sender.sendMessage(ChatColor.GOLD + "/CEdit remove toc [toCommand] : removes the command based on the toCommand.");
    }

    /**
     * Toggles the state of the Poke-plugin.
     * If the plugin is enabled, it disables it. if it is disabled, it will enable it
     * in NORMAL mode.
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
     * @param player - The target.
     * @param oldType - The mode the target is on now.
     * @param newType  - The mode to which it needs to switch to.
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
     * Swtiches the state of the plugin. Capable of sending 2 messages: 
     * 1 to the target, 1 to the sender. If the target IS the sender, it will only
     * send 1 message.
     * @param target - The target(PLAYER)
     * @param sender - The sender.
     * @param oldType - The mode the target is on now.
     * @param newType  - The mode to which it needs to switch to.
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
     * Initial handling of the commands.(THERE HAS TO BE A BETTER WAY OF DOING THIS, RIGHT?)
     * 
     * @param sender - Person who sent the command
     * @param command - The command
     * @param commandLabel - (Not sure what this is.)
     * @param args - The arguments typed after the command.
     */
    public boolean handleCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (cmd.getName().equalsIgnoreCase("Poke")) {
            if (plugin.isPokeEnabled()) {

                po.handlePokeCommand(sender, cmd, commandLabel, args);
            } else {
                sender.sendMessage(ChatColor.RED + poke + "[Poke] is not able to respond, because it's turned off in the config file.");
            }
            return true;

        } else if (cmd.getName().equalsIgnoreCase("EnterWithABang") || cmd.getName().equalsIgnoreCase("ewab")) {
            if (plugin.isEWABEnabled()) {
                ew.handleEwabCommand(sender, cmd, commandLabel, args);
            } else {
                sender.sendMessage(ChatColor.RED + "[EnterWithABang] EnterWithABang is not able to respond, because it's turned off in the config file.");
            }
            return true;

        } else if (cmd.getName().equalsIgnoreCase("MassivePackage") || cmd.getName().equalsIgnoreCase("mp")) {
            mp.handleMPCommand(sender, cmd, commandLabel, args);
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("CEdit")) {
            if(plugin.isCeditEnabled()) {
            ce.handleCEditCommand(sender, cmd, commandLabel, args);
            }
            else {
                sender.sendMessage(ChatColor.RED + "[CEdit] is not able to respond, because it's turned off in the config file.");
            }
            return true;
        }
        return false;
    }
    /**
     * Reloads the Configuration file.
     * 
     */
    protected void reloadCommands() {
        plugin.reloadConfigAlt();
        plugin.onEnableEss();
    }
}
