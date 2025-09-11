package com.roberto.ticket.models.enums;

public enum Priority {
    LOW("Low priority - Minor impact, no immediate action needed"),
    MEDIUM("Medium priority - Moderate impact, should be addressed soon"),
    HIGH("High priority - Significant impact, needs timely resolution"),
    CRITICAL("Critical priority - Severe impact, immediate attention required");

    private final String description;

    Priority(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}