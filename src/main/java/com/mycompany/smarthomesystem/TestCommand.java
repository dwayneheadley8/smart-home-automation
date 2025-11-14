package com.mycompany.smarthomesystem;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import com.smarthome.behavioral.*;

/**
 * Test class to demonstrate the Command Pattern.
 */
public class TestCommand {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("       COMMAND PATTERN DEMONSTRATION");
        System.out.println("============================================\n");
        
        // Reset for clean test
        CentralController.resetInstance();
        
        DeviceFactory factory = new DeviceFactory();
        CentralController controller = CentralController.getInstance();
        
        // ===== TEST 1: BASIC COMMAND EXECUTION =====
        System.out.println("Step 1: Creating Devices and Commands\n");
        
        Light livingRoomLight = (Light) factory.createDevice("light", "Living Room Light");
        Thermostat bedroomThermostat = (Thermostat) factory.createDevice("thermostat", "Bedroom Thermostat");
        
        System.out.println();
        
        controller.addDevice(livingRoomLight);
        controller.addDevice(bedroomThermostat);
        
        System.out.println();
        pause();
        
        // ===== TEST 2: EXECUTE COMMANDS =====
        System.out.println("Step 2: Executing Commands\n");
        
        System.out.println("Creating TurnOnCommand for light:");
        Command turnOnLight = new TurnOnCommand(livingRoomLight);
        System.out.println("  Command created: " + turnOnLight.getDescription());
        System.out.println();
        
        System.out.println("Executing command through controller:");
        controller.executeCommand(turnOnLight);
        System.out.println();
        pause();
        
        System.out.println("Current light status: " + livingRoomLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 3: UNDO COMMAND =====
        System.out.println("Step 3: Undoing Commands\n");
        
        System.out.println("Pressing UNDO button...");
        controller.undoLastCommand();
        System.out.println();
        pause();
        
        System.out.println("Current light status: " + livingRoomLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 4: REDO COMMAND =====
        System.out.println("Step 4: Redoing Commands\n");
        
        System.out.println("Pressing REDO button...");
        controller.redoLastCommand();
        System.out.println();
        pause();
        
        System.out.println("Current light status: " + livingRoomLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 5: MULTIPLE COMMANDS =====
        System.out.println("Step 5: Executing Multiple Commands\n");
        
        Command adjustBrightness1 = new AdjustBrightnessCommand(livingRoomLight, 75);
        Command adjustBrightness2 = new AdjustBrightnessCommand(livingRoomLight, 50);
        Command adjustBrightness3 = new AdjustBrightnessCommand(livingRoomLight, 25);
        
        controller.executeCommand(adjustBrightness1);
        System.out.println("  " + livingRoomLight.getStatus());
        System.out.println();
        
        controller.executeCommand(adjustBrightness2);
        System.out.println("  " + livingRoomLight.getStatus());
        System.out.println();
        
        controller.executeCommand(adjustBrightness3);
        System.out.println("  " + livingRoomLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 6: DISPLAY HISTORY =====
        System.out.println("Step 6: Command History\n");
        
        controller.displayCommandHistory();
        pause();
        
        // ===== TEST 7: MULTIPLE UNDOS =====
        System.out.println("Step 7: Multiple Undos\n");
        
        System.out.println("Undo #1:");
        controller.undoLastCommand();
        System.out.println("  " + livingRoomLight.getStatus());
        System.out.println();
        
        System.out.println("Undo #2:");
        controller.undoLastCommand();
        System.out.println("  " + livingRoomLight.getStatus());
        System.out.println();
        
        System.out.println("Undo #3:");
        controller.undoLastCommand();
        System.out.println("  " + livingRoomLight.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 8: THERMOSTAT COMMANDS =====
        System.out.println("Step 8: Thermostat Temperature Commands\n");
        
        Command turnOnThermostat = new TurnOnCommand(bedroomThermostat);
        Command setTemp72 = new AdjustTemperatureCommand(bedroomThermostat, 72);
        Command setTemp75 = new AdjustTemperatureCommand(bedroomThermostat, 75);
        Command setTemp68 = new AdjustTemperatureCommand(bedroomThermostat, 68);
        
        controller.executeCommand(turnOnThermostat);
        System.out.println("  " + bedroomThermostat.getStatus());
        System.out.println();
        
        controller.executeCommand(setTemp72);
        System.out.println("  " + bedroomThermostat.getStatus());
        System.out.println();
        
        controller.executeCommand(setTemp75);
        System.out.println("  " + bedroomThermostat.getStatus());
        System.out.println();
        
        controller.executeCommand(setTemp68);
        System.out.println("  " + bedroomThermostat.getStatus());
        System.out.println();
        pause();
        
        // ===== TEST 9: UNDO TEMPERATURE CHANGES =====
        System.out.println("Step 9: Undoing Temperature Changes\n");
        
        System.out.println("Before undo: " + bedroomThermostat.getTargetTemp() + "°F");
        controller.undoLastCommand();
        System.out.println("After undo: " + bedroomThermostat.getTargetTemp() + "°F");
        System.out.println();
        
        controller.undoLastCommand();
        System.out.println("After 2nd undo: " + bedroomThermostat.getTargetTemp() + "°F");
        System.out.println();
        pause();
        
        // ===== TEST 10: MIXED DEVICE COMMANDS =====
        System.out.println("Step 10: Mixed Commands (Multiple Devices)\n");
        
        controller.clearHistory();
        
        SmartDevice kitchenLight = factory.createDevice("light", "Kitchen Light");
        SmartDevice livingRoomSpeaker = factory.createDevice("speaker", "Living Room Speaker");
        
        System.out.println();
        
        Command cmd1 = new TurnOnCommand(kitchenLight);
        Command cmd2 = new TurnOnCommand(livingRoomSpeaker);
        Command cmd3 = new TurnOffCommand(livingRoomLight);
        Command cmd4 = new TurnOffCommand(bedroomThermostat);
        
        controller.executeCommand(cmd1);
        controller.executeCommand(cmd2);
        controller.executeCommand(cmd3);
        controller.executeCommand(cmd4);
        
        System.out.println();
        controller.displayCommandHistory();
        
        // ===== TEST 11: UNDO ALL =====
        System.out.println("Step 11: Undoing Everything\n");
        
        System.out.println("Undoing all commands...");
        while (controller.undoLastCommand()) {
            System.out.println();
        }
        
        System.out.println("\nTrying to undo when history is empty:");
        controller.undoLastCommand();
        System.out.println();
        pause();
        
        // ===== SCENARIO: USER MAKES MISTAKE =====
        System.out.println("============================================");
        System.out.println("     SCENARIO: ACCIDENTAL TURN OFF");
        System.out.println("============================================\n");
        
        controller.clearHistory();
        
        Light bedroomLight = (Light) factory.createDevice("light", "Bedroom Light");
        System.out.println();
        
        System.out.println("Setting up bedroom for reading:");
        Command setupCmd1 = new TurnOnCommand(bedroomLight);
        Command setupCmd2 = new AdjustBrightnessCommand(bedroomLight, 80);
        
        controller.executeCommand(setupCmd1);
        controller.executeCommand(setupCmd2);
        
        System.out.println("\n✓ Perfect reading light: " + bedroomLight.getStatus());
        System.out.println();
        pause();
        
        System.out.println("Oops! Accidentally turned off:");
        Command accidentalCmd = new TurnOffCommand(bedroomLight);
        controller.executeCommand(accidentalCmd);
        System.out.println("  " + bedroomLight.getStatus());
        System.out.println();
        pause();
        
        System.out.println("No problem! Press UNDO:");
        controller.undoLastCommand();
        System.out.println("\n✓ Back to perfect reading light: " + bedroomLight.getStatus());
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("============================================");
        System.out.println("       COMMAND PATTERN BENEFITS:");
        System.out.println("============================================");
        System.out.println("✓ Undo/Redo functionality");
        System.out.println("✓ Command history tracking");
        System.out.println("✓ Encapsulates actions as objects");
        System.out.println("✓ Can queue commands");
        System.out.println("✓ Can log all user actions");
        System.out.println("✓ Supports macro recording");
        System.out.println("✓ Decouples sender from receiver");
        System.out.println("✓ Easy to add new commands");
        System.out.println("============================================");
    }
    
    /**
     * Pauses for readability.
     */
    private static void pause() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            // Ignore
        }
    }
}