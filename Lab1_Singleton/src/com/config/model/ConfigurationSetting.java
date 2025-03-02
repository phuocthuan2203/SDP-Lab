package com.config.model;

public class ConfigurationSetting {
    private String key;
    private Object value;
    private String description;
    private long lastModified;

    public ConfigurationSetting(String key, Object value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.lastModified = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
        this.lastModified = System.currentTimeMillis();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return "ConfigurationSetting{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}