package com.example.demo.service;

import com.example.demo.enums.TemperatureUnit;
import com.example.demo.exception.InvalidUnitException;
import com.example.demo.model.ConversionRequest;
import com.example.demo.model.ConversionResponse;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService {

    public ConversionResponse convert(ConversionRequest request) {
        try {
            TemperatureUnit fromUnit = TemperatureUnit.fromString(request.getFromUnit());
            TemperatureUnit toUnit = TemperatureUnit.fromString(request.getToUnit());
            Double value = request.getValue();

            Double result = convertTemperature(value, fromUnit, toUnit);
            String formula = generateFormula(value, fromUnit, toUnit, result);

            return new ConversionResponse(result, formula, null, null);

        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid temperature units: " + e.getMessage());
        }
    }

    private Double convertTemperature(Double value, TemperatureUnit from, TemperatureUnit to) {
        if (from == to) {
            return value;
        }

        // Convert to Celsius first
        Double celsius = switch (from) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5.0 / 9.0;
            case KELVIN -> value - 273.15;
        };

        // Convert from Celsius to target unit
        return switch (to) {
            case CELSIUS -> celsius;
            case FAHRENHEIT -> celsius * 9.0 / 5.0 + 32;
            case KELVIN -> celsius + 273.15;
        };
    }

    private String generateFormula(Double value, TemperatureUnit from, TemperatureUnit to, Double result) {
        if (from == to) {
            return String.format("%.2f°%s = %.2f°%s", value, getUnitSymbol(from), result, getUnitSymbol(to));
        }

        return switch (from) {
            case CELSIUS -> switch (to) {
                case FAHRENHEIT -> String.format("(%.2f°C × 9/5) + 32 = %.2f°F", value, result);
                case KELVIN -> String.format("%.2f°C + 273.15 = %.2fK", value, result);
                default -> "";
            };
            case FAHRENHEIT -> switch (to) {
                case CELSIUS -> String.format("(%.2f°F - 32) × 5/9 = %.2f°C", value, result);
                case KELVIN -> String.format("((%.2f°F - 32) × 5/9) + 273.15 = %.2fK", value, result);
                default -> "";
            };
            case KELVIN -> switch (to) {
                case CELSIUS -> String.format("%.2fK - 273.15 = %.2f°C", value, result);
                case FAHRENHEIT -> String.format("((%.2fK - 273.15) × 9/5) + 32 = %.2f°F", value, result);
                default -> "";
            };
        };
    }

    private String getUnitSymbol(TemperatureUnit unit) {
        return switch (unit) {
            case CELSIUS -> "C";
            case FAHRENHEIT -> "F";
            case KELVIN -> "K";
        };
    }
}
