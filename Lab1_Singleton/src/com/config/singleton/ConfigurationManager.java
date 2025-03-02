package com.config.singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import com.config.exception.ConfigurationException;
import com.config.model.ConfigurationSetting;

public class ConfigurationManager {
    // Private static instance - the only one that will exist
    private static volatile ConfigurationManager instance;
    
    // Thread-safe map to store configuration settings
    private final Map<String, ConfigurationSetting> configSettings;

    private static final String CONFIG_FILE = "src/config/config.properties";
    
    // Private constructor to prevent direct instantiation
    private ConfigurationManager() {
        configSettings = new ConcurrentHashMap<>();
        initializeDefaultSettings();
    }

    private void loadConfiguration() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            
            // Load properties into configuration settings
            props.forEach((key, value) -> 
                setConfigValue(key.toString(), value, "Loaded from configuration file")
            );
            
        } catch (IOException e) {
            System.err.println("Warning: Could not load configuration file. Using default values.");
            System.err.println("Please copy template.config.properties to config.properties and set appropriate values.");
            initializeDefaultSettings();
        }
    }
    
    private void initializeDefaultSettings() {
        // Initialize with Docker MySQL settings
        setConfigValue("database.url", "jdbc:mysql://localhost:3306/myapp", "Database URL");
        setConfigValue("database.username", "root", "Database Username");
        setConfigValue("database.password", "Thuan@np4", "Database Password");
        setConfigValue("max.connections", 100, "Maximum Connection Pool Size");
        setConfigValue("app.name", "MyApplication", "Application Name");
    }
    
    // Public method to get the singleton instance
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new ConfigurationManager();
                }
            }
        }
        return instance;
    }
    
    // Method to get a configuration value
    public Object getConfigValue(String key) {
        ConfigurationSetting setting = configSettings.get(key);
        if (setting == null) {
            throw new ConfigurationException("Configuration key not found: " + key);
        }
        return setting.getValue();
    }
    
    // Method to set a configuration value with description
    public void setConfigValue(String key, Object value, String description) {
        configSettings.put(key, new ConfigurationSetting(key, value, description));
    }
    
    // Method to update an existing configuration value
    public void updateConfigValue(String key, Object value) {
        if (!configSettings.containsKey(key)) {
            throw new ConfigurationException("Cannot update non-existent configuration: " + key);
        }
        ConfigurationSetting existing = configSettings.get(key);
        existing.setValue(value);
    }
    
    // Method to check if a configuration exists
    public boolean hasConfig(String key) {
        return configSettings.containsKey(key);
    }
    
    // Method to get configuration description
    public String getConfigDescription(String key) {
        ConfigurationSetting setting = configSettings.get(key);
        if (setting == null) {
            throw new ConfigurationException("Configuration key not found: " + key);
        }
        return setting.getDescription();
    }
    
    // Method to check configuration status
    public boolean checkStatus() {
        return !configSettings.isEmpty();
    }
    
    // Method to remove a configuration
    public void removeConfig(String key) {
        if (!configSettings.containsKey(key)) {
            throw new ConfigurationException("Cannot remove non-existent configuration: " + key);
        }
        configSettings.remove(key);
    }
    
    // Method to get all configuration keys
    public Set<String> getAllConfigurationKeys() {
        return new HashSet<>(configSettings.keySet());
    }
    
    // Method to clear all configurations
    public void clearAllConfigs() {
        configSettings.clear();
        initializeDefaultSettings();
    }

    public boolean testDatabaseConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            String url = (String) getConfigValue("database.url");
            String username = (String) getConfigValue("database.username");
            String password = (String) getConfigValue("database.password");
            
            System.out.println("Attempting to connect to database...");
            System.out.println("URL: " + url);
            System.out.println("Username: " + username);
            
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                boolean isValid = conn.isValid(5);
                if (isValid) {
                    System.out.println("Successfully connected to the database!");
                }
                return isValid;
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure mysql-connector-j is in your classpath");
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}