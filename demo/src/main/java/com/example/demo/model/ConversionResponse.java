package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversionResponse {

    @JsonProperty("result")
    private Double result;

    @JsonProperty("formula")
    private String formula;

    @JsonProperty("originalInput")
    private ConversionRequest originalInput;

    @JsonProperty("status")
    private String status;

    // Default constructor
    public ConversionResponse() {
    }

    // Constructor with all fields
    public ConversionResponse(Double result, String formula, ConversionRequest originalInput, String status) {
        this.result = result;
        this.formula = formula;
        this.originalInput = originalInput;
        this.status = status;
    }

    // Getters and Setters
    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public ConversionRequest getOriginalInput() {
        return originalInput;
    }

    public void setOriginalInput(ConversionRequest originalInput) {
        this.originalInput = originalInput;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ConversionResponse{" +
                "result=" + result +
                ", formula='" + formula + '\'' +
                ", originalInput=" + originalInput +
                ", status='" + status + '\'' +
                '}';
    }
}
