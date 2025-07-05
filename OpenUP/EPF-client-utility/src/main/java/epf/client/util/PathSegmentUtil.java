package epf.client.util;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.PathSegment;

public class PathSegmentUtil implements PathSegment {
	
	private String path;
	
	private MultivaluedMap<String, String> matrixParameters;

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public MultivaluedMap<String, String> getMatrixParameters() {
		return matrixParameters;
	}

	public static PathSegment clone(final PathSegment pathSegment) {
		final PathSegmentUtil pathSegmentUtil = new PathSegmentUtil();
		pathSegmentUtil.path = pathSegment.getPath();
		pathSegmentUtil.matrixParameters = new MultivaluedHashMap<>(pathSegment.getMatrixParameters());
		return pathSegmentUtil;
	}
}
