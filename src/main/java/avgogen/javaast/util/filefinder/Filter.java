package avgogen.javaast.util.filefinder;

import java.nio.file.Path;

public interface Filter {
    boolean match(Path path);
}
