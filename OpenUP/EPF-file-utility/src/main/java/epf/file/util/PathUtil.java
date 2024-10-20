package epf.file.util;

import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author PC
 *
 */
public interface PathUtil {
	
	/**
	 * @param first
	 * @param more
	 * @return
	 */
	static Path of(final String first, final String... more) {
		return FileSystems.getDefault().getPath(first, more);
	}
	
	/**
	 * @param path
	 * @return
	 * @throws Exception
	 */
	static URI toURI(final Path path) throws Exception {
		final List<String> paths = new ArrayList<>();
		path.forEach(p -> paths.add(p.toString()));
		final String uri = paths.stream().collect(Collectors.joining("/"));
		return new URI(uri);
	}
	
	/**
	 * @param path
	 * @return
	 * @throws Exception
	 */
	static boolean isEmpty(final Path path) throws Exception {
		if(Files.isDirectory(path)) {
			return !Files.list(path).findAny().isPresent();
		}
		return false;
	}
}
