/**
 * 
 */
package epf.file;

import java.nio.file.FileSystem;
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
	 * 
	 */
	private transient final FileSystem system;
	
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
	public PathBuilder(final String rootFolder, final FileSystem system) {
		this.rootFolder = rootFolder;
		this.system = system;
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
		return system.getPath(rootFolder, subPaths.toArray(new String[0]));
	}
	
	/**
	 * @return
	 */
	public String buildRelative() {
		return String.join("/", subPaths);
	}
}
