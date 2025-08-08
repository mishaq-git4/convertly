package com.example.demo.service;

import com.example.demo.enums.LengthUnit;
import com.example.demo.exception.InvalidUnitException;
import com.example.demo.model.ConversionRequest;
import com.example.demo.model.ConversionResponse;
import org.springframework.stereotype.Service;

@Service
public class LengthService {

    public ConversionResponse convert(ConversionRequest request) {
        try {
            LengthUnit fromUnit = LengthUnit.fromString(request.getFromUnit());
            LengthUnit toUnit = LengthUnit.fromString(request.getToUnit());
            Double value = request.getValue();

            Double result = convertLength(value, fromUnit, toUnit);
            String formula = generateFormula(value, fromUnit, toUnit, result);

            return new ConversionResponse(result, formula, null, null);

        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid length units: " + e.getMessage());
        }
    }

    private Double convertLength(Double value, LengthUnit from, LengthUnit to) {
        if (from == to) {
            return value;
        }

        // Convert to meters first
        Double meters = switch (from) {
            case METER -> value;
            case KILOMETER -> value * 1000;
            case MILE -> value * 1609.344;
            case INCH -> value * 0.0254;
            case FOOT -> value * 0.3048;
        };

        // Convert from meters to target unit
        return switch (to) {
            case METER -> meters;
            case KILOMETER -> meters / 1000;
            case MILE -> meters / 1609.344;
            case INCH -> meters / 0.0254;
            case FOOT -> meters / 0.3048;
        };
    }

    private String generateFormula(Double value, LengthUnit from, LengthUnit to, Double result) {
        if (from == to) {
            return String.format("%.6f %s = %.6f %s", value, from.getValue(), result, to.getValue());
        }

        String conversion = switch (from) {
            case METER -> switch (to) {
                case KILOMETER -> String.format("%.6f m ÷ 1000", value);
                case MILE -> String.format("%.6f m ÷ 1609.344", value);
                case INCH -> String.format("%.6f m ÷ 0.0254", value);
                case FOOT -> String.format("%.6f m ÷ 0.3048", value);
                default -> "";
            };
            case KILOMETER -> switch (to) {
                case METER -> String.format("%.6f km × 1000", value);
                case MILE -> String.format("%.6f km × 1000 ÷ 1609.344", value);
                case INCH -> String.format("%.6f km × 1000 ÷ 0.0254", value);
                case FOOT -> String.format("%.6f km × 1000 ÷ 0.3048", value);
                default -> "";
            };
            case MILE -> switch (to) {
                case METER -> String.format("%.6f mi × 1609.344", value);
                case KILOMETER -> String.format("%.6f mi × 1609.344 ÷ 1000", value);
                case INCH -> String.format("%.6f mi × 1609.344 ÷ 0.0254", value);
                case FOOT -> String.format("%.6f mi × 1609.344 ÷ 0.3048", value);
                default -> "";
            };
            case INCH -> switch (to) {
                case METER -> String.format("%.6f in × 0.0254", value);
                case KILOMETER -> String.format("%.6f in × 0.0254 ÷ 1000", value);
                case MILE -> String.format("%.6f in × 0.0254 ÷ 1609.344", value);
                case FOOT -> String.format("%.6f in × 0.0254 ÷ 0.3048", value);
                default -> "";
            };
            case FOOT -> switch (to) {
                case METER -> String.format("%.6f ft × 0.3048", value);
                case KILOMETER -> String.format("%.6f ft × 0.3048 ÷ 1000", value);
                case MILE -> String.format("%.6f ft × 0.3048 ÷ 1609.344", value);
                case INCH -> String.format("%.6f ft × 0.3048 ÷ 0.0254", value);
                default -> "";
            };
        };

        return String.format("%s = %.6f %s", conversion, result, to.getValue());
    }
}
