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
 * Supports both intelligent analysis and random automation modes.
 * 
 * @author dwayne headley
 * @version 2.0
 */
public class AIControl implements ControlStrategy {
    
    /**
     * Callback interface for device state changes during automation.
     */
    public interface AutomationCallback {
        void onDeviceStateChanged(String deviceName, boolean isOn);
    }
    
    private Random random;
    private int occupancyLevel; // 0-100 (simulated occupancy detection)
    private Thread aiThread;
    private boolean isRunning;
    private List<SmartDevice> controlledDevices;
    private AutomationCallback callback;
    
    /**
     * Creates an AI control strategy.
     */
    public AIControl() {
        this.random = new Random();
        this.occupancyLevel = random.nextInt(101);
        this.isRunning = false;
        this.aiThread = null;
        this.callback = null;
    }
    
    /**
     * Sets the callback for device state changes.
     * 
     * @param callback The callback to invoke when devices change state
     */
    public void setAutomationCallback(AutomationCallback callback) {
        this.callback = callback;
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
     * Starts random device automation - devices turn on/off randomly every 5 seconds.
     * 
     * @param devices List of devices to automate
     */
    public void startRandomAutomation(List<SmartDevice> devices) {
        if (isRunning) {
            System.out.println("[AI MODE] Random automation already running!");
            return;
        }
        
        this.controlledDevices = devices;
        this.isRunning = true;
        
        System.out.println("[AI MODE] Starting Random Device Automation...");
        System.out.println("[AI MODE] Devices will turn on/off randomly every 5 seconds");
        
        aiThread = new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(5000);
                    
                    if (isRunning && controlledDevices != null) {
                        if (!controlledDevices.isEmpty()) {
                            int randomIndex = random.nextInt(controlledDevices.size());
                            SmartDevice device = controlledDevices.get(randomIndex);
                            
                            boolean turnOn = random.nextBoolean();
                            
                            if (turnOn) {
                                device.turnOn();
                                System.out.println("[AI MODE] 🟢 " + device.getName() + " turned ON automatically");
                                if (callback != null) {
                                    callback.onDeviceStateChanged(device.getName(), true);
                                }
                            } else {
                                device.turnOff();
                                System.out.println("[AI MODE] 🔴 " + device.getName() + " turned OFF automatically");
                                if (callback != null) {
                                    callback.onDeviceStateChanged(device.getName(), false);
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    if (isRunning) {
                        System.out.println("[AI MODE] Automation interrupted");
                    }
                    break;
                }
            }
        });
        
        aiThread.setName("AI-Automation-Thread");
        aiThread.setDaemon(true);
        aiThread.start();
    }
    
    /**
     * Stops the random device automation.
     */
    public void stopRandomAutomation() {
        if (!isRunning) {
            System.out.println("[AI MODE] Random automation is not running");
            return;
        }
        
        System.out.println("[AI MODE] Stopping Random Device Automation...");
        isRunning = false;
        
        if (aiThread != null) {
            aiThread.interrupt();
            try {
                aiThread.join(1000);
            } catch (InterruptedException e) {
                System.out.println("[AI MODE] Interrupted while stopping automation");
            }
            aiThread = null;
        }
        
        System.out.println("[AI MODE] Random automation stopped");
    }
    
    /**
     * Checks if random automation is currently running.
     * 
     * @return true if running, false otherwise
     */
    public boolean isAutomationRunning() {
        return isRunning;
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