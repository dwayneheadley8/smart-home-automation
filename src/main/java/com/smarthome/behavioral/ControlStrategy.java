package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;
import java.util.List;

/**
 * Strategy interface for different control modes.
 * Defines how devices should be controlled.
 * 
 * This implements the Strategy Pattern - allows switching
 * between different control algorithms at runtime.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public interface ControlStrategy {
    /**
     * Controls the given list of devices according to this strategy.
     * 
     * @param devices List of devices to control
     */
    void controlDevices(List<SmartDevice> devices);
    
    /**
     * Gets the name of this control strategy.
     * 
     * @return Strategy name
     */
    String getStrategyName();
    
    /**
     * Gets a description of how this strategy works.
     * 
     * @return Strategy description
     */
    String getDescription();
}