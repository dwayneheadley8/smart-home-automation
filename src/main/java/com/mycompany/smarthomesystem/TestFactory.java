package com.mycompany.smarthomesystem;

import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;

/**
 * Test class to demonstrate the Factory Pattern.
 */
public class TestFactory {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("     FACTORY PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        // Create the factory
        DeviceFactory factory = new DeviceFactory();
        
        System.out.println("Step 1: Display available device types\n");
        factory.displayAvailableDevices();
        System.out.println();
        
        // ===== CREATE DEVICES USING FACTORY =====
        System.out.println("Step 2: Creating devices using Factory\n");
        
        SmartDevice livingRoomLight = factory.createDevice("light", "Living Room Light");
        SmartDevice kitchenLight = factory.createDevice("LIGHT", "Kitchen Light"); // Test case-insensitive
        SmartDevice bedroomClimate = factory.createDevice("thermostat", "Bedroom Climate");
        SmartDevice livingRoomSpeaker = factory.createDevice("speaker", "Living Room Speaker");
        
        System.out.println();
        
        // ===== TEST THE CREATED DEVICES =====
        System.out.println("Step 3: Testing created devices\n");
        
        System.out.println("Testing Light:");
        livingRoomLight.turnOn();
        System.out.println("  " + livingRoomLight.getStatus());
        System.out.println();
        
        System.out.println("Testing Thermostat:");
        bedroomClimate.turnOn();
        System.out.println("  " + bedroomClimate.getStatus());
        System.out.println();
        
        System.out.println("Testing Speaker:");
        livingRoomSpeaker.turnOn();
        System.out.println("  " + livingRoomSpeaker.getStatus());
        System.out.println();
        
        // ===== CREATE THERMOSTAT WITH CUSTOM TEMPERATURE =====
        System.out.println("Step 4: Creating thermostat with custom temperature\n");
        
        SmartDevice kitchenClimate = factory.createThermostat("Kitchen Climate", 65.0);
        kitchenClimate.turnOn();
        System.out.println("  " + kitchenClimate.getStatus());
        System.out.println();
        
        // ===== TEST INVALID DEVICE TYPE =====
        System.out.println("Step 5: Testing error handling\n");
        
        try {
            SmartDevice invalid = factory.createDevice("refrigerator", "Kitchen Fridge");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✓ Error caught correctly: " + e.getMessage());
        }
        System.out.println();
        
        // ===== CREATE MULTIPLE DEVICES EASILY =====
        System.out.println("Step 6: Creating multiple devices in a loop\n");
        
        String[] rooms = {"Bedroom", "Living Room", "Kitchen", "Bathroom"};
        
        System.out.println("Creating lights for all rooms:");
        for (String room : rooms) {
            SmartDevice light = factory.createDevice("light", room + " Light");
        }
        System.out.println();
        
        // ===== DEMONSTRATE POLYMORPHISM =====
        System.out.println("Step 7: Demonstrating polymorphism\n");
        
        // Array of different device types, all treated as SmartDevice
        SmartDevice[] devices = {
            factory.createDevice("light", "Hallway Light"),
            factory.createDevice("thermostat", "Office Climate"),
            factory.createDevice("speaker", "Garage Speaker")
        };
        
        System.out.println("\nTurning on all devices:");
        for (SmartDevice device : devices) {
            device.turnOn();
            System.out.println("  " + device.getStatus());
        }
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("     FACTORY PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Centralized device creation");
        System.out.println("✓ Easy to add new device types");
        System.out.println("✓ Client code simplified");
        System.out.println("✓ Type safety with error handling");
        System.out.println("✓ Supports polymorphism");
        System.out.println("============================================");
    }
}