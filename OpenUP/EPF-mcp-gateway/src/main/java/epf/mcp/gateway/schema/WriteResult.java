package epf.mcp.gateway.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Result from backend write operations.")
public class WriteResult {

	@JsonPropertyDescription("Error message on failure, None on success.")
	private String error;
	@JsonPropertyDescription("Absolute path of written file, None on failure.")
	private String path;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
