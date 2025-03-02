package demo;

import com.config.singleton.ConfigurationManager;
import com.config.exception.ConfigurationException;

public class ConfigurationManagerDemo {
    public static void main(String[] args) {
        try {
            // Get the singleton instance
            ConfigurationManager config = ConfigurationManager.getInstance();

            // Display database configurations
            System.out.println("=== Database Configurations ===");
            System.out.println("Database URL: " + config.getConfigValue("database.url"));
            System.out.println("Database Username: " + config.getConfigValue("database.username"));
            
            // Test database connection
            System.out.println("\n=== Testing Database Connection ===");
            boolean isConnected = config.testDatabaseConnection();
            System.out.println("Database connection test: " + 
                (isConnected ? "SUCCESS" : "FAILED"));
            
            // Display default configurations
            System.out.println("\n=== Default Configurations ===");
            System.out.println("Database URL: " + config.getConfigValue("database.url"));
            System.out.println("Max connections: " + config.getConfigValue("max.connections"));
            
            // Update some configurations
            System.out.println("\n=== Updating Configurations ===");
            config.updateConfigValue("max.connections", 200);
            config.setConfigValue("app.timeout", 3000, "Application timeout in milliseconds");
            
            // Display updated configurations
            System.out.println("Updated max connections: " + config.getConfigValue("max.connections"));
            System.out.println("New timeout setting: " + config.getConfigValue("app.timeout"));
            
            // Demonstrate configuration checking
            System.out.println("\n=== Configuration Status ===");
            System.out.println("Has database.url? " + config.hasConfig("database.url"));
            System.out.println("Has invalid.key? " + config.hasConfig("invalid.key"));
            
            // Show configuration descriptions
            System.out.println("\n=== Configuration Descriptions ===");
            System.out.println("max.connections description: " + 
                    config.getConfigDescription("max.connections"));
            
            // Demonstrate singleton behavior
            ConfigurationManager anotherConfig = ConfigurationManager.getInstance();
            System.out.println("\n=== Singleton Verification ===");
            System.out.println("Same instance? " + (config == anotherConfig));
            
        } catch (ConfigurationException e) {
            System.err.println("Configuration error: " + e.getMessage());
        }
    }
}