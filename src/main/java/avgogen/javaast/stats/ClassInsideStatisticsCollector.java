package avgogen.javaast.stats;

import com.github.javaparser.ast.expr.SimpleName;

import java.util.ArrayList;
import java.util.List;

public class ClassInsideStatisticsCollector {

    public ClassInsideStatisticsCollector(SimpleName className, int type) {
        this.className = className.toString();
        this.isEnum = type;
        methodsCount = 0;
        fieldsCount = 0;
        avgMethodNameLength = 0.0;
        avgFieldNameLength = 0.0;
        methodsStatistics = new ArrayList<>();
    }

    public void newMethod(SimpleName methodName) {
        ++methodsCount;
        avgMethodNameLength += methodName.toString().length();
    }

    public void newField(SimpleName fieldName) {
        ++fieldsCount;
        avgFieldNameLength += fieldName.toString().length();
    }

    public void newMethodsStatistics(MethodInsideStatisticsCollector statisticsCollector) {
        methodsStatistics.add(statisticsCollector);
    }

    String getClassName() {
        return className;
    }

    int getMethodsCount() {
        return methodsCount;
    }

    int getFieldsCount() {
        return fieldsCount;
    }

    double getAvgMethodNameLength() {
        return avgMethodNameLength;
    }

    double getAvgFieldNameLength() {
        return avgFieldNameLength;
    }

    int getType() {
        return isEnum;
    }

    List<MethodInsideStatisticsCollector> getMethodsStatistics() {
        return methodsStatistics;
    }

    private String className;
    private int methodsCount;
    private int fieldsCount;
    private double avgMethodNameLength;
    private double avgFieldNameLength;
    private int isEnum;
    private List<MethodInsideStatisticsCollector> methodsStatistics;
}
