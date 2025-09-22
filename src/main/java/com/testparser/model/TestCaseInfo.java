package com.testparser.model;

import java.util.List;

public class TestCaseInfo {
    /** 测试用例所在的文件路径 */
    private String filePath;

    /** 测试用例的方法名 */
    private String methodName;

    /** 测试用例的源码（方法体完整代码） */
    private String sourceCode;

    /** 测试方法上的注解（例如 @Test, @ParameterizedTest 等） */
    private List<String> annotations;

    public TestCaseInfo(String filePath, String methodName, String sourceCode, List<String> annotations) {
        this.filePath = filePath;
        this.methodName = methodName;
        this.sourceCode = sourceCode;
        this.annotations = annotations;
    }

    // getter & setter
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    @Override
    public String toString() {
        return "TestCaseInfo{" +
                "filePath='" + filePath + '\'' +
                ", methodName='" + methodName + '\'' +
                ", annotations=" + annotations +
                '}';
    }
}
