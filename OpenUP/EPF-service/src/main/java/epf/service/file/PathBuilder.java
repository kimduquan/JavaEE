/**
 * 
 */
package epf.service.file;

import java.nio.file.Path;
import java.security.Principal;
import java.util.ArrayList;
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
	 * The user folder
	 */
	private transient String userFolder;
	
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
	 * Set current security principal
	 * @param principal
	 * @return
	 */
	public PathBuilder principal(final Principal principal) {
		userFolder = principal.getName();
		return this;
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
		final List<String> buildPaths = new ArrayList<>();
		if(userFolder != null) {
			buildPaths.add(userFolder);
		}
		buildPaths.addAll(subPaths);
		return Path.of(rootFolder, buildPaths.toArray(new String[0]));
	}
}
