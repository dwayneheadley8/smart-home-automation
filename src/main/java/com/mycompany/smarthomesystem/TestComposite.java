package com.mycompany.smarthomesystem;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;
import com.smarthome.structural.Room;

/**
 * Test class to demonstrate the Composite Pattern.
 */
public class TestComposite {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("     COMPOSITE PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        // Create factory and controller
        DeviceFactory factory = new DeviceFactory();
        CentralController controller = CentralController.getInstance();
        
        // ===== TEST 1: CREATE INDIVIDUAL DEVICES =====
        System.out.println("Step 1: Creating Individual Devices\n");
        
        // Living Room devices
        SmartDevice livingRoomLight = factory.createDevice("light", "Living Room Light");
        SmartDevice livingRoomThermostat = factory.createDevice("thermostat", "Living Room Thermostat");
        SmartDevice livingRoomSpeaker = factory.createDevice("speaker", "Living Room Speaker");
        
        // Bedroom devices
        SmartDevice bedroomLight = factory.createDevice("light", "Bedroom Light");
        SmartDevice bedroomThermostat = factory.createDevice("thermostat", "Bedroom Thermostat");
        
        // Kitchen devices
        SmartDevice kitchenLight = factory.createDevice("light", "Kitchen Light");
        SmartDevice kitchenSpeaker = factory.createDevice("speaker", "Kitchen Speaker");
        
        System.out.println();
        
        // ===== TEST 2: CREATE ROOMS (COMPOSITES) =====
        System.out.println("Step 2: Creating Rooms (Composites)\n");
        
        Room livingRoom = new Room("Living Room");
        Room bedroom = new Room("Bedroom");
        Room kitchen = new Room("Kitchen");
        
        System.out.println();
        
        // ===== TEST 3: ADD DEVICES TO ROOMS =====
        System.out.println("Step 3: Adding Devices to Rooms\n");
        
        System.out.println("Adding devices to Living Room:");
        livingRoom.addDevice(livingRoomLight);
        livingRoom.addDevice(livingRoomThermostat);
        livingRoom.addDevice(livingRoomSpeaker);
        System.out.println();
        
        System.out.println("Adding devices to Bedroom:");
        bedroom.addDevice(bedroomLight);
        bedroom.addDevice(bedroomThermostat);
        System.out.println();
        
        System.out.println("Adding devices to Kitchen:");
        kitchen.addDevice(kitchenLight);
        kitchen.addDevice(kitchenSpeaker);
        System.out.println();
        
        // ===== TEST 4: DISPLAY ROOM STRUCTURE =====
        System.out.println("Step 4: Room Structure (Tree View)\n");
        
        livingRoom.displayTree();
        System.out.println();
        bedroom.displayTree();
        System.out.println();
        kitchen.displayTree();
        System.out.println();
        
        // ===== TEST 5: CONTROL ENTIRE ROOM AT ONCE =====
        System.out.println("Step 5: Composite Pattern Magic - Control Entire Room!\n");
        
        System.out.println("═══ Turning on Living Room ═══");
        livingRoom.turnOn(); // Turns on ALL 3 devices!
        System.out.println();
        
        // ===== TEST 6: CHECK ROOM STATUS =====
        System.out.println("Step 6: Checking Room Status\n");
        
        System.out.println(livingRoom.getStatus());
        
        // ===== TEST 7: CONTROL INDIVIDUAL DEVICE IN ROOM =====
        System.out.println("Step 7: Controlling Individual Devices\n");
        
        System.out.println("Finding Living Room Light and adjusting brightness:");
        // We can still control individual devices
        if (livingRoomLight instanceof com.smarthome.devices.Light) {
            com.smarthome.devices.Light light = (com.smarthome.devices.Light) livingRoomLight;
            light.setBrightness(50);
        }
        System.out.println();
        
        // ===== TEST 8: TURN OFF ENTIRE ROOM =====
        System.out.println("Step 8: Turning Off Entire Room\n");
        
        System.out.println("═══ Turning off Living Room ═══");
        livingRoom.turnOff(); // Turns off ALL 3 devices!
        System.out.println();
        
        System.out.println(livingRoom.getStatus());
        
        // ===== TEST 9: MULTIPLE ROOMS =====
        System.out.println("Step 9: Controlling Multiple Rooms\n");
        
        System.out.println("Turning on Bedroom:");
        bedroom.turnOn();
        System.out.println();
        
        System.out.println("Turning on Kitchen:");
        kitchen.turnOn();
        System.out.println();
        
        // ===== TEST 10: ADD ROOMS TO CONTROLLER =====
        System.out.println("Step 10: Adding Rooms to Controller\n");
        
        // Rooms ARE SmartDevices, so we can add them to controller!
        controller.addDevice(livingRoom);
        controller.addDevice(bedroom);
        controller.addDevice(kitchen);
        
        System.out.println();
        
        // ===== TEST 11: DISPLAY ALL THROUGH CONTROLLER =====
        System.out.println("Step 11: View All Rooms Through Controller\n");
        
        controller.displayAllDevices();
        
        // ===== TEST 12: REMOVE DEVICE FROM ROOM =====
        System.out.println("Step 12: Removing Device from Room\n");
        
        System.out.println("Removing speaker from Kitchen:");
        kitchen.removeDevice(kitchenSpeaker);
        System.out.println();
        
        kitchen.displayTree();
        System.out.println();
        
        // ===== SCENARIO: SMART HOME IN ACTION =====
        System.out.println("============================================");
        System.out.println("     SCENARIO: LEAVING HOME");
        System.out.println("============================================\n");
        
        System.out.println("You're leaving home. Turn off all rooms:\n");
        
        livingRoom.turnOff();
        System.out.println();
        bedroom.turnOff();
        System.out.println();
        kitchen.turnOff();
        System.out.println();
        
        System.out.println("✓ All rooms turned off with just 3 commands!");
        System.out.println("  (Without Composite Pattern, you'd need 7 commands!)");
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("     COMPOSITE PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Group devices into logical units (rooms)");
        System.out.println("✓ Control multiple devices with one command");
        System.out.println("✓ Room IS-A SmartDevice (can be treated uniformly)");
        System.out.println("✓ Easy to add/remove devices from rooms");
        System.out.println("✓ Simplifies complex hierarchies");
        System.out.println("✓ Perfect for real-world smart home structure");
        System.out.println("============================================");
    }
}