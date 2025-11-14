package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

/**
 * AI-powered control strategy.
 * Makes intelligent decisions based on:
 * - Time of day
 * - Device usage patterns
 * - Energy efficiency
 * - User preferences (simulated)
 * 
 * @author Your Name
 * @version 1.0
 */
public class AIControl implements ControlStrategy {
    private Random random;
    private int occupancyLevel; // 0-100 (simulated occupancy detection)
    
    /**
     * Creates an AI control strategy.
     */
    public AIControl() {
        this.random = new Random();
        this.occupancyLevel = random.nextInt(101);
    }
    
    /**
     * AI analyzes environment and makes intelligent decisions.
     * 
     * @param devices List of devices to control
     */
    @Override
    public void controlDevices(List<SmartDevice> devices) {
        System.out.println("[AI MODE] 🤖 Analyzing environment...");
        analyzeEnvironment(devices);
        System.out.println();
        
        System.out.println("[AI MODE] Making intelligent decisions...");
        makeIntelligentDecisions(devices);
    }
    
    /**
     * Analyzes the current environment.
     */
    private void analyzeEnvironment(List<SmartDevice> devices) {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        occupancyLevel = random.nextInt(101);
        
        // Simulate outside temperature
        int outsideTemp = 60 + random.nextInt(40); // 60-100°F
        
        System.out.println("  📊 Environment Analysis:");
        System.out.println("     • Time: " + currentTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        System.out.println("     • Occupancy detected: " + occupancyLevel + "%");
        System.out.println("     • Outside temperature: " + outsideTemp + "°F");
        System.out.println("     • Active devices: " + countActiveDevices(devices));
        System.out.println("     • Energy mode: " + (outsideTemp > 80 ? "High usage" : "Normal"));
    }
    
    /**
     * Makes intelligent control decisions.
     */
    private void makeIntelligentDecisions(List<SmartDevice> devices) {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        
        // Decision 1: Occupancy-based control
        if (occupancyLevel < 20) {
            System.out.println("  🤖 Decision: Low occupancy detected");
            System.out.println("     → Turning off devices in unoccupied rooms");
            turnOffUnoccupiedRooms(devices);
        } else if (occupancyLevel > 80) {
            System.out.println("  🤖 Decision: High occupancy detected");
            System.out.println("     → Optimizing comfort settings");
            optimizeForComfort(devices);
        }
        
        // Decision 2: Time-based adjustments
        if (hour >= 22 || hour < 6) {
            System.out.println("  🤖 Decision: Late night/early morning");
            System.out.println("     → Enabling energy-saving mode");
            enableEnergySaving(devices);
        }
        
        // Decision 3: Predictive adjustments
        System.out.println("  🤖 Decision: Predictive comfort adjustment");
        System.out.println("     → Learning from usage patterns");
        predictiveAdjustment(devices);
    }
    
    /**
     * Turns off devices in unoccupied rooms.
     */
    private void turnOffUnoccupiedRooms(List<SmartDevice> devices) {
        for (SmartDevice device : devices) {
            // Simulate: randomly mark some rooms as unoccupied
            if (random.nextBoolean()) {
                System.out.println("     → " + device.getName() + " turned off (room empty)");
                device.turnOff();
            }
        }
    }
    
    /**
     * Optimizes settings for maximum comfort.
     */
    private void optimizeForComfort(List<SmartDevice> devices) {
        for (SmartDevice device : devices) {
            if (device instanceof Light) {
                Light light = (Light) device;
                light.turnOn();
                light.setBrightness(80);
                System.out.println("     → " + device.getName() + " optimized (80% brightness)");
            }
            if (device instanceof Thermostat) {
                Thermostat thermostat = (Thermostat) device;
                thermostat.turnOn();
                thermostat.setTargetTemp(72);
                System.out.println("     → " + device.getName() + " optimized (72°F)");
            }
        }
    }
    
    /**
     * Enables energy-saving mode.
     */
    private void enableEnergySaving(List<SmartDevice> devices) {
        for (SmartDevice device : devices) {
            if (device instanceof Light) {
                Light light = (Light) device;
                if (light.getBrightness() > 30) {
                    light.setBrightness(30);
                    System.out.println("     → " + device.getName() + " dimmed (energy saving)");
                }
            }
            if (device instanceof Thermostat) {
                Thermostat thermostat = (Thermostat) device;
                thermostat.setTargetTemp(68);
                System.out.println("     → " + device.getName() + " adjusted (68°F - energy saving)");
            }
        }
    }
    
    /**
     * Makes predictive adjustments based on patterns.
     */
    private void predictiveAdjustment(List<SmartDevice> devices) {
        // Simulate AI learning
        int deviceCount = Math.min(2, devices.size());
        System.out.println("     → Adjusting " + deviceCount + " devices based on learned preferences");
        
        for (int i = 0; i < deviceCount && i < devices.size(); i++) {
            SmartDevice device = devices.get(i);
            System.out.println("     → " + device.getName() + " adjusted proactively");
        }
    }
    
    /**
     * Counts active (turned on) devices.
     */
    private int countActiveDevices(List<SmartDevice> devices) {
        int count = 0;
        for (SmartDevice device : devices) {
            if (device.getStatus().contains("ON")) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public String getStrategyName() {
        return "AI Control";
    }
    
    @Override
    public String getDescription() {
        return "AI-powered intelligent control. Learns patterns and optimizes automatically.";
    }
}