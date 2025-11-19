package com.smarthome.structural;

import com.smarthome.devices.SmartDevice;
import com.smarthome.behavioral.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter that makes OldFan compatible with SmartDevice interface.
 * This implements the Adapter Pattern.
 * 
 * The adapter "wraps" the old fan and translates:
 * - turnOn() → startFan()
 * - turnOff() → stopFan()
 * - getStatus() → getFanStatus()
 * 
 * Now the old fan can work in our smart home system!
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class FanAdapter implements SmartDevice {
    private OldFan oldFan;  // The legacy fan we're adapting
    private List<Observer> observers;
    
    /**
     * Creates an adapter for the given legacy fan.
     * 
     * @param oldFan The legacy fan to adapt
     */
    public FanAdapter(OldFan oldFan) {
        this.oldFan = oldFan;
        this.observers = new ArrayList<>();
        System.out.println("[ADAPTER] Created adapter for: " + oldFan.getFanName());
    }
    
    /**
     * Turns on the fan.
     * Translates to oldFan.startFan()
     */
    @Override
    public void turnOn() {
        System.out.println("[ADAPTER] Translating turnOn() → startFan()");
        oldFan.startFan();
        notifyObservers();
    }
    
    /**
     * Turns off the fan.
     * Translates to oldFan.stopFan()
     */
    @Override
    public void turnOff() {
        System.out.println("[ADAPTER] Translating turnOff() → stopFan()");
        oldFan.stopFan();
        notifyObservers();
    }
    
    /**
     * Gets the fan status.
     * Translates to oldFan.getFanStatus()
     * 
     * @return Status string
     */
    @Override
    public String getStatus() {
        return oldFan.getFanStatus();
    }
    
    /**
     * Gets the fan name.
     * 
     * @return The fan name
     */
    @Override
    public String getName() {
        return oldFan.getFanName();
    }
    
    /**
     * Sets the fan speed (additional functionality).
     * This method is specific to fans.
     * 
     * @param speed Speed level (0-3)
     */
    public void setSpeed(int speed) {
        System.out.println("[ADAPTER] Setting speed through adapter");
        oldFan.setSpeed(speed);
        notifyObservers();
    }
    
    /**
     * Gets the current speed.
     * 
     * @return Speed level (0-3)
     */
    public int getSpeed() {
        return oldFan.getSpeed();
    }
    
    /**
     * Gets the wrapped legacy fan (if direct access needed).
     * 
     * @return The OldFan instance
     */
    public OldFan getOldFan() {
        return oldFan;
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