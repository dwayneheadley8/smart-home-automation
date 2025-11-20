# 🏠 Smart Home Automation System

A comprehensive Java application demonstrating object-oriented design patterns and principles through a practical smart home automation system. This project includes a full-featured GUI for managing smart devices across multiple rooms with advanced AI automation capabilities.

## 📋 Table of Contents

- [Features](#features)
- [Design Patterns](#design-patterns)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Control Modes](#control-modes)
- [AI Automation](#ai-automation)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Building & Running](#building--running)

## ✨ Features

### Core Device Management
- **Add/Remove Devices**: Dynamically add lights, thermostats, and speakers to any room
- **Device Control**: Turn devices on/off with individual or room-wide controls
- **Room Grouping**: Organize devices into logical rooms and control them as a group
- **Real-time Status**: View live device status and state changes in the GUI
- **Brightness Control**: Adjustable brightness slider for lights (0-100%)
- **Device-Specific Settings**: Temperature control for thermostats, volume for speakers

### Room Management
- **Multiple Rooms**: Pre-configured with Living Room, Bedroom, Kitchen, and Bathroom
- **Composite Control**: Turn on/off all devices in a room with a single command
- **Room Details**: View all devices in a room and their current status
- **Dynamic Updates**: Add devices to rooms and see changes instantly
- **Visual Organization**: Rooms displayed with emoji indicators

### Control Modes
- **Manual Control**: Direct user control via GUI buttons
- **Scheduled Control**: Time-based automation with intelligent decisions
- **AI Control**: Intelligent automated control with device learning (new!)

### AI Automation (NEW!)
- **Random Device Automation**: Devices turn ON/OFF randomly every 5 seconds
- **Intelligent Analysis**: AI analyzes occupancy, time, and temperature
- **Contextual Decisions**: Makes smart choices based on environment
- **Real-time Logging**: Live status updates in dedicated AI log
- **Automation Callbacks**: Device state changes trigger UI notifications
- **Start/Stop Controls**: Easy toggle for automation mode
- **Dual Logging**: Updates appear in both AI log and main status log

### Advanced Features
- **Command History**: Undo/Redo functionality for all device commands
- **Legacy Device Support**: Integrate old fan devices using the Adapter pattern
- **Energy Monitoring**: Track energy consumption for decorated devices
- **Activity Logging**: Comprehensive timestamp-based activity log with 24-hour format
- **Voice Control Ready**: Framework for voice control decorators
- **Flexible UI**: Responsive tabbed interface with dynamic tab management

## 🏗️ Design Patterns

This project demonstrates 8 core object-oriented design patterns:

### 1. **Factory Pattern** (`DeviceFactory`)
Creates smart device instances without exposing creation logic to client code.
- Centralized device instantiation
- Easy to add new device types
- Encapsulates creation complexity

### 2. **Singleton Pattern** (`CentralController`)
Ensures only one instance of the central controller exists throughout the application.
- Single point of control for all devices
- Maintains global device state
- Manages command history and strategies

### 3. **Observer Pattern** (`Observer`, `SmartDevice`)
Notifies multiple observers when device state changes.
- Devices notify observers of status changes
- Decoupled event handling
- Real-time UI updates

### 4. **Command Pattern** (`Command`, `TurnOnCommand`, `TurnOffCommand`, `AdjustBrightnessCommand`)
Encapsulates requests as objects with undo/redo capability.
- Enables command queuing and history
- Supports undo/redo operations
- Decouples invoker from receiver

### 5. **Strategy Pattern** (`ControlStrategy`, `ManualControl`, `ScheduledControl`, `AIControl`)
Allows runtime selection of control algorithms.
- Interchangeable control strategies
- Switch modes without code changes
- Each strategy has different behavior

### 6. **Decorator Pattern** (`DeviceDecorator`, `EnergyMonitorDecorator`, `VoiceControlDecorator`)
Adds new functionality to devices dynamically without modifying original classes.
- Energy monitoring wrapper
- Voice control enhancement
- Composable feature additions

### 7. **Adapter Pattern** (`FanAdapter`, `OldFan`)
Makes incompatible legacy devices work with the smart home system.
- Bridges old fan interface to SmartDevice
- Translates legacy method calls
- Example: `startFan()` → `turnOn()`

### 8. **Composite Pattern** (`Room`, `SmartDevice`)
Treats individual devices and groups of devices (rooms) uniformly.
- Rooms contain multiple devices
- Rooms can turn on/off all devices
- Hierarchical device organization

## 📁 Project Structure

```
SmartHomeSystem/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
├── .gitignore                                 # Git ignore rules
└── src/
    ├── main/
    │   └── java/
    │       ├── com/mycompany/smarthomesystem/
    │       │   ├── SmartHomeSystem.java       # Main entry point
    │       │   ├── Speaker.java               # Speaker device
    │       │   └── Test*.java                 # Pattern demonstration tests
    │       │
    │       └── com/smarthome/
    │           ├── behavioral/                # Behavioral patterns
    │           │   ├── Command.java           # Command Pattern interface
    │           │   ├── TurnOnCommand.java     # Concrete command
    │           │   ├── TurnOffCommand.java    # Concrete command
    │           │   ├── ControlStrategy.java   # Strategy Pattern interface
    │           │   ├── ManualControl.java     # Concrete strategy
    │           │   ├── ScheduledControl.java  # Concrete strategy
    │           │   ├── AIControl.java         # Concrete strategy
    │           │   ├── Observer.java          # Observer Pattern interface
    │           │   └── DeviceLogger.java      # Concrete observer
    │           │
    │           ├── creational/                # Creational patterns
    │           │   ├── DeviceFactory.java     # Factory Pattern
    │           │   └── CentralController.java # Singleton Pattern
    │           │
    │           ├── devices/                   # Smart device implementations
    │           │   ├── SmartDevice.java       # Device interface
    │           │   ├── Light.java             # Light device with brightness
    │           │   ├── Thermostat.java        # Temperature control
    │           │   └── Speaker.java           # Audio device
    │           │
    │           ├── structural/                # Structural patterns
    │           │   ├── DeviceDecorator.java   # Decorator Pattern base
    │           │   ├── EnergyMonitorDecorator.java # Energy tracking
    │           │   ├── VoiceControlDecorator.java  # Voice control
    │           │   ├── Room.java              # Composite Pattern
    │           │   ├── FanAdapter.java        # Adapter Pattern
    │           │   └── OldFan.java            # Legacy device
    │           │
    │           └── gui/                       # User interface
    │               └── SmartHomeGUI.java      # Swing GUI application
    │
    └── test/
        └── java/                              # Unit tests
```

## 🚀 Getting Started

### Prerequisites

- **Java 24+** (project compiled with Java 24)
- **Maven 3.8.0+** (for building)
- If you don't have JDK 24 installed, download and install a JDK 24 distribution (Adoptium, Oracle, or vendor of your choice) and ensure `java` and `javac` on your PATH point to the JDK 24 executables.
- **Git** (for version control)

### Installation

1. **Clone or download the repository**
   ```bash
   cd "c:\Users\dwayn\OneDrive\Documentos\Coding Projects (New)\Java\Java Practice\OOP"
   ```

2. **Build the project**
   ```bash
   cd SmartHomeSystem
   mvn clean compile
   ```

3. **Run the GUI application**
   ```bash
   cd SmartHomeSystem
   java -cp target/classes com.smarthome.gui.SmartHomeGUI
   ```

   Or using Maven:
   ```bash
   mvn exec:java -Dexec.mainClass="com.smarthome.gui.SmartHomeGUI"
   ```

## 💻 Usage

### Main Window

The GUI is organized into several sections:

#### Left Panel - Device List
- **Device Selection**: Click any device to select it
- **Add Buttons**: 
  - "+ Add Light" - Create new light device
  - "+ Add Thermostat" - Create new thermostat
  - "+ Add Speaker" - Create new speaker
- **Remove Button**: Delete selected device (with confirmation)

#### Center Panel - Device Control
- **Selected Device Display**: Shows currently selected device
- **Control Buttons**:
  - **Turn ON** (Green): Activates device or entire room
  - **Turn OFF** (Red): Deactivates device or entire room
  - **↶ Undo**: Revert last command
  - **↷ Redo**: Reapply undone command
- **Brightness Slider**: Adjust light brightness (0-100%)

#### Right Panel - Tabs
- **Status Tab**: Real-time activity log with timestamps
  - Shows all device state changes
  - Command execution history
  - Control mode changes
  - Device additions/removals
  
- **Rooms Tab**: Room management and overview
  - List of all rooms with emojis
  - Device list for selected room
  - Individual device status details
  - Room control instructions

#### Top Panel - Control Mode
- **Dropdown Menu**: Select control strategy
  - **Manual**: Direct user control
  - **Scheduled**: Time-based automation
  - **AI**: Intelligent automated control

#### Bottom Panel - System Info
- Design patterns used in the system
- Current device count

### Workflow Examples

#### Adding a Device
1. Click "+ Add Light" (or desired device type)
2. Enter device name in dialog
3. Select target room from dropdown
4. Device appears in device list and room details

#### Controlling a Device
1. Click device in device list to select it
2. Click "Turn ON" or "Turn OFF" button
3. Watch status update in log and room details
4. Adjust brightness slider if it's a light

#### Room Control
1. Switch to "Rooms" tab
2. Click a room name in the room list
3. View all devices in that room
4. Click "Turn ON" or "Turn OFF" to control entire room
5. Watch all devices change state simultaneously

#### Undoing Commands
1. Perform device control (turn on/off)
2. Click "↶ Undo" to revert
3. Click "↷ Redo" to restore

## 🏛️ Architecture

### Design Principles

1. **Separation of Concerns**: Each class has a single responsibility
2. **Open/Closed Principle**: Extensible without modifying existing code
3. **Dependency Inversion**: Depend on abstractions, not concrete classes
4. **DRY (Don't Repeat Yourself)**: Reusable components and patterns

### Control Flow

```
User Input (GUI)
    ↓
SmartHomeGUI (Presentation)
    ↓
CentralController (Singleton - Business Logic)
    ↓
Command Pattern (Encapsulated Actions)
    ↓
SmartDevice (Control & State)
    ↓
Observer Pattern (State Notifications)
    ↓
GUI Update (Status Log & Room Display)
```

### Device Hierarchy

```
SmartDevice (Interface)
├── Light (with brightness control)
├── Thermostat (with temperature control)
├── Speaker (with volume control)
├── FanAdapter (adapted legacy OldFan)
└── Decorated Variants
    ├── EnergyMonitorDecorator (+ energy tracking)
    ├── VoiceControlDecorator (+ voice commands)
    └── Custom Decorators (extensible)
```

### Room Composition

```
Room (Composite)
├── SmartDevice (Light)
├── SmartDevice (Thermostat)
├── SmartDevice (Speaker)
├── SmartDevice (Adapted Fan)
└── ... (any SmartDevice)
```

## 🛠️ Technologies

- **Language**: Java 24
- **Build Tool**: Maven 3.8.0+
- **GUI Framework**: Swing (javax.swing)
- **Version Control**: Git
- **Code Patterns**: 8 GoF Design Patterns
- **Architecture**: MVC-inspired with layered pattern architecture

## 📚 Learning Outcomes

This project demonstrates:

✅ **Object-Oriented Design**: Proper use of inheritance and polymorphism  
✅ **Design Patterns**: All major GoF patterns applied in real context  
✅ **GUI Development**: Swing-based user interface with real-time updates  
✅ **Event-Driven Programming**: Observer pattern for state notifications  
✅ **Command Pattern**: Undo/redo functionality  
✅ **Factory Pattern**: Device creation and abstraction  
✅ **Adapter Pattern**: Legacy system integration  
✅ **Composite Pattern**: Hierarchical device organization  
✅ **Singleton Pattern**: Global state management  
✅ **Strategy Pattern**: Runtime behavior switching  
✅ **Decorator Pattern**: Feature composition  

## 🔄 Future Enhancements

Potential extensions to the project:

- [ ] Database persistence for device configurations
- [ ] Network connectivity for remote control
- [ ] Mobile app integration
- [ ] Advanced scheduling with cron expressions
- [ ] Machine learning-based AI control
- [ ] Voice assistant integration (Alexa, Google Home)
- [ ] Energy analytics and optimization
- [ ] Scene automation (movie mode, sleep mode, etc.)
- [ ] Security and authentication
- [ ] Cloud synchronization

## 📝 License

This project is provided as-is for educational purposes.

## 👨‍💻 Author

Created as an Object-Oriented Programming practice project demonstrating design patterns and principles.

---

**Happy coding!** 🎉 Feel free to extend this project with additional patterns and features.
"# smart-home-automation" 
