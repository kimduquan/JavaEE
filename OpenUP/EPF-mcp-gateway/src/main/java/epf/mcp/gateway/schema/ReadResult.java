package epf.mcp.gateway.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Result from backend read operations.")
public class ReadResult {

	@JsonPropertyDescription("Error message on failure, None on success.")
	private String error;
	@JsonPropertyDescription("FileData dict on success, None on failure.")
	private FileData file_data;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public FileData getFile_data() {
		return file_data;
	}
	public void setFile_data(FileData file_data) {
		this.file_data = file_data;
	}
}
