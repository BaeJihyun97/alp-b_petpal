package com.petpal.petpalapp.common.enums;

public enum PState {
    ENABLED("Enabled"),
    DISABLED("Disabled");

    private final String description;

    PState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
