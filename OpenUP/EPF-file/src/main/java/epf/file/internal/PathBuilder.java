package epf.file.internal;

import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.ws.rs.core.PathSegment;

public class PathBuilder {
	
	private transient final FileSystem system;
	
	private transient final String rootFolder;
	
	private transient final Optional<String> tenant;
	
	private transient List<String> subPaths;
	
	private List<String> getPaths() {
		final List<String> paths = new ArrayList<>(subPaths);
		if(tenant.isPresent()) {
			paths.add(0, tenant.get());
		}
		return paths;
	}

	public PathBuilder(final FileSystem system, final String rootFolder, final String tenant) {
		this.rootFolder = rootFolder;
		this.system = system;
		this.tenant = Optional.ofNullable(tenant);
	}
	
	public PathBuilder paths(final List<PathSegment> paths) {
		this.subPaths = paths
				.stream()
				.map(segment -> segment.getPath())
				.collect(Collectors.toList());
		return this;
	}

	public Path build() {
		return system.getPath(rootFolder, getPaths().toArray(new String[0]));
	}
	
	public String buildRelative() {
		return String.join("/", getPaths());
	}
}
