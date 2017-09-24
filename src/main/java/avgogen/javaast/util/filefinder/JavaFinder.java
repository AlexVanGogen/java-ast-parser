package avgogen.javaast.util.filefinder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaFinder {

    public JavaFinder(Handler handler, Filter filter) {
        this.handler = handler;
        this.filter = filter;
    }

    public void iterate(Path path) {
        if (Files.isRegularFile(path) && Files.isReadable(path) && filter.match(path)) {
            try {
                handler.handle(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                for (Path p : directoryStream)
                    iterate(p);
            } catch (IOException ignored) {}
        }
    }

    private Handler handler;
    private Filter filter;
}
