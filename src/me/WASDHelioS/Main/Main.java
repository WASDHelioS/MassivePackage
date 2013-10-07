/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Main;

import java.io.File;
import java.util.HashMap;
import me.WASDHelioS.Handler.ConfigHandler;
import me.WASDHelioS.Handler.SubCommandHandler.CEditHandler;
import me.WASDHelioS.Handler.SubCommandHandler.EwabHandler;
import me.WASDHelioS.Handler.SubCommandHandler.MPHandler;
import me.WASDHelioS.Handler.SubCommandHandler.PokeHandler;
import me.WASDHelioS.Listener.CommandPreProcessListener;
import me.WASDHelioS.Listener.PlayerListener;
import me.WASDHelioS.Util.Type.PokeType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Nick
 */
public class Main extends JavaPlugin {

    private CommandPreProcessListener preProcess;
    private PokeType type;
    private final PlayerListener pl = new PlayerListener(this);
    private final ConfigHandler cfH = new ConfigHandler(this);
    private boolean ewab_isEnabled;
    private boolean poke_isEnabled;
    private boolean cedit_isEnabled;
    //HashMap Player, Boolean to check per player if it's enabled or not.
    private static HashMap<Player, Boolean> ee = new HashMap<>();
    private FileConfiguration config;

    /**
     * Method which gets called on resetting or starting this plugin.
     * It creates a config if necessary, reloads it and sends messages about
     * activation.
     */
    public void onEnableEss() {


        
        
        createConfig();
        reloadConfig();
        getLogger().info("[MassivePackage] is now ENABLED.");
        ewab_isEnabled = config.getBoolean("enabled.ewab");
        poke_isEnabled = config.getBoolean("enabled.poke");
        cedit_isEnabled = config.getBoolean("enabled.cedit");

        if (cedit_isEnabled) {
            getLogger().info("[CommandEdit] is enabled. To change this, edit the config file.");
        } else {
            getLogger().info("[CommandEdit] is disabled. To change this, edit the config file.");
        }
        if (poke_isEnabled) {
            getLogger().info("[Poke] is enabled. To change this, edit the config file.");
        } else {
            getLogger().info("[Poke] is disabled. To change this, edit the config file.");
        }
        if (ewab_isEnabled) {
            getLogger().info("[EnterWithABang] is enabled. To change this, edit the config file.");
        } else {
            getLogger().info("[EnterWithABang] is disabled. To change this, edit the config file.");
        }
        
        config.options().copyDefaults(true);
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {

        config = this.getConfig();
        onEnableEss();


        preProcess = new CommandPreProcessListener(this);


        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(pl, this);
        pm.registerEvents(preProcess, this);
        
        MPHandler mp = new MPHandler(this);
        
        getCommand("CEdit").setExecutor(new CEditHandler(this));
        getCommand("MassivePackage").setExecutor(mp);
        getCommand("MP").setExecutor(mp);
        getCommand("Poke").setExecutor(new PokeHandler(this));
        getCommand("ewab").setExecutor(new EwabHandler(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("[MassivePackage] is now DISABLED");
    }

    public HashMap<Player, Boolean> getHashMap() {
        return ee;
    }

    public void setType(PokeType type) {
        this.type = type;
    }

    public PokeType getType() {
        return type;
    }

    public boolean isEWABEnabled() {
        return ewab_isEnabled;
    }

    public boolean isPokeEnabled() {
        return poke_isEnabled;
    }

    public boolean isCeditEnabled() {
        return cedit_isEnabled;
    }

    public FileConfiguration getConfiguration() {
        return config;
    }
    
    public ConfigHandler getConfigHandler() {
        return cfH;
    }

    /**
     * Creates a config if necessary.
     */
    private void createConfig() {
        File configfile = new File(getDataFolder(), "config.yml");
        if (!configfile.exists()) {
            this.config.options().copyDefaults();
            this.saveDefaultConfig();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("helios.opCommands")) {

                    Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[MassivePackage] A NEW CONFIG FILE HAS BEEN CREATED!!");
                }
            }
        } else if (cfH.isConfigEmptyValues(config)) {
            resetConfig();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("helios.opCommands")) {
                    Bukkit.getServer().broadcastMessage(ChatColor.RED + "[MassivePackage] YOUR CONFIG FILE HAS BEEN RESET DUE TO ERORS! ");
                }
            }
        }
    }
    /**
     * Resets the config to the default one.
     */

    public void resetConfig() {
        File conf = new File(getDataFolder(), "config.yml");
        conf.delete();
        saveDefaultConfig();
        reloadConfig();
    }

    /**
     * Reloads the config differently from reloadConfig().
     * this one gets the file in the plugin directory and loads that one.
     * (In use for runtime reloading.)
     */
    public void reloadConfigAlt() {
        File conf = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(conf);
    }
}
