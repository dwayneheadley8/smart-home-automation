package com.smarthome.structural;

import com.smarthome.devices.SmartDevice;
import com.smarthome.behavioral.Observer;

/**
 * Abstract base class for device decorators.
 * Implements the Decorator Pattern.
 * 
 * Decorators "wrap" a device and add additional functionality
 * without modifying the original device class.
 * 
 * @author Your Name
 * @version 1.0
 */
public abstract class DeviceDecorator implements SmartDevice {
    protected SmartDevice wrappedDevice;
    
    /**
     * Creates a decorator wrapping the given device.
     * 
     * @param device The device to wrap/decorate
     */
    public DeviceDecorator(SmartDevice device) {
        this.wrappedDevice = device;
    }
    
    /**
     * Default implementation delegates to wrapped device.
     */
    @Override
    public void turnOn() {
        wrappedDevice.turnOn();
    }
    
    /**
     * Default implementation delegates to wrapped device.
     */
    @Override
    public void turnOff() {
        wrappedDevice.turnOff();
    }
    
    /**
     * Default implementation delegates to wrapped device.
     */
    @Override
    public String getStatus() {
        return wrappedDevice.getStatus();
    }
    
    /**
     * Default implementation delegates to wrapped device.
     */
    @Override
    public String getName() {
        return wrappedDevice.getName();
    }
    
    /**
     * Default implementation delegates to wrapped device.
     */
    @Override
    public void addObserver(Observer observer) {
        wrappedDevice.addObserver(observer);
    }
    
    /**
     * Default implementation delegates to wrapped device.
     */
    @Override
    public void notifyObservers() {
        wrappedDevice.notifyObservers();
    }
    
    /**
     * Gets the wrapped device.
     * Useful for accessing the original device.
     * 
     * @return The wrapped device
     */
    public SmartDevice getWrappedDevice() {
        return wrappedDevice;
    }
}