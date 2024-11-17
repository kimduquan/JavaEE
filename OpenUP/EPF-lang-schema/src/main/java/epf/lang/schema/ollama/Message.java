package epf.lang.schema.ollama;

import java.io.Serializable;
import java.util.List;

/**
 * 
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Role role;
	private String content;
	private List<ToolCall> tool_calls;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<ToolCall> getTool_calls() {
		return tool_calls;
	}
	public void setTool_calls(List<ToolCall> tool_calls) {
		this.tool_calls = tool_calls;
	}
}
