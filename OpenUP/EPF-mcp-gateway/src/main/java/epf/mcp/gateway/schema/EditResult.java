package epf.mcp.gateway.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Result from backend edit operations.")
public class EditResult {

	@JsonPropertyDescription("Error message on failure, None on success.")
	private String error;
	@JsonPropertyDescription("Absolute path of edited file, None on failure.")
	private String path;
	@JsonPropertyDescription("Number of replacements made, None on failure.")
	private Integer occurrences;
	
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
	public Integer getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(Integer occurrences) {
		this.occurrences = occurrences;
	}
}
