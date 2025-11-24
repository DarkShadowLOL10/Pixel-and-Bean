package com.pixelbean.models;

/**
 * Enum representing user roles in the system
 */
public enum Role {
    ADMIN("Administrador"),
    OPERADOR("Operador");
    
    private final String displayName;
    
    Role(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
