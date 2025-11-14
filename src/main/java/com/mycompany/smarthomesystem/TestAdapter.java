package com.mycompany.smarthomesystem;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;
import com.smarthome.structural.OldFan;
import com.smarthome.structural.FanAdapter;
import com.smarthome.structural.Room;

/**
 * Test class to demonstrate the Adapter Pattern.
 */
public class TestAdapter {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("      ADAPTER PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        // ===== TEST 1: SHOW THE PROBLEM =====
        System.out.println("Step 1: The Problem - Incompatible Interface\n");
        
        OldFan vintageFan = new OldFan("Vintage Ceiling Fan");
        
        System.out.println("Old fan has different methods:");
        System.out.println("  - startFan() instead of turnOn()");
        System.out.println("  - stopFan() instead of turnOff()");
        System.out.println("  - getFanStatus() instead of getStatus()");
        System.out.println();
        
        System.out.println("Testing old fan directly:");
        vintageFan.startFan();
        System.out.println("  " + vintageFan.getFanStatus());
        vintageFan.setSpeed(2);
        System.out.println("  " + vintageFan.getFanStatus());
        vintageFan.stopFan();
        System.out.println("  " + vintageFan.getFanStatus());
        System.out.println();
        
        // This won't work - OldFan doesn't implement SmartDevice!
        // SmartDevice device = vintageFan; // ERROR!
        
        System.out.println("❌ Problem: Can't add old fan to smart home system!");
        System.out.println("   (It doesn't implement SmartDevice interface)");
        System.out.println();
        
        // ===== TEST 2: THE SOLUTION - ADAPTER =====
        System.out.println("Step 2: The Solution - Adapter Pattern\n");
        
        System.out.println("Creating another old fan:");
        OldFan bedroomFan = new OldFan("Bedroom Ceiling Fan");
        System.out.println();
        
        System.out.println("Wrapping it with an adapter:");
        SmartDevice adaptedFan = new FanAdapter(bedroomFan);
        System.out.println();
        
        System.out.println("✓ Now it's a SmartDevice!");
        System.out.println("  We can call SmartDevice methods:");
        System.out.println();
        
        // ===== TEST 3: USE ADAPTED FAN LIKE ANY SMART DEVICE =====
        System.out.println("Step 3: Using Adapted Fan as SmartDevice\n");
        
        System.out.println("Calling turnOn() (SmartDevice method):");
        adaptedFan.turnOn();
        System.out.println("  " + adaptedFan.getStatus());
        System.out.println();
        
        System.out.println("Calling turnOff() (SmartDevice method):");
        adaptedFan.turnOff();
        System.out.println("  " + adaptedFan.getStatus());
        System.out.println();
        
        // ===== TEST 4: ADD TO CONTROLLER =====
        System.out.println("Step 4: Adding to CentralController\n");
        
        CentralController controller = CentralController.getInstance();
        DeviceFactory factory = new DeviceFactory();
        
        // Create some regular smart devices
        SmartDevice light = factory.createDevice("light", "Bedroom Light");
        SmartDevice thermostat = factory.createDevice("thermostat", "Bedroom Thermostat");
        
        System.out.println();
        
        // Add both smart and adapted devices
        controller.addDevice(light);
        controller.addDevice(thermostat);
        controller.addDevice(adaptedFan);
        
        System.out.println();
        System.out.println("✓ Adapted fan works alongside smart devices!");
        System.out.println();
        
        // ===== TEST 5: ADD TO ROOM (COMPOSITE) =====
        System.out.println("Step 5: Adding to Room (Composite Pattern)\n");
        
        Room bedroom = new Room("Bedroom");
        bedroom.addDevice(light);
        bedroom.addDevice(thermostat);
        bedroom.addDevice(adaptedFan);
        
        System.out.println();
        bedroom.displayTree();
        System.out.println();
        
        // ===== TEST 6: CONTROL ROOM WITH ADAPTED FAN =====
        System.out.println("Step 6: Controlling Room (includes adapted fan)\n");
        
        System.out.println("Turning on entire bedroom:");
        bedroom.turnOn();
        System.out.println();
        
        System.out.println("Current status:");
        System.out.println(bedroom.getStatus());
        
        // ===== TEST 7: FAN-SPECIFIC FEATURES =====
        System.out.println("Step 7: Accessing Fan-Specific Features\n");
        
        System.out.println("Adapter also provides fan-specific methods:");
        if (adaptedFan instanceof FanAdapter) {
            FanAdapter fanAdapter = (FanAdapter) adaptedFan;
            
            System.out.println("Setting fan to HIGH speed:");
            fanAdapter.setSpeed(3);
            System.out.println("  " + fanAdapter.getStatus());
            System.out.println();
            
            System.out.println("Setting fan to LOW speed:");
            fanAdapter.setSpeed(1);
            System.out.println("  " + fanAdapter.getStatus());
            System.out.println();
        }
        
        // ===== TEST 8: POLYMORPHISM DEMONSTRATION =====
        System.out.println("Step 8: Polymorphism - All Treated Uniformly\n");
        
        SmartDevice[] devices = {
            factory.createDevice("light", "Kitchen Light"),
            factory.createDevice("speaker", "Kitchen Speaker"),
            new FanAdapter(new OldFan("Kitchen Fan"))
        };
        
        System.out.println("Turning on mixed devices (smart + adapted):");
        for (SmartDevice device : devices) {
            device.turnOn();
        }
        System.out.println();
        
        System.out.println("All device statuses:");
        for (SmartDevice device : devices) {
            System.out.println("  - " + device.getStatus());
        }
        System.out.println();
        
        // ===== SCENARIO: SMART HOME WITH LEGACY DEVICES =====
        System.out.println("============================================");
        System.out.println("     SCENARIO: UPGRADING OLD HOME");
        System.out.println("============================================\n");
        
        System.out.println("You have 3 old ceiling fans installed.");
        System.out.println("Instead of replacing them (expensive!),");
        System.out.println("you use adapters to make them smart!\n");
        
        OldFan fan1 = new OldFan("Living Room Fan");
        OldFan fan2 = new OldFan("Bedroom Fan");
        OldFan fan3 = new OldFan("Kitchen Fan");
        
        SmartDevice smartFan1 = new FanAdapter(fan1);
        SmartDevice smartFan2 = new FanAdapter(fan2);
        SmartDevice smartFan3 = new FanAdapter(fan3);
        
        System.out.println("\nAll fans adapted! Now they work in your smart home:");
        smartFan1.turnOn();
        smartFan2.turnOn();
        smartFan3.turnOn();
        
        System.out.println("\n✓ Saved money by adapting instead of replacing!");
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("      ADAPTER PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Integrate legacy devices without modification");
        System.out.println("✓ Old devices work with new system");
        System.out.println("✓ No need to replace working equipment");
        System.out.println("✓ Adapter translates between interfaces");
        System.out.println("✓ Works with Composite, Singleton, Factory");
        System.out.println("✓ Preserves both old and new functionality");
        System.out.println("============================================");
    }
}