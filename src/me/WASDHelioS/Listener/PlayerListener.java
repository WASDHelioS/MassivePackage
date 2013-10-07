/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Listener;

import me.WASDHelioS.Instantiation.FireworkCreator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.WASDHelioS.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 *
 * @author Nick
 */
public class PlayerListener implements Listener {

    private Main plugin;
    private final FireworkCreator fwC;

    public PlayerListener(Main poke) {
        plugin = poke;
        fwC = new FireworkCreator(plugin);
    }

    /**
     * 
     * Eventhandler which handles the poke-plugin of this package. 
     * 
     * @param ev EntityDamageByEntityEvent (Basically if one entity is damaged by
     * another, no other damage types go into this.)
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerInteract(EntityDamageByEntityEvent ev) {

        //Gets damager and damagee
        Entity p = ev.getDamager();
        Entity d = ev.getEntity();

        //casting from Entity to Player
        if (p instanceof Player) {
            if (d instanceof Player) {
                Player player = (Player) p;
                Player damagedPlayer = (Player) d;

                //supposedly (NEEDS DEBUGGING) only activates when player has nothing
                //selected.
                if (player.getItemInHand().getType() == Material.AIR);
                {
                    if (plugin.getHashMap().containsKey(player)) {
                        if (plugin.getHashMap().get(player)) {
                            float knockback = 0;

                            switch (plugin.getType().ordinal()) {
                                //OFF
                                case 0:
                                    knockback = 0;
                                    break;
                                //SOFT
                                case 1:
                                    knockback = plugin.getConfig().getInt("poke.soft");
                                    break;
                                //NORMAL
                                case 2:
                                    knockback = plugin.getConfig().getInt("poke.normal");
                                    break;
                                //HARD
                                case 3:
                                    knockback = plugin.getConfig().getInt("poke.hard");
                                    break;
                            }
                            //Cancels damage
                            ev.setDamage(0);

                            //Velocity vector setting.
                            damagedPlayer.setVelocity(damagedPlayer.getVelocity().add(damagedPlayer.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(knockback)));

                            //cancels further events triggered by EntityDamageByEntityEvents.
                            ev.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
    /**
     * 
     * EventHandler which handles the creation of fireworks when a new player joins.
     * 
     * @param pje PlayerJoinEvent (happens when a player joins.)
     */

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent pje) {

        if (plugin.isEWABEnabled()) {
            //If the player hasnt played before, Bukkit schedules a delayed task: 

            String world;
            world = plugin.getConfig().getString("ewab.world");
            if (world.equalsIgnoreCase(null) || world.equalsIgnoreCase(" ")) {
                world = "world";
            }
            if (pje.getPlayer().getWorld() == Bukkit.getServer().getWorld(world)) {
                if (!pje.getPlayer().hasPlayedBefore()) {
                    Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {

                        @Override
                        public void run() {

                            fwC.createFireworkInstance(pje.getPlayer());
                        }
                    }, plugin.getConfig().getInt("ewab.delay")); // delay
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent ev) {
        
        String message = ev.getMessage();
        
        List<String> wordslist = plugin.getConfiguration().getStringList("FSwearing.words");
        
        if(containsWord(wordslist, message)) {
            
            ev.getPlayer().sendMessage(ChatColor.RED + plugin.getConfiguration().getString("FSwearing.message"));
            
            ev.setCancelled(true);
        }
    }
    
    
    private static boolean containsWord(List<String> words, String sentence) {
        for(String word : words) {
            if(sentence.contains(word)) {
                return true;
            }
        }
        return false;
    }
}
