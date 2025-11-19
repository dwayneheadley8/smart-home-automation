package com.smarthome.devices;

import com.smarthome.behavioral.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a smart speaker that can play audio.
 * Can control volume and play different content.
 * 
 * @author dwayne headley
 * @version 1.0
 */
public class Speaker implements SmartDevice {
    private String name;
    private boolean isOn;
    private int volume; // 0-100
    private String currentlyPlaying;
    private List<Observer> observers;
    
    /**
     * Creates a new Speaker with the given name.
     * Speaker starts in OFF state with 0 volume.
     * 
     * @param name The name identifier for this speaker
     */
    public Speaker(String name) {
        this.name = name;
        this.isOn = false;
        this.volume = 0;
        this.currentlyPlaying = "Nothing";
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void turnOn() {
        isOn = true;
        volume = 50; // Default volume when turned on
        System.out.println(name + " turned ON - Volume: " + volume + "%");
        notifyObservers();
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        volume = 0;
        currentlyPlaying = "Nothing";
        System.out.println(name + " turned OFF");
        notifyObservers();
    }
    
    @Override
    public String getStatus() {
        return name + " is " + (isOn ? "ON" : "OFF") + 
               ", Volume: " + volume + "%" +
               ", Playing: " + currentlyPlaying;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    /**
     * Sets the volume level.
     * 
     * @param volume Value from 0-100
     * @throws IllegalArgumentException if volume is out of range
     */
    public void setVolume(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be between 0 and 100");
        }
        this.volume = volume;
        if (volume > 0) {
            isOn = true; // Automatically turn on if volume set
        }
        System.out.println(name + " volume set to " + volume + "%");
        notifyObservers();
    }
    
    /**
     * Gets the current volume level.
     * 
     * @return Current volume (0-100)
     */
    public int getVolume() {
        return volume;
    }
    
    /**
     * Plays the specified content.
     * 
     * @param content What to play (song, podcast, etc.)
     */
    public void play(String content) {
        if (!isOn) {
            turnOn();
        }
        this.currentlyPlaying = content;
        System.out.println(name + " now playing: " + content);
        notifyObservers();
    }
    
    /**
     * Stops playback.
     */
    public void stop() {
        currentlyPlaying = "Nothing";
        System.out.println(name + " playback stopped");
        notifyObservers();
    }
    
    /**
     * Gets what is currently playing.
     * 
     * @return The current content
     */
    public String getCurrentlyPlaying() {
        return currentlyPlaying;
    }
    
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }
}
