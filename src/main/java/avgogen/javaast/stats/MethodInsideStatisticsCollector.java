package avgogen.javaast.stats;

import com.github.javaparser.ast.expr.SimpleName;

public class MethodInsideStatisticsCollector {

    public MethodInsideStatisticsCollector(SimpleName className) {
        this.methodName = className.toString();
        parametersCount = 0;
        avgParameterNameLength = 0.0;
    }

    public void newParameter(SimpleName parameterName) {
        ++parametersCount;
        avgParameterNameLength += parameterName.toString().length();
    }

    String getMethodName() {
        return methodName;
    }

    int getParametersCount() {
        return parametersCount;
    }

    double getAvgParameterNameLength() {
        return avgParameterNameLength;
    }

    private String methodName;
    private int parametersCount;
    private double avgParameterNameLength;
    private int isEnum;
}
