package com.smarthome.structural;

import com.smarthome.devices.SmartDevice;
import com.smarthome.behavioral.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room that contains multiple smart devices.
 * Implements Composite Pattern - a Room IS a SmartDevice that contains SmartDevices.
 * 
 * This allows you to control multiple devices as a single unit.
 * For example: "Turn on Living Room" turns on all devices in that room.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class Room implements SmartDevice {
    private String roomName;
    private List<SmartDevice> devices;
    private List<Observer> observers;
    private boolean isOn; // true if ANY device is on
    
    /**
     * Creates a new Room with the given name.
     * 
     * @param roomName The name of the room
     */
    public Room(String roomName) {
        this.roomName = roomName;
        this.devices = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.isOn = false;
        System.out.println("[ROOM] Room created: " + roomName);
    }
    
    /**
     * Adds a device to this room.
     * 
     * @param device The device to add
     */
    public void addDevice(SmartDevice device) {
        devices.add(device);
        System.out.println("[ROOM] Device added to " + roomName + ": " + device.getName() + 
                         " (Total devices: " + devices.size() + ")");
        notifyObservers();
    }
    
    /**
     * Removes a device from this room.
     * 
     * @param device The device to remove
     * @return true if removed, false if not found
     */
    public boolean removeDevice(SmartDevice device) {
        boolean removed = devices.remove(device);
        if (removed) {
            System.out.println("[ROOM] Device removed from " + roomName + ": " + device.getName());
            notifyObservers();
        }
        return removed;
    }
    
    /**
     * Gets all devices in this room.
     * 
     * @return List of devices
     */
    public List<SmartDevice> getDevices() {
        return new ArrayList<>(devices); // Return copy
    }
    
    /**
     * Gets the number of devices in this room.
     * 
     * @return Device count
     */
    public int getDeviceCount() {
        return devices.size();
    }
    
    /**
     * Turns on ALL devices in the room.
     */
    @Override
    public void turnOn() {
        System.out.println("[ROOM] Turning on all devices in " + roomName + "...");
        for (SmartDevice device : devices) {
            device.turnOn();
        }
        isOn = true;
        System.out.println("[ROOM] All devices in " + roomName + " are now ON");
        notifyObservers();
    }
    
    /**
     * Turns off ALL devices in the room.
     */
    @Override
    public void turnOff() {
        System.out.println("[ROOM] Turning off all devices in " + roomName + "...");
        for (SmartDevice device : devices) {
            device.turnOff();
        }
        isOn = false;
        System.out.println("[ROOM] All devices in " + roomName + " are now OFF");
        notifyObservers();
    }
    
    /**
     * Gets the status of the room and all its devices.
     * 
     * @return Status string
     */
    @Override
    public String getStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Room: ").append(roomName);
        status.append(" (").append(devices.size()).append(" devices)\n");
        
        if (devices.isEmpty()) {
            status.append("  - No devices");
        } else {
            for (SmartDevice device : devices) {
                status.append("  - ").append(device.getStatus()).append("\n");
            }
        }
        
        return status.toString();
    }
    
    /**
     * Gets the room name.
     * 
     * @return The room name
     */
    @Override
    public String getName() {
        return roomName;
    }
    
    /**
     * Sets the room name.
     * 
     * @param name The new room name
     */
    public void setName(String name) {
        this.roomName = name;
        System.out.println("[ROOM] Room renamed to: " + name);
    }
    
    /**
     * Checks if the room has any devices.
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return devices.isEmpty();
    }
    
    /**
     * Displays a tree view of the room and its devices.
     */
    public void displayTree() {
        System.out.println("📁 " + roomName);
        for (int i = 0; i < devices.size(); i++) {
            boolean isLast = (i == devices.size() - 1);
            String prefix = isLast ? "└── " : "├── ";
            System.out.println("   " + prefix + devices.get(i).getName());
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