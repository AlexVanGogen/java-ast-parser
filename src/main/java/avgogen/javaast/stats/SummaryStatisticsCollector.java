package avgogen.javaast.stats;


import com.github.javaparser.ast.expr.SimpleName;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class SummaryStatisticsCollector {

    public SummaryStatisticsCollector() {
        classesCount = 0;
        interfacesCount = 0;
        methodsCount = 0;
        enumsCount = 0;
        parametersCount = 0;
        avgClassNameLength = 0.0;
        avgInterfaceNameLength = 0.0;
        avgMethodNameLength = 0.0;
        avgEnumNameLength = 0.0;
        avgParameterNameLength = 0.0;
        classesStatistics = new ArrayList<>();
    }

    public static void summarize() {
        summarize(System.out);
    }

    public static void summarize(PrintStream out) {
        out.println("Summary:");
        out.println("\tTotal classes: " + classesCount);
        out.println("\tTotal interfaces: " + interfacesCount);
        out.println("\tTotal enums: " + enumsCount);
        out.println("\tTotal methods: " + methodsCount);
        out.println("\tTotal parameters: " + parametersCount);
        classesStatistics.forEach(stats -> {
            if (stats.getType() == 0) {
                out.print("\t\tClass [" + stats.getClassName() + "]: " + stats.getFieldsCount() + " fields");
                if (stats.getFieldsCount() > 0)
                    out.println(" with average name lendth " + stats.getAvgFieldNameLength() / stats.getFieldsCount());
                else
                    out.println();
                out.print("\t\t\t" + stats.getMethodsCount() +
                        " methods");
                if (stats.getMethodsCount() > 0)
                    out.println(" with average name length " + stats.getAvgMethodNameLength() / stats.getMethodsCount());
                else
                    out.println();
                stats.getMethodsStatistics().forEach(stat -> {
                    out.print("\t\t\tMethod [" + stat.getMethodName() + "]: " + stat.getParametersCount() + " parameters");
                    if (stat.getParametersCount() > 0)
                        out.println(" with average name length " + stat.getAvgParameterNameLength() / stat.getParametersCount());
                    else
                        out.println();
                });
            }
        });
        classesStatistics.forEach(stats -> {
            if (stats.getType() == 1) {
                out.print("\t\tEnum [" + stats.getClassName() + "]: " + stats.getMethodsCount() +
                        " methods");
                if (stats.getMethodsCount() > 0)
                    out.println(" with average name length " + stats.getAvgMethodNameLength() / stats.getMethodsCount());
                else
                    out.println();
            }
        });
        out.println("\nAverage statistics");
        if (classesCount != 0) {
            out.println("\tAverage class name length: " + avgClassNameLength / classesCount);
        }
        if (interfacesCount != 0) {
            out.println("\tAverage interface name length: " + avgInterfaceNameLength / interfacesCount);
        }
        if (methodsCount != 0) {
            out.println("\tAverage method name length: " + avgMethodNameLength / methodsCount);
        }
        if (enumsCount != 0) {
            out.println("\tAverage enum name length: " + avgEnumNameLength / enumsCount);
        }
        if (parametersCount != 0) {
            out.println("\tAverage parameter name length: " + avgParameterNameLength / parametersCount);
        }
    }

    public static void newClass(SimpleName className) {
        ++classesCount;
        avgClassNameLength += className.toString().length();
    }

    public static void newInterface(SimpleName interfaceName) {
        ++interfacesCount;
        avgInterfaceNameLength += interfaceName.toString().length();
    }

    public static void newMethod(SimpleName methodName) {
        ++methodsCount;
        avgMethodNameLength += methodName.toString().length();
    }

    public static void newEnum(SimpleName methodName) {
        ++enumsCount;
        avgEnumNameLength += methodName.toString().length();
    }

    public static void newParameter(SimpleName parameterName) {
        ++parametersCount;
        avgParameterNameLength += parameterName.toString().length();
    }

    public static void newClassStatistics(ClassInsideStatisticsCollector statisticsCollector) {
        classesStatistics.add(statisticsCollector);
    }

    private static int classesCount;
    private static int interfacesCount;
    private static int methodsCount;
    private static int enumsCount;
    private static int parametersCount;
    private static double avgClassNameLength;
    private static double avgInterfaceNameLength;
    private static double avgMethodNameLength;
    private static double avgEnumNameLength;
    private static double avgParameterNameLength;
    private static List<ClassInsideStatisticsCollector> classesStatistics;
}
