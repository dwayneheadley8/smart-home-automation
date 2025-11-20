package com.smarthome.devices;

import com.smarthome.behavioral.Observer;
import com.smarthome.structural.FanAdapter; // For documentation link
import com.smarthome.structural.Room; // For documentation link

/**
 * Interface for all smart devices in the home automation system.
 * All devices must implement these basic operations.
 * 
 *  * <p>All devices in the smart home must implement this interface,
 * which provides basic control operations and observer pattern support.</p>
 * 
 * <h2>Implementing Classes:</h2>
 * <ul>
 *   <li>{@link Light} - Smart light with brightness control</li>
 *   <li>{@link Thermostat} - Temperature control system</li>
 *   <li>{@link Speaker} - Audio playback device</li>
 *   <li>{@link Room} - Composite of multiple devices</li>
 *   <li>{@link FanAdapter} - Adapted legacy fan</li>
 * </ul>
 * 
 * @author dwayne headley
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