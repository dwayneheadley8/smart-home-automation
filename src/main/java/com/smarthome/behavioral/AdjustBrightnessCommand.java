package com.smarthome.behavioral;

import com.smarthome.devices.Light;

/**
 * Command to adjust the brightness of a light.
 * The undo operation restores the previous brightness.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class AdjustBrightnessCommand implements Command {
    private Light light;
    private int newBrightness;
    private int previousBrightness;
    
    /**
     * Creates a command to adjust light brightness.
     * 
     * @param light The light to adjust
     * @param newBrightness The new brightness level (0-100)
     */
    public AdjustBrightnessCommand(Light light, int newBrightness) {
        this.light = light;
        this.newBrightness = newBrightness;
        this.previousBrightness = light.getBrightness(); // Remember current!
    }
    
    /**
     * Executes the command - sets new brightness.
     */
    @Override
    public void execute() {
        System.out.println("[COMMAND] Executing: Adjust " + light.getName() + 
                         " brightness to " + newBrightness + "%");
        light.setBrightness(newBrightness);
    }
    
    /**
     * Undoes the command - restores previous brightness.
     */
    @Override
    public void undo() {
        System.out.println("[COMMAND] Undoing: Adjust " + light.getName() + 
                         " brightness (restoring to " + previousBrightness + "%)");
        light.setBrightness(previousBrightness);
    }
    
    /**
     * Gets the command description.
     * 
     * @return Description string
     */
    @Override
    public String getDescription() {
        return "Adjust Brightness: " + light.getName() + 
               " (" + previousBrightness + "% → " + newBrightness + "%)";
    }
}