package com.mycompany.smarthomesystem;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import com.smarthome.behavioral.DeviceLogger;
import com.smarthome.structural.Room;

/**
 * Test class to demonstrate the Observer Pattern.
 */
public class TestObserver {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("      OBSERVER PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        // Reset controller for clean test
        CentralController.resetInstance();
        
        // ===== TEST 1: BASIC OBSERVER PATTERN =====
        System.out.println("Step 1: Setting Up Observer Pattern\n");
        
        DeviceFactory factory = new DeviceFactory();
        CentralController controller = CentralController.getInstance();
        
        System.out.println("Creating a light:");
        SmartDevice light = factory.createDevice("light", "Living Room Light");
        System.out.println();
        
        System.out.println("Adding light to controller (controller observes it):");
        controller.addDevice(light);
        System.out.println();
        
        System.out.println("Now when the light changes, controller is notified!\n");
        
        // ===== TEST 2: TRIGGER NOTIFICATIONS =====
        System.out.println("Step 2: Triggering Notifications\n");
        
        System.out.println("Action: Turning on light...\n");
        light.turnOn();
        
        System.out.println("\n⬆ Notice the notification above!\n");
        pause();
        
        System.out.println("Action: Adjusting brightness...\n");
        if (light instanceof Light) {
            ((Light) light).setBrightness(50);
        }
        
        System.out.println("\n⬆ Another notification!\n");
        pause();
        
        System.out.println("Action: Turning off light...\n");
        light.turnOff();
        
        System.out.println("\n⬆ And another notification!\n");
        pause();
        
        // ===== TEST 3: MULTIPLE OBSERVERS =====
        System.out.println("Step 3: Multiple Observers Watching Same Device\n");
        
        // Create custom loggers
        DeviceLogger activityLogger = new DeviceLogger("Activity Logger");
        DeviceLogger securityLogger = new DeviceLogger("Security Logger");
        
        System.out.println();
        
        // Create a new device
        SmartDevice thermostat = factory.createDevice("thermostat", "Bedroom Thermostat");
        System.out.println();
        
        System.out.println("Adding multiple observers to thermostat:");
        controller.addDevice(thermostat); // Controller observes
        thermostat.addObserver(activityLogger); // Activity logger observes
        thermostat.addObserver(securityLogger); // Security logger observes
        
        System.out.println("✓ Thermostat has 3 observers now!\n");
        pause();
        
        System.out.println("Action: Turning on thermostat...\n");
        thermostat.turnOn();
        
        System.out.println("\n⬆ Notice 3 different observers were notified!\n");
        pause();
        
        System.out.println("Action: Adjusting temperature...\n");
        if (thermostat instanceof Thermostat) {
            ((Thermostat) thermostat).setTargetTemp(75);
        }
        
        System.out.println("\n⬆ All 3 observers notified again!\n");
        pause();
        
        // ===== TEST 4: DISPLAY LOGS =====
        System.out.println("Step 4: Viewing Logger History\n");
        
        activityLogger.displayLogs();
        securityLogger.displayLogs();
        
        // ===== TEST 5: OBSERVER WITH COMPOSITE PATTERN =====
        System.out.println("Step 5: Observer + Composite Pattern\n");
        
        Room livingRoom = new Room("Living Room");
        
        SmartDevice roomLight = factory.createDevice("light", "LR Light");
        SmartDevice roomSpeaker = factory.createDevice("speaker", "LR Speaker");
        
        System.out.println();
        
        // Add devices to room
        livingRoom.addDevice(roomLight);
        livingRoom.addDevice(roomSpeaker);
        
        System.out.println();
        
        // Controller observes the room itself
        controller.addDevice(livingRoom);
        
        System.out.println("\nAction: Turning on entire room...\n");
        livingRoom.turnOn(); // This triggers notifications for each device!
        
        System.out.println("\n⬆ Multiple notifications cascaded!\n");
        pause();
        
        // ===== TEST 6: REAL-TIME MONITORING =====
        System.out.println("Step 6: Simulating Real-Time Monitoring\n");
        
        DeviceLogger monitorLogger = new DeviceLogger("Real-Time Monitor");
        
        SmartDevice monitoredLight = factory.createDevice("light", "Monitored Light");
        System.out.println();
        
        monitoredLight.addObserver(controller);
        monitoredLight.addObserver(monitorLogger);
        
        System.out.println("\nSimulating a series of changes:\n");
        
        System.out.println("1. Turn on");
        monitoredLight.turnOn();
        sleep(1000);
        
        System.out.println("\n2. Adjust brightness to 75%");
        if (monitoredLight instanceof Light) {
            ((Light) monitoredLight).setBrightness(75);
        }
        sleep(1000);
        
        System.out.println("\n3. Adjust brightness to 25%");
        if (monitoredLight instanceof Light) {
            ((Light) monitoredLight).setBrightness(25);
        }
        sleep(1000);
        
        System.out.println("\n4. Turn off");
        monitoredLight.turnOff();
        
        System.out.println("\n\nMonitoring log:");
        monitorLogger.displayLogs();
        
        // ===== TEST 7: OBSERVER COUNT =====
        System.out.println("Step 7: Observer Statistics\n");
        
        System.out.println("Activity Logger recorded: " + activityLogger.getLogCount() + " events");
        System.out.println("Security Logger recorded: " + securityLogger.getLogCount() + " events");
        System.out.println("Monitor Logger recorded: " + monitorLogger.getLogCount() + " events");
        System.out.println();
        
        // ===== SCENARIO: SMART HOME SECURITY =====
        System.out.println("============================================");
        System.out.println("     SCENARIO: SECURITY MONITORING");
        System.out.println("============================================\n");
        
        DeviceLogger securityMonitor = new DeviceLogger("Security System");
        
        SmartDevice frontDoorLight = factory.createDevice("light", "Front Door Light");
        SmartDevice backDoorLight = factory.createDevice("light", "Back Door Light");
        SmartDevice garageDoorLight = factory.createDevice("light", "Garage Door Light");
        
        System.out.println();
        
        // Security system observes all entry lights
        frontDoorLight.addObserver(securityMonitor);
        backDoorLight.addObserver(securityMonitor);
        garageDoorLight.addObserver(securityMonitor);
        
        System.out.println("Security system is monitoring entry points...\n");
        
        System.out.println("Scenario: Someone approaches front door at night");
        frontDoorLight.turnOn();
        System.out.println();
        
        System.out.println("Scenario: Motion detected at back door");
        backDoorLight.turnOn();
        System.out.println();
        
        System.out.println("Scenario: Garage door opened");
        garageDoorLight.turnOn();
        System.out.println();
        
        System.out.println("Security log:");
        securityMonitor.displayLogs();
        
        System.out.println("✓ Security system has complete activity history!");
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("      OBSERVER PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Automatic notifications on state changes");
        System.out.println("✓ Multiple observers can watch same device");
        System.out.println("✓ Loose coupling (device doesn't know observers)");
        System.out.println("✓ Perfect for GUI updates");
        System.out.println("✓ Enables activity logging");
        System.out.println("✓ Supports real-time monitoring");
        System.out.println("✓ Easy to add/remove observers dynamically");
        System.out.println("============================================");
    }
    
    /**
     * Pauses for user to read output.
     */
    private static void pause() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
    
    /**
     * Sleeps for specified milliseconds.
     */
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}