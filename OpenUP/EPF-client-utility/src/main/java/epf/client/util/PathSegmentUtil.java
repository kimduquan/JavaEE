package epf.client.util;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.PathSegment;

/**
 * @author PC
 *
 */
public class PathSegmentUtil implements PathSegment {
	
	/**
	 * 
	 */
	private String path;
	
	/**
	 * 
	 */
	private MultivaluedMap<String, String> matrixParameters;

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public MultivaluedMap<String, String> getMatrixParameters() {
		return matrixParameters;
	}

	/**
	 * @param pathSegment
	 * @return
	 */
	public static PathSegment clone(final PathSegment pathSegment) {
		final PathSegmentUtil pathSegmentUtil = new PathSegmentUtil();
		pathSegmentUtil.path = pathSegment.getPath();
		pathSegmentUtil.matrixParameters = new MultivaluedHashMap<>(pathSegment.getMatrixParameters());
		return pathSegmentUtil;
	}
}
