package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversionRequest {

    @JsonProperty("category")
    private String category;

    @JsonProperty("fromUnit")
    private String fromUnit;

    @JsonProperty("toUnit")
    private String toUnit;

    @JsonProperty("value")
    private Double value;

    // Default constructor
    public ConversionRequest() {
    }

    // Constructor with all fields
    public ConversionRequest(String category, String fromUnit, String toUnit, Double value) {
        this.category = category;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.value = value;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(String fromUnit) {
        this.fromUnit = fromUnit;
    }

    public String getToUnit() {
        return toUnit;
    }

    public void setToUnit(String toUnit) {
        this.toUnit = toUnit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConversionRequest{" +
                "category='" + category + '\'' +
                ", fromUnit='" + fromUnit + '\'' +
                ", toUnit='" + toUnit + '\'' +
                ", value=" + value +
                '}';
    }
}
