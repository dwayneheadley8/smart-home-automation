package com.smarthome.behavioral;

import com.smarthome.devices.SmartDevice;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A logger that observes devices and keeps a history of changes.
 * Demonstrates that multiple observers can watch the same device.
 * 
 * @author Your Name
 * @version 1.0
 */
public class DeviceLogger implements Observer {
    private String loggerName;
    private List<String> logs;
    private DateTimeFormatter formatter;
    
    /**
     * Creates a new device logger.
     * 
     * @param loggerName The name of this logger
     */
    public DeviceLogger(String loggerName) {
        this.loggerName = loggerName;
        this.logs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println("[LOGGER] DeviceLogger created: " + loggerName);
    }
    
    /**
     * Called when a device's state changes.
     * Logs the change to history.
     * 
     * @param device The device that changed
     */
    @Override
    public void update(SmartDevice device) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] " + device.getName() + " - " + device.getStatus();
        logs.add(logEntry);
        
        System.out.println("[LOGGER-" + loggerName + "] 📝 Logged: " + device.getName());
    }
    
    /**
     * Displays all logged events.
     */
    public void displayLogs() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("  " + loggerName + " - Activity Log");
        System.out.println("═══════════════════════════════════════════");
        
        if (logs.isEmpty()) {
            System.out.println("  No activity logged yet.");
        } else {
            for (int i = 0; i < logs.size(); i++) {
                System.out.println((i + 1) + ". " + logs.get(i));
            }
        }
        
        System.out.println("═══════════════════════════════════════════\n");
    }
    
    /**
     * Gets the number of logged events.
     * 
     * @return Log count
     */
    public int getLogCount() {
        return logs.size();
    }
    
    /**
     * Clears all logs.
     */
    public void clearLogs() {
        logs.clear();
        System.out.println("[LOGGER-" + loggerName + "] Logs cleared");
    }
    
    /**
     * Gets all log entries.
     * 
     * @return List of log entries
     */
    public List<String> getLogs() {
        return new ArrayList<>(logs); // Return copy
    }
}