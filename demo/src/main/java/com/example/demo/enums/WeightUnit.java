package com.example.demo.enums;

public enum WeightUnit {
    GRAM("gram"),
    KILOGRAM("kilogram"),
    POUND("pound"),
    OUNCE("ounce");

    private final String value;

    WeightUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WeightUnit fromString(String value) {
        for (WeightUnit unit : WeightUnit.values()) {
            if (unit.value.equalsIgnoreCase(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Invalid weight unit: " + value);
    }
}
