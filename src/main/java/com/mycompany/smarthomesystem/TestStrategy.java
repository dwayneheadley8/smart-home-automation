package com.mycompany.smarthomesystem;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;
import com.smarthome.behavioral.*;

/**
 * Test class to demonstrate the Strategy Pattern.
 */
public class TestStrategy {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("      STRATEGY PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        // Reset for clean test
        CentralController.resetInstance();
        
        DeviceFactory factory = new DeviceFactory();
        CentralController controller = CentralController.getInstance();
        
        // ===== SETUP: CREATE DEVICES =====
        System.out.println("Setting up smart home...\n");
        
        SmartDevice livingRoomLight = factory.createDevice("light", "Living Room Light");
        SmartDevice bedroomLight = factory.createDevice("light", "Bedroom Light");
        SmartDevice kitchenLight = factory.createDevice("light", "Kitchen Light");
        SmartDevice livingRoomThermostat = factory.createDevice("thermostat", "Living Room Thermostat");
        SmartDevice bedroomThermostat = factory.createDevice("thermostat", "Bedroom Thermostat");
        SmartDevice livingRoomSpeaker = factory.createDevice("speaker", "Living Room Speaker");
        
        System.out.println();
        
        controller.addDevice(livingRoomLight);
        controller.addDevice(bedroomLight);
        controller.addDevice(kitchenLight);
        controller.addDevice(livingRoomThermostat);
        controller.addDevice(bedroomThermostat);
        controller.addDevice(livingRoomSpeaker);
        
        System.out.println();
        pause();
        
        // ===== TEST 1: MANUAL CONTROL STRATEGY =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  TEST 1: MANUAL CONTROL STRATEGY");
        System.out.println("═══════════════════════════════════════════\n");
        
        ControlStrategy manualControl = new ManualControl();
        controller.setControlStrategy(manualControl);
        System.out.println();
        
        controller.activateControlStrategy();
        System.out.println("\n✓ In manual mode, user controls everything\n");
        pause();
        
        // ===== TEST 2: SCHEDULED CONTROL STRATEGY =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  TEST 2: SCHEDULED CONTROL STRATEGY");
        System.out.println("═══════════════════════════════════════════\n");
        
        ScheduledControl scheduledControl = new ScheduledControl();
        
        System.out.println("Viewing default schedule:");
        scheduledControl.displaySchedule();
        pause();
        
        controller.setControlStrategy(scheduledControl);
        System.out.println();
        
        System.out.println("Activating scheduled control (checks current time):");
        controller.activateControlStrategy();
        System.out.println("\n✓ Devices controlled based on time of day\n");
        pause();
        
        // ===== TEST 3: AI CONTROL STRATEGY =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  TEST 3: AI CONTROL STRATEGY");
        System.out.println("═══════════════════════════════════════════\n");
        
        AIControl aiControl = new AIControl();
        controller.setControlStrategy(aiControl);
        System.out.println();
        
        System.out.println("Activating AI control (analyzes and decides):");
        controller.activateControlStrategy();
        System.out.println("\n✓ AI makes intelligent decisions automatically\n");
        pause();
        
        // ===== TEST 4: SWITCHING STRATEGIES AT RUNTIME =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  TEST 4: SWITCHING STRATEGIES");
        System.out.println("═══════════════════════════════════════════\n");
        
        System.out.println("Scenario: User's day progression\n");
        
        System.out.println("8:00 AM - Switching to Scheduled Mode:");
        controller.setControlStrategy(scheduledControl);
        controller.activateControlStrategy();
        System.out.println();
        pause();
        
        System.out.println("12:00 PM - User wants manual control:");
        controller.setControlStrategy(manualControl);
        controller.activateControlStrategy();
        System.out.println();
        pause();
        
        System.out.println("6:00 PM - Enabling AI for evening optimization:");
        controller.setControlStrategy(aiControl);
        controller.activateControlStrategy();
        System.out.println();
        pause();
        
        // ===== TEST 5: STRATEGY COMPARISON =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("  TEST 5: STRATEGY COMPARISON");
        System.out.println("═══════════════════════════════════════════\n");
        
        ControlStrategy[] strategies = {manualControl, scheduledControl, aiControl};
        
        System.out.println("Available Control Strategies:\n");
        for (int i = 0; i < strategies.length; i++) {
            System.out.println((i + 1) + ". " + strategies[i].getStrategyName());
            System.out.println("   " + strategies[i].getDescription());
            System.out.println();
        }
        
        pause();
        
        // ===== SCENARIO: SMART HOME MODES =====
        System.out.println("═══════════════════════════════════════════");
        System.out.println("     SCENARIO: WEEKEND vs WEEKDAY");
        System.out.println("═══════════════════════════════════════════\n");
        
        System.out.println("WEEKDAY MODE:");
        System.out.println("Using scheduled control for consistent routine\n");
        controller.setControlStrategy(scheduledControl);
        controller.activateControlStrategy();
        System.out.println();
        pause();
        
        System.out.println("WEEKEND MODE:");
        System.out.println("Using manual control for flexibility\n");
        controller.setControlStrategy(manualControl);
        controller.activateControlStrategy();
        System.out.println();
        pause();
        
        System.out.println("VACATION MODE:");
        System.out.println("Using AI control for smart energy management\n");
        controller.setControlStrategy(aiControl);
        controller.activateControlStrategy();
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("      STRATEGY PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Switch algorithms at runtime");
        System.out.println("✓ Easy to add new strategies");
        System.out.println("✓ Separates algorithm from client code");
        System.out.println("✓ Different behaviors for different situations");
        System.out.println("✓ No complex conditional statements");
        System.out.println("✓ Each strategy is independent and testable");
        System.out.println("============================================");
    }
    
    /**
     * Pauses for readability.
     */
    private static void pause() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}