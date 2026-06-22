package epf.mcp.gateway.schema;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Result from backend find operations.")
public class FindResult {

	@JsonPropertyDescription("Error message on failure, None on success.")
	private String error;
	@JsonPropertyDescription("List of matching file info dicts on success, None on failure.")
	private List<FileInfo> matches;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<FileInfo> getMatches() {
		return matches;
	}
	public void setMatches(List<FileInfo> matches) {
		this.matches = matches;
	}
}
