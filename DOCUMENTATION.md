# 📚 Smart Home Automation System - Complete Documentation

**Version**: 1.0-SNAPSHOT  
**Last Updated**: November 19, 2025  
**Java Version**: 24  
**Build Tool**: Maven 3.8.0+

---

## Table of Contents

1. [Overview](#overview)
2. [Quick Start](#quick-start)
3. [Architecture](#architecture)
4. [Design Patterns](#design-patterns)
5. [Core Components](#core-components)
6. [API Reference](#api-reference)
7. [Usage Guide](#usage-guide)
8. [Advanced Features](#advanced-features)
9. [Development Guide](#development-guide)
10. [Troubleshooting](#troubleshooting)

---

## Overview

### What is Smart Home Automation System?

The Smart Home Automation System is a comprehensive Java application that demonstrates object-oriented programming principles and design patterns through a practical smart home control system. It provides a graphical user interface for managing smart devices (lights, thermostats, speakers, and fans) across multiple rooms with advanced automation capabilities.

### Key Highlights

- **8 Design Patterns**: Factory, Singleton, Observer, Command, Strategy, Decorator, Adapter, and Composite
- **Full-Featured GUI**: Swing-based interface with real-time updates
- **AI Automation**: Intelligent device control with pattern recognition
- **Undo/Redo**: Complete command history management
- **Room Management**: Hierarchical device organization
- **Extensible**: Easy to add new device types and features

### Project Goals

1. **Educational**: Demonstrate practical application of design patterns
2. **Clean Code**: Maintainable, well-documented source code
3. **Real-World Applicable**: Patterns shown in realistic context
4. **Extensible**: Framework for adding new features

---

## Quick Start

### Installation

-#### Prerequisites
- Java 24 or higher
  - If you don't have JDK 24 installed, download and install a JDK 24 distribution (Eclipse Temurin/Adoptium, Oracle JDK, or another vendor) and ensure `java` and `javac` on your PATH point to the JDK 24 installation.
- Maven 3.8.0 or higher (optional, but recommended)
- Windows, macOS, or Linux with GUI support

#### Step 1: Navigate to Project Directory
```powershell
cd "c:\Users\dwayn\OneDrive\Documentos\Coding Projects (New)\Java\Java Practice\OOP\SmartHomeSystem"
```

#### Step 2: Compile the Project
```bash
# Using javac (built-in Java compiler)
javac -encoding UTF-8 -d target/classes -sourcepath src/main/java src/main/java/com/mycompany/smarthomesystem/*.java src/main/java/com/smarthome/behavioral/*.java src/main/java/com/smarthome/creational/*.java src/main/java/com/smarthome/devices/*.java src/main/java/com/smarthome/gui/*.java src/main/java/com/smarthome/structural/*.java
```

#### Step 3: Run the Application
```bash
# Run the GUI
java -cp target/classes com.smarthome.gui.SmartHomeGUI

# Or run the main entry point
java -cp target/classes com.mycompany.smarthomesystem.SmartHomeSystem
```

### First-Time Use

1. **Launch Application**: Run either of the commands above
2. **View Default Devices**: 10 sample devices are pre-configured
3. **Select a Device**: Click any device in the left panel
4. **Control Device**: Use Turn ON/OFF buttons or sliders
5. **Check Status**: Monitor the Status tab for real-time updates

---

## Architecture

### System Layers

```
┌─────────────────────────────────────────────────────┐
│         Presentation Layer (GUI)                    │
│    SmartHomeGUI (Swing, Tabbed Interface)          │
└──────────────────┬──────────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────────┐
│      Controller/Business Logic Layer                │
│  CentralController (Singleton), ControlStrategy    │
└──────────────────┬──────────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────────┐
│       Command & Strategy Layer                      │
│  Command Pattern, Strategy Pattern, Observer       │
└──────────────────┬──────────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────────┐
│        Device & Room Layer                          │
│  SmartDevice Interface, Room (Composite)           │
│  Light, Thermostat, Speaker, FanAdapter           │
└──────────────────┬──────────────────────────────────┘
                   │
┌──────────────────▼──────────────────────────────────┐
│   Enhancement Layer (Decorators & Adapters)        │
│  Decorators, Adapters for legacy devices          │
└─────────────────────────────────────────────────────┘
```

### Package Organization

#### com.mycompany.smarthomesystem
- **Purpose**: Application entry point
- **Contents**: SmartHomeSystem.java, Speaker.java, Test files
- **Responsibility**: Main execution and pattern demonstrations

#### com.smarthome.behavioral
- **Purpose**: Behavioral design patterns
- **Patterns**: Command, Observer, Strategy
- **Classes**:
  - `Command` - Interface for encapsulated actions
  - `TurnOnCommand`, `TurnOffCommand`, `AdjustBrightnessCommand` - Concrete commands
  - `ControlStrategy` - Interface for control algorithms
  - `ManualControl`, `ScheduledControl`, `AIControl` - Strategy implementations
  - `Observer` - Interface for state change notifications
  - `DeviceLogger` - Concrete observer for logging

#### com.smarthome.creational
- **Purpose**: Creational design patterns
- **Patterns**: Factory, Singleton
- **Classes**:
  - `CentralController` - Singleton managing all devices
  - `DeviceFactory` - Factory for creating device instances

#### com.smarthome.devices
- **Purpose**: Smart device implementations
- **Interface**: SmartDevice
- **Classes**:
  - `Light` - Dimmable light with brightness control
  - `Thermostat` - Temperature controller
  - `Speaker` - Audio device with volume control

#### com.smarthome.structural
- **Purpose**: Structural design patterns
- **Patterns**: Decorator, Adapter, Composite
- **Classes**:
  - `Room` - Composite container for devices
  - `DeviceDecorator` - Base class for decorators
  - `EnergyMonitorDecorator` - Tracks energy usage
  - `VoiceControlDecorator` - Adds voice control
  - `FanAdapter` - Adapter for legacy OldFan
  - `OldFan` - Legacy fan device

#### com.smarthome.gui
- **Purpose**: User interface
- **Framework**: Swing (javax.swing)
- **Classes**:
  - `SmartHomeGUI` - Main GUI window and logic

---

## Design Patterns

### 1. Factory Pattern

**Location**: `com.smarthome.creational.DeviceFactory`

**Purpose**: Create device instances without exposing creation logic

**Implementation**:
```java
SmartDevice device = factory.createDevice("light", "Living Room Light");
```

**Benefits**:
- Centralized device creation
- Easy to add new device types
- Client code doesn't depend on concrete classes
- Factory can implement smart creation logic

**Real-World Usage**:
- Database connection pooling
- Object serialization/deserialization
- UI component creation

### 2. Singleton Pattern

**Location**: `com.smarthome.creational.CentralController`

**Purpose**: Ensure only one controller instance exists globally

**Implementation**:
```java
CentralController controller = CentralController.getInstance();
```

**Benefits**:
- Global access to controller
- Prevents multiple controller instances
- Manages shared state (all devices)
- Centralized command history

**Real-World Usage**:
- Database connections
- Configuration managers
- Logging systems
- Thread pools

### 3. Observer Pattern

**Location**: `com.smarthome.behavioral.Observer`, `com.smarthome.devices.SmartDevice`

**Purpose**: Notify multiple observers when device state changes

**Implementation**:
```java
device.addObserver(logger);
device.turnOn();  // Automatically notifies all observers
```

**Benefits**:
- Loose coupling between devices and observers
- Real-time UI updates
- Support for multiple observers
- Easy to add/remove observers

**Real-World Usage**:
- Event listeners (GUI frameworks)
- MVC pattern (Model updates notify Views)
- Real-time notifications
- Publish-subscribe systems

### 4. Command Pattern

**Location**: `com.smarthome.behavioral.Command`

**Purpose**: Encapsulate requests as objects with undo/redo

**Implementation**:
```java
Command command = new TurnOnCommand(device);
controller.executeCommand(command);
controller.undoLastCommand();
```

**Benefits**:
- Undo/Redo functionality
- Command queuing and scheduling
- Command logging
- Decouples invoker from receiver

**Real-World Usage**:
- Undo/Redo in text editors
- Transaction management
- Macro recording
- Job scheduling

### 5. Strategy Pattern

**Location**: `com.smarthome.behavioral.ControlStrategy`

**Purpose**: Switch control algorithms at runtime

**Implementation**:
```java
controller.setControlStrategy(new AIControl());
// Later...
controller.setControlStrategy(new ManualControl());
```

**Benefits**:
- Interchangeable algorithms
- Runtime behavior switching
- Easy to add new strategies
- Cleaner than if-else chains

**Real-World Usage**:
- Sorting algorithms
- Payment methods
- Authentication strategies
- Routing algorithms

### 6. Decorator Pattern

**Location**: `com.smarthome.structural.DeviceDecorator`

**Purpose**: Add features to devices dynamically

**Implementation**:
```java
SmartDevice light = factory.createDevice("light", "Kitchen Light");
SmartDevice monitored = new EnergyMonitorDecorator(light);
```

**Benefits**:
- Compose features flexibly
- Avoid class explosion
- Single Responsibility Principle
- Features are optional

**Real-World Usage**:
- Input/Output streams (Java)
- UI components with borders/scrollbars
- Feature flags/toggles
- Logging wrappers

### 7. Adapter Pattern

**Location**: `com.smarthome.structural.FanAdapter`

**Purpose**: Make incompatible legacy devices compatible

**Implementation**:
```java
OldFan oldFan = new OldFan("Vintage Fan");
FanAdapter adapter = new FanAdapter(oldFan);
// Now adapter implements SmartDevice
```

**Benefits**:
- Legacy device integration
- Interface translation
- Minimal legacy code changes
- Clean wrapper

**Real-World Usage**:
- Driver adapters
- Third-party library integration
- Legacy system integration
- Protocol adapters

### 8. Composite Pattern

**Location**: `com.smarthome.structural.Room`

**Purpose**: Treat individual and grouped devices uniformly

**Implementation**:
```java
Room room = new Room("Living Room");
room.addDevice(light);
room.addDevice(thermostat);
room.turnOn();  // Turns on all devices
```

**Benefits**:
- Uniform treatment of individuals and groups
- Hierarchical organization
- Simplified client code
- Recursive composition

**Real-World Usage**:
- File system directories
- GUI component hierarchies
- Organizational structures
- Game object hierarchies

---

## Core Components

### SmartDevice Interface

**Location**: `com.smarthome.devices.SmartDevice`

**Responsibility**: Defines contract for all smart devices

**Methods**:
```java
interface SmartDevice {
    void turnOn();                          // Enable device
    void turnOff();                         // Disable device
    String getStatus();                     // Get current state
    String getName();                       // Get device name
    void addObserver(Observer observer);    // Register observer
    void notifyObservers();                 // Notify observers
}
```

### SmartDevice Implementations

#### Light
- **Purpose**: Dimmable light device
- **Features**:
  - On/Off control
  - Brightness adjustment (0-100%)
  - Status reporting
  - Observer notifications
- **Usage**:
  ```java
  Light light = new Light("Living Room Light");
  light.turnOn();
  light.setBrightness(75);
  System.out.println(light.getStatus());
  ```

#### Thermostat
- **Purpose**: Temperature control
- **Features**:
  - Temperature range: 50-90°F
  - Target temperature setting
  - Mode: heating/cooling/maintaining
  - 5-second auto-adjustment
- **Usage**:
  ```java
  Thermostat thermo = new Thermostat("Living Room Thermostat", 70.0);
  thermo.turnOn();
  thermo.setTargetTemp(75);
  // After 5 seconds, current temp adjusts to target
  ```

#### Speaker
- **Purpose**: Audio playback device
- **Features**:
  - Volume control (0-100%)
  - Playback control (play/stop)
  - Content display
- **Usage**:
  ```java
  Speaker speaker = new Speaker("Kitchen Speaker");
  speaker.setVolume(50);
  speaker.play("Spotify Playlist");
  ```

### Room (Composite)

**Location**: `com.smarthome.structural.Room`

**Responsibility**: Container for devices with composite control

**Key Methods**:
```java
Room room = new Room("Living Room");
room.addDevice(device);              // Add device
room.removeDevice(device);           // Remove device
room.turnOn();                        // Turn on all devices
room.turnOff();                       // Turn off all devices
List<SmartDevice> getDevices();      // Get all devices
```

### CentralController (Singleton)

**Location**: `com.smarthome.creational.CentralController`

**Responsibility**: Centralized control and state management

**Key Methods**:
```java
CentralController controller = CentralController.getInstance();
controller.addDevice(device);                          // Register device
controller.setControlStrategy(strategy);               // Change control mode
controller.executeCommand(command);                    // Execute command
controller.undoLastCommand();                          // Undo
controller.redoLastCommand();                          // Redo
List<SmartDevice> getAllDevices();                    // Get all devices
ControlStrategy getControlStrategy();                 // Get current strategy
```

---

## API Reference

### SmartDevice Interface

```java
package com.smarthome.devices;

public interface SmartDevice {
    /**
     * Turns the device on.
     */
    void turnOn();
    
    /**
     * Turns the device off.
     */
    void turnOff();
    
    /**
     * Gets the current status of the device.
     * @return Status string
     */
    String getStatus();
    
    /**
     * Gets the device name.
     * @return Device name
     */
    String getName();
    
    /**
     * Adds an observer to this device.
     * @param observer Observer to add
     */
    void addObserver(Observer observer);
    
    /**
     * Notifies all observers of state change.
     */
    void notifyObservers();
}
```

### Light Class

```java
public class Light implements SmartDevice {
    public Light(String name);
    public void turnOn();
    public void turnOff();
    public String getStatus();
    public String getName();
    public void setBrightness(int brightness);
    public int getBrightness();
    public void addObserver(Observer observer);
    public void notifyObservers();
}
```

### Thermostat Class

```java
public class Thermostat implements SmartDevice {
    public Thermostat(String name, double currentTemp);
    public void turnOn();
    public void turnOff();
    public String getStatus();
    public String getName();
    public void setTargetTemp(double targetTemp);
    public double getTargetTemp();
    public double getCurrentTemp();
    public String getMode();
    public void addObserver(Observer observer);
    public void notifyObservers();
}
```

### CentralController Class

```java
public class CentralController {
    public static CentralController getInstance();
    public void addDevice(SmartDevice device);
    public void setControlStrategy(ControlStrategy strategy);
    public void executeCommand(Command command);
    public boolean undoLastCommand();
    public boolean redoLastCommand();
    public List<SmartDevice> getAllDevices();
    public ControlStrategy getControlStrategy();
}
```

### DeviceFactory Class

```java
public class DeviceFactory {
    public SmartDevice createDevice(String type, String name);
    // type: "light", "thermostat", "speaker"
}
```

---

## Usage Guide

### Basic Device Control

#### Turning Devices On/Off
```java
CentralController controller = CentralController.getInstance();
SmartDevice device = controller.getAllDevices().get(0);

// Turn on
Command turnOn = new TurnOnCommand(device);
controller.executeCommand(turnOn);

// Turn off
Command turnOff = new TurnOffCommand(device);
controller.executeCommand(turnOff);
```

#### Adjusting Light Brightness
```java
Light light = (Light) device;
Command brightness = new AdjustBrightnessCommand(light, 75);
controller.executeCommand(brightness);
```

#### Adjusting Thermostat
```java
Thermostat thermo = (Thermostat) device;
thermo.setTargetTemp(72);  // Target temperature
// Current temperature adjusts after 5 seconds
```

### Room Operations

#### Creating a Room
```java
Room livingRoom = new Room("Living Room");
livingRoom.addDevice(light);
livingRoom.addDevice(thermostat);
```

#### Room Control
```java
// Turn on all devices in room
livingRoom.turnOn();

// Turn off all devices in room
livingRoom.turnOff();

// Get all devices in room
List<SmartDevice> devices = livingRoom.getDevices();
```

### Control Strategies

#### Switch to Manual Control
```java
controller.setControlStrategy(new ManualControl());
// Users control everything manually
```

#### Switch to Scheduled Control
```java
controller.setControlStrategy(new ScheduledControl());
// Time-based automation kicks in
```

#### Switch to AI Control
```java
controller.setControlStrategy(new AIControl());
// Intelligent automated control
```

### Undo/Redo

```java
// Execute a command
Command cmd = new TurnOnCommand(device);
controller.executeCommand(cmd);

// Undo
controller.undoLastCommand();

// Redo
controller.redoLastCommand();
```

### Using Decorators

#### Energy Monitoring
```java
SmartDevice light = factory.createDevice("light", "Kitchen Light");
SmartDevice monitored = new EnergyMonitorDecorator(light);
// Now device tracks energy consumption
monitored.turnOn();
```

#### Voice Control
```java
SmartDevice monitored = new EnergyMonitorDecorator(light);
SmartDevice voiceEnabled = new VoiceControlDecorator(monitored);
// Device now supports voice commands
```

### Legacy Device Integration

```java
OldFan oldFan = new OldFan("Vintage Fan");
FanAdapter adapter = new FanAdapter(oldFan);

// Now can control legacy fan like any SmartDevice
adapter.turnOn();
adapter.setSpeed(2);
System.out.println(adapter.getStatus());
```

---

## Advanced Features

### AI Automation

#### How It Works
1. **Activation**: Start AI Control mode
2. **Automation**: Devices toggle ON/OFF randomly every 5 seconds
3. **Logging**: Changes logged to AI log and status log
4. **Callbacks**: Custom callbacks notify UI of changes

#### Starting AI Automation
```java
AIControl aiControl = new AIControl();
aiControl.setAutomationCallback((deviceName, isOn) -> {
    String action = isOn ? "turned ON" : "turned OFF";
    System.out.println(deviceName + " " + action);
});
aiControl.startRandomAutomation(controller.getAllDevices());
```

#### Stopping AI Automation
```java
aiControl.stopRandomAutomation();
```

### Dynamic Temperature Adjustment

When thermostat target temperature is set:
1. Target temperature is updated immediately
2. A 5-second timer starts
3. After 5 seconds, current temperature matches target
4. Heating/cooling mode updates appropriately
5. Observers are notified

```java
thermostat.setTargetTemp(75);
// Wait 5 seconds...
// Current temperature is now 75°F
```

### Device Sliders in GUI

#### Brightness Slider (Lights)
- **Range**: 0-100%
- **Increments**: 1%
- **Major Ticks**: Every 25%

#### Temperature Slider (Thermostats)
- **Range**: 50-90°F
- **Increments**: 1°F
- **Major Ticks**: Every 10°F

#### Volume Slider (Speakers)
- **Range**: 0-100%
- **Increments**: 1%
- **Major Ticks**: Every 25%

#### Fan Speed Slider (Fans)
- **Range**: 0-3 (OFF, LOW, MEDIUM, HIGH)
- **Increments**: 1 level
- **Major Ticks**: Each level

---

## Development Guide

### Adding a New Device Type

1. **Create Device Class**:
```java
public class NewDevice implements SmartDevice {
    private String name;
    private boolean isOn;
    private List<Observer> observers;
    
    public NewDevice(String name) {
        this.name = name;
        this.observers = new ArrayList<>();
    }
    
    @Override
    public void turnOn() {
        isOn = true;
        notifyObservers();
    }
    
    @Override
    public void turnOff() {
        isOn = false;
        notifyObservers();
    }
    
    @Override
    public String getStatus() {
        return name + " is " + (isOn ? "ON" : "OFF");
    }
    
    // ... implement other methods
}
```

2. **Update DeviceFactory**:
```java
public SmartDevice createDevice(String type, String name) {
    switch(type.toLowerCase()) {
        case "newdevice":
            return new NewDevice(name);
        // ... other cases
    }
    return null;
}
```

3. **Add to GUI** (if needed):
```java
JButton addNewButton = new JButton("+ Add New Device");
addNewButton.addActionListener(e -> addDevice("newdevice"));
```

### Adding a New Decorator

```java
public class CustomDecorator extends DeviceDecorator {
    public CustomDecorator(SmartDevice wrappedDevice) {
        super(wrappedDevice);
    }
    
    @Override
    public void turnOn() {
        // Custom behavior
        super.turnOn();
    }
    
    // Override other methods as needed
}
```

### Adding a New Control Strategy

```java
public class CustomStrategy implements ControlStrategy {
    @Override
    public String getDescription() {
        return "Custom control strategy";
    }
    
    // Custom strategy logic
}
```

### Building and Testing

```bash
# Compile
javac -encoding UTF-8 -d target/classes -sourcepath src/main/java [all java files]

# Generate Javadoc
javadoc -encoding UTF-8 -d target/javadoc -sourcepath src/main/java -subpackages com

# Run tests
java -cp target/classes com.mycompany.smarthomesystem.Test[ClassName]
```

---

## Troubleshooting

### Common Issues

#### 1. "Cannot find symbol" Errors
**Problem**: Compilation fails with missing symbols
**Solution**: 
- Ensure all source files are compiled together
- Check encoding is set to UTF-8
- Verify package declarations match directory structure

#### 2. GUI Window Doesn't Appear
**Problem**: Application runs but GUI doesn't show
**Solution**:
- Ensure X11 forwarding if on Linux/Mac
- Check if running headless environment
- Try: `java -Djava.awt.headless=false -cp target/classes com.smarthome.gui.SmartHomeGUI`

#### 3. Emoji Characters Display as "?"
**Problem**: Room/device names show question marks
**Solution**:
- Already handled with UTF-8 encoding
- Terminal may not support emoji display (GUI works fine)
- No functional impact

#### 4. Undo/Redo Not Working
**Problem**: Undo button disabled or doesn't revert changes
**Solution**:
- Only works with commands executed through CentralController
- Direct method calls bypass command pattern
- Check that executeCommand() is used

#### 5. Thermostat Temperature Not Changing
**Problem**: Current temperature doesn't adjust after 5 seconds
**Solution**:
- Ensure thermostat is ON (turnOn())
- Wait full 5 seconds
- Check that setTargetTemp() was called
- Verify target temp is within 50-90°F range

### Debug Output

Enable detailed logging:
```java
// Many classes include System.out.println statements
// Run with:
java -cp target/classes com.smarthome.gui.SmartHomeGUI 2>&1 | tee debug.log
```

### Getting Help

1. Check Javadoc documentation: `target/javadoc/index.html`
2. Review source code comments
3. Examine test files for usage examples
4. Check README.md for common workflows

---

## Glossary

| Term | Definition |
|------|-----------|
| **Command Pattern** | Design pattern that encapsulates requests as objects |
| **Decorator Pattern** | Design pattern that adds features to objects dynamically |
| **Observer Pattern** | Design pattern that notifies multiple objects of state changes |
| **Strategy Pattern** | Design pattern that allows runtime algorithm switching |
| **Factory Pattern** | Design pattern that creates objects without exposing creation logic |
| **Singleton Pattern** | Design pattern that ensures only one instance exists |
| **Adapter Pattern** | Design pattern that makes incompatible interfaces compatible |
| **Composite Pattern** | Design pattern that treats groups and individuals uniformly |
| **Undo/Redo** | Ability to reverse and restore command execution |
| **Observer** | Object that monitors and reacts to state changes |
| **Strategy** | Encapsulated algorithm that can be changed at runtime |

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0-SNAPSHOT | Nov 19, 2025 | Initial release with 8 design patterns, full GUI, AI automation, and device sliders |

---

## Additional Resources

- **Javadoc**: `target/javadoc/index.html` - Complete API documentation
- **README**: `README.md` - Quick start guide
- **Source Code**: `src/main/java/` - Well-commented implementation

---

**Last Updated**: November 19, 2025  
**Status**: Complete and Production-Ready ✅
