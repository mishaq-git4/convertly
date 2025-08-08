package com.example.demo.service;

import com.example.demo.enums.TimeUnit;
import com.example.demo.exception.InvalidUnitException;
import com.example.demo.model.ConversionRequest;
import com.example.demo.model.ConversionResponse;
import org.springframework.stereotype.Service;

@Service
public class TimeService {

    public ConversionResponse convert(ConversionRequest request) {
        try {
            TimeUnit fromUnit = TimeUnit.fromString(request.getFromUnit());
            TimeUnit toUnit = TimeUnit.fromString(request.getToUnit());
            Double value = request.getValue();

            Double result = convertTime(value, fromUnit, toUnit);
            String formula = generateFormula(value, fromUnit, toUnit, result);

            return new ConversionResponse(result, formula, null, null);

        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid time units: " + e.getMessage());
        }
    }

    private Double convertTime(Double value, TimeUnit from, TimeUnit to) {
        if (from == to) {
            return value;
        }

        // Convert to seconds first
        Double seconds = switch (from) {
            case SECONDS -> value;
            case MINUTES -> value * 60;
            case HOURS -> value * 3600;
            case DAYS -> value * 86400;
        };

        // Convert from seconds to target unit
        return switch (to) {
            case SECONDS -> seconds;
            case MINUTES -> seconds / 60;
            case HOURS -> seconds / 3600;
            case DAYS -> seconds / 86400;
        };
    }

    private String generateFormula(Double value, TimeUnit from, TimeUnit to, Double result) {
        if (from == to) {
            return String.format("%.6f %s = %.6f %s", value, from.getValue(), result, to.getValue());
        }

        String conversion = switch (from) {
            case SECONDS -> switch (to) {
                case MINUTES -> String.format("%.6f seconds ÷ 60", value);
                case HOURS -> String.format("%.6f seconds ÷ 3600", value);
                case DAYS -> String.format("%.6f seconds ÷ 86400", value);
                default -> "";
            };
            case MINUTES -> switch (to) {
                case SECONDS -> String.format("%.6f minutes × 60", value);
                case HOURS -> String.format("%.6f minutes ÷ 60", value);
                case DAYS -> String.format("%.6f minutes ÷ 1440", value);
                default -> "";
            };
            case HOURS -> switch (to) {
                case SECONDS -> String.format("%.6f hours × 3600", value);
                case MINUTES -> String.format("%.6f hours × 60", value);
                case DAYS -> String.format("%.6f hours ÷ 24", value);
                default -> "";
            };
            case DAYS -> switch (to) {
                case SECONDS -> String.format("%.6f days × 86400", value);
                case MINUTES -> String.format("%.6f days × 1440", value);
                case HOURS -> String.format("%.6f days × 24", value);
                default -> "";
            };
        };

        return String.format("%s = %.6f %s", conversion, result, to.getValue());
    }
}
