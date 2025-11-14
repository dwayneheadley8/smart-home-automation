package com.mycompany.smarthomesystem;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Light;
import com.smarthome.structural.EnergyMonitorDecorator;
import com.smarthome.structural.VoiceControlDecorator;

/**
 * Test class to demonstrate the Decorator Pattern.
 */
public class TestDecorator {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("      DECORATOR PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        DeviceFactory factory = new DeviceFactory();
        CentralController controller = CentralController.getInstance();
        
        // ===== TEST 1: BASIC DEVICE WITHOUT DECORATOR =====
        System.out.println("Step 1: Basic Device (No Decorators)\n");
        
        SmartDevice basicLight = factory.createDevice("light", "Basic Light");
        System.out.println();
        
        basicLight.turnOn();
        System.out.println("Status: " + basicLight.getStatus());
        System.out.println();
        
        basicLight.turnOff();
        System.out.println("Status: " + basicLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 2: ADD ENERGY MONITORING =====
        System.out.println("Step 2: Adding Energy Monitoring Decorator\n");
        
        SmartDevice monitoredLight = factory.createDevice("light", "Kitchen Light");
        System.out.println();
        
        System.out.println("Wrapping with EnergyMonitorDecorator:");
        monitoredLight = new EnergyMonitorDecorator(monitoredLight);
        System.out.println();
        pause();
        
        System.out.println("Testing energy-monitored light:");
        monitoredLight.turnOn();
        System.out.println("Status: " + monitoredLight.getStatus());
        System.out.println();
        
        System.out.println("Simulating device usage (3 seconds)...");
        sleep(3000);
        
        monitoredLight.turnOff();
        System.out.println("Status: " + monitoredLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 3: DETAILED ENERGY REPORT =====
        System.out.println("Step 3: Energy Report\n");
        
        if (monitoredLight instanceof EnergyMonitorDecorator) {
            EnergyMonitorDecorator energyLight = (EnergyMonitorDecorator) monitoredLight;
            energyLight.displayEnergyReport();
        }
        pause();
        
        // ===== TEST 4: ADD VOICE CONTROL =====
        System.out.println("Step 4: Adding Voice Control Decorator\n");
        
        SmartDevice voiceLight = factory.createDevice("light", "Living Room Light");
        System.out.println();
        
        System.out.println("Wrapping with VoiceControlDecorator:");
        voiceLight = new VoiceControlDecorator(voiceLight, "Google");
        System.out.println();
        pause();
        
        System.out.println("Testing voice-controlled light:");
        voiceLight.turnOn();
        System.out.println();
        
        voiceLight.turnOff();
        System.out.println();
        pause();
        
        // ===== TEST 5: STACKING DECORATORS =====
        System.out.println("Step 5: Stacking Multiple Decorators!\n");
        
        SmartDevice superLight = factory.createDevice("light", "Bedroom Light");
        System.out.println();
        
        System.out.println("Adding Energy Monitor:");
        superLight = new EnergyMonitorDecorator(superLight, 0.15);
        System.out.println();
        
        System.out.println("Adding Voice Control:");
        superLight = new VoiceControlDecorator(superLight, "Alexa");
        System.out.println();
        pause();
        
        System.out.println("Testing super-enhanced light:");
        superLight.turnOn();
        sleep(2000);
        System.out.println("Status: " + superLight.getStatus());
        System.out.println();
        
        superLight.turnOff();
        System.out.println("Status: " + superLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 6: MULTIPLE USAGE CYCLES =====
        System.out.println("Step 6: Multiple Usage Cycles\n");
        
        SmartDevice trackedLight = factory.createDevice("light", "Office Light");
        trackedLight = new EnergyMonitorDecorator(trackedLight);
        System.out.println();
        
        System.out.println("Cycle 1:");
        trackedLight.turnOn();
        sleep(1000);
        trackedLight.turnOff();
        System.out.println();
        
        System.out.println("Cycle 2:");
        trackedLight.turnOn();
        sleep(1500);
        trackedLight.turnOff();
        System.out.println();
        
        System.out.println("Cycle 3:");
        trackedLight.turnOn();
        sleep(2000);
        trackedLight.turnOff();
        System.out.println();
        
        if (trackedLight instanceof EnergyMonitorDecorator) {
            EnergyMonitorDecorator energyTracker = (EnergyMonitorDecorator) trackedLight;
            energyTracker.displayEnergyReport();
        }
        pause();
        
        // ===== TEST 7: COMPARING COSTS =====
        System.out.println("Step 7: Cost Comparison\n");
        
        SmartDevice cheapLight = factory.createDevice("light", "Cheap Light");
        SmartDevice expensiveLight = factory.createDevice("light", "Expensive Light");
        
        cheapLight = new EnergyMonitorDecorator(cheapLight, 0.10);  // $0.10/kWh
        expensiveLight = new EnergyMonitorDecorator(expensiveLight, 0.25);  // $0.25/kWh
        
        System.out.println();
        
        System.out.println("Running both lights for 2 seconds:");
        cheapLight.turnOn();
        expensiveLight.turnOn();
        sleep(2000);
        cheapLight.turnOff();
        expensiveLight.turnOff();
        
        System.out.println("\nCheap Light: " + cheapLight.getStatus());
        System.out.println("Expensive Light: " + expensiveLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 8: ADD TO CONTROLLER =====
        System.out.println("Step 8: Adding Decorated Devices to Controller\n");
        
        controller.addDevice(basicLight);
        controller.addDevice(monitoredLight);
        controller.addDevice(voiceLight);
        controller.addDevice(superLight);
        
        System.out.println();
        controller.displayAllDevices();
        pause();
        
        // ===== SCENARIO: ENERGY TRACKING =====
        System.out.println("============================================");
        System.out.println("     SCENARIO: MONTHLY ENERGY TRACKING");
        System.out.println("============================================\n");
        
        SmartDevice light1 = new EnergyMonitorDecorator(
            factory.createDevice("light", "Light 1"), 0.12);
        SmartDevice light2 = new EnergyMonitorDecorator(
            factory.createDevice("light", "Light 2"), 0.12);
        SmartDevice light3 = new EnergyMonitorDecorator(
            factory.createDevice("light", "Light 3"), 0.12);
        
        System.out.println();
        
        System.out.println("Simulating one day of usage...\n");
        
        // Morning
        light1.turnOn();
        light2.turnOn();
        sleep(1000);
        light1.turnOff();
        light2.turnOff();
        
        // Evening
        light1.turnOn();
        light2.turnOn();
        light3.turnOn();
        sleep(2000);
        light1.turnOff();
        light2.turnOff();
        light3.turnOff();
        
        System.out.println("\nDaily Energy Summary:");
        if (light1 instanceof EnergyMonitorDecorator) {
            EnergyMonitorDecorator e1 = (EnergyMonitorDecorator) light1;
            EnergyMonitorDecorator e2 = (EnergyMonitorDecorator) light2;
            EnergyMonitorDecorator e3 = (EnergyMonitorDecorator) light3;
            
            double totalEnergy = e1.getEnergyUsage() + e2.getEnergyUsage() + e3.getEnergyUsage();
            double totalCost = e1.getCost() + e2.getCost() + e3.getCost();
            
            System.out.println("Light 1: " + String.format("%.4f kWh ($%.2f)", e1.getEnergyUsage(), e1.getCost()));
            System.out.println("Light 2: " + String.format("%.4f kWh ($%.2f)", e2.getEnergyUsage(), e2.getCost()));
            System.out.println("Light 3: " + String.format("%.4f kWh ($%.2f)", e3.getEnergyUsage(), e3.getCost()));
            System.out.println("─────────────────────────────");
            System.out.println("Total: " + String.format("%.4f kWh ($%.2f)", totalEnergy, totalCost));
            System.out.println("\nProjected Monthly: $" + String.format("%.2f", totalCost * 30));
        }
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("      DECORATOR PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Add features dynamically at runtime");
        System.out.println("✓ No need to modify original device classes");
        System.out.println("✓ Can stack multiple decorators");
        System.out.println("✓ Follow Open/Closed Principle");
        System.out.println("✓ Each decorator is independent");
        System.out.println("✓ Easy to add/remove features");
        System.out.println("✓ Great for cross-cutting concerns");
        System.out.println("============================================");
    }
    
    private static void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
    
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}