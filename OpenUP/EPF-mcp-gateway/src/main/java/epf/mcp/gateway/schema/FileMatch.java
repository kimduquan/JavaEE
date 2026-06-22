package epf.mcp.gateway.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("A single match from a file search.")
public class FileMatch {

	@JsonPropertyDescription("Path to the file containing the match.")
	private String path;
	@JsonPropertyDescription("1-indexed line number of the match.")
	private Integer line;
	@JsonPropertyDescription("Content of the matching line.")
	private String text;
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
