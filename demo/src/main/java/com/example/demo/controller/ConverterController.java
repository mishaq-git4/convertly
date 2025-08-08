package com.example.demo.controller;

import com.example.demo.enums.*;
import com.example.demo.model.ConversionRequest;
import com.example.demo.model.ConversionResponse;
import com.example.demo.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class ConverterController {

    @Autowired
    private ConversionService conversionService;

    /**
     * POST /convert - Convert a value from one unit to another
     */
    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(@RequestBody ConversionRequest request) {
        ConversionResponse response = conversionService.convert(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /categories - Get all available unit categories
     */
    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        List<String> categories = Arrays.stream(Category.values())
                .map(Category::getValue)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    /**
     * GET /units?category=temperature - Get supported units for a given category
     */
    @GetMapping("/units")
    public ResponseEntity<List<String>> getUnits(@RequestParam String category) {
        try {
            Category cat = Category.fromString(category);
            List<String> units;

            switch (cat) {
                case TEMPERATURE:
                    units = Arrays.stream(TemperatureUnit.values())
                            .map(TemperatureUnit::getValue)
                            .collect(Collectors.toList());
                    break;
                case LENGTH:
                    units = Arrays.stream(LengthUnit.values())
                            .map(LengthUnit::getValue)
                            .collect(Collectors.toList());
                    break;
                case WEIGHT:
                    units = Arrays.stream(WeightUnit.values())
                            .map(WeightUnit::getValue)
                            .collect(Collectors.toList());
                    break;
                case TIME:
                    units = Arrays.stream(TimeUnit.values())
                            .map(TimeUnit::getValue)
                            .collect(Collectors.toList());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported category: " + category);
            }

            return ResponseEntity.ok(units);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    /**
     * GET /sample-payload - Get sample request body for testing /convert endpoint
     */
    @GetMapping("/sample-payload")
    public ResponseEntity<Map<String, Object>> getSamplePayload() {
        Map<String, Object> samples = new HashMap<>();

        // Temperature sample
        Map<String, Object> temperatureSample = new HashMap<>();
        temperatureSample.put("category", "temperature");
        temperatureSample.put("fromUnit", "celsius");
        temperatureSample.put("toUnit", "fahrenheit");
        temperatureSample.put("value", 25);

        // Length sample
        Map<String, Object> lengthSample = new HashMap<>();
        lengthSample.put("category", "length");
        lengthSample.put("fromUnit", "meter");
        lengthSample.put("toUnit", "foot");
        lengthSample.put("value", 1);

        // Weight sample
        Map<String, Object> weightSample = new HashMap<>();
        weightSample.put("category", "weight");
        weightSample.put("fromUnit", "kilogram");
        weightSample.put("toUnit", "pound");
        weightSample.put("value", 1);

        // Time sample
        Map<String, Object> timeSample = new HashMap<>();
        timeSample.put("category", "time");
        timeSample.put("fromUnit", "hours");
        timeSample.put("toUnit", "minutes");
        timeSample.put("value", 1);

        samples.put("temperature_example", temperatureSample);
        samples.put("length_example", lengthSample);
        samples.put("weight_example", weightSample);
        samples.put("time_example", timeSample);
        samples.put("description", "Use any of these sample payloads to test the /convert endpoint");

        return ResponseEntity.ok(samples);
    }

    /**
     * GET /health - Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "Unit Converter API is up and running");
        return ResponseEntity.ok(response);
    }
}
