import com.config.singleton.ConfigurationManager;
import com.config.exception.ConfigurationException;

public class ConfigurationManagerTest {
    public static void testSingletonBehavior() {
        ConfigurationManager instance1 = ConfigurationManager.getInstance();
        ConfigurationManager instance2 = ConfigurationManager.getInstance();
        
        assert instance1 == instance2 : "Singleton pattern failed: instances are not the same";
        System.out.println("Singleton behavior test: PASSED");
    }
    
    public static void testConfigurationOperations() {
        ConfigurationManager config = ConfigurationManager.getInstance();
        
        // Test setting and getting values
        config.setConfigValue("test.key", "test.value", "Test configuration");
        assert "test.value".equals(config.getConfigValue("test.key")) : "Configuration get/set failed";
        
        // Test updating values
        config.updateConfigValue("test.key", "updated.value");
        assert "updated.value".equals(config.getConfigValue("test.key")) : "Configuration update failed";
        
        // Test removing values
        config.removeConfig("test.key");
        assert !config.hasConfig("test.key") : "Configuration removal failed";
        
        System.out.println("Configuration operations test: PASSED");
    }
    
    public static void testExceptionHandling() {
        ConfigurationManager config = ConfigurationManager.getInstance();
        
        try {
            config.getConfigValue("non.existent.key");
            assert false : "Should have thrown ConfigurationException";
        } catch (ConfigurationException e) {
            System.out.println("Exception handling test: PASSED");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Starting Configuration Manager Tests...\n");
        
        testSingletonBehavior();
        testConfigurationOperations();
        testExceptionHandling();
        
        System.out.println("\nAll tests completed successfully!");
    }
}