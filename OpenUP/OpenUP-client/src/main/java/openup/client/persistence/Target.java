package openup.client.persistence;

import java.net.URI;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

public class Target {
	
	private URI uri;
	private List<Segment> paths;
	private MultivaluedMap<String, String> queryParams;
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public List<Segment> getPaths() {
		return paths;
	}
	public void setPaths(List<Segment> paths) {
		this.paths = paths;
	}
	public MultivaluedMap<String, String> getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(MultivaluedMap<String, String> queryParams) {
		this.queryParams = queryParams;
	}
}
