package epf.mcp.gateway.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("""
Structured file listing info.

Minimal contract used across backends. Only `path` is required.
Other fields are best-effort and may be absent depending on backend.
""")
public class FileInfo {

	@JsonPropertyDescription("Absolute or relative file path.")
	private String path;
	@JsonPropertyDescription("Whether the entry is a directory.")
	private Boolean is_dir;
	@JsonPropertyDescription("File size in bytes (approximate).")
	private Integer size;
	@JsonPropertyDescription("ISO 8601 timestamp of last modification, if known.")
	private String modified_at;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Boolean getIs_dir() {
		return is_dir;
	}
	public void setIs_dir(Boolean is_dir) {
		this.is_dir = is_dir;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getModified_at() {
		return modified_at;
	}
	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}
}
