package epf.file.internal;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.ws.rs.core.PathSegment;

public class PathBuilder {
	
	private transient final String bucketName;
	
	private transient final String organization;
	
	private transient List<String> subPaths;
	
	private List<String> getPaths() {
		final List<String> paths = new ArrayList<>(subPaths);
		paths.add(0, organization);
		return paths;
	}

	public PathBuilder(final String bucketName, final String organization) {
		this.bucketName = bucketName;
		this.organization = organization;
	}
	
	public PathBuilder paths(final List<PathSegment> paths) {
		this.subPaths = paths
				.stream()
				.map(segment -> segment.getPath())
				.collect(Collectors.toList());
		return this;
	}

	public Path build() {
		return Path.of(bucketName, getPaths().toArray(new String[0]));
	}
	
	public String buildRelative() {
		return String.join("/", getPaths());
	}
}
