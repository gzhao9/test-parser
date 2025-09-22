package com.testparser;

import com.testparser.model.TestCaseInfo;
import com.testparser.util.TestCaseJsonExporter;

import java.io.IOException;
import java.util.List;

/**
 * 主类，演示如何使用 TestCaseFinder 和 TestCaseJsonExporter
 */
public class TestParserApp {

    public static void main(String[] args) {
        // 设置指定的路径
        String projectRootPath = "C:\\Java_projects\\Apache\\druid";
        String outputJsonPath = "C:\\Users\\10590\\OneDrive - stevens.edu\\PHD\\2025 Fall\\test-parser\\druid-testcases.json";

        // 如果有命令行参数，使用提供的路径（可选）
        if (args.length >= 1) {
            projectRootPath = args[0];
        }
        if (args.length >= 2) {
            outputJsonPath = args[1];
        }

        try {
            System.out.println("Starting to parse project: " + projectRootPath);
            
            // 步骤1: 解析测试用例
            List<TestCaseInfo> testCases = TestCaseFinder.parseTests(projectRootPath);
            System.out.println("Successfully parsed " + testCases.size() + " test cases");

            // 步骤2: 导出到 JSON 文件
            TestCaseJsonExporter exporter = new TestCaseJsonExporter();
            exporter.exportToJson(testCases, outputJsonPath);
            
            // 可选：输出一些统计信息
            printStatistics(testCases);
            
        } catch (IOException e) {
            System.err.println("Error occurred during processing: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 打印测试用例统计信息
     * 
     * @param testCases 测试用例列表
     */
    private static void printStatistics(List<TestCaseInfo> testCases) {
        if (testCases.isEmpty()) {
            System.out.println("No test cases found");
            return;
        }

        System.out.println("\n=== Statistics ===");
        System.out.println("Total test cases: " + testCases.size());

        // 统计不同文件中的测试用例数量
        long uniqueFiles = testCases.stream()
                .map(TestCaseInfo::getFilePath)
                .distinct()
                .count();
        System.out.println("Files involved: " + uniqueFiles);

        // 统计注解类型
        testCases.stream()
                .flatMap(tc -> tc.getAnnotations().stream())
                .distinct()
                .forEach(annotation -> {
                    long count = testCases.stream()
                            .mapToLong(tc -> tc.getAnnotations().stream()
                                    .mapToLong(a -> a.equals(annotation) ? 1 : 0)
                                    .sum())
                            .sum();
                    System.out.println("Annotation @" + annotation + ": " + count + " occurrences");
                });
    }
}