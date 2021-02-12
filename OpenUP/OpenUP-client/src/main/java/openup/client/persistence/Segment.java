package openup.client.persistence;

import javax.ws.rs.core.MultivaluedMap;

public class Segment {
	
	private String path;
	private MultivaluedMap<String, String> matrixParams;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public MultivaluedMap<String, String> getMatrixParams() {
		return matrixParams;
	}
	public void setMatrixParams(MultivaluedMap<String, String> matrixParams) {
		this.matrixParams = matrixParams;
	}
}
