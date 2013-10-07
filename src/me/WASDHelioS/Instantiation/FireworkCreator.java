/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Instantiation;

import me.WASDHelioS.Util.Type.TypeConverter;
import java.util.ArrayList;
import java.util.List;
import me.WASDHelioS.Main.Main;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

/**
 *
 * @author Nick
 */
public class FireworkCreator {

    private Main plugin;
    private TypeConverter tc;

    public FireworkCreator(Main main) {
        plugin = main;
        tc = new TypeConverter(main);
    }

    /**
     * 
     * Creates firework(Amount, effects and colors based on the config file.)
     * 
     * @param p Player who just joined.
     */
    public void createFireworkInstance(Player p) {

        FileConfiguration c = plugin.getConfig();

        //gets offset and amount, catching possible errors of minuses or nulltypes.
        //***CURRENTLY LIMITED TO AMOUNT=MAX(8) AND OFFSET=MAX(10).
        int offset = c.getInt("ewab.offset");
        int amount = c.getInt("ewab.amount");
        if (amount > 8) {
            amount = 8;
        }
        if (amount < 1) {
            amount = 1;
        }
        if (offset > 10) {
            offset = 10;
        }
        if (offset < 1) {
            offset = 1;
        }

        //variable ArrayList to be used in the for loop for rocket creation.
        ArrayList<Location> locs = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            switch (i) {
                case 0:
                    locs.add(p.getLocation().add(offset, 0, 0));
                    break;
                case 1:
                    locs.add(p.getLocation().add(0 - offset, 0, 0));
                    break;
                case 2:
                    locs.add(p.getLocation().add(0, 0, offset));
                    break;
                case 3:
                    locs.add(p.getLocation().add(0, 0, 0 - offset));
                    break;
                case 4:
                    locs.add(p.getLocation().add(offset, 0, offset));

                    break;
                case 5:
                    locs.add(p.getLocation().add(0 - offset, 0, offset));
                    break;
                case 6:
                    locs.add(p.getLocation().add(offset, 0, 0 - offset));
                    break;
                case 7:
                    locs.add(p.getLocation().add(0 - offset, 0, 0 - offset));
                    break;
            }
        }

        List<String> rocketEffects = c.getStringList("ewab.rocketeffect");
        List<String> rocketColors = c.getStringList("ewab.rocketcolor");
        List<String> rocketFades = c.getStringList("ewab.rocketfade");
        
        //Determines that amount of rockets will be created
        for (int i = 0; i < amount; i++) {
            Location loc = locs.get(i);

            //switch case on 'i' of the for-loop.
            //This basically turns the different values in the config
            //file as well as the 4 locations into a single variable 
            //to be used in the for-loop.
            String rocketEffect = "" ;
            String rocketColor = "" ;
            String rocketFade = "" ;
            if (rocketEffects.size() >= i) {
                rocketEffect = rocketEffects.get(i);
            }
            if(rocketColors.size() >= i) {
                rocketColor = rocketColors.get(i);
            }
            if(rocketFades.size() >= i) {
                rocketFade = rocketFades.get(i);
            }
            
            //Spawns a firework.class at specific location in the current world

            Firework f = (Firework) p.getWorld().spawn(loc, Firework.class);

            //Creates a new string effect based on rocket(i)effect
            //from the config file.
            //Then creates a FireworkEffect.Type.
            //Then runs the string 'effect' through a converter,
            //which turns the string into a FireworkEffect.Type.

            FireworkEffect.Type feEffect;
            feEffect = tc.convertStringToEffect(rocketEffect);

            //Same goes as above, but with rocket(i)color.

            Color fColor;
            fColor = tc.convertStringToColor(rocketColor);

            //Same goes as above, but with rocket(i)fade.

            Color cFade;
            cFade = tc.convertStringToColor(rocketFade);

            //gets fireworkMeta from firework,
            //adds the effects to the meta,
            //sets the power,
            //sets the newly acquired meta into the firework.

            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder().flicker(true).withTrail().with(feEffect).withColor(fColor).withFade(cFade).build());
            fm.setPower(c.getInt("ewab.power"));
            f.setFireworkMeta(fm);
        }

    }
}
