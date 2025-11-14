package com.smarthome.devices;

import com.smarthome.behavioral.Observer;

/**
 * Interface for all smart devices in the home automation system.
 * All devices must implement these basic operations.
 * 
 * @author Your Name
 * @version 1.0
 */
public interface SmartDevice {
    /**
     * Turns the device on.
     */
    void turnOn();
    
    /**
     * Turns the device off.
     */
    void turnOff();
    
    /**
     * Gets the current status of the device.
     * @return A string describing the device's current state
     */
    String getStatus();
    
    /**
     * Gets the name of the device.
     * @return The device name
     */
    String getName();
    
    /**
     * Adds an observer to be notified of state changes.
     * @param observer The observer to add
     */
    void addObserver(Observer observer);
    
    /**
     * Notifies all observers of a state change.
     */
    void notifyObservers();
}