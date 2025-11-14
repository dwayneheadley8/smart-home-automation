package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;
import java.util.List;

/**
 * Manual control strategy.
 * User has complete control over all devices.
 * No automatic actions are taken.
 * 
 * @author Your Name
 * @version 1.0
 */
public class ManualControl implements ControlStrategy {
    
    /**
     * In manual mode, no automatic control is applied.
     * User controls everything manually through commands.
     * 
     * @param devices List of devices (not automatically controlled)
     */
    @Override
    public void controlDevices(List<SmartDevice> devices) {
        System.out.println("[MANUAL MODE] User has full control");
        System.out.println("  → Devices waiting for manual commands");
        System.out.println("  → No automatic actions will be taken");
        System.out.println("  → " + devices.size() + " devices under manual control");
    }
    
    /**
     * Gets the strategy name.
     * 
     * @return "Manual Control"
     */
    @Override
    public String getStrategyName() {
        return "Manual Control";
    }
    
    /**
     * Gets the strategy description.
     * 
     * @return Description of manual control
     */
    @Override
    public String getDescription() {
        return "User controls all devices manually. No automation.";
    }
}