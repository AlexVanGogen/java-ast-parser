package avgogen.javaast.visitor;

import avgogen.javaast.stats.ClassInsideStatisticsCollector;
import avgogen.javaast.stats.MethodInsideStatisticsCollector;
import avgogen.javaast.stats.SummaryStatisticsCollector;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class VisitorImpl extends VoidVisitorAdapter<Object> {

    private List<ClassOrInterfaceDeclaration> currentVisitedClass = new ArrayList<>();
    private List<EnumDeclaration> currentVisitedEnum = new ArrayList<>();
    private List<ClassInsideStatisticsCollector> statisticsCollectorsStack = new ArrayList<>();
    private List<Integer> entriesStack = new ArrayList<>();                                 // 0 is for class, 1 is for enum
    private List<MethodDeclaration> currentVisitedMethod = new ArrayList<>();
    private List<MethodInsideStatisticsCollector> methodStatisticsCollectorsStack = new ArrayList<>();

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        entriesStack.add(0);
        currentVisitedClass.add(n);
        SimpleName declName = n.getName();
        statisticsCollectorsStack.add(new ClassInsideStatisticsCollector(declName, 0));
        if (n.isInterface())
            SummaryStatisticsCollector.newInterface(declName);
        else {
            SummaryStatisticsCollector.newClass(declName);
        }
        super.visit(n, arg);
        entriesStack.remove(entriesStack.size() - 1);
        currentVisitedClass.remove(currentVisitedClass.size()-1);
        SummaryStatisticsCollector.newClassStatistics(statisticsCollectorsStack.remove(statisticsCollectorsStack.size() - 1));
    }


    @Override
    public void visit(EnumDeclaration n, Object arg) {
        entriesStack.add(1);
        currentVisitedEnum.add(n);
        SimpleName declName = n.getName();
        statisticsCollectorsStack.add(new ClassInsideStatisticsCollector(declName, 1));
        SummaryStatisticsCollector.newEnum(declName);
        super.visit(n, arg);
        entriesStack.remove(entriesStack.size() - 1);
        currentVisitedEnum.remove(currentVisitedEnum.size()-1);
        SummaryStatisticsCollector.newClassStatistics(statisticsCollectorsStack.remove(statisticsCollectorsStack.size() - 1));
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        currentVisitedMethod.add(n);
        SimpleName declName = n.getName();
        methodStatisticsCollectorsStack.add(new MethodInsideStatisticsCollector(declName));
        SummaryStatisticsCollector.newMethod(declName);
        super.visit(n, arg);
        currentVisitedMethod.remove(currentVisitedMethod.size() - 1);
        ClassInsideStatisticsCollector collector = statisticsCollectorsStack.remove(statisticsCollectorsStack.size() - 1);
        collector.newMethod(declName);
        collector.newMethodsStatistics(methodStatisticsCollectorsStack.remove(methodStatisticsCollectorsStack.size() - 1));
        statisticsCollectorsStack.add(collector);
    }

    @Override
    public void visit(Parameter n, Object arg) {
        super.visit(n, arg);
        SimpleName declName = n.getName();
        SummaryStatisticsCollector.newParameter(declName);
        if (methodStatisticsCollectorsStack.size() > 0) {             // method input parameters
            MethodInsideStatisticsCollector collector = methodStatisticsCollectorsStack.remove(methodStatisticsCollectorsStack.size() - 1);
            collector.newParameter(declName);
            methodStatisticsCollectorsStack.add(collector);
        } else {                                                      // class fields
            ClassInsideStatisticsCollector collector = statisticsCollectorsStack.remove(statisticsCollectorsStack.size() - 1);
            collector.newField(declName);
            statisticsCollectorsStack.add(collector);
        }
    }
}
