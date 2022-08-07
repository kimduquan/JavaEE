package epf.file;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
	 * 
	 */
	private transient final Optional<String> tenant;
	
	/**
	 * Paths
	 */
	private transient List<String> subPaths;
	
	/**
	 * @return
	 */
	private List<String> getPaths() {
		final List<String> paths = new ArrayList<>(subPaths);
		if(tenant.isPresent()) {
			paths.add(0, tenant.get());
		}
		return paths;
	}

	/**
	 * @param system
	 * @param rootFolder
	 * @param tenant
	 */
	public PathBuilder(final FileSystem system, final String rootFolder, final String tenant) {
		this.rootFolder = rootFolder;
		this.system = system;
		this.tenant = Optional.ofNullable(tenant);
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
		return system.getPath(rootFolder, getPaths().toArray(new String[0]));
	}
	
	/**
	 * @return
	 */
	public String buildRelative() {
		return String.join("/", getPaths());
	}
}
