package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Scheduled control strategy.
 * Controls devices based on time schedules.
 * Perfect for routines like morning wake-up, evening wind-down, etc.
 * 
 * @author Your Name
 * @version 1.0
 */
public class ScheduledControl implements ControlStrategy {
    private Map<String, String> schedule;
    
    /**
     * Creates a scheduled control strategy with default schedules.
     */
    public ScheduledControl() {
        schedule = new HashMap<>();
        initializeDefaultSchedule();
    }
    
    /**
     * Initializes default schedule.
     */
    private void initializeDefaultSchedule() {
        schedule.put("06:00", "Morning routine - Turn on bedroom lights");
        schedule.put("07:00", "Breakfast time - Turn on kitchen devices");
        schedule.put("08:00", "Leave for work - Turn off unnecessary devices");
        schedule.put("18:00", "Evening return - Turn on living room");
        schedule.put("22:00", "Bedtime - Dim all lights");
        schedule.put("23:00", "Sleep mode - Turn off all devices");
    }
    
    /**
     * Controls devices based on current time and schedule.
     * 
     * @param devices List of devices to control
     */
    @Override
    public void controlDevices(List<SmartDevice> devices) {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        
        System.out.println("[SCHEDULED MODE] Checking schedule...");
        System.out.println("  → Current time: " + currentTime.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")));
        System.out.println("  → Controlling " + devices.size() + " devices");
        System.out.println();
        
        // Morning routine (6 AM - 8 AM)
        if (hour >= 6 && hour < 8) {
            System.out.println("  📅 MORNING ROUTINE ACTIVE");
            morningRoutine(devices);
        }
        // Daytime (8 AM - 6 PM)
        else if (hour >= 8 && hour < 18) {
            System.out.println("  📅 DAYTIME MODE");
            daytimeMode(devices);
        }
        // Evening (6 PM - 10 PM)
        else if (hour >= 18 && hour < 22) {
            System.out.println("  📅 EVENING ROUTINE ACTIVE");
            eveningRoutine(devices);
        }
        // Night (10 PM - 6 AM)
        else {
            System.out.println("  📅 NIGHT MODE ACTIVE");
            nightMode(devices);
        }
    }
    
    /**
     * Morning routine: Gradually turn on lights, adjust temperature.
     */
    private void morningRoutine(List<SmartDevice> devices) {
        System.out.println("  → Turning on bedroom lights gradually");
        System.out.println("  → Setting comfortable temperature (72°F)");
        
        for (SmartDevice device : devices) {
            if (device.getName().toLowerCase().contains("bedroom")) {
                device.turnOn();
                if (device instanceof Light) {
                    ((Light) device).setBrightness(60);
                }
            }
        }
    }
    
    /**
     * Daytime mode: Turn off unnecessary devices to save energy.
     */
    private void daytimeMode(List<SmartDevice> devices) {
        System.out.println("  → Turning off bedroom devices (not home)");
        System.out.println("  → Minimal lighting, energy-saving mode");
        
        for (SmartDevice device : devices) {
            if (device.getName().toLowerCase().contains("bedroom") ||
                device.getName().toLowerCase().contains("living room")) {
                device.turnOff();
            }
        }
    }
    
    /**
     * Evening routine: Welcome home setup.
     */
    private void eveningRoutine(List<SmartDevice> devices) {
        System.out.println("  → Turning on living room devices");
        System.out.println("  → Setting ambient lighting (70%)");
        System.out.println("  → Comfortable temperature (70°F)");
        
        for (SmartDevice device : devices) {
            if (device.getName().toLowerCase().contains("living room")) {
                device.turnOn();
                if (device instanceof Light) {
                    ((Light) device).setBrightness(70);
                }
                if (device instanceof Thermostat) {
                    ((Thermostat) device).setTargetTemp(70);
                }
            }
        }
    }
    
    /**
     * Night mode: Prepare for sleep.
     */
    private void nightMode(List<SmartDevice> devices) {
        System.out.println("  → Dimming all lights");
        System.out.println("  → Setting sleep temperature (68°F)");
        System.out.println("  → Preparing sleep mode");
        
        for (SmartDevice device : devices) {
            if (device instanceof Light) {
                ((Light) device).setBrightness(10);
            }
            if (device instanceof Thermostat) {
                ((Thermostat) device).setTargetTemp(68);
            }
        }
    }
    
    /**
     * Adds a scheduled action.
     * 
     * @param time Time in HH:mm format
     * @param action Action description
     */
    public void addSchedule(String time, String action) {
        schedule.put(time, action);
        System.out.println("[SCHEDULED MODE] Added: " + time + " - " + action);
    }
    
    /**
     * Displays the current schedule.
     */
    public void displaySchedule() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("     DAILY SCHEDULE");
        System.out.println("═══════════════════════════════════");
        schedule.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> System.out.println(entry.getKey() + " - " + entry.getValue()));
        System.out.println("═══════════════════════════════════\n");
    }
    
    @Override
    public String getStrategyName() {
        return "Scheduled Control";
    }
    
    @Override
    public String getDescription() {
        return "Automated control based on time schedules. Perfect for daily routines.";
    }
}