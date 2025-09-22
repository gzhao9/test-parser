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
                System.err.println("解析失败: " + file.getAbsolutePath());
                continue;
            }

            cu.findAll(MethodDeclaration.class).forEach(method -> {
                // 筛选注解包含 "test"
                List<String> annotations = method.getAnnotations()
                        .stream()
                        .map(a -> a.getNameAsString())
                        .filter(a -> a.toLowerCase().contains("test"))
                        .collect(Collectors.toList());

                if (!annotations.isEmpty()) {
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
