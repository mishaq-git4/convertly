package com.example.demo.service;

import com.example.demo.enums.Category;
import com.example.demo.exception.InvalidUnitException;
import com.example.demo.model.ConversionRequest;
import com.example.demo.model.ConversionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    @Autowired
    private TemperatureService temperatureService;

    @Autowired
    private LengthService lengthService;

    @Autowired
    private WeightService weightService;

    @Autowired
    private TimeService timeService;

    public ConversionResponse convert(ConversionRequest request) {
        validateRequest(request);

        try {
            Category category = Category.fromString(request.getCategory());

            ConversionResponse response;
            switch (category) {
                case TEMPERATURE:
                    response = temperatureService.convert(request);
                    break;
                case LENGTH:
                    response = lengthService.convert(request);
                    break;
                case WEIGHT:
                    response = weightService.convert(request);
                    break;
                case TIME:
                    response = timeService.convert(request);
                    break;
                default:
                    throw new InvalidUnitException("Unsupported category: " + request.getCategory());
            }

            response.setOriginalInput(request);
            response.setStatus("success");
            return response;

        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid category: " + request.getCategory());
        }
    }

    private void validateRequest(ConversionRequest request) {
        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            throw new InvalidUnitException("Category is required");
        }
        if (request.getFromUnit() == null || request.getFromUnit().trim().isEmpty()) {
            throw new InvalidUnitException("FromUnit is required");
        }
        if (request.getToUnit() == null || request.getToUnit().trim().isEmpty()) {
            throw new InvalidUnitException("ToUnit is required");
        }
        if (request.getValue() == null) {
            throw new InvalidUnitException("Value is required");
        }

        // Validate that value is not negative for weight and time
        if (("weight".equalsIgnoreCase(request.getCategory()) || "time".equalsIgnoreCase(request.getCategory()))
                && request.getValue() < 0) {
            throw new InvalidUnitException("Value cannot be negative for " + request.getCategory());
        }
    }
}
