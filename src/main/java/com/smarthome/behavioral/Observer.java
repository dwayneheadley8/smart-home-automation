package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;

/**
 * Observer interface for the Observer Pattern.
 * Will be fully implemented later.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public interface Observer {
    /**
     * Called when a device's state changes.
     * 
     * @param device The device that changed
     */
    void update(SmartDevice device);
}