package com.pixelbean.models;

/**
 * Enum representing active/inactive status
 */
public enum Status {
    ACTIVE("Activo"),
    INACTIVE("Inactivo");
    
    private final String displayName;
    
    Status(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
