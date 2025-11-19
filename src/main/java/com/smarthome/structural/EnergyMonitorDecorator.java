package com.smarthome.structural;

import com.smarthome.devices.SmartDevice;

/**
 * Decorator that adds energy monitoring to any device.
 * Tracks energy usage and calculates costs.
 * 
 * This demonstrates the Decorator Pattern - adding functionality
 * dynamically without modifying the original device class.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class EnergyMonitorDecorator extends DeviceDecorator {
    private double energyUsage;        // in kWh
    private double costPerKWh;         // cost per kilowatt-hour
    private long onTimeStart;          // when device was turned on
    private long totalOnTime;          // total time device was on (milliseconds)
    private boolean isCurrentlyOn;
    
    /**
     * Creates an energy monitoring decorator for the given device.
     * Default cost is $0.12 per kWh (average US rate).
     * 
     * @param device The device to monitor
     */
    public EnergyMonitorDecorator(SmartDevice device) {
        super(device);
        this.energyUsage = 0.0;
        this.costPerKWh = 0.12;  // Average US electricity cost
        this.totalOnTime = 0;
        this.isCurrentlyOn = false;
        System.out.println("[DECORATOR] Energy monitoring added to: " + device.getName());
    }
    
    /**
     * Creates an energy monitoring decorator with custom cost rate.
     * 
     * @param device The device to monitor
     * @param costPerKWh Cost per kilowatt-hour
     */
    public EnergyMonitorDecorator(SmartDevice device, double costPerKWh) {
        super(device);
        this.energyUsage = 0.0;
        this.costPerKWh = costPerKWh;
        this.totalOnTime = 0;
        this.isCurrentlyOn = false;
        System.out.println("[DECORATOR] Energy monitoring added to: " + device.getName() + 
                         " (Rate: $" + costPerKWh + "/kWh)");
    }
    
    /**
     * Turns on the device and starts energy tracking.
     */
    @Override
    public void turnOn() {
        wrappedDevice.turnOn();
        
        if (!isCurrentlyOn) {
            onTimeStart = System.currentTimeMillis();
            isCurrentlyOn = true;
            System.out.println("[ENERGY] Started tracking energy for: " + getName());
        }
    }
    
    /**
     * Turns off the device and stops energy tracking.
     */
    @Override
    public void turnOff() {
        wrappedDevice.turnOff();
        
        if (isCurrentlyOn) {
            long onDuration = System.currentTimeMillis() - onTimeStart;
            totalOnTime += onDuration;
            
            // Calculate energy used during this session
            // Assuming average device power of 60W (0.06 kW)
            double hoursOn = onDuration / 3600000.0; // Convert ms to hours
            double sessionEnergy = 0.06 * hoursOn;   // kWh
            energyUsage += sessionEnergy;
            
            isCurrentlyOn = false;
            System.out.println("[ENERGY] Stopped tracking. Session: " + 
                             String.format("%.4f", sessionEnergy) + " kWh");
        }
    }
    
    /**
     * Gets the device status with energy information.
     * 
     * @return Status string including energy data
     */
    @Override
    public String getStatus() {
        String baseStatus = wrappedDevice.getStatus();
        String energyInfo = String.format(" | Energy: %.3f kWh | Cost: $%.2f", 
                                         energyUsage, getCost());
        return baseStatus + energyInfo;
    }
    
    /**
     * Gets the total energy usage.
     * 
     * @return Energy used in kWh
     */
    public double getEnergyUsage() {
        return energyUsage;
    }
    
    /**
     * Gets the total cost of energy used.
     * 
     * @return Cost in dollars
     */
    public double getCost() {
        return energyUsage * costPerKWh;
    }
    
    /**
     * Gets the total time the device has been on.
     * 
     * @return Time in milliseconds
     */
    public long getTotalOnTime() {
        return totalOnTime;
    }
    
    /**
     * Gets the total time in hours.
     * 
     * @return Hours as a decimal
     */
    public double getTotalOnTimeHours() {
        return totalOnTime / 3600000.0;
    }
    
    /**
     * Gets the cost per kWh.
     * 
     * @return Cost rate
     */
    public double getCostPerKWh() {
        return costPerKWh;
    }
    
    /**
     * Sets the cost per kWh.
     * 
     * @param costPerKWh New cost rate
     */
    public void setCostPerKWh(double costPerKWh) {
        this.costPerKWh = costPerKWh;
        System.out.println("[ENERGY] Cost rate updated to: $" + costPerKWh + "/kWh");
    }
    
    /**
     * Resets energy tracking.
     */
    public void resetEnergyTracking() {
        energyUsage = 0.0;
        totalOnTime = 0;
        System.out.println("[ENERGY] Energy tracking reset for: " + getName());
    }
    
    /**
     * Displays a detailed energy report.
     */
    public void displayEnergyReport() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║       ENERGY MONITORING REPORT         ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ Device: " + getName());
        System.out.println("║ Total Energy Used: " + String.format("%.3f kWh", energyUsage));
        System.out.println("║ Total Cost: $" + String.format("%.2f", getCost()));
        System.out.println("║ Total Runtime: " + String.format("%.2f hours", getTotalOnTimeHours()));
        System.out.println("║ Rate: $" + costPerKWh + " per kWh");
        System.out.println("║ Status: " + (isCurrentlyOn ? "Currently ON" : "Currently OFF"));
        System.out.println("╚════════════════════════════════════════╝\n");
    }
}