package epf.file.internal;

import java.util.ArrayList;
import java.util.Arrays;
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
		paths.add(0, bucketName);
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
	
	public PathBuilder paths(final String...paths) {
		this.subPaths = Arrays.asList(paths);
		return this;
	}
	
	public String build() {
		return String.join("/", getPaths());
	}
}
