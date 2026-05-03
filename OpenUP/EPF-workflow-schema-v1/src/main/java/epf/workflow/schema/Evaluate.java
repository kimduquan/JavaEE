package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Configures a workflow's runtime expression evaluation.")
public class Evaluate {

	@NotNull
	@JsonPropertyDescription("The language used for writting runtime expressions. Defaults to jq.")
	private String language = "jq";
	
	@NotNull
	@JsonPropertyDescription("The runtime expression evaluation mode. Defaults to strict.")
	private String mode = "strict";

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
