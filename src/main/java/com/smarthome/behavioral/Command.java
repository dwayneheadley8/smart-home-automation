package com.smarthome.behavioral;

/**
 * Command interface for the Command Pattern.
 * <p>Commands encapsulate actions that can be executed and undone.
 * This enables features like undo/redo, command queuing, and logging.</p>
 * 
 * <h2>Implementing Classes:</h2>
 * <ul>
 *   <li>{@link TurnOnCommand} - Turns on a device</li>
 *   <li>{@link TurnOffCommand} - Turns off a device</li>
 *   <li>{@link AdjustBrightnessCommand} - Adjusts light brightness</li>
 *   <li>{@link AdjustTemperatureCommand} - Adjusts thermostat temperature</li>
 * </ul>
 * 
 * <h2>Example Usage:</h2>
 * <pre>
 * {@code
 * // Create and execute command
 * Command cmd = new TurnOnCommand(light);
 * cmd.execute();
 * 
 * // Undo the command
 * cmd.undo();
 * }
 * </pre>
 * 
 * @author dwayne headley
 * @version 1.0
 */
public interface Command {
    /**
     * Executes the command.
     * Performs the intended action.
     */
    void execute();
    
    /**
     * Undoes the command.
     * Reverses the action performed by execute().
     */
    void undo();
    
    /**
     * Gets a description of what this command does.
     * 
     * @return Command description
     */
    String getDescription();
}