package epf.mcp.gateway.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Data structure for storing file contents with metadata.")
public class FileData {

	@JsonPropertyDescription("File content as a plain string (utf-8 text or base64-encoded binary).")
	private String content;
	@JsonPropertyDescription("Content encoding: `\"utf-8\"` for text, `\"base64\"` for binary.")
	private String encoding;
	@JsonPropertyDescription("ISO 8601 timestamp of file creation.")
	private String created_at;
	@JsonPropertyDescription("ISO 8601 timestamp of last modification.")
	private String modified_at;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getModified_at() {
		return modified_at;
	}
	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}
}
