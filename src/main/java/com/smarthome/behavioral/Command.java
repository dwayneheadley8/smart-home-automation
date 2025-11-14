package com.smarthome.behavioral;

/**
 * Command interface for the Command Pattern.
 * Each command encapsulates an action that can be executed and undone.
 * 
 * This enables:
 * - Undo/redo functionality
 * - Command queuing
 * - Command logging
 * - Macro recording
 * 
 * @author Your Name
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