/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler.SubCommandHandler;

import me.WASDHelioS.Handler.CommandHandler;
import me.WASDHelioS.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Nick
 */
public class EwabHandler extends CommandHandler {

    public EwabHandler(Main plugin) {
        super(plugin);
    }

    /**
     * 
     * Handles all possible EnterWithABang commands (THERE HAS TO BE A BETTER WAY OF DOING THIS, RIGHT?)
     * 
     * @param sender - Person who sent the command
     * @param command - The command
     * @param commandLabel - (Not sure what this is.)
     * @param args - The arguments typed after the command.
     */
    public void handleEwabCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (sender.hasPermission("helios.opCommands")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.GOLD + "[EnterWithABang] Thank you for using EnterWithABang. To edit fireworks, check the config file.");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission!");
        }
    }
}
