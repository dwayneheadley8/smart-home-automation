package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;

/**
 * Command to turn off a device.
 * The undo operation turns it back on.
 * 
 * @author Your Name
 * @version 1.0
 */
public class TurnOffCommand implements Command {
    private SmartDevice device;
    
    /**
     * Creates a command to turn off the specified device.
     * 
     * @param device The device to turn off
     */
    public TurnOffCommand(SmartDevice device) {
        this.device = device;
    }
    
    /**
     * Executes the command - turns off the device.
     */
    @Override
    public void execute() {
        System.out.println("[COMMAND] Executing: Turn Off " + device.getName());
        device.turnOff();
    }
    
    /**
     * Undoes the command - turns on the device.
     */
    @Override
    public void undo() {
        System.out.println("[COMMAND] Undoing: Turn Off " + device.getName() + " (turning on)");
        device.turnOn();
    }
    
    /**
     * Gets the command description.
     * 
     * @return Description string
     */
    @Override
    public String getDescription() {
        return "Turn Off: " + device.getName();
    }
}