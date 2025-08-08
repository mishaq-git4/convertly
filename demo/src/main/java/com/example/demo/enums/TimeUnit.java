package com.example.demo.enums;

public enum TimeUnit {
    SECONDS("seconds"),
    MINUTES("minutes"),
    HOURS("hours"),
    DAYS("days");

    private final String value;

    TimeUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TimeUnit fromString(String value) {
        for (TimeUnit unit : TimeUnit.values()) {
            if (unit.value.equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid time unit: " + value);
    }
}
