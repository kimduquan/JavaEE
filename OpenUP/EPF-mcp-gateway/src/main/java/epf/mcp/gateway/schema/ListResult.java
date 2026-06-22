package epf.mcp.gateway.schema;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Result from backend list_files operations.")
public class ListResult {

	@JsonPropertyDescription("Error message on failure, None on success.")
	private String error;
	@JsonPropertyDescription("List of file info dicts on success, None on failure.")
	private List<FileInfo> entries;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<FileInfo> getEntries() {
		return entries;
	}
	public void setEntries(List<FileInfo> entries) {
		this.entries = entries;
	}
}
