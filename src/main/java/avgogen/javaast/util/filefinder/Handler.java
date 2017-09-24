package avgogen.javaast.util.filefinder;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public interface Handler {
    void handle(Path path) throws FileNotFoundException;
}
