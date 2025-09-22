package com.testparser.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.testparser.model.TestCaseInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 测试用例 JSON 导出工具类
 * 负责将测试用例列表转换为 JSON 格式并保存到指定文件
 */
public class TestCaseJsonExporter {

    private final ObjectMapper objectMapper;

    public TestCaseJsonExporter() {
        this.objectMapper = new ObjectMapper();
        // 启用格式化输出，使 JSON 文件更易读
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        // 可以根据需要添加其他配置
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * 将测试用例列表保存为 JSON 文件
     * 
     * @param testCases 测试用例列表
     * @param outputPath 输出文件路径（包含文件名，例如："/path/to/output/testcases.json"）
     * @throws IOException 如果文件写入失败
     */
    public void exportToJson(List<TestCaseInfo> testCases, String outputPath) throws IOException {
        if (testCases == null) {
            throw new IllegalArgumentException("测试用例列表不能为 null");
        }
        
        if (outputPath == null || outputPath.trim().isEmpty()) {
            throw new IllegalArgumentException("输出路径不能为空");
        }

        // 确保输出目录存在
        File outputFile = new File(outputPath);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new IOException("无法创建输出目录: " + parentDir.getAbsolutePath());
            }
        }

        // 将测试用例列表转换为 JSON 并写入文件
        objectMapper.writeValue(outputFile, testCases);
        
        System.out.println("JSON 文件已保存到: " + outputPath);
        System.out.println("共导出 " + testCases.size() + " 个测试用例");
    }

    /**
     * 将测试用例列表转换为 JSON 字符串
     * 
     * @param testCases 测试用例列表
     * @return JSON 字符串
     * @throws IOException 如果转换失败
     */
    public String toJsonString(List<TestCaseInfo> testCases) throws IOException {
        if (testCases == null) {
            throw new IllegalArgumentException("测试用例列表不能为 null");
        }
        
        return objectMapper.writeValueAsString(testCases);
    }

    /**
     * 从 JSON 字符串解析测试用例列表
     * 
     * @param jsonString JSON 字符串
     * @return 测试用例列表
     * @throws IOException 如果解析失败
     */
    public List<TestCaseInfo> fromJsonString(String jsonString) throws IOException {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON 字符串不能为空");
        }
        
        return objectMapper.readValue(jsonString, 
            objectMapper.getTypeFactory().constructCollectionType(List.class, TestCaseInfo.class));
    }

    /**
     * 从 JSON 文件读取测试用例列表
     * 
     * @param filePath JSON 文件路径
     * @return 测试用例列表
     * @throws IOException 如果文件读取失败
     */
    public List<TestCaseInfo> importFromJson(String filePath) throws IOException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("文件路径不能为空");
        }
        
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("文件不存在: " + filePath);
        }
        
        return objectMapper.readValue(file, 
            objectMapper.getTypeFactory().constructCollectionType(List.class, TestCaseInfo.class));
    }
}