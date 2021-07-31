/**
 * 
 */
package epf.file;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.core.PathSegment;
import epf.util.file.PathUtil;

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
		return PathUtil.of(rootFolder, subPaths.toArray(new String[0]));
	}
	
	/**
	 * @return
	 */
	public String buildRelative() {
		return String.join("/", subPaths);
	}
}
