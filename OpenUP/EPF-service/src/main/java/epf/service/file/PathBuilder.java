/**
 * 
 */
package epf.service.file;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.PathSegment;

/**
 * @author PC
 *
 */
public class PathBuilder {
	
	/**
	 * The configured root folder 
	 */
	private transient final String rootFolder;
	
	/**
	 * Paths
	 */
	private transient List<String> subPaths;

	/**
	 * 
	 */
	public PathBuilder(final String rootFolder) {
		this.rootFolder = rootFolder;
	}
	
	/**
	 * @param paths
	 * @return
	 */
	public PathBuilder paths(final List<PathSegment> paths) {
		this.subPaths = paths
				.stream()
				.map(segment -> segment.getPath())
				.collect(Collectors.toList());
		return this;
	}

	/**
	 * Build the path
	 * @return
	 */
	public Path build() {
		return Path.of(rootFolder, subPaths.toArray(new String[0]));
	}
}
