package com.smarthome.devices;

import com.smarthome.behavioral.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a smart light with adjustable brightness.
 * The light can be turned on/off and brightness can be adjusted.
 * 
 * @author Your Name
 * @version 1.0
 */
public class Light implements SmartDevice {
    private String name;
    private boolean isOn;
    private int brightness; // 0-100
    private List<Observer> observers;
    
    /**
     * Creates a new Light with the given name.
     * Light starts in OFF state with 0% brightness.
     * 
     * @param name The name identifier for this light
     */
    public Light(String name) {
        this.name = name;
        this.isOn = false;
        this.brightness = 0;
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void turnOn() {
        isOn = true;
        brightness = 100;
        System.out.println(name + " turned ON - Brightness: 100%");
        notifyObservers();
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        brightness = 0;
        System.out.println(name + " turned OFF");
        notifyObservers();
    }
    
    @Override
    public String getStatus() {
        return name + " is " + (isOn ? "ON" : "OFF") + 
               ", Brightness: " + brightness + "%";
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Sets the brightness level of the light.
     * 
     * @param brightness Value from 0-100
     * @throws IllegalArgumentException if brightness is out of range
     */
    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 100) {
            throw new IllegalArgumentException("Brightness must be between 0 and 100");
        }
        this.brightness = brightness;
        isOn = brightness > 0; // Automatically turn on if brightness > 0
        System.out.println(name + " brightness set to " + brightness + "%");
        notifyObservers();
    }
    
    /**
     * Gets the current brightness level.
     * 
     * @return Current brightness (0-100)
     */
    public int getBrightness() {
        return brightness;
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