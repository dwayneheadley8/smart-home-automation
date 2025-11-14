package com.smarthome.structural;

import com.smarthome.devices.SmartDevice;

/**
 * Decorator that adds voice control capability to any device.
 * Allows controlling devices through voice commands.
 * 
 * @author Your Name
 * @version 1.0
 */
public class VoiceControlDecorator extends DeviceDecorator {
    private String voiceAssistant;
    private boolean voiceEnabled;
    
    /**
     * Creates a voice control decorator for the given device.
     * Default assistant is "Alexa".
     * 
     * @param device The device to add voice control to
     */
    public VoiceControlDecorator(SmartDevice device) {
        super(device);
        this.voiceAssistant = "Alexa";
        this.voiceEnabled = true;
        System.out.println("[DECORATOR] Voice control added to: " + device.getName() + 
                         " (Assistant: " + voiceAssistant + ")");
    }
    
    /**
     * Creates a voice control decorator with specified assistant.
     * 
     * @param device The device to add voice control to
     * @param assistant Voice assistant name (Alexa, Google, Siri)
     */
    public VoiceControlDecorator(SmartDevice device, String assistant) {
        super(device);
        this.voiceAssistant = assistant;
        this.voiceEnabled = true;
        System.out.println("[DECORATOR] Voice control added to: " + device.getName() + 
                         " (Assistant: " + voiceAssistant + ")");
    }
    
    /**
     * Turns on the device via voice command.
     */
    @Override
    public void turnOn() {
        if (voiceEnabled) {
            System.out.println("[VOICE] \"" + voiceAssistant + ", turn on " + getName() + "\"");
            System.out.println("[VOICE] Command recognized!");
        }
        wrappedDevice.turnOn();
    }
    
    /**
     * Turns off the device via voice command.
     */
    @Override
    public void turnOff() {
        if (voiceEnabled) {
            System.out.println("[VOICE] \"" + voiceAssistant + ", turn off " + getName() + "\"");
            System.out.println("[VOICE] Command recognized!");
        }
        wrappedDevice.turnOff();
    }
    
    /**
     * Gets the device status with voice control info.
     * 
     * @return Status string including voice capability
     */
    @Override
    public String getStatus() {
        String baseStatus = wrappedDevice.getStatus();
        String voiceInfo = " | Voice: " + (voiceEnabled ? voiceAssistant : "Disabled");
        return baseStatus + voiceInfo;
    }
    
    /**
     * Enables voice control.
     */
    public void enableVoice() {
        voiceEnabled = true;
        System.out.println("[VOICE] Voice control enabled for: " + getName());
    }
    
    /**
     * Disables voice control.
     */
    public void disableVoice() {
        voiceEnabled = false;
        System.out.println("[VOICE] Voice control disabled for: " + getName());
    }
    
    /**
     * Checks if voice control is enabled.
     * 
     * @return true if enabled
     */
    public boolean isVoiceEnabled() {
        return voiceEnabled;
    }
    
    /**
     * Gets the voice assistant name.
     * 
     * @return Assistant name
     */
    public String getVoiceAssistant() {
        return voiceAssistant;
    }
    
    /**
     * Sets the voice assistant.
     * 
     * @param assistant New assistant name
     */
    public void setVoiceAssistant(String assistant) {
        this.voiceAssistant = assistant;
        System.out.println("[VOICE] Voice assistant changed to: " + assistant);
    }
}