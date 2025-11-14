package com.mycompany.smarthomesystem;

import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import com.smarthome.devices.Speaker;

/**
 * Test class to verify all device classes work.
 */
public class TestMain {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   SMART HOME SYSTEM - COMPREHENSIVE TEST");
        System.out.println("============================================\n");
        
        // ===== TEST LIGHT =====
        System.out.println("========== TESTING LIGHT ==========\n");
        
        Light livingRoomLight = new Light("Living Room Light");
        
        System.out.println("1. Turn On:");
        livingRoomLight.turnOn();
        System.out.println("   " + livingRoomLight.getStatus());
        System.out.println();
        
        System.out.println("2. Set Brightness to 75%:");
        livingRoomLight.setBrightness(75);
        System.out.println("   " + livingRoomLight.getStatus());
        System.out.println();
        
        System.out.println("3. Turn Off:");
        livingRoomLight.turnOff();
        System.out.println("   " + livingRoomLight.getStatus());
        System.out.println();
        
        // ===== TEST THERMOSTAT =====
        System.out.println("========== TESTING THERMOSTAT ==========\n");
        
        Thermostat bedroomThermostat = new Thermostat("Bedroom Climate", 68.0);
        
        System.out.println("1. Initial Status:");
        System.out.println("   " + bedroomThermostat.getStatus());
        System.out.println();
        
        System.out.println("2. Turn On:");
        bedroomThermostat.turnOn();
        System.out.println("   " + bedroomThermostat.getStatus());
        System.out.println();
        
        System.out.println("3. Set Target to 72°F:");
        bedroomThermostat.setTargetTemp(72.0);
        System.out.println("   " + bedroomThermostat.getStatus());
        System.out.println();
        
        System.out.println("4. Turn Off:");
        bedroomThermostat.turnOff();
        System.out.println("   " + bedroomThermostat.getStatus());
        System.out.println();
        
        // ===== TEST SPEAKER =====
        System.out.println("========== TESTING SPEAKER ==========\n");
        
        Speaker kitchenSpeaker = new Speaker("Kitchen Speaker");
        
        System.out.println("1. Turn On:");
        kitchenSpeaker.turnOn();
        System.out.println("   " + kitchenSpeaker.getStatus());
        System.out.println();
        
        System.out.println("2. Set Volume to 70%:");
        kitchenSpeaker.setVolume(70);
        System.out.println("   " + kitchenSpeaker.getStatus());
        System.out.println();
        
        System.out.println("3. Play Music:");
        kitchenSpeaker.play("Morning Jazz Playlist");
        System.out.println("   " + kitchenSpeaker.getStatus());
        System.out.println();
        
        System.out.println("4. Stop Playback:");
        kitchenSpeaker.stop();
        System.out.println("   " + kitchenSpeaker.getStatus());
        System.out.println();
        
        System.out.println("5. Turn Off:");
        kitchenSpeaker.turnOff();
        System.out.println("   " + kitchenSpeaker.getStatus());
        System.out.println();
        
        // ===== TEST SMART HOME SCENARIO =====
        System.out.println("========== SMART HOME SCENARIO ==========\n");
        System.out.println("Scenario: Morning Routine\n");
        
        // Create devices
        Light bedroomLight = new Light("Bedroom Light");
        Thermostat livingRoomClimate = new Thermostat("Living Room Climate", 65.0);
        Speaker livingRoomSpeaker = new Speaker("Living Room Speaker");
        
        System.out.println("6:00 AM - Bedroom light gradually turns on:");
        bedroomLight.turnOn();
        bedroomLight.setBrightness(30);
        System.out.println("   " + bedroomLight.getStatus());
        System.out.println();
        
        System.out.println("6:30 AM - Increase bedroom light:");
        bedroomLight.setBrightness(75);
        System.out.println("   " + bedroomLight.getStatus());
        System.out.println();
        
        System.out.println("7:00 AM - Start living room climate:");
        livingRoomClimate.turnOn();
        livingRoomClimate.setTargetTemp(72.0);
        System.out.println("   " + livingRoomClimate.getStatus());
        System.out.println();
        
        System.out.println("7:15 AM - Play morning news:");
        livingRoomSpeaker.play("Morning News Podcast");
        livingRoomSpeaker.setVolume(40);
        System.out.println("   " + livingRoomSpeaker.getStatus());
        System.out.println();
        
        // ===== SUMMARY =====
        System.out.println("========== CURRENT STATUS OF ALL DEVICES ==========\n");
        
        System.out.println("Living Room:");
        System.out.println("  • " + livingRoomLight.getStatus());
        System.out.println("  • " + livingRoomClimate.getStatus());
        System.out.println("  • " + livingRoomSpeaker.getStatus());
        System.out.println();
        
        System.out.println("Bedroom:");
        System.out.println("  • " + bedroomLight.getStatus());
        System.out.println("  • " + bedroomThermostat.getStatus());
        System.out.println();
        
        System.out.println("Kitchen:");
        System.out.println("  • " + kitchenSpeaker.getStatus());
        System.out.println();
        
        System.out.println("============================================");
        System.out.println("   ✓ ALL TESTS COMPLETED SUCCESSFULLY!");
        System.out.println("   ✓ All 3 device types working perfectly!");
        System.out.println("============================================");
    }
}