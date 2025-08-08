package com.example.demo.service;

import com.example.demo.enums.WeightUnit;
import com.example.demo.exception.InvalidUnitException;
import com.example.demo.model.ConversionRequest;
import com.example.demo.model.ConversionResponse;
import org.springframework.stereotype.Service;

@Service
public class WeightService {

    public ConversionResponse convert(ConversionRequest request) {
        try {
            WeightUnit fromUnit = WeightUnit.fromString(request.getFromUnit());
            WeightUnit toUnit = WeightUnit.fromString(request.getToUnit());
            Double value = request.getValue();

            Double result = convertWeight(value, fromUnit, toUnit);
            String formula = generateFormula(value, fromUnit, toUnit, result);

            return new ConversionResponse(result, formula, null, null);

        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid weight units: " + e.getMessage());
        }
    }

    private Double convertWeight(Double value, WeightUnit from, WeightUnit to) {
        if (from == to) {
            return value;
        }

        // Convert to grams first
        Double grams = switch (from) {
            case GRAM -> value;
            case KILOGRAM -> value * 1000;
            case POUND -> value * 453.592;
            case OUNCE -> value * 28.3495;
        };

        // Convert from grams to target unit
        return switch (to) {
            case GRAM -> grams;
            case KILOGRAM -> grams / 1000;
            case POUND -> grams / 453.592;
            case OUNCE -> grams / 28.3495;
        };
    }

    private String generateFormula(Double value, WeightUnit from, WeightUnit to, Double result) {
        if (from == to) {
            return String.format("%.6f %s = %.6f %s", value, from.getValue(), result, to.getValue());
        }

        String conversion = switch (from) {
            case GRAM -> switch (to) {
                case KILOGRAM -> String.format("%.6f g ÷ 1000", value);
                case POUND -> String.format("%.6f g ÷ 453.592", value);
                case OUNCE -> String.format("%.6f g ÷ 28.3495", value);
                default -> "";
            };
            case KILOGRAM -> switch (to) {
                case GRAM -> String.format("%.6f kg × 1000", value);
                case POUND -> String.format("%.6f kg × 1000 ÷ 453.592", value);
                case OUNCE -> String.format("%.6f kg × 1000 ÷ 28.3495", value);
                default -> "";
            };
            case POUND -> switch (to) {
                case GRAM -> String.format("%.6f lb × 453.592", value);
                case KILOGRAM -> String.format("%.6f lb × 453.592 ÷ 1000", value);
                case OUNCE -> String.format("%.6f lb × 453.592 ÷ 28.3495", value);
                default -> "";
            };
            case OUNCE -> switch (to) {
                case GRAM -> String.format("%.6f oz × 28.3495", value);
                case KILOGRAM -> String.format("%.6f oz × 28.3495 ÷ 1000", value);
                case POUND -> String.format("%.6f oz × 28.3495 ÷ 453.592", value);
                default -> "";
            };
        };

        return String.format("%s = %.6f %s", conversion, result, to.getValue());
    }
}
