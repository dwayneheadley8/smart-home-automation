package com.mycompany.smarthomesystem;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;

/**
 * Test class to demonstrate the Singleton Pattern.
 */
public class TestSingleton {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("     SINGLETON PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        // ===== TEST 1: SINGLETON BEHAVIOR =====
        System.out.println("Test 1: Verifying Singleton Pattern\n");
        
        System.out.println("Creating first controller instance:");
        CentralController controller1 = CentralController.getInstance();
        System.out.println("Controller 1 created: " + controller1);
        System.out.println();
        
        System.out.println("Trying to create second controller instance:");
        CentralController controller2 = CentralController.getInstance();
        System.out.println("Controller 2 created: " + controller2);
        System.out.println();
        
        System.out.println("Are they the same instance?");
        if (controller1 == controller2) {
            System.out.println("✓ YES! Both variables point to the SAME object");
            System.out.println("  This proves Singleton Pattern is working!");
        } else {
            System.out.println("✗ NO! Different objects created (Singleton FAILED)");
        }
        System.out.println();
        
        // ===== TEST 2: ADD DEVICES USING FACTORY =====
        System.out.println("Test 2: Adding Devices to Controller\n");
        
        DeviceFactory factory = new DeviceFactory();
        
        SmartDevice light1 = factory.createDevice("light", "Living Room Light");
        SmartDevice light2 = factory.createDevice("light", "Bedroom Light");
        SmartDevice thermostat = factory.createDevice("thermostat", "Main Thermostat");
        SmartDevice speaker = factory.createDevice("speaker", "Kitchen Speaker");
        
        System.out.println();
        
        // Add devices to controller
        controller1.addDevice(light1);
        controller1.addDevice(light2);
        controller1.addDevice(thermostat);
        controller1.addDevice(speaker);
        
        System.out.println();
        
        // ===== TEST 3: VERIFY SAME DEVICES IN BOTH REFERENCES =====
        System.out.println("Test 3: Verifying Shared State\n");
        
        System.out.println("Devices in controller1: " + controller1.getDeviceCount());
        System.out.println("Devices in controller2: " + controller2.getDeviceCount());
        
        if (controller1.getDeviceCount() == controller2.getDeviceCount()) {
            System.out.println("✓ Both have same count (same object!)");
        }
        System.out.println();
        
        // ===== TEST 4: DISPLAY ALL DEVICES =====
        System.out.println("Test 4: Displaying All Devices\n");
        
        controller1.displayAllDevices();
        
        // ===== TEST 5: GET DEVICE BY NAME =====
        System.out.println("Test 5: Finding Device by Name\n");
        
        SmartDevice foundDevice = controller1.getDevice("Bedroom Light");
        if (foundDevice != null) {
            System.out.println("✓ Found device: " + foundDevice.getName());
            System.out.println("  Status: " + foundDevice.getStatus());
        } else {
            System.out.println("✗ Device not found");
        }
        System.out.println();
        
        // ===== TEST 6: CONTROL DEVICES THROUGH CONTROLLER =====
        System.out.println("Test 6: Controlling Devices\n");
        
        System.out.println("Turning on Living Room Light through controller:");
        SmartDevice livingRoomLight = controller1.getDevice("Living Room Light");
        if (livingRoomLight != null) {
            livingRoomLight.turnOn();
        }
        System.out.println();
        
        // ===== TEST 7: TURN ON ALL DEVICES =====
        System.out.println("Test 7: Mass Control - Turn On All\n");
        
        controller1.turnOnAllDevices();
        System.out.println();
        
        controller1.displayAllDevices();
        
        // ===== TEST 8: TURN OFF ALL DEVICES =====
        System.out.println("Test 8: Mass Control - Turn Off All\n");
        
        controller1.turnOffAllDevices();
        System.out.println();
        
        controller1.displayAllDevices();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("     SINGLETON PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Only ONE controller instance exists");
        System.out.println("✓ All parts of program access same controller");
        System.out.println("✓ Centralized device management");
        System.out.println("✓ No conflicting states");
        System.out.println("✓ Global access point");
        System.out.println("============================================");
    }
}
