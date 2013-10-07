/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Handler;

import java.util.ArrayList;
import java.util.List;
import me.WASDHelioS.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author Nick
 */
public class ConfigHandler {

    List<String> e = new ArrayList<>();
    List<String> c = new ArrayList<>();

    public ConfigHandler(Main pl) {
        e.add("BALL");
        e.add("BALL_LARGE");
        e.add("BURST");
        e.add("CREEPER");
        e.add("STAR");

        c.add("AQUA");
        c.add("BLACK");
        c.add("BLUE");
        c.add("FUCHSIA");
        c.add("GRAY");
        c.add("GREEN");
        c.add("LIME");
        c.add("MAROON");
        c.add("NAVY");
        c.add("OLIVE");
        c.add("ORANGE");
        c.add("PURPLE");
        c.add("RED");
        c.add("SILVER");
        c.add("TEAL");
        c.add("WHITE");
        c.add("YELLOW");
    }

    /**
     *
     * Checks if this configuration file has empty values or missing paths. If
     * so, it returns true. Otherwise it returns false.
     *
     * @param config - The active configuration file.
     * @return
     */
    public boolean isConfigEmptyValues(FileConfiguration config) {

        if (!config.isSet("enabled.ewab")
                || !config.isSet("enabled.poke")
                || !config.isSet("enabled.cedit")
                || !config.isSet("poke.soft")
                || !config.isSet("poke.normal")
                || !config.isSet("poke.hard")
                || !config.isSet("ewab.world")
                || !config.isSet("ewab.amount")
                || !config.isSet("ewab.offset")
                || !config.isSet("ewab.power")
                || !config.isSet("ewab.rocketeffect")
                || !config.isSet("ewab.rocketcolor")
                || !config.isSet("ewab.rocketfade")
                || !config.isSet("ewab.delay")
                || !config.isSet("cedit.fromcommand")
                || !config.isSet("cedit.tocommand")
                || !config.isSet("FSwearing.words")
                || !config.isSet("FSwearing.message")) {
            return true;

        } else if (!config.getString("enabled.ewab").equalsIgnoreCase("true")
                && !config.getString("enabled.ewab").equalsIgnoreCase("false")
                || !config.getString("enabled.poke").equalsIgnoreCase("true")
                && !config.getString("enabled.poke").equalsIgnoreCase("false")
                || !config.getString("enabled.cedit").equalsIgnoreCase("true")
                && !config.getString("enabled.cedit").equalsIgnoreCase("false")) {
            return true;
        }

        if (!config.isInt("ewab.amount")
                || !config.isInt("ewab.offset")
                || !config.isInt("ewab.power")
                || !config.isInt("ewab.delay")
                || !config.isInt("poke.soft")
                || !config.isInt("poke.normal")
                || !config.isInt("poke.hard")) {
            return true;
        }

        boolean shouldReturnTrue;
        List<String> effects = config.getStringList("ewab.rocketeffect");
        for (int i = 0; i < effects.size(); i++) {
            shouldReturnTrue = false;

            nestedLoop:
            for (int j = 0; j < e.size(); j++) {
                if (effects.get(i).equals(e.get(j))) {
                    shouldReturnTrue = true;
                    break nestedLoop;
                } else {
                    shouldReturnTrue = false;
                }
            }
            if (!shouldReturnTrue) {
                return true;
            }
        }

        List<String> colors = config.getStringList("ewab.rocketcolor");
        for (int i = 0; i < colors.size(); i++) {
            shouldReturnTrue = false;

            nestedLoop:
            for (int j = 0; j < c.size(); j++) {
                if (colors.get(i).equals(c.get(j))) {
                    shouldReturnTrue = true;
                    break nestedLoop;
                } else {
                    shouldReturnTrue = false;
                }
            }
            if (!shouldReturnTrue) {
                return true;
            }
        }

        List<String> fades = config.getStringList("ewab.rocketfade");
        for (int i = 0; i < fades.size(); i++) {
            shouldReturnTrue = false;

            nestedLoop:
            for (int j = 0; j < c.size(); j++) {
                if (fades.get(i).equals(c.get(j))) {
                    shouldReturnTrue = true;
                    break nestedLoop;
                } else {
                    shouldReturnTrue = false;
                }
            }
            if (!shouldReturnTrue) {
                return true;
            }
        }



        return false;
    }
}
