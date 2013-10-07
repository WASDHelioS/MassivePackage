/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.WASDHelioS.Util.Type;

import java.util.logging.Level;
import me.WASDHelioS.Main.Main;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;

/**
 *
 * @author Nick
 */
public class TypeConverter {

    private Main plugin;

    public TypeConverter(Main main) {
        plugin = main;
    }

    /**
     * Converts a String to a FireworkEffect.Type.
     * @param effect String Effect which you want converted
     *      Values: BALL
     *              BALL_LARGE
     *              BURST
     *              CREEPER
     *              STAR
     * @return FireworkEffect.Type of which the name is the same as param.
     * 
     */
    
    
    public FireworkEffect.Type convertStringToEffect(String effect) {
        FireworkEffect.Type feEffect;
        if (effect == null) {
            effect = "NOTHING";
        }

        if (effect.equalsIgnoreCase("BALL")) {
            feEffect = FireworkEffect.Type.BALL;
        } else if (effect.equalsIgnoreCase("BALL_LARGE")) {
            feEffect = FireworkEffect.Type.BALL_LARGE;
        } else if (effect.equalsIgnoreCase("BURST")) {
            feEffect = FireworkEffect.Type.BURST;
        } else if (effect.equalsIgnoreCase("CREEPER")) {
            feEffect = FireworkEffect.Type.CREEPER;
        } else if (effect.equalsIgnoreCase("STAR")) {
            feEffect = FireworkEffect.Type.STAR;
        } else {
            plugin.getLogger().log(Level.INFO, "ERROR IN EFFECT CONVERSION! "
                    + "check your config file to see if you typed the effect correctly. " + "As of now, it says : {0}", effect);
            feEffect = FireworkEffect.Type.CREEPER;
        }

        return feEffect;
    }

    /**
     * Converts a String to a Color.
     * @param color String color which needs converting
     * @return Color of which the name is the same as param.
     */
    public Color convertStringToColor(String color) {
        Color fColor;
        
        
        if (color == null) {
            color = "NOTHING";
        }

        if (color.equalsIgnoreCase("AQUA")) {
            fColor = Color.AQUA;
        } else if (color.equalsIgnoreCase("BLACK")) {
            fColor = Color.BLACK;
        } else if (color.equalsIgnoreCase("BLUE")) {
            fColor = Color.BLUE;
        } else if (color.equalsIgnoreCase("FUCHSIA")) {
            fColor = Color.FUCHSIA;
        } else if (color.equalsIgnoreCase("GRAY")) {
            fColor = Color.GRAY;
        } else if (color.equalsIgnoreCase("GREEN")) {
            fColor = Color.GREEN;
        } else if (color.equalsIgnoreCase("LIME")) {
            fColor = Color.LIME;
        } else if (color.equalsIgnoreCase("MAROON")) {
            fColor = Color.MAROON;
        } else if (color.equalsIgnoreCase("NAVY")) {
            fColor = Color.NAVY;
        } else if (color.equalsIgnoreCase("OLIVE")) {
            fColor = Color.OLIVE;
        } else if (color.equalsIgnoreCase("ORANGE")) {
            fColor = Color.ORANGE;
        } else if (color.equalsIgnoreCase("PURPLE")) {
            fColor = Color.PURPLE;
        } else if (color.equalsIgnoreCase("RED")) {
            fColor = Color.RED;
        } else if (color.equalsIgnoreCase("SILVER")) {
            fColor = Color.SILVER;
        } else if (color.equalsIgnoreCase("TEAL")) {
            fColor = Color.TEAL;
        } else if (color.equalsIgnoreCase("WHITE")) {
            fColor = Color.WHITE;
        } else if (color.equalsIgnoreCase("YELLOW")) {
            fColor = Color.YELLOW;
        } else {
            plugin.getLogger().log(Level.INFO, "ERROR IN COLOR CONVERSION! "
                    + "check your config file to see if you typed the color correctly. " + "As of now, it says : {0}", color);
            fColor = Color.GREEN;
        }
        return fColor;
    }
}
