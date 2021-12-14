package epf.file.bundle;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.osgi.service.component.annotations.*;
import epf.file.FileSystem;

@Component
public class FileSystemImpl implements FileSystem {

	@Override
	public Path getPath(final String first, final String... more) {
		return Paths.get(first, more);
	}
}
