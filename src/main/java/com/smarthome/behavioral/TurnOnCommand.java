package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;

/**
 * Command to turn on a device.
 * The undo operation turns it back off.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class TurnOnCommand implements Command {
    private SmartDevice device;
    
    /**
     * Creates a command to turn on the specified device.
     * 
     * @param device The device to turn on
     */
    public TurnOnCommand(SmartDevice device) {
        this.device = device;
    }
    
    /**
     * Executes the command - turns on the device.
     */
    @Override
    public void execute() {
        System.out.println("[COMMAND] Executing: Turn On " + device.getName());
        device.turnOn();
    }
    
    /**
     * Undoes the command - turns off the device.
     */
    @Override
    public void undo() {
        System.out.println("[COMMAND] Undoing: Turn On " + device.getName() + " (turning off)");
        device.turnOff();
    }
    
    /**
     * Gets the command description.
     * 
     * @return Description string
     */
    @Override
    public String getDescription() {
        return "Turn On: " + device.getName();
    }
}