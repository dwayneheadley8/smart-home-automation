# 🖥️ Smart Home GUI Documentation

**User Interface Guide for Smart Home Automation System**

---

## Table of Contents

1. [GUI Overview](#gui-overview)
2. [Main Window Layout](#main-window-layout)
3. [Left Panel - Device Management](#left-panel---device-management)
4. [Center Panel - Device Control](#center-panel---device-control)
5. [Right Panel - Information Tabs](#right-panel---information-tabs)
6. [Top Panel - Control Mode](#top-panel---control-mode)
7. [Bottom Panel - System Info](#bottom-panel---system-info)
8. [Interactive Features](#interactive-features)
9. [Device-Specific Controls](#device-specific-controls)
10. [Keyboard Shortcuts](#keyboard-shortcuts)
11. [Tips and Tricks](#tips-and-tricks)

---

## GUI Overview

### Window Specifications
- **Title**: Smart Home Automation System
- **Size**: 1600×900 pixels (expandable)
- **Framework**: Java Swing
- **Look & Feel**: System default

### Design Philosophy

The GUI is organized into logical sections:
- **Left**: Device selection and management
- **Center**: Device control and adjustment
- **Right**: Information display and monitoring
- **Top**: Global settings
- **Bottom**: System information

---

## Main Window Layout

```
┌─────────────────────────────────────────────────────────────────────┐
│ ☐ _ □ ×  Smart Home Automation System                              │
├─────────────────────────────────────────────────────────────────────┤
│ Smart Home Automation System            Control Mode: [Manual      ▼] │
├──────────────┬──────────────────────────┬──────────────────────────┤
│              │                          │                          │
│   Devices    │                          │    Status / Rooms /      │
│              │   Device Control         │    AI Control Tab        │
│  [+ Light]   │                          │                          │
│  [+Thermo]   │   [Turn ON] [Turn OFF]   │ [Status] [Rooms] [🤖 AI]│
│  [+ Speaker] │   [Undo]    [Redo]       │                          │
│              │                          │ Activity log or           │
│  [- Remove]  │ Device Settings:         │ Room details              │
│              │ ─────────────────────    │                          │
│              │ ◄────●───────► 100%      │                          │
│              │                          │                          │
├──────────────┴──────────────────────────┴──────────────────────────┤
│ Smart Home System | Design Patterns: 8 GoF                Devices: 10│
└─────────────────────────────────────────────────────────────────────┘
```

---

## Left Panel - Device Management

### Title
**"Devices"** - Bordered section with dropdown list

### Device List
- **Display**: Scrollable list of all devices
- **Selection Mode**: Single selection (click to select)
- **Device Names**: Display with optional emoji indicators
  - Light devices: Plain name
  - Energy-monitored devices: Name + ⚡
  - Fan devices: Name + 🌀

### Action Buttons

#### "+ Add Light"
- **Action**: Creates new light device
- **Dialog 1**: Input device name
- **Dialog 2**: Select target room
- **Result**: Device appears in list and room

#### "+ Add Thermostat"
- **Action**: Creates new thermostat device
- **Default Temp**: 72°F
- **Dialog 1**: Input device name
- **Dialog 2**: Select target room
- **Result**: Device appears in list and room

#### "+ Add Speaker"
- **Action**: Creates new speaker device
- **Default Volume**: 0%
- **Dialog 1**: Input device name
- **Dialog 2**: Select target room
- **Result**: Device appears in list and room

#### "- Remove Device"
- **Color**: Red background
- **Action**: Removes selected device
- **Confirmation**: Yes/No dialog
- **Result**: Device removed from all locations

### Interaction Example

```
1. Click "+ Add Light" button
   ↓
2. Dialog appears: "Enter device name:"
   User types: "Kitchen Light"
   ↓
3. Dialog appears: "Select a room to add this device to:"
   User selects: "Kitchen"
   ↓
4. Device "Kitchen Light" appears in device list
5. Device also appears in Kitchen room details
```

---

## Center Panel - Device Control

### Title
**"Device Control"** - Main control panel

### Selected Device Label
- **Format**: "Selected: [Device Name]"
- **Font**: Arial Bold, Size 16
- **Update**: Changes when device selection changes
- **Default**: "No device selected"

### Control Buttons

#### "Turn ON" Button
- **Color**: Green (RGB: 76, 175, 80)
- **Text Color**: Black
- **Font**: Arial Bold, Size 14
- **State**: Disabled when no device selected
- **Action**: Executes TurnOnCommand
- **Feedback**: 
  - Device status updates
  - Activity logged with timestamp
  - Observers notified

#### "Turn OFF" Button
- **Color**: Red (RGB: 244, 67, 54)
- **Text Color**: Black
- **Font**: Arial Bold, Size 14
- **State**: Disabled when no device selected
- **Action**: Executes TurnOffCommand
- **Feedback**: Same as Turn ON

#### "Undo" Button
- **Color**: Default system color
- **Font**: Arial Bold, Size 14
- **State**: Always enabled
- **Action**: Reverts last command
- **Feedback**: Logs "Command undone" or "Nothing to undo"

#### "Redo" Button
- **Color**: Default system color
- **Font**: Arial Bold, Size 14
- **State**: Always enabled
- **Action**: Restores last undone command
- **Feedback**: Logs "Command redone" or "Nothing to redo"

### Device Settings Section

#### Title
**"Device Settings"** - Bordered subsection

#### Sliders (Dynamic)

The correct slider appears based on selected device type.

##### Brightness Slider (Lights)
- **Label**: "Brightness: X%"
- **Range**: 0-100%
- **Default**: 100%
- **Major Ticks**: Every 25% (0, 25, 50, 75, 100)
- **Minor Ticks**: Every 1%
- **Visibility**: Shows only for Light devices
- **Action**: Updates light brightness in real-time
- **Logging**: Each adjustment logged with timestamp

##### Temperature Slider (Thermostats)
- **Label**: "Temperature: X°F"
- **Range**: 50-90°F
- **Default**: 72°F
- **Major Ticks**: Every 10°F (50, 60, 70, 80, 90)
- **Minor Ticks**: Every 1°F
- **Visibility**: Shows only for Thermostat devices
- **Action**: Sets target temperature
- **Behavior**: Current temperature auto-adjusts after 5 seconds
- **Logging**: Each adjustment logged with timestamp

##### Volume Slider (Speakers)
- **Label**: "Volume: X%"
- **Range**: 0-100%
- **Default**: 0%
- **Major Ticks**: Every 25% (0, 25, 50, 75, 100)
- **Minor Ticks**: Every 1%
- **Visibility**: Shows only for Speaker devices
- **Action**: Sets speaker volume
- **Auto-enable**: Device turns ON when volume > 0%
- **Logging**: Each adjustment logged with timestamp

##### Fan Speed Slider (Fans)
- **Label**: "Fan Speed: X/3"
- **Range**: 0-3
- **Levels**:
  - 0: OFF
  - 1: LOW speed
  - 2: MEDIUM speed
  - 3: HIGH speed
- **Major Ticks**: Each level (0, 1, 2, 3)
- **Visibility**: Shows only for Fan devices
- **Action**: Controls legacy fan speed through adapter
- **Logging**: Each change logged with timestamp

---

## Right Panel - Information Tabs

### Tabbed Interface
- **Type**: JTabbedPane
- **Location**: Right side of main window
- **Width**: ~700 pixels
- **Height**: Expandable

### Tab 1: Status & Activity Log

#### Title
**"Status"** - First tab (default active)

#### Display
- **Component**: Scrollable text area
- **Font**: Monospaced, Size 11
- **Color**: Black text on white background
- **Behavior**: Auto-scrolls to bottom

#### Content
Shows real-time activity log with timestamps:

```
[20:25:30] System initialized.
[20:25:30] Ready for commands.
[20:25:32] Selected: Living Room Light
[20:25:32] Status: Living Room Light is ON, Brightness: 100%
[20:25:35] Adjusted Living Room Light brightness to 75%
[20:25:35] Status: Living Room Light is ON, Brightness: 75%
[20:25:40] Turned ON all devices in: Living Room
```

#### Log Entries
Each entry includes:
- **Timestamp**: [HH:MM:SS] format
- **Message**: Human-readable action description
- **Status**: Device state after action

#### Clear Button
- **Location**: Bottom of Status tab
- **Action**: Clears all log entries
- **Confirmation**: None (immediate)

### Tab 2: Rooms & Devices

#### Title
**"Rooms"** - Second tab

#### Room List (Top Section)
- **Component**: Scrollable list
- **Height**: ~150 pixels (adjustable splitter)
- **Content**: List of rooms with emoji:
  - 🏠 Living Room
  - 🛏️ Bedroom
  - 🍳 Kitchen
  - 🚿 Bathroom
- **Interaction**: Click room to view details

#### Room Details (Bottom Section)
- **Component**: Scrollable text area
- **Height**: Remaining space
- **Font**: Monospaced, Size 10
- **Content**: Device list and control instructions

#### Example Display
```
Room: 🏠 Living Room
======================================
Devices in this room:
  • Living Room Light
    └─ Living Room Light is ON, Brightness: 100%
  • Living Room Thermostat
    └─ Living Room Thermostat is ON, Current: 70.0°F, 
       Target: 72.0°F, Mode: heating
  • Vintage Ceiling Fan 🌀
    └─ Vintage Ceiling Fan is RUNNING at speed: LOW

Room Controls:
  • Click 'Turn ON' to turn on all devices
  • Click 'Turn OFF' to turn off all devices
```

#### Splitter
- **Location**: Between room list and details
- **Behavior**: Draggable to adjust sections
- **Default Split**: 40% rooms, 60% details

### Tab 3: 🤖 AI Control (Dynamic)

#### Visibility
- **Hidden in**: Manual and Scheduled modes
- **Shown in**: AI mode only
- **Auto-switching**: Automatically switches to this tab when AI mode selected

#### Components

##### Status Label
- **Position**: Top of panel
- **Colors**:
  - 🟢 RUNNING: Cyan (#64C8FF)
  - 🛑 STOPPED: Yellow (#FFC1 07)
- **Format**: "🟢 AI Automation: RUNNING"

##### Button Panel
- **Buttons**:
  - **"Start AI Automation"** (Left)
    - Green background
    - Black text
    - Disabled after click
  - **"Stop AI Automation"** (Right)
    - Green background
    - Black text
    - Disabled before start

##### AI Info Panel
- **Background**: Dark (#202028)
- **Text Color**: Cyan
- **Content**:
  ```
  About AI Automation:
  • Devices randomly turn ON/OFF every 5 seconds
  • Cycles through all available smart devices
  • Perfect for testing and demonstrations
  • Real-time status updates in Status & AI Log tabs
  • Click Start to begin, Stop to halt automation
  ```

##### Automation Log
- **Title**: "Automation Log"
- **Background**: Dark (#202028)
- **Text Color**: Cyan (#64C8FF)
- **Font**: Monospaced, Size 11
- **Scrollable**: Yes, auto-scroll to bottom
- **Content**: AI automation events
  ```
  [20:26:46] AI Automation started
  [20:26:46] 🟢 Antique Pedestal Fan turned ON
  [20:26:51] 🔴 Bedroom Light turned OFF
  [20:26:56] 🟢 Living Room Light turned ON
  [20:27:10] AI Automation stopped
  ```

---

## Top Panel - Control Mode

### Location
**Top-right corner** of main window

### Label
- **Text**: "Control Mode:"
- **Font**: Default

### Dropdown Menu (Combo Box)
- **Options**:
  1. Manual
  2. Scheduled
  3. AI
- **Default**: Manual
- **Selection**: Click to expand dropdown

#### Manual Mode
- **Description**: Direct user control
- **Behavior**: Users manually control each device
- **UI Effect**: Hides AI Control tab
- **Use Case**: Simple testing, specific control

#### Scheduled Mode
- **Description**: Time-based automation
- **Behavior**: Devices controlled by schedule
- **UI Effect**: Hides AI Control tab
- **Use Case**: Regular patterns, energy savings

#### AI Mode
- **Description**: Intelligent automated control
- **Behavior**: AI analyzes and controls devices
- **UI Effect**: Shows AI Control tab, auto-switches to it
- **Use Case**: Learning patterns, optimization

### Selection Example
```
1. Click dropdown arrow
   ↓
2. Menu appears with options
   ↓
3. Click "AI"
   ↓
4. Mode changes to AI
5. AI Control tab appears
6. UI automatically switches to AI Control tab
```

---

## Bottom Panel - System Info

### Layout
- **Type**: Horizontal bar
- **Location**: Bottom of window
- **Components**: Left label and right label

### Left Section
```
Smart Home System | Design Patterns: Factory, Singleton, Composite, 
Adapter, Observer, Command, Strategy, Decorator
```

### Right Section
```
Devices: 10
```
- **Updates**: When devices added/removed

---

## Interactive Features

### Device Selection
```
User clicks device in list
    ↓
Device becomes highlighted
    ↓
Selected device label updates
    ↓
Appropriate slider appears (if device has one)
    ↓
Control buttons become enabled
    ↓
Status log shows: "Selected: [Device Name]"
```

### Control Execution
```
User clicks "Turn ON"
    ↓
TurnOnCommand created
    ↓
CentralController executes command
    ↓
Device state changes
    ↓
Observers notified
    ↓
Status log updated
    ↓
Room details refreshed
```

### Tab Navigation
```
User clicks tab
    ↓
Tab content displays
    ↓
Device list state preserved
    ↓
Selections maintained across tabs
```

### Room Control
```
User selects room in Rooms tab
    ↓
Room details populate
    ↓
User clicks "Turn ON"
    ↓
ALL devices in room turn on
    ↓
Room details refresh
    ↓
Status log shows: "Turned ON all devices in: [Room]"
```

---

## Device-Specific Controls

### Light Control
1. **Select light device**
2. **Brightness slider appears**
3. **Drag slider to adjust** (0-100%)
4. **Label updates**: "Brightness: X%"
5. **Changes logged in Status tab**
6. **Immediate UI update**

### Thermostat Control
1. **Select thermostat device**
2. **Temperature slider appears**
3. **Drag slider to set target** (50-90°F)
4. **Label updates**: "Temperature: X°F"
5. **5-second timer starts**
6. **After 5 seconds, current temperature adjusts**
7. **Mode changes automatically** (heating/cooling/maintaining)
8. **Changes logged with timestamps**

### Speaker Control
1. **Select speaker device**
2. **Volume slider appears**
3. **Drag slider to adjust** (0-100%)
4. **Label updates**: "Volume: X%"
5. **Device auto-enables** if volume > 0%
6. **Changes logged in Status tab**

### Fan Control
1. **Select fan device**
2. **Fan Speed slider appears**
3. **Drag slider to set speed** (0-3 levels)
4. **Label updates**: "Fan Speed: X/3"
5. **Level descriptions**:
   - 0/3: OFF
   - 1/3: LOW
   - 2/3: MEDIUM
   - 3/3: HIGH
6. **Changes logged with timestamps**

---

## Keyboard Shortcuts

Currently, the GUI supports mouse-only interaction. Potential keyboard shortcuts (for future enhancement):

| Shortcut | Action |
|----------|--------|
| Ctrl+Z | Undo (replaces button) |
| Ctrl+Y | Redo (replaces button) |
| Alt+L | Add Light |
| Alt+T | Add Thermostat |
| Alt+S | Add Speaker |
| Alt+R | Remove Device |
| Del | Remove selected device |
| F1 | Help/Documentation |

---

## Tips and Tricks

### Efficient Device Management

1. **Batch Control**: Use Rooms tab to control all devices at once
2. **Quick Status Check**: Status tab shows all changes in real-time
3. **Undo Mistakes**: Always use Undo if control went wrong

### Thermostat Optimization

1. **Plan Ahead**: Set target temperature and wait 5 seconds
2. **Monitor Mode**: Check heating/cooling indicator in status
3. **Energy Saving**: Lower target temperatures in winter

### AI Automation Testing

1. **Observation**: Watch status tab during AI automation
2. **Pattern Learning**: Observe device sequence for patterns
3. **Logging**: All AI actions logged for review

### Performance Tips

1. **Keep Device Count Reasonable**: <50 devices for smooth operation
2. **Close Unused Tabs**: Reduce memory usage
3. **Clear Logs Periodically**: Clear Status tab if it gets very large

### Troubleshooting Tips

1. **Slider Not Appearing**: Wrong device type selected
2. **Buttons Disabled**: Device not selected
3. **Temperature Not Changing**: Wait 5 seconds after setting target
4. **AI Tab Missing**: Switch to AI mode from dropdown

---

## GUI Accessibility

### Color Contrast
- **Buttons**: High contrast (green/red on white)
- **Status Text**: Black text on white background
- **Sliders**: Visible tick marks and labels

### Font Sizes
- **Title**: 20pt
- **Labels**: 12pt
- **Buttons**: 14pt
- **Text Areas**: 11pt

### Layout
- **Scalable**: Window resizable for different screen sizes
- **Organized**: Logical grouping of related controls
- **Intuitive**: Clear labeling and visual hierarchy

---

## Future GUI Enhancements

Potential improvements:
- [ ] Dark mode support
- [ ] Device icons/images
- [ ] Drag-and-drop room assignment
- [ ] Real-time device preview
- [ ] Energy usage graphs
- [ ] Device history charts
- [ ] Voice command integration
- [ ] Mobile/responsive design

---

**Last Updated**: November 19, 2025  
**GUI Version**: 1.0 ✅
