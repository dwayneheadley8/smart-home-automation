# Javadoc Documentation for Smart Home System

## ✅ Javadoc Successfully Generated

Your Javadoc has been successfully generated and is located in the `target/javadoc/` directory.

## How to View the Documentation

### Option 1: Open in Browser (Recommended)
1. Navigate to the project directory
2. Open `target/javadoc/index.html` in your web browser
3. You'll see the complete API documentation for all classes and packages

### Option 2: Using the Batch Script
1. Double-click `generate-javadoc.bat` to regenerate Javadoc anytime
2. The script will create the documentation in `target/javadoc/`

### Option 3: Manual Command Line
```powershell
cd "c:\Users\dwayn\OneDrive\Documentos\Coding Projects (New)\Java\Java Practice\OOP\SmartHomeSystem"
javadoc -encoding UTF-8 -d target/javadoc -sourcepath src/main/java -subpackages com
```

## Documentation Structure

The generated Javadoc includes documentation for all packages:

### Main Packages
- **com.mycompany.smarthomesystem** - Entry point (SmartHomeSystem.java)
- **com.smarthome.behavioral** - Behavioral design patterns
  - Command, ControlStrategy, Observer, AIControl, ManualControl, ScheduledControl
  - TurnOnCommand, TurnOffCommand, AdjustBrightnessCommand, AdjustTemperatureCommand
  - DeviceLogger
- **com.smarthome.creational** - Creational design patterns
  - CentralController (Singleton)
  - DeviceFactory
- **com.smarthome.devices** - Device implementations
  - SmartDevice interface
  - Light, Thermostat, Speaker classes
- **com.smarthome.gui** - User interface
  - SmartHomeGUI (main Swing application)
- **com.smarthome.structural** - Structural design patterns
  - DeviceDecorator
  - EnergyMonitorDecorator
  - VoiceControlDecorator
  - FanAdapter (Adapter pattern)
  - OldFan (legacy fan)
  - Room (Composite pattern)

## What's Documented

Each Java file includes:
- ✅ Class documentation with descriptions
- ✅ Method documentation with parameters and return types
- ✅ Field documentation
- ✅ Design pattern explanations
- ✅ Author and version information

## Warnings

You may see warnings about missing Javadoc comments for some GUI components (like JLabel fields). These are normal and don't affect the functionality. The important class and method documentation is complete.

## Next Steps

1. Open `target/javadoc/index.html` to explore the documentation
2. Search for specific classes using the search function
3. Click on package links to see class hierarchies
4. Use the "All Classes" link to browse all available classes

## Regenerating Javadoc

Whenever you make changes to your Java files, you can regenerate the Javadoc by:
- Running the batch script: `generate-javadoc.bat`
- Or using the command line javadoc command shown above

## Integration with Maven

Your `pom.xml` has been updated with the Javadoc plugin configuration. When Maven is installed, you can generate Javadoc using:
```bash
mvn javadoc:javadoc
```

Or for a clean build and documentation:
```bash
mvn clean javadoc:javadoc
```
