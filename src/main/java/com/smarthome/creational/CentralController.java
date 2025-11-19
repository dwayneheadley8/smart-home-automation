package com.smarthome.creational;

import com.smarthome.devices.SmartDevice;
import com.smarthome.behavioral.Observer;
import com.smarthome.behavioral.Command;
import com.smarthome.behavioral.ControlStrategy;  
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Central controller for the smart home system.
 * Implements Singleton Pattern - only ONE instance can exist.
 * 
 * This is the "brain" of the smart home that manages all devices.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class CentralController implements Observer {
    
    private Stack<Command> commandHistory;
    private Stack<Command> redoHistory;
    private ControlStrategy controlStrategy;

    // The single instance (static = shared across all uses)
    private static CentralController instance = null;
    
    // List of all devices in the system
    private List<SmartDevice> devices;
    
    /**
     * Private constructor prevents direct instantiation.
     * This is KEY to Singleton pattern!
     */

    private CentralController() {
    devices = new ArrayList<>();
    commandHistory = new Stack<>();
    redoHistory = new Stack<>();
    controlStrategy = null; // No strategy by default
    System.out.println("[CONTROLLER] CentralController initialized");
    }
    
    /**
     * Gets the single instance of CentralController.
     * Creates it if it doesn't exist yet (lazy initialization).
     * 
     * @return The one and only CentralController instance
     */
    public static CentralController getInstance() {
        if (instance == null) {
            instance = new CentralController();
        }
        return instance;
    }
    
    /**
     * Adds a device to the system.
     * The controller will observe this device for state changes.
     * 
     * @param device The device to add
     */
    public void addDevice(SmartDevice device) {
        devices.add(device);
        device.addObserver(this); // Controller observes all devices
        System.out.println("[CONTROLLER] Device added: " + device.getName() + 
                         " (Total devices: " + devices.size() + ")");
    }
    
    /**
     * Removes a device from the system.
     * 
     * @param device The device to remove
     * @return true if removed, false if not found
     */
    public boolean removeDevice(SmartDevice device) {
        boolean removed = devices.remove(device);
        if (removed) {
            System.out.println("[CONTROLLER] Device removed: " + device.getName());
        }
        return removed;
    }
    
    /**
     * Gets a device by name.
     * 
     * @param name The name of the device
     * @return The device, or null if not found
     */
    public SmartDevice getDevice(String name) {
        for (SmartDevice device : devices) {
            if (device.getName().equalsIgnoreCase(name)) {
                return device;
            }
        }
        return null;
    }
    
    /**
     * Gets all devices in the system.
     * 
     * @return A copy of the device list
     */
    public List<SmartDevice> getAllDevices() {
        return new ArrayList<>(devices); // Return a copy for safety
    }
    
    /**
     * Gets the total number of devices.
     * 
     * @return Number of devices
     */
    public int getDeviceCount() {
        return devices.size();
    }
    
    /**
     * Turns on all devices in the system.
     */
    public void turnOnAllDevices() {
        System.out.println("[CONTROLLER] Turning on all devices...");
        for (SmartDevice device : devices) {
            device.turnOn();
        }
        System.out.println("[CONTROLLER] All devices turned on");
    }
    
    /**
     * Turns off all devices in the system.
     */
    public void turnOffAllDevices() {
        System.out.println("[CONTROLLER] Turning off all devices...");
        for (SmartDevice device : devices) {
            device.turnOff();
        }
        System.out.println("[CONTROLLER] All devices turned off");
    }
    
    /**
     * Displays status of all devices.
     */
    public void displayAllDevices() {
        System.out.println("\n========== DEVICE STATUS ==========");
        if (devices.isEmpty()) {
            System.out.println("No devices registered");
        } else {
            for (int i = 0; i < devices.size(); i++) {
                System.out.println((i + 1) + ". " + devices.get(i).getStatus());
            }
        }
        System.out.println("===================================\n");
    }
    
    /**
     * Observer pattern implementation.
     * Called when a device's state changes.
     * 
     * @param device The device that changed
     */
@Override
public void update(SmartDevice device) {
    System.out.println("┌─────────────────────────────────────────────┐");
    System.out.println("│  🔔 OBSERVER NOTIFICATION RECEIVED          │");
    System.out.println("├─────────────────────────────────────────────┤");
    System.out.println("│  Device: " + device.getName());
    System.out.println("│  Status: " + device.getStatus());
    System.out.println("│  Time: " + java.time.LocalTime.now());
    System.out.println("└─────────────────────────────────────────────┘");
    
    // In a real GUI application, this would:
    // - Update the device's visual representation
    // - Change button states (on/off)
    // - Update status labels
    // - Refresh energy displays
    // - Log to activity feed
}
    
    /**
     * Resets the singleton instance (useful for testing).
     * WARNING: Use with caution!
     */
    public static void resetInstance() {
        instance = null;
        System.out.println("[CONTROLLER] Instance reset");
    }
    
    /**
 * Executes a command and adds it to history.
 * 
 * @param command The command to execute
 */
public void executeCommand(Command command) {
    System.out.println("[CONTROLLER] Executing command: " + command.getDescription());
    command.execute();
    commandHistory.push(command);
    redoHistory.clear(); // Clear redo history when new command executed
    System.out.println("[CONTROLLER] Command history size: " + commandHistory.size());
}

/**
 * Undoes the last command.
 * 
 * @return true if undo successful, false if no commands to undo
 */
public boolean undoLastCommand() {
    if (commandHistory.isEmpty()) {
        System.out.println("[CONTROLLER] No commands to undo!");
        return false;
    }
    
    Command command = commandHistory.pop();
    System.out.println("[CONTROLLER] Undoing command: " + command.getDescription());
    command.undo();
    redoHistory.push(command);
    System.out.println("[CONTROLLER] Command history size: " + commandHistory.size());
    return true;
}

/**
 * Redoes the last undone command.
 * 
 * @return true if redo successful, false if no commands to redo
 */
public boolean redoLastCommand() {
    if (redoHistory.isEmpty()) {
        System.out.println("[CONTROLLER] No commands to redo!");
        return false;
    }
    
    Command command = redoHistory.pop();
    System.out.println("[CONTROLLER] Redoing command: " + command.getDescription());
    command.execute();
    commandHistory.push(command);
    return true;
}

/**
 * Gets the command history.
 * 
 * @return List of executed commands
 */
public Stack<Command> getCommandHistory() {
    return new Stack<>(); // Return copy
}

/**
 * Clears the command history.
 */
public void clearHistory() {
    commandHistory.clear();
    redoHistory.clear();
    System.out.println("[CONTROLLER] Command history cleared");
}

/**
 * Displays the command history.
 */
public void displayCommandHistory() {
    System.out.println("\n═══════════════════════════════════");
    System.out.println("     COMMAND HISTORY");
    System.out.println("═══════════════════════════════════");
    
    if (commandHistory.isEmpty()) {
        System.out.println("  No commands in history");
    } else {
        for (int i = 0; i < commandHistory.size(); i++) {
            System.out.println((i + 1) + ". " + commandHistory.get(i).getDescription());
        }
    }
    
    System.out.println("═══════════════════════════════════\n");
}

/**
 * Sets the control strategy.
 * 
 * @param strategy The control strategy to use
 */
public void setControlStrategy(ControlStrategy strategy) {
    this.controlStrategy = strategy;
    System.out.println("[CONTROLLER] Control strategy changed to: " + strategy.getStrategyName());
    System.out.println("[CONTROLLER] " + strategy.getDescription());
}

/**
 * Gets the current control strategy.
 * 
 * @return The current strategy, or null if none set
 */
public ControlStrategy getControlStrategy() {
    return controlStrategy;
}

/**
 * Activates the current control strategy.
 * Applies the strategy to all devices.
 */
public void activateControlStrategy() {
    if (controlStrategy == null) {
        System.out.println("[CONTROLLER] No control strategy set!");
        System.out.println("[CONTROLLER] Using manual control by default");
        return;
    }
    
    System.out.println("[CONTROLLER] Activating: " + controlStrategy.getStrategyName());
    controlStrategy.controlDevices(devices);
}

}