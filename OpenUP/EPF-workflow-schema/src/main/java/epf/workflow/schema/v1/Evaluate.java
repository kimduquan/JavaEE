package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Configures a workflow's runtime expression evaluation.")
public class Evaluate {

	@NotNull
	@Description("The language used for writting runtime expressions.")
	private String language = "jq";
	
	@NotNull
	@Description("The runtime expression evaluation mode.")
	private String mode;

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
