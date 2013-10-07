/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler.SubCommandHandler;

import me.WASDHelioS.Handler.CommandHandler;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.WASDHelioS.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Nick
 */
public class CEditHandler extends CommandHandler {

    private String CEdit = "[CEdit] ";

    public CEditHandler(Main plugin) {
        super(plugin);
    }

    private List<String> getFromCommandsList(FileConfiguration config) {
        List<String> fromCommandList = config.getStringList("cedit.fromcommand");
        return fromCommandList;
    }

    private List<String> getToCommandsList(FileConfiguration config) {
        List<String> toCommandList = config.getStringList("cedit.tocommand");
        return toCommandList;
    }

    private void sendList(CommandSender sender) {
        FileConfiguration config = plugin.getConfig();
        List<String> fromCommandList = getFromCommandsList(config);
        List<String> toCommandList = getToCommandsList(config);

        if (fromCommandList.size() == toCommandList.size()) {
            for (int i = 0; i < fromCommandList.size(); i++) {
                sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.GRAY + (i + 1) + " : from " + fromCommandList.get(i) + " to " + toCommandList.get(i));
            }
        } else {
            sender.sendMessage(ChatColor.RED + CEdit + "ERROR! FromCommand and ToCommand aren't the same size. check the config file and fix this!");
        }
    }

    private void addFromCommand(String fromcommand) {
        List<String> fromlist = getFromCommandsList(plugin.getConfiguration());
        fromlist.add(fromcommand);
        plugin.getConfiguration().set("cedit.fromcommand", fromlist);

    }

    private void addToCommand(String tocommand) {
        List<String> tolist = getToCommandsList(plugin.getConfiguration());
        tolist.add(tocommand);
        plugin.getConfiguration().set("cedit.tocommand", tolist);
    }

    private void removeFromCommand(String fromCommand) {
        List<String> fromlist = getFromCommandsList(plugin.getConfiguration());
        List<String> tolist = getToCommandsList(plugin.getConfiguration());

        int index = fromlist.indexOf(fromCommand);
        fromlist.remove(index);
        tolist.remove(index);
        plugin.getConfiguration().set("cedit.fromcommand", fromlist);
        plugin.getConfiguration().set("cedit.tocommand", tolist);
    }

    private void removeToCommand(String toCommand) {
        List<String> fromlist = getFromCommandsList(plugin.getConfiguration());
        List<String> tolist = getToCommandsList(plugin.getConfiguration());

        int index = tolist.indexOf(toCommand);
        fromlist.remove(index);
        tolist.remove(index);
        plugin.getConfiguration().set("cedit.fromcommand", fromlist);
        plugin.getConfiguration().set("cedit.tocommand", tolist);
    }

    private void replaceCommand(String fromCommand, String toCommand, String fromNewCommand, String toNewCommand) {
        List<String> fromList = getFromCommandsList(plugin.getConfiguration());
        List<String> toList = getToCommandsList(plugin.getConfiguration());

        int index = 0;
        boolean cont = false;
        if (fromList.indexOf(fromCommand) == toList.indexOf(toCommand)) {
            index = fromList.indexOf(fromCommand);
            cont = true;
        }

        if (cont) {
            fromList.remove(index);
            toList.remove(index);

            fromList.add(index, fromNewCommand);
            toList.add(index, toNewCommand);

            plugin.getConfiguration().set("cedit.fromcommand", fromList);
            plugin.getConfiguration().set("cedit.tocommand", toList);
        }
    }

    /**
     *
     * Gets all the words after a specified keyword.
     *
     * @param keyword the keyword this method looks out for.
     * @param args the list to check.
     * @return
     */
    private String getCommandArgs(String keyword, String[] arguments) {

        List<String> args = Arrays.asList(arguments);

        String command = "";
        int keywordindex = 0;
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).equalsIgnoreCase(keyword)) {
                keywordindex = i + 1;
            }
        }

        for (int i = keywordindex; i < args.size(); i++) {
            command = command + args.get(i) + " ";
        }

        if (command.length() > 0 && command.charAt(command.length() - 1) == ' ') {
            command = command.substring(0, command.length() - 1);
        }

        return command;
    }

    /**
     *
     * Gets all the words in between specified keywords
     *
     * @param keywordbegin The beginning word.
     * @param keywordend The ending word.
     * @param args The list to check.
     * @return
     */
    private String getCommandArgs(String keywordbegin, String keywordend, String[] arguments) {
        List<String> args = Arrays.asList(arguments);

        String command = "";
        int keywordindex = 0;
        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).equalsIgnoreCase(keywordbegin)) {
                keywordindex = i + 1;
                break;
            }
        }

        for (int i = keywordindex; i < args.size(); i++) {
            if (args.get(i).equalsIgnoreCase(keywordend)) {
                break;
            } else {
                command = command + args.get(i) + " ";
            }
        }
        if (command.length() > 0 && command.charAt(command.length() - 1) == ' ') {
            command = command.substring(0, command.length() - 1);
        }


        return command;
    }

    private boolean checkIfKeywordExists(String keyword, String[] arguments) {

        List<String> args = Arrays.asList(arguments);

        for (int i = 0; i < args.size(); i++) {
            if (args.get(i).equalsIgnoreCase(keyword)) {
                return true;
            }
        }

        return false;
    }

    private boolean checkIfToCommandExists(String toc) {

        for (int i = 0; i < getToCommandsList(plugin.getConfiguration()).size(); i++) {
            if (getToCommandsList(plugin.getConfiguration()).get(i).equalsIgnoreCase(toc)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfFromCommandExists(String fromc) {

        for (int i = 0; i < getFromCommandsList(plugin.getConfiguration()).size(); i++) {
            if (getFromCommandsList(plugin.getConfiguration()).get(i).equalsIgnoreCase(fromc)) {
                return true;
            }
        }
        return false;
    }

    public void handleCEditCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (sender.hasPermission("helios.opCommands")) {
            if (args.length < 1) {
                sender.sendMessage(ChatColor.GOLD + CEdit + "CEdit command editor. type /CEdit help or /CEdit ? for commands.");
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                    sendCEditHelpMessage(sender);
                } else if (args[0].equalsIgnoreCase("list")) {
                    sendList(sender);
                }
            } else if (args.length > 1) {
                if (args[0].equalsIgnoreCase("add") && checkIfKeywordExists("fromc", args) && checkIfKeywordExists("toc", args)) {
                    if (!checkIfToCommandExists(getCommandArgs("toc", args))) {
                        if (!getCommandArgs("toc", args).equalsIgnoreCase("") && !getCommandArgs("fromc", "toc", args).equalsIgnoreCase("")) {

                            addFromCommand(getCommandArgs("fromc", "toc", args));
                            addToCommand(getCommandArgs("toc", args));

                            saveConfiguration();

                            sender.sendMessage(ChatColor.GOLD + CEdit + "Command added : from " + getCommandArgs("fromc", "toc", args) + " to " + getCommandArgs("toc", args));
                        } else {
                            sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "You cannot add empty commands!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "This ToCommand already exists!");
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args[1].equalsIgnoreCase("fromc")) {
                        if (checkIfFromCommandExists(getCommandArgs("fromc", args))) {
                            if (!getCommandArgs("fromc", args).equalsIgnoreCase("")) {

                                removeFromCommand(getCommandArgs("fromc", args));
                                saveConfiguration();

                                sender.sendMessage(ChatColor.GOLD + CEdit + "FromCommand " + getCommandArgs("fromc", args) + " and the corresponding tocommand have been removed!");
                            } else {
                                sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "Too few arguments!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "This FromCommand is not registered!");
                        }

                    } else if (args[1].equalsIgnoreCase("toc")) {
                        if (checkIfToCommandExists(getCommandArgs("toc", args))) {
                            if (!getCommandArgs("toc", args).equalsIgnoreCase("")) {

                                removeToCommand(getCommandArgs("toc", args));
                                saveConfiguration();

                                sender.sendMessage(ChatColor.GOLD + CEdit + "ToCommand " + getCommandArgs("toc", args) + " and the corresponding fromcommand have been removed!");
                            } else {
                                sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "Too few arguments!");
                            }
                        } else {
                            sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "This ToCommand is not registered!");
                        }
                    } else {

                        sender.sendMessage(ChatColor.RED + "This command does not exist!");
                    }
                } else if (args[0].equalsIgnoreCase("edit") && checkIfKeywordExists("fromc", args) && checkIfKeywordExists("toc", args)
                        && checkIfKeywordExists("newfromc", args) && checkIfKeywordExists("newtoc", args)) {
                    if (checkIfFromCommandExists(getCommandArgs("fromc", "toc", args)) && checkIfToCommandExists(getCommandArgs("toc", "newfromc", args))) {
                        if (!checkIfToCommandExists(getCommandArgs("newtoc", args))) {
                            if (!getCommandArgs("fromc", "toc", args).equalsIgnoreCase("") && !getCommandArgs("toc", "newfromc", args).equalsIgnoreCase("")
                                    && !getCommandArgs("newfromc", "newtoc", args).equalsIgnoreCase("") && !getCommandArgs("newtoc", args).equalsIgnoreCase("")) {

                                replaceCommand(getCommandArgs("fromc", "toc", args), getCommandArgs("toc", "newfromc", args),
                                        getCommandArgs("newfromc", "newtoc", args), getCommandArgs("newtoc", args));

                                saveConfiguration();
                                sender.sendMessage(ChatColor.GOLD + CEdit + "fromc " + getCommandArgs("fromc", "toc", args) + " toc "
                                        + getCommandArgs("toc", "newfromc", args) + " changed to fromc " + getCommandArgs("newfromc", "newtoc", args) + " toc " + getCommandArgs("newtoc", args));
                            } else { 
                                sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "You cannot use empty commands");
                            }
                        } else {
                            sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "New command is already registered!");
                        }
                    } else {
                        sender.sendMessage(ChatColor.GOLD + CEdit + ChatColor.RED + "This command is not registered!");
                    }



                } else {
                    sender.sendMessage(ChatColor.RED + "This command does not exist!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "This command does not exist!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission!");
        }
    }

    private void saveConfiguration() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        try {
            plugin.getConfiguration().save(file);


        } catch (IOException ex) {
            Logger.getLogger(CEditHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        plugin.reloadConfigAlt();
    }
}
