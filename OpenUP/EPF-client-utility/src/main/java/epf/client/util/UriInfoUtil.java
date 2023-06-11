package epf.client.util;

import java.net.URI;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

/**
 * @author PC
 *
 */
public class UriInfoUtil implements UriInfo {
	
	/**
	 * 
	 */
	private String path;
	
	/**
	 * 
	 */
	private List<PathSegment> pathSegments;
	
	/**
	 * 
	 */
	private URI requestUri;
	
	/**
	 * 
	 */
	private URI absolutePath;
	
	/**
	 * 
	 */
	private URI baseUri;
	
	/**
	 * 
	 */
	private MultivaluedMap<String, String> pathParameters;
	
	/**
	 * 
	 */
	private MultivaluedMap<String, String> queryParameters;
	
	/**
	 * 
	 */
	private List<String> matchedURIs;
	
	/**
	 * 
	 */
	private List<Object> matchedResources;

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getPath(boolean decode) {
		return path;
	}

	@Override
	public List<PathSegment> getPathSegments() {
		return pathSegments;
	}

	@Override
	public List<PathSegment> getPathSegments(boolean decode) {
		return pathSegments;
	}

	@Override
	public URI getRequestUri() {
		return requestUri;
	}

	@Override
	public UriBuilder getRequestUriBuilder() {
		return UriBuilder.fromUri(requestUri);
	}

	@Override
	public URI getAbsolutePath() {
		return absolutePath;
	}

	@Override
	public UriBuilder getAbsolutePathBuilder() {
		return UriBuilder.fromUri(absolutePath);
	}

	@Override
	public URI getBaseUri() {
		return baseUri;
	}

	@Override
	public UriBuilder getBaseUriBuilder() {
		return UriBuilder.fromUri(baseUri);
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters() {
		return pathParameters;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters(boolean decode) {
		return pathParameters;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters() {
		return queryParameters;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
		return queryParameters;
	}

	@Override
	public List<String> getMatchedURIs() {
		return matchedURIs;
	}

	@Override
	public List<String> getMatchedURIs(boolean decode) {
		return matchedURIs;
	}

	@Override
	public List<Object> getMatchedResources() {
		return matchedResources;
	}

	@Override
	public URI resolve(URI uri) {
		return baseUri.resolve(uri);
	}

	@Override
	public URI relativize(URI uri) {
		return baseUri.relativize(uri);
	}
	
	/**
	 * @param uriInfo
	 * @return
	 */
	public static UriInfo clone(final UriInfo uriInfo) {
		final UriInfoUtil newUriInfo = new UriInfoUtil();
		newUriInfo.absolutePath = uriInfo.getAbsolutePath();
		newUriInfo.baseUri = uriInfo.getBaseUri();
		newUriInfo.matchedURIs = new ArrayList<>(uriInfo.getMatchedURIs());
		newUriInfo.path = uriInfo.getPath();
		newUriInfo.pathParameters = new MultivaluedHashMap<>(uriInfo.getPathParameters());
		newUriInfo.pathSegments = new ArrayList<>();
		uriInfo.getPathSegments().forEach(pathSegment -> newUriInfo.pathSegments.add(PathSegmentUtil.clone(pathSegment)));
		newUriInfo.queryParameters = new MultivaluedHashMap<>(uriInfo.getQueryParameters());
		newUriInfo.requestUri = uriInfo.getRequestUri();
		return newUriInfo;
	}
}
