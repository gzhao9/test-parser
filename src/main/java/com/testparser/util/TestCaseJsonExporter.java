package com.testparser.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testparser.model.TestCaseInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Test case JSON export utility class
 * Responsible for converting test case lists to JSON format and saving to specified files
 */
public class TestCaseJsonExporter {

    private final ObjectMapper objectMapper;

    public TestCaseJsonExporter() {
        this.objectMapper = new ObjectMapper();
        // Enable formatted output to make JSON files more readable
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // Additional configuration can be added as needed
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Save test case list as JSON file
     * 
     * @param testCases Test case list
     * @param outputPath Output file path (including filename, e.g.: "/path/to/output/testcases.json")
     * @throws IOException If file writing fails
     */
    public void exportToJson(List<TestCaseInfo> testCases, String outputPath) throws IOException {
        if (testCases == null) {
            throw new IllegalArgumentException("Test cases list cannot be null");
        }
        
        if (outputPath == null || outputPath.trim().isEmpty()) {
            throw new IllegalArgumentException("Output path cannot be empty");
        }

        // Ensure output directory exists
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new IOException("Unable to create output directory: " + parentDir.getAbsolutePath());
            }
        }

        // Convert test case list to JSON and write to file
        objectMapper.writeValue(outputFile, testCases);
        
        System.out.println("JSON file saved to: " + outputPath);
        System.out.println("Exported " + testCases.size() + " test cases");
    }

    /**
     * Convert test case list to JSON string
     * 
     * @param testCases Test case list
     * @return JSON string
     * @throws IOException If conversion fails
     */
    public String toJsonString(List<TestCaseInfo> testCases) throws IOException {
        if (testCases == null) {
            throw new IllegalArgumentException("Test cases list cannot be null");
        }
        
        return objectMapper.writeValueAsString(testCases);
    }

    /**
     * Parse test case list from JSON string
     * 
     * @param jsonString JSON string
     * @return Test case list
     * @throws IOException If parsing fails
     */
    public List<TestCaseInfo> fromJsonString(String jsonString) throws IOException {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON string cannot be empty");
        }
        
        return objectMapper.readValue(jsonString, 
            objectMapper.getTypeFactory().constructCollectionType(List.class, TestCaseInfo.class));
    }

    /**
     * Read test case list from JSON file
     * 
     * @param filePath JSON file path
     * @return Test case list
     * @throws IOException If file reading fails
     */
    public List<TestCaseInfo> importFromJson(String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
        
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + filePath);
        }
        
        return objectMapper.readValue(file, 
            objectMapper.getTypeFactory().constructCollectionType(List.class, TestCaseInfo.class));
    }
}