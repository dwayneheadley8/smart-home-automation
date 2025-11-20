package com.smarthome.devices;

import com.smarthome.behavioral.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a smart thermostat that controls temperature.
 * Can heat or cool to reach a target temperature.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class Thermostat implements SmartDevice {
    private String name;
    private boolean isOn;
    private double currentTemp;
    private double targetTemp;
    private String mode; // "heating", "cooling", or "off"
    private List<Observer> observers;
    private Timer temperatureAdjustmentTimer;
    
    /**
     * Creates a new Thermostat with the given name and current temperature.
     * Thermostat starts in OFF state.
     * 
     * @param name The name identifier for this thermostat
     * @param currentTemp The current room temperature
     */
    public Thermostat(String name, double currentTemp) {
        this.name = name;
        this.isOn = false;
        this.currentTemp = currentTemp;
        this.targetTemp = 72.0; // Default target
        this.mode = "off";
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void turnOn() {
        isOn = true;
        updateMode();
        System.out.println(name + " turned ON - Mode: " + mode + 
                         ", Target: " + targetTemp + "°F");
        notifyObservers();
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        mode = "off";
        System.out.println(name + " turned OFF");
        notifyObservers();
    }
    
    @Override
    public String getStatus() {
        return name + " is " + (isOn ? "ON" : "OFF") + 
               ", Current: " + currentTemp + "°F" +
               ", Target: " + targetTemp + "°F" +
               ", Mode: " + mode;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Sets the target temperature.
     * 
     * @param targetTemp The desired temperature (50-90°F)
     * @throws IllegalArgumentException if temperature is out of range
     */
    public void setTargetTemp(double targetTemp) {
        if (targetTemp < 50 || targetTemp > 90) {
            throw new IllegalArgumentException("Target temp must be between 50-90°F");
        }
        this.targetTemp = targetTemp;
        if (isOn) {
            updateMode();
        }
        System.out.println(name + " target temperature set to " + targetTemp + "°F");
        
        // Cancel any existing timer
        if (temperatureAdjustmentTimer != null) {
            temperatureAdjustmentTimer.cancel();
        }
        
        // Start a new timer to adjust current temperature after 5 seconds
        temperatureAdjustmentTimer = new Timer();
        temperatureAdjustmentTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentTemp = targetTemp;
                System.out.println(name + " current temperature adjusted to " + currentTemp + "°F");
                notifyObservers();
            }
        }, 5000); // 5 second delay
        
        notifyObservers();
    }
    
    /**
     * Gets the target temperature.
     * 
     * @return The target temperature
     */
    public double getTargetTemp() {
        return targetTemp;
    }
    
    /**
     * Gets the current temperature.
     * 
     * @return The current temperature
     */
    public double getCurrentTemp() {
        return currentTemp;
    }
    
    /**
     * Sets the current mode (heating/cooling/off).
     * 
     * @param mode The mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
        System.out.println(name + " mode changed to: " + mode);
        notifyObservers();
    }
    
    /**
     * Gets the current mode.
     * 
     * @return The current mode
     */
    public String getMode() {
        return mode;
    }
    
    /**
     * Updates the mode based on current vs target temperature.
     */
    private void updateMode() {
        if (!isOn) {
            mode = "off";
        } else if (currentTemp < targetTemp) {
            mode = "heating";
        } else if (currentTemp > targetTemp) {
            mode = "cooling";
        } else {
            mode = "maintaining";
        }
    }
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}