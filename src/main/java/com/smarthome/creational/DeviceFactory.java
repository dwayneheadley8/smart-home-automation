package com.smarthome.creational;

import com.smarthome.devices.*;

/**
 * Factory class for creating smart devices.
 * <p>This factory simplifies device creation and makes it easy to
 * add new device types without modifying client code.</p>
 * 
 * <h2>Supported Device Types:</h2>
 * <ul>
 *   <li><b>"light"</b> - Creates a {@link Light} instance</li>
 *   <li><b>"thermostat"</b> - Creates a {@link Thermostat} instance</li>
 *   <li><b>"speaker"</b> - Creates a {@link Speaker} instance</li>
 * </ul>
 * 
 * <h2>Example Usage:</h2>
 * <pre>
 * {@code
 * DeviceFactory factory = new DeviceFactory();
 * 
 * // Create different device types
 * SmartDevice light = factory.createDevice("light", "Living Room Light");
 * SmartDevice thermostat = factory.createDevice("thermostat", "Main Thermostat");
 * SmartDevice speaker = factory.createDevice("speaker", "Kitchen Speaker");
 * }
 * </pre>
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class DeviceFactory {
    
    /**
     * Creates a smart device based on the type specified.
     * 
     * @param type The type of device ("light", "thermostat", "speaker")
     * @param name The name for the device
     * @return A SmartDevice instance
     * @throws IllegalArgumentException if type is unknown
     */
    public SmartDevice createDevice(String type, String name) {
        // Convert to lowercase for case-insensitive comparison
        type = type.toLowerCase();
        
        switch (type) {
            case "light":
                System.out.println("[FACTORY] Creating Light: " + name);
                return new Light(name);
                
            case "thermostat":
                System.out.println("[FACTORY] Creating Thermostat: " + name);
                // Default current temperature is 70°F
                return new Thermostat(name, 70.0);
                
            case "speaker":
                System.out.println("[FACTORY] Creating Speaker: " + name);
                return new Speaker(name);
                
            default:
                throw new IllegalArgumentException(
                    "Unknown device type: " + type + 
                    ". Valid types: light, thermostat, speaker"
                );
        }
    }
    
    /**
     * Creates a thermostat with a specific initial temperature.
     * 
     * @param name The name for the thermostat
     * @param currentTemp The current room temperature
     * @return A Thermostat instance
     */
    public SmartDevice createThermostat(String name, double currentTemp) {
        System.out.println("[FACTORY] Creating Thermostat: " + name + 
                         " (Current temp: " + currentTemp + "°F)");
        return new Thermostat(name, currentTemp);
    }
    
    /**
     * Displays all available device types.
     */
    public void displayAvailableDevices() {
        System.out.println("Available device types:");
        System.out.println("  - light");
        System.out.println("  - thermostat");
        System.out.println("  - speaker");
    }
}