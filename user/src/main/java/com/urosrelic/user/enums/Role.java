package com.urosrelic.user.enums;

public enum Role {
    USER("USER"), ADMIN("ADMIN");

    private final String roleValue;

    Role(String value) {
        this.roleValue = value;
    }

    public String getRoleValue() {
        return roleValue;
    }
}
