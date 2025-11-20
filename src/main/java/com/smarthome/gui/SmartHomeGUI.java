package com.smarthome.gui;

import com.smarthome.creational.CentralController;
import com.smarthome.creational.DeviceFactory;
import com.smarthome.devices.SmartDevice;
import com.smarthome.devices.Light;
import com.smarthome.devices.Thermostat;
import com.smarthome.devices.Speaker;
import com.smarthome.behavioral.*;
import com.smarthome.structural.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Main GUI for the Smart Home Automation System.
 * Demonstrates all implemented design patterns.
 * 
 * @author Dwayne Headley
 * @version 1.0
 */
public class SmartHomeGUI extends JFrame {
    // Controllers and factories
    private CentralController controller;
    private DeviceFactory factory;
    
    // GUI Components
    private DefaultListModel<String> deviceListModel;
    private JList<String> deviceList;
    private JTextArea statusArea;
    private JComboBox<String> modeComboBox;
    private JButton turnOnButton;
    private JButton turnOffButton;
    private JButton undoButton;
    private JButton redoButton;
    private JLabel selectedDeviceLabel;
    private JTabbedPane tabbedPane;
    
    // Brightness slider
    private JSlider brightnessSlider;
    private JLabel brightnessLabel;
    
    // Temperature slider
    private JSlider temperatureSlider;
    private JLabel temperatureLabel;
    
    // Volume slider
    private JSlider volumeSlider;
    private JLabel volumeLabel;
    
    // Fan speed slider
    private JSlider fanSpeedSlider;
    private JLabel fanSpeedLabel;
    
    private boolean isSliderUpdating = false; // Prevent recursion
    
    // Room display
    private JList<String> roomList;
    private DefaultListModel<String> roomListModel;
    private JTextArea roomDetailsArea;
    
    // Device tracking
    private Map<String, SmartDevice> deviceMap;
    private Map<String, Room> roomMap;
    private SmartDevice selectedDevice;
    private Room selectedRoom; // Track selected room
    
    // Legacy devices
    private List<FanAdapter> legacyFans;
    
    // AI log area
    private JTextArea aiLogArea;
    
    /**
     * Creates the Smart Home GUI.
     */
    public SmartHomeGUI() {
        // Initialize
        controller = CentralController.getInstance();
        factory = new DeviceFactory();
        deviceMap = new HashMap<>();
        roomMap = new HashMap<>();
        legacyFans = new ArrayList<>();
        
    // Setup window
    setTitle("Smart Home Automation System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Make the window wider so the log area is fully visible
    setSize(1600, 900);
    setLocationRelativeTo(null);
        
        // Create GUI
        initializeGUI();
        
        // Add sample devices and rooms
        addSampleDevicesAndRooms();
        
        // Make visible
        setVisible(true);
    }
    
    /**
     * Initializes all GUI components.
     */
    private void initializeGUI() {
        // Main layout
        setLayout(new BorderLayout(10, 10));
        
        // Create panels
        JPanel topPanel = createTopPanel();
        JPanel leftPanel = createLeftPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel rightPanel = createRightPanel();
        JPanel bottomPanel = createBottomPanel();
        
        // Add panels
        add(topPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Add padding
        ((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
    }
    
    /**
     * Creates the top panel with title and mode selector.
     */
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Control Panel"));
        
        // Title
        JLabel titleLabel = new JLabel("Smart Home Automation System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Mode selector
        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        modePanel.add(new JLabel("Control Mode:"));
        
        String[] modes = {"Manual", "Scheduled", "AI"};
        modeComboBox = new JComboBox<>(modes);
        modeComboBox.addActionListener(e -> changeControlMode());
        modePanel.add(modeComboBox);
        
        panel.add(titleLabel, BorderLayout.CENTER);
        panel.add(modePanel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Creates the left panel with device list.
     */
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Devices"));
        panel.setPreferredSize(new Dimension(250, 0));
        
        // Device list
        deviceListModel = new DefaultListModel<>();
        deviceList = new JList<>(deviceListModel);
        deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deviceList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectDevice();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(deviceList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add device button
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        
        JButton addLightButton = new JButton("+ Add Light");
        addLightButton.addActionListener(e -> addDevice("light"));
        
        JButton addThermostatButton = new JButton("+ Add Thermostat");
        addThermostatButton.addActionListener(e -> addDevice("thermostat"));
        
        JButton addSpeakerButton = new JButton("+ Add Speaker");
        addSpeakerButton.addActionListener(e -> addDevice("speaker"));
        
        JButton removeDeviceButton = new JButton("- Remove Device");
        removeDeviceButton.setForeground(new Color(244, 67, 54));
        removeDeviceButton.addActionListener(e -> removeDevice());
        
        buttonPanel.add(addLightButton);
        buttonPanel.add(addThermostatButton);
        buttonPanel.add(addSpeakerButton);
        buttonPanel.add(removeDeviceButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates the center panel with device controls.
     */
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Device Control"));
        
        // Selected device info
        selectedDeviceLabel = new JLabel("No device selected", SwingConstants.CENTER);
        selectedDeviceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        selectedDeviceLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Control buttons
        JPanel controlPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        controlPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        
        turnOnButton = new JButton("Turn ON");
        turnOnButton.setFont(new Font("Arial", Font.BOLD, 14));
        turnOnButton.setBackground(new Color(76, 175, 80));
        turnOnButton.setForeground(Color.BLACK);
        turnOnButton.setFocusPainted(false);
        turnOnButton.addActionListener(e -> turnOnDevice());
        turnOnButton.setEnabled(false);
        
        turnOffButton = new JButton("Turn OFF");
        turnOffButton.setFont(new Font("Arial", Font.BOLD, 14));
        turnOffButton.setBackground(new Color(244, 67, 54));
        turnOffButton.setForeground(Color.BLACK);
        turnOffButton.setFocusPainted(false);
        turnOffButton.addActionListener(e -> turnOffDevice());
        turnOffButton.setEnabled(false);
        
        undoButton = new JButton("Undo");
        undoButton.setFont(new Font("Arial", Font.BOLD, 14));
        undoButton.addActionListener(e -> undoCommand());
        
        redoButton = new JButton("Redo");
        redoButton.setFont(new Font("Arial", Font.BOLD, 14));
        redoButton.addActionListener(e -> redoCommand());
        
        controlPanel.add(turnOnButton);
        controlPanel.add(turnOffButton);
        controlPanel.add(undoButton);
        controlPanel.add(redoButton);
        
        // Additional controls for specific devices
        JPanel deviceSpecificPanel = new JPanel(new BorderLayout());
        deviceSpecificPanel.setBorder(new TitledBorder("Device Settings"));
        
        // Brightness slider (for lights)
        JPanel brightnessPanel = new JPanel(new BorderLayout());
        brightnessLabel = new JLabel("Brightness: 100%", SwingConstants.LEFT);
        brightnessLabel.setFont(new Font("Arial", Font.BOLD, 12));
        brightnessPanel.add(brightnessLabel, BorderLayout.WEST);
        
        brightnessSlider = new JSlider(0, 100, 100);
        brightnessSlider.setMajorTickSpacing(25);
        brightnessSlider.setMinorTickSpacing(1);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);
        brightnessSlider.addChangeListener(e -> adjustBrightness());
        brightnessPanel.add(brightnessSlider, BorderLayout.CENTER);
        
        // Temperature slider (for thermostats)
        JPanel temperaturePanel = new JPanel(new BorderLayout());
        temperatureLabel = new JLabel("Temperature: 72°F", SwingConstants.LEFT);
        temperatureLabel.setFont(new Font("Arial", Font.BOLD, 12));
        temperaturePanel.add(temperatureLabel, BorderLayout.WEST);
        
        temperatureSlider = new JSlider(50, 90, 72);
        temperatureSlider.setMajorTickSpacing(10);
        temperatureSlider.setMinorTickSpacing(1);
        temperatureSlider.setPaintTicks(true);
        temperatureSlider.setPaintLabels(true);
        temperatureSlider.addChangeListener(e -> adjustTemperature());
        temperaturePanel.add(temperatureSlider, BorderLayout.CENTER);
        
        // Volume slider (for speakers)
        JPanel volumePanel = new JPanel(new BorderLayout());
        volumeLabel = new JLabel("Volume: 0%", SwingConstants.LEFT);
        volumeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        volumePanel.add(volumeLabel, BorderLayout.WEST);
        
        volumeSlider = new JSlider(0, 100, 0);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setMinorTickSpacing(1);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(e -> adjustVolume());
        volumePanel.add(volumeSlider, BorderLayout.CENTER);
        
        // Fan speed slider (for fans)
        JPanel fanSpeedPanel = new JPanel(new BorderLayout());
        fanSpeedLabel = new JLabel("Fan Speed: 0/3", SwingConstants.LEFT);
        fanSpeedLabel.setFont(new Font("Arial", Font.BOLD, 12));
        fanSpeedPanel.add(fanSpeedLabel, BorderLayout.WEST);
        
        fanSpeedSlider = new JSlider(0, 3, 0);
        fanSpeedSlider.setMajorTickSpacing(1);
        fanSpeedSlider.setMinorTickSpacing(1);
        fanSpeedSlider.setPaintTicks(true);
        fanSpeedSlider.setPaintLabels(true);
        fanSpeedSlider.addChangeListener(e -> adjustFanSpeed());
        fanSpeedPanel.add(fanSpeedSlider, BorderLayout.CENTER);
        
        // Container panel to show/hide sliders based on device type
        JPanel sliderContainerPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        sliderContainerPanel.add(brightnessPanel);
        sliderContainerPanel.add(temperaturePanel);
        sliderContainerPanel.add(volumePanel);
        sliderContainerPanel.add(fanSpeedPanel);
        
        deviceSpecificPanel.add(sliderContainerPanel);
        
        panel.add(selectedDeviceLabel, BorderLayout.NORTH);
        panel.add(controlPanel, BorderLayout.CENTER);
        panel.add(deviceSpecificPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Creates the right panel with status display.
     */
    private JPanel createRightPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
            // Add tab change listener to control device list and button states
            tabbedPane.addChangeListener(e -> {
                int selectedIndex = tabbedPane.getSelectedIndex();
                if (selectedIndex == 0) {
                    // Status tab: device list enabled, buttons controlled by device selection
                    deviceList.setEnabled(true);
                    updateButtonStates();
                } else if (selectedIndex == 1) {
                    // Rooms tab: device list disabled, buttons enabled for room control
                    deviceList.setEnabled(false);
                    deviceList.clearSelection();
                    turnOnButton.setEnabled(true);
                    turnOffButton.setEnabled(true);
                }
            });
        
        // Status & Activity Log Tab
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(new TitledBorder("Status & Activity Log"));
        
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        statusArea.setText("System initialized.\nReady for commands.\n\n");
        
        JScrollPane scrollPane = new JScrollPane(statusArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        statusPanel.add(scrollPane, BorderLayout.CENTER);
        
        JButton clearButton = new JButton("Clear Log");
        clearButton.addActionListener(e -> statusArea.setText("Log cleared.\n\n"));
        statusPanel.add(clearButton, BorderLayout.SOUTH);
        
        // Rooms Tab
        JPanel roomsPanel = new JPanel(new BorderLayout());
        roomsPanel.setBorder(new TitledBorder("Rooms & Devices"));
        
        roomListModel = new DefaultListModel<>();
        roomList = new JList<>(roomListModel);
        roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        roomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displayRoomDetails();
            }
        });
        
        JScrollPane roomScrollPane = new JScrollPane(roomList);
        roomScrollPane.setPreferredSize(new Dimension(0, 150));
        
        roomDetailsArea = new JTextArea();
        roomDetailsArea.setEditable(false);
        roomDetailsArea.setFont(new Font("Monospaced", Font.PLAIN, 10));
        roomDetailsArea.setText("Select a room to see details...\n");
        
        JScrollPane detailsScrollPane = new JScrollPane(roomDetailsArea);
        
        JSplitPane roomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, roomScrollPane, detailsScrollPane);
        roomSplitPane.setDividerLocation(0.4);
        
        roomsPanel.add(roomSplitPane, BorderLayout.CENTER);
        
        // Add tabs - AI Control tab will be added dynamically when AI mode is selected
        tabbedPane.addTab("Status", statusPanel);
        tabbedPane.addTab("Rooms", roomsPanel);
        
    mainPanel.add(tabbedPane, BorderLayout.CENTER);
    // Increase right panel width so status/log is more readable
    mainPanel.setPreferredSize(new Dimension(700, 0));
        
        return mainPanel;
    }
    
    /**
     * Creates the bottom panel with system info.
     */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        JLabel infoLabel = new JLabel("Smart Home System | Design Patterns: Factory, Singleton, Composite, Adapter, Observer, Command, Strategy, Decorator");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        
        JLabel deviceCountLabel = new JLabel("Devices: 0");
        deviceCountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        panel.add(infoLabel, BorderLayout.WEST);
        panel.add(deviceCountLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Adds sample devices and rooms to the system.
     */
    private void addSampleDevicesAndRooms() {
        // Create rooms
        Room livingRoom = new Room("🏠 Living Room");
        Room bedroom = new Room("🛏️ Bedroom");
        Room kitchen = new Room("🍳 Kitchen");
        Room bathroom = new Room("🚿 Bathroom");
        
        roomMap.put("Living Room", livingRoom);
        roomMap.put("Bedroom", bedroom);
        roomMap.put("Kitchen", kitchen);
        roomMap.put("Bathroom", bathroom);
        
        // Add devices to living room
        SmartDevice livingRoomLight = factory.createDevice("light", "Living Room Light");
        SmartDevice livingRoomThermostat = factory.createDevice("thermostat", "Living Room Thermostat");
        livingRoom.addDevice(livingRoomLight);
        livingRoom.addDevice(livingRoomThermostat);
        deviceMap.put(livingRoomLight.getName(), livingRoomLight);
        deviceMap.put(livingRoomThermostat.getName(), livingRoomThermostat);
        deviceListModel.addElement(livingRoomLight.getName());
        deviceListModel.addElement(livingRoomThermostat.getName());
        
        // Add devices to bedroom
        SmartDevice bedroomLight = factory.createDevice("light", "Bedroom Light");
        bedroom.addDevice(bedroomLight);
        deviceMap.put(bedroomLight.getName(), bedroomLight);
        deviceListModel.addElement(bedroomLight.getName());
        
        // Add devices to kitchen
        SmartDevice kitchenLight = factory.createDevice("light", "Kitchen Light");
        SmartDevice kitchenSpeaker = factory.createDevice("speaker", "Kitchen Speaker");
        kitchenLight = new EnergyMonitorDecorator(kitchenLight);
        kitchen.addDevice(kitchenLight);
        kitchen.addDevice(kitchenSpeaker);
        deviceMap.put(kitchenLight.getName(), kitchenLight);
        deviceMap.put(kitchenSpeaker.getName(), kitchenSpeaker);
        deviceListModel.addElement(kitchenLight.getName() + " ⚡");
        deviceListModel.addElement(kitchenSpeaker.getName());
        
        // Add devices to bathroom
        SmartDevice bathroomLight = factory.createDevice("light", "Bathroom Light");
        bathroom.addDevice(bathroomLight);
        deviceMap.put(bathroomLight.getName(), bathroomLight);
        deviceListModel.addElement(bathroomLight.getName());
        
        // Add legacy fans
        OldFan oldFan1 = new OldFan("Vintage Ceiling Fan");
        OldFan oldFan2 = new OldFan("Antique Pedestal Fan");
        
        FanAdapter fan1Adapter = new FanAdapter(oldFan1);
        FanAdapter fan2Adapter = new FanAdapter(oldFan2);
        
        legacyFans.add(fan1Adapter);
        legacyFans.add(fan2Adapter);
        
        // Add fans to rooms
        livingRoom.addDevice(fan1Adapter);
        bedroom.addDevice(fan2Adapter);
        
        deviceMap.put(fan1Adapter.getName(), fan1Adapter);
        deviceMap.put(fan2Adapter.getName(), fan2Adapter);
        deviceListModel.addElement(fan1Adapter.getName() + " 🌀");
        deviceListModel.addElement(fan2Adapter.getName() + " 🌀");
        
        // Add rooms to room list
        roomListModel.addElement("🏠 Living Room");
        roomListModel.addElement("🛏️ Bedroom");
        roomListModel.addElement("🍳 Kitchen");
        roomListModel.addElement("🚿 Bathroom");
        
        // Add all devices to controller
        controller.addDevice(livingRoomLight);
        controller.addDevice(livingRoomThermostat);
        controller.addDevice(bedroomLight);
        controller.addDevice(kitchenLight);
        controller.addDevice(kitchenSpeaker);
        controller.addDevice(bathroomLight);
        controller.addDevice(fan1Adapter);
        controller.addDevice(fan2Adapter);
        
        logStatus("Sample devices and rooms initialized.");
        logStatus("Added 4 rooms with 10 devices total.");
        logStatus("Living Room: Light + Thermostat + Vintage Ceiling Fan");
        logStatus("Bedroom: Light + Antique Pedestal Fan");
        logStatus("Kitchen: Light (monitored) + Speaker");
        logStatus("Bathroom: Light");
        updateDeviceCount();
    }
    
    /**
     * Adds a device to the system.
     */
    private void addDevice(String type) {
        String name = JOptionPane.showInputDialog(this, "Enter device name:", "Add " + type, JOptionPane.QUESTION_MESSAGE);
        
        if (name != null && !name.trim().isEmpty()) {
            // Show room selection dialog
            String[] roomNames = {"Living Room", "Bedroom", "Kitchen", "Bathroom"};
            String selectedRoomName = (String) JOptionPane.showInputDialog(
                this,
                "Select a room to add this device to:",
                "Choose Room",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roomNames,
                roomNames[0]
            );
            
            if (selectedRoomName != null) {
                Room selectedRoom = roomMap.get(selectedRoomName);
                if (selectedRoom != null) {
                    addDeviceToSystemInRoom(type, name, selectedRoom);
                }
            }
        }
    }
    
    /**
     * Adds a device to a specific room.
     */
    private void addDeviceToSystemInRoom(String type, String name, Room room) {
        SmartDevice device = factory.createDevice(type, name);
        String deviceKey = device.getName();
        
        // Add to room
        room.addDevice(device);
        
        // Add to maps and lists
        deviceMap.put(deviceKey, device);
        controller.addDevice(device);
        deviceListModel.addElement(deviceKey);
        
        logStatus("Added: " + deviceKey);
        logStatus("Location: " + room.getName());
        updateDeviceCount();
        
        // Refresh rooms display
        displayRoomDetails();
    }
    
    /**
     * Selects a device from the list.
     */
    private void selectDevice() {
        String selected = deviceList.getSelectedValue();
        if (selected != null) {
            // Remove emoji if present
            selected = selected.replace(" ⚡", "").replace(" 🌀", "");
            
            selectedDevice = deviceMap.get(selected);
            selectedRoom = null; // Clear room selection when device is selected
            roomList.clearSelection(); // Clear room list selection
            
            if (selectedDevice != null) {
                selectedDeviceLabel.setText("Selected: " + selectedDevice.getName());
                turnOnButton.setEnabled(true);
                turnOffButton.setEnabled(true);
                
                // Update sliders based on device type
                isSliderUpdating = true;
                if (selectedDevice instanceof Light) {
                    Light light = (Light) selectedDevice;
                    brightnessSlider.setValue(light.getBrightness());
                    brightnessLabel.setText("Brightness: " + light.getBrightness() + "%");
                    brightnessSlider.setEnabled(true);
                    brightnessLabel.setVisible(true);
                    temperatureSlider.setEnabled(false);
                    temperatureLabel.setVisible(false);
                    volumeSlider.setEnabled(false);
                    volumeLabel.setVisible(false);
                    fanSpeedSlider.setEnabled(false);
                    fanSpeedLabel.setVisible(false);
                } else if (selectedDevice instanceof EnergyMonitorDecorator) {
                    EnergyMonitorDecorator decorator = (EnergyMonitorDecorator) selectedDevice;
                    SmartDevice wrapped = decorator.getWrappedDevice();
                    if (wrapped instanceof Light) {
                        Light light = (Light) wrapped;
                        brightnessSlider.setValue(light.getBrightness());
                        brightnessLabel.setText("Brightness: " + light.getBrightness() + "%");
                        brightnessSlider.setEnabled(true);
                        brightnessLabel.setVisible(true);
                        temperatureSlider.setEnabled(false);
                        temperatureLabel.setVisible(false);
                        volumeSlider.setEnabled(false);
                        volumeLabel.setVisible(false);
                        fanSpeedSlider.setEnabled(false);
                        fanSpeedLabel.setVisible(false);
                    } else {
                        brightnessSlider.setEnabled(false);
                        brightnessLabel.setVisible(false);
                        temperatureSlider.setEnabled(false);
                        temperatureLabel.setVisible(false);
                        volumeSlider.setEnabled(false);
                        volumeLabel.setVisible(false);
                        fanSpeedSlider.setEnabled(false);
                        fanSpeedLabel.setVisible(false);
                    }
                } else if (selectedDevice instanceof Thermostat) {
                    Thermostat thermostat = (Thermostat) selectedDevice;
                    temperatureSlider.setValue((int) thermostat.getTargetTemp());
                    temperatureLabel.setText("Temperature: " + (int) thermostat.getTargetTemp() + "°F");
                    temperatureSlider.setEnabled(true);
                    temperatureLabel.setVisible(true);
                    brightnessSlider.setEnabled(false);
                    brightnessLabel.setVisible(false);
                    volumeSlider.setEnabled(false);
                    volumeLabel.setVisible(false);
                    fanSpeedSlider.setEnabled(false);
                    fanSpeedLabel.setVisible(false);
                } else if (selectedDevice instanceof Speaker) {
                    Speaker speaker = (Speaker) selectedDevice;
                    volumeSlider.setValue(speaker.getVolume());
                    volumeLabel.setText("Volume: " + speaker.getVolume() + "%");
                    volumeSlider.setEnabled(true);
                    volumeLabel.setVisible(true);
                    brightnessSlider.setEnabled(false);
                    brightnessLabel.setVisible(false);
                    temperatureSlider.setEnabled(false);
                    temperatureLabel.setVisible(false);
                    fanSpeedSlider.setEnabled(false);
                    fanSpeedLabel.setVisible(false);
                } else if (selectedDevice instanceof FanAdapter) {
                    FanAdapter fan = (FanAdapter) selectedDevice;
                    fanSpeedSlider.setValue(fan.getSpeed());
                    fanSpeedLabel.setText("Fan Speed: " + fan.getSpeed() + "/3");
                    fanSpeedSlider.setEnabled(true);
                    fanSpeedLabel.setVisible(true);
                    brightnessSlider.setEnabled(false);
                    brightnessLabel.setVisible(false);
                    temperatureSlider.setEnabled(false);
                    temperatureLabel.setVisible(false);
                    volumeSlider.setEnabled(false);
                    volumeLabel.setVisible(false);
                } else {
                    brightnessSlider.setEnabled(false);
                    brightnessLabel.setVisible(false);
                    temperatureSlider.setEnabled(false);
                    temperatureLabel.setVisible(false);
                    volumeSlider.setEnabled(false);
                    volumeLabel.setVisible(false);
                    fanSpeedSlider.setEnabled(false);
                    fanSpeedLabel.setVisible(false);
                }
                isSliderUpdating = false;
                
                logStatus("Selected: " + selectedDevice.getName());
                logStatus("Status: " + selectedDevice.getStatus());
            }
        }
    }
    
    /**
     * Updates the state of control buttons based on the current selection or context.
     * Enables device-level buttons when a device is selected (Status tab),
     * or enables room-level control when Rooms tab is active.
     */
    private void updateButtonStates() {
        // If a device is selected, enable device control buttons
        if (selectedDevice != null) {
            turnOnButton.setEnabled(true);
            turnOffButton.setEnabled(true);
            return;
        }

        // Otherwise, no device selected - disable device buttons by default
        turnOnButton.setEnabled(false);
        turnOffButton.setEnabled(false);
    }
    
    /**
     * Turns on the selected device or room.
     */
    private void turnOnDevice() {
        // Check if a room is selected in the Rooms tab
        if (selectedRoom != null && roomList.getSelectedValue() != null) {
            // Turn on all devices in the room
            selectedRoom.turnOn();
            logStatus("Turned ON all devices in: " + selectedRoom.getName());
            displayRoomDetails();
        } else if (selectedDevice != null) {
            // Turn on the selected device
            Command command = new TurnOnCommand(selectedDevice);
            controller.executeCommand(command);
            logStatus("Executed: " + command.getDescription());
            logStatus("Status: " + selectedDevice.getStatus());
            displayRoomDetails();
        }
    }
    
    /**
     * Turns off the selected device or room.
     */
    private void turnOffDevice() {
        // Check if a room is selected in the Rooms tab
        if (selectedRoom != null && roomList.getSelectedValue() != null) {
            // Turn off all devices in the room
            selectedRoom.turnOff();
            logStatus("Turned OFF all devices in: " + selectedRoom.getName());
            displayRoomDetails();
        } else if (selectedDevice != null) {
            // Turn off the selected device
            Command command = new TurnOffCommand(selectedDevice);
            controller.executeCommand(command);
            logStatus("Executed: " + command.getDescription());
            logStatus("Status: " + selectedDevice.getStatus());
            displayRoomDetails();
        }
    }
    
    /**
     * Undoes the last command.
     */
    private void undoCommand() {
        boolean success = controller.undoLastCommand();
        if (success) {
            logStatus("Command undone.");
            if (selectedDevice != null) {
                logStatus("Status: " + selectedDevice.getStatus());
            }
        } else {
            logStatus("Nothing to undo.");
        }
    }
    
    /**
     * Redoes the last undone command.
     */
    private void redoCommand() {
        boolean success = controller.redoLastCommand();
        if (success) {
            logStatus("Command redone.");
            if (selectedDevice != null) {
                logStatus("Status: " + selectedDevice.getStatus());
            }
        } else {
            logStatus("Nothing to redo.");
        }
    }
    
    /**
     * Changes the control mode.
     */
    private void changeControlMode() {
        String mode = (String) modeComboBox.getSelectedItem();
        ControlStrategy strategy;
        
        switch (mode) {
            case "Manual":
                strategy = new ManualControl();
                // Hide AI Control tab for Manual mode
                if (tabbedPane.getTabCount() > 2) {
                    tabbedPane.removeTabAt(2);
                }
                break;
            case "Scheduled":
                strategy = new ScheduledControl();
                // Hide AI Control tab for Scheduled mode
                if (tabbedPane.getTabCount() > 2) {
                    tabbedPane.removeTabAt(2);
                }
                break;
            case "AI":
                strategy = new AIControl();
                // Show AI Control tab for AI mode (if not already visible)
                if (tabbedPane.getTabCount() == 2) {
                    createAndAddAIControlTab();
                }
                // Auto-switch to AI Control tab
                tabbedPane.setSelectedIndex(2);
                break;
            default:
                strategy = new ManualControl();
                // Hide AI Control tab by default
                if (tabbedPane.getTabCount() > 2) {
                    tabbedPane.removeTabAt(2);
                }
        }
        
        controller.setControlStrategy(strategy);
        logStatus("Control mode changed to: " + mode);
        logStatus(strategy.getDescription());
    }
    
    /**
     * Creates and adds the AI Control tab to the tabbed pane.
     */
    private void createAndAddAIControlTab() {
        // AI Control Tab
        JPanel aiPanel = new JPanel(new BorderLayout());
        aiPanel.setBorder(new TitledBorder("AI Automation Control"));
        
        // Create AI button panel
        JPanel aiButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        aiButtonPanel.setBackground(new Color(32, 32, 40));
        
        JButton startAIButton = new JButton("Start AI Automation");
        startAIButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        startAIButton.setBackground(new Color(244, 244, 244));
        startAIButton.setForeground(Color.BLACK);
        startAIButton.setFocusPainted(false);
        startAIButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startAIButton.setPreferredSize(new Dimension(200, 45));
        
        JButton stopAIButton = new JButton("Stop AI Automation");
        stopAIButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        stopAIButton.setBackground(new Color(244, 244, 244));
        stopAIButton.setForeground(Color.BLACK);
        stopAIButton.setFocusPainted(false);
        stopAIButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stopAIButton.setPreferredSize(new Dimension(200, 45));
        stopAIButton.setEnabled(false);
        
        JLabel aiStatusLabel = new JLabel("🛑 AI Automation: STOPPED");
        aiStatusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        aiStatusLabel.setForeground(new Color(255, 193, 7));
        
        startAIButton.addActionListener(e -> {
            AIControl aiControl = (AIControl) controller.getControlStrategy();
            if (aiControl != null) {
                // Set up callback for device state changes
                aiControl.setAutomationCallback((deviceName, isOn) -> {
                    String action = isOn ? "🟢 turned ON" : "🔴 turned OFF";
                    logAIStatus(deviceName + " " + action);
                });
                
                aiControl.startRandomAutomation(controller.getAllDevices());
                startAIButton.setEnabled(false);
                stopAIButton.setEnabled(true);
                aiStatusLabel.setText("🟢 AI Automation: RUNNING");
                aiStatusLabel.setForeground(new Color(100, 200, 255));
                logAIStatus("AI Automation started");
            }
        });
        
        stopAIButton.addActionListener(e -> {
            AIControl aiControl = (AIControl) controller.getControlStrategy();
            if (aiControl != null) {
                aiControl.stopRandomAutomation();
                startAIButton.setEnabled(true);
                stopAIButton.setEnabled(false);
                aiStatusLabel.setText("🛑 AI Automation: STOPPED");
                aiStatusLabel.setForeground(new Color(255, 193, 7));
                logAIStatus("AI Automation stopped");
            }
        });
        
        aiButtonPanel.add(startAIButton);
        aiButtonPanel.add(stopAIButton);
        
        // AI Log area
        aiLogArea = new JTextArea();
        aiLogArea.setEditable(false);
        aiLogArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        aiLogArea.setText("AI Automation Log:\nWaiting for automation to start...\n\n");
        aiLogArea.setBackground(new Color(32, 32, 40));
        aiLogArea.setForeground(new Color(100, 200, 255));
        
        JScrollPane aiLogScrollPane = new JScrollPane(aiLogArea);
        aiLogScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        aiLogScrollPane.setBorder(new TitledBorder("Automation Log"));
        
        // AI info panel
        JPanel aiInfoPanel = new JPanel(new BorderLayout());
        aiInfoPanel.setBackground(new Color(32, 32, 40));
        aiInfoPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel infoTextLabel = new JLabel("<html><div style='color: #64C8FF; font-family: Segoe UI;'>" +
                "<b>About AI Automation:</b><br>" +
                "• Devices randomly turn ON/OFF every 5 seconds<br>" +
                "• Cycles through all available smart devices<br>" +
                "• Perfect for testing and demonstrations<br>" +
                "• Real-time status updates in Status & AI Log tabs<br>" +
                "• Click Start to begin, Stop to halt automation" +
                "</div></html>");
        infoTextLabel.setOpaque(false);
        
        aiInfoPanel.add(infoTextLabel, BorderLayout.WEST);
        
        // Assemble AI panel with split pane
        JPanel aiTopPanel = new JPanel();
        aiTopPanel.setLayout(new BoxLayout(aiTopPanel, BoxLayout.Y_AXIS));
        aiTopPanel.setBackground(new Color(32, 32, 40));
        aiTopPanel.add(aiStatusLabel);
        aiTopPanel.add(Box.createVerticalStrut(10));
        aiTopPanel.add(aiButtonPanel);
        
        JSplitPane aiSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, aiInfoPanel, aiLogScrollPane);
        aiSplitPane.setDividerLocation(0.3);
        
        aiPanel.add(aiTopPanel, BorderLayout.NORTH);
        aiPanel.add(aiSplitPane, BorderLayout.CENTER);
        aiPanel.setBackground(new Color(32, 32, 40));
        
        // Add AI Control tab
        tabbedPane.addTab("🤖 AI Control", aiPanel);
    }
    
    /**
     * Logs a status message.
     */
    private void logStatus(String message) {
        statusArea.append("[" + getCurrentTime() + "] " + message + "\n");
        statusArea.setCaretPosition(statusArea.getDocument().getLength());
    }
    
    /**
     * Logs an AI status message to both AI log and main status area.
     */
    private void logAIStatus(String message) {
        String timestamp = "[" + getCurrentTime() + "] ";
        aiLogArea.append(timestamp + message + "\n");
        aiLogArea.setCaretPosition(aiLogArea.getDocument().getLength());
        // Also log to main status area
        statusArea.append(timestamp + "🤖 " + message + "\n");
        statusArea.setCaretPosition(statusArea.getDocument().getLength());
    }
    
    /**
     * Gets current time as string.
     */
    private String getCurrentTime() {
        return java.time.LocalTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
        );
    }
    
    /**
     * Updates the device count label.
     */
    private void updateDeviceCount() {
        Component[] components = ((JPanel)getContentPane()).getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                for (Component subComp : panel.getComponents()) {
                    if (subComp instanceof JLabel) {
                        JLabel label = (JLabel) subComp;
                        if (label.getText().startsWith("Devices:")) {
                            label.setText("Devices: " + deviceMap.size());
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Removes the selected device from the system.
     */
    private void removeDevice() {
        if (selectedDevice == null) {
            JOptionPane.showMessageDialog(this, "Please select a device to remove.", "No Device Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to remove: " + selectedDevice.getName() + "?",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            String deviceName = selectedDevice.getName();
            
            // Remove from all rooms
            for (Room room : roomMap.values()) {
                room.removeDevice(selectedDevice);
            }
            
            // Remove from maps and lists
            deviceMap.remove(deviceName);
            deviceListModel.removeElement(deviceName);
            deviceListModel.removeElement(deviceName + " ⚡");
            deviceListModel.removeElement(deviceName + " 🌀");
            
            // Remove from controller
            // Note: CentralController may not have a removeDevice method, but we clear the selection
            
            selectedDevice = null;
            selectedRoom = null;
            deviceList.clearSelection();
            roomList.clearSelection();
            selectedDeviceLabel.setText("No device selected");
            roomDetailsArea.setText("Select a room to see details...\n");
            
            logStatus("Removed device: " + deviceName);
            updateDeviceCount();
        }
    }
    
    /**
     * Adjusts brightness when slider is moved.
     */
    private void adjustBrightness() {
        if (selectedDevice != null && !isSliderUpdating) {
            int brightnessValue = brightnessSlider.getValue();
            brightnessLabel.setText("Brightness: " + brightnessValue + "%");
            
            // Check if selected device is a Light
            if (selectedDevice instanceof Light) {
                Light light = (Light) selectedDevice;
                light.setBrightness(brightnessValue);
                logStatus("Adjusted " + light.getName() + " brightness to " + brightnessValue + "%");
                logStatus("Status: " + light.getStatus());
            }
            // Check if it's a decorator wrapping a Light
            else if (selectedDevice instanceof EnergyMonitorDecorator) {
                EnergyMonitorDecorator decorator = (EnergyMonitorDecorator) selectedDevice;
                SmartDevice wrapped = decorator.getWrappedDevice();
                if (wrapped instanceof Light) {
                    Light light = (Light) wrapped;
                    light.setBrightness(brightnessValue);
                    logStatus("Adjusted " + light.getName() + " brightness to " + brightnessValue + "%");
                    logStatus("Status: " + light.getStatus());
                }
            }
        }
    }
    
    /**
     * Adjusts temperature when slider is moved.
     */
    private void adjustTemperature() {
        if (selectedDevice != null && !isSliderUpdating) {
            int temperatureValue = temperatureSlider.getValue();
            temperatureLabel.setText("Temperature: " + temperatureValue + "°F");
            
            // Check if selected device is a Thermostat
            if (selectedDevice instanceof Thermostat) {
                Thermostat thermostat = (Thermostat) selectedDevice;
                thermostat.setTargetTemp(temperatureValue);
                logStatus("Adjusted " + thermostat.getName() + " temperature to " + temperatureValue + "°F");
                logStatus("Status: " + thermostat.getStatus());
            }
        }
    }
    
    /**
     * Adjusts volume when slider is moved.
     */
    private void adjustVolume() {
        if (selectedDevice != null && !isSliderUpdating) {
            int volumeValue = volumeSlider.getValue();
            volumeLabel.setText("Volume: " + volumeValue + "%");
            
            // Check if selected device is a Speaker
            if (selectedDevice instanceof Speaker) {
                Speaker speaker = (Speaker) selectedDevice;
                speaker.setVolume(volumeValue);
                logStatus("Adjusted " + speaker.getName() + " volume to " + volumeValue + "%");
                logStatus("Status: " + speaker.getStatus());
            }
        }
    }
    
    /**
     * Adjusts fan speed when slider is moved.
     */
    private void adjustFanSpeed() {
        if (selectedDevice != null && !isSliderUpdating) {
            int speedValue = fanSpeedSlider.getValue();
            fanSpeedLabel.setText("Fan Speed: " + speedValue + "/3");
            
            // Check if selected device is a FanAdapter
            if (selectedDevice instanceof FanAdapter) {
                FanAdapter fan = (FanAdapter) selectedDevice;
                fan.setSpeed(speedValue);
                logStatus("Adjusted " + fan.getName() + " speed to " + speedValue + "/3");
                logStatus("Status: " + fan.getStatus());
            }
        }
    }
    
    /**
     * Displays room details when a room is selected.
     */
    private void displayRoomDetails() {
        String selectedRoomStr = roomList.getSelectedValue();
        if (selectedRoomStr != null) {
            // Remove emoji and extra spaces for lookup
            String roomName = selectedRoomStr.replaceAll("[^a-zA-Z ]", "").trim();
            selectedRoom = roomMap.get(roomName);
            
            if (selectedRoom != null) {
                StringBuilder details = new StringBuilder();
                details.append("Room: ").append(selectedRoom.getName()).append("\n");
                details.append("======================================\n");
                details.append("Devices in this room:\n");
                
                List<SmartDevice> devices = selectedRoom.getDevices();
                if (devices.isEmpty()) {
                    details.append("  (No devices)\n");
                } else {
                    for (SmartDevice device : devices) {
                        details.append("  • ").append(device.getName()).append("\n");
                        details.append("    └─ ").append(device.getStatus()).append("\n");
                    }
                }
                
                details.append("\nRoom Controls:\n");
                details.append("  • Click 'Turn ON' to turn on all devices\n");
                details.append("  • Click 'Turn OFF' to turn off all devices\n");
                
                roomDetailsArea.setText(details.toString());
                logStatus("Viewing room: " + selectedRoom.getName());
            } else {
                roomDetailsArea.setText("Room not found: " + roomName);
                selectedRoom = null;
            }
        } else {
            selectedRoom = null;
        }
    }
    
    /**
     * Main method to launch the GUI.
     */
    public static void main(String[] args) {
        // Use system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Launch GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new SmartHomeGUI());
    }
}