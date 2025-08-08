package com.example.demo.enums;

public enum LengthUnit {
    METER("meter"),
    KILOMETER("kilometer"),
    MILE("mile"),
    INCH("inch"),
    FOOT("foot");

    private final String value;

    LengthUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LengthUnit fromString(String value) {
        for (LengthUnit unit : LengthUnit.values()) {
            if (unit.value.equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid length unit: " + value);
    }
}
