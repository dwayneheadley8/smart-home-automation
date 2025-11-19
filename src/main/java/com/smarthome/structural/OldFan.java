package com.smarthome.structural;

/**
 * Represents a legacy fan device with its own interface.
 * This fan was manufactured before smart home standards existed.
 * 
 * Notice: This class does NOT implement SmartDevice!
 * It has different method names (startFan vs turnOn).
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class OldFan {
    private String fanName;
    private boolean isRunning;
    private int speed; // 0-3 (Off, Low, Medium, High)
    
    /**
     * Creates a legacy fan with the given name.
     * 
     * @param fanName The fan's identifier
     */
    public OldFan(String fanName) {
        this.fanName = fanName;
        this.isRunning = false;
        this.speed = 0;
        System.out.println("[OLD FAN] Legacy fan initialized: " + fanName);
    }
    
    /**
     * Starts the fan (legacy method name).
     * Note: NOT "turnOn()" like SmartDevice!
     */
    public void startFan() {
        isRunning = true;
        speed = 1; // Default to low speed
        System.out.println("[OLD FAN] " + fanName + " started at speed " + speed);
    }
    
    /**
     * Stops the fan (legacy method name).
     * Note: NOT "turnOff()" like SmartDevice!
     */
    public void stopFan() {
        isRunning = false;
        speed = 0;
        System.out.println("[OLD FAN] " + fanName + " stopped");
    }
    
    /**
     * Sets the fan speed.
     * 
     * @param speed Speed level (0=off, 1=low, 2=medium, 3=high)
     */
    public void setSpeed(int speed) {
        if (speed < 0 || speed > 3) {
            System.out.println("[OLD FAN] Invalid speed! Must be 0-3");
            return;
        }
        
        this.speed = speed;
        this.isRunning = speed > 0;
        
        String speedName = getSpeedName(speed);
        System.out.println("[OLD FAN] " + fanName + " speed set to: " + speedName);
    }
    
    /**
     * Gets the current speed.
     * 
     * @return Current speed (0-3)
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Gets the fan's status (legacy method name).
     * Note: NOT "getStatus()" like SmartDevice!
     * 
     * @return Status string
     */
    public String getFanStatus() {
        String speedName = getSpeedName(speed);
        return fanName + " is " + (isRunning ? "RUNNING" : "STOPPED") + 
               " at speed: " + speedName;
    }
    
    /**
     * Gets the fan name.
     * 
     * @return The fan name
     */
    public String getFanName() {
        return fanName;
    }
    
    /**
     * Checks if fan is running.
     * 
     * @return true if running
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Converts speed number to name.
     */
    private String getSpeedName(int speed) {
        switch (speed) {
            case 0: return "OFF";
            case 1: return "LOW";
            case 2: return "MEDIUM";
            case 3: return "HIGH";
            default: return "UNKNOWN";
        }
    }
}