package avgogen.javaast.run;

import avgogen.javaast.stats.SummaryStatisticsCollector;
import avgogen.javaast.util.filefinder.JavaFinder;
import avgogen.javaast.visitor.VisitorImpl;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static void getClasses(Path projectPath) {
        new JavaFinder(path -> {
            try {
                CompilationUnit compilationUnit = JavaParser.parse(path);
                compilationUnit.accept(new VisitorImpl(), null);
            } catch (IOException ignored) {}
        }, path -> path.toString().endsWith(".java")).iterate(projectPath);
        try {
            final PrintStream out = new PrintStream("summary.txt");
            SummaryStatisticsCollector.summarize(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SummaryStatisticsCollector summaryStatisticsCollector = new SummaryStatisticsCollector();
        Path projectPath = Paths.get("src\\main\\java");
        getClasses(projectPath);
    }
}
