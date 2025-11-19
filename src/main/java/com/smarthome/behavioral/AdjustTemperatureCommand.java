package com.smarthome.behavioral;

import com.smarthome.devices.Thermostat;

/**
 * Command to adjust the temperature of a thermostat.
 * The undo operation restores the previous temperature.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class AdjustTemperatureCommand implements Command {
    private Thermostat thermostat;
    private double newTemp;
    private double previousTemp;
    
    /**
     * Creates a command to adjust thermostat temperature.
     * 
     * @param thermostat The thermostat to adjust
     * @param newTemp The new target temperature
     */
    public AdjustTemperatureCommand(Thermostat thermostat, double newTemp) {
        this.thermostat = thermostat;
        this.newTemp = newTemp;
        this.previousTemp = thermostat.getTargetTemp(); // Remember current!
    }
    
    /**
     * Executes the command - sets new temperature.
     */
    @Override
    public void execute() {
        System.out.println("[COMMAND] Executing: Adjust " + thermostat.getName() + 
                         " to " + newTemp + "°F");
        thermostat.setTargetTemp(newTemp);
    }
    
    /**
     * Undoes the command - restores previous temperature.
     */
    @Override
    public void undo() {
        System.out.println("[COMMAND] Undoing: Adjust " + thermostat.getName() + 
                         " (restoring to " + previousTemp + "°F)");
        thermostat.setTargetTemp(previousTemp);
    }
    
    /**
     * Gets the command description.
     * 
     * @return Description string
     */
    @Override
    public String getDescription() {
        return "Adjust Temperature: " + thermostat.getName() + 
               " (" + previousTemp + "°F → " + newTemp + "°F)";
    }
}