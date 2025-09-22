package com.testparser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.testparser.model.TestCaseInfo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestCaseFinder {

    public static List<TestCaseInfo> parseTests(String rootPath) throws IOException {
        List<TestCaseInfo> testCases = new ArrayList<>();

        // 遍历所有 java 文件（路径含 "test"）
        List<File> javaFiles = Files.walk(new File(rootPath).toPath())
                .filter(p -> p.toString().toLowerCase().endsWith(".java"))
                .filter(p -> p.toString().toLowerCase().contains("test"))
                .map(p -> p.toFile())
                .collect(Collectors.toList());

        for (File file : javaFiles) {
            CompilationUnit cu;
            try {
                cu = StaticJavaParser.parse(file);
            } catch (Exception e) {
                System.err.println("Parse failed: " + file.getAbsolutePath());
                continue;
            }

            cu.findAll(MethodDeclaration.class).forEach(method -> {
                // 获取所有注解
                List<String> annotations = method.getAnnotations()
                        .stream()
                        .map(a -> a.getNameAsString())
                        .collect(Collectors.toList());

                // 检查是否有测试相关的注解
                boolean hasTestAnnotation = annotations.stream()
                        .anyMatch(a -> a.toLowerCase().contains("test"));

                if (hasTestAnnotation) {
                    TestCaseInfo info = new TestCaseInfo(
                            file.getAbsolutePath(),
                            method.getNameAsString(),
                            method.toString(),
                            annotations
                    );
                    testCases.add(info);
                }
            });
        }

        return testCases;
    }
}
