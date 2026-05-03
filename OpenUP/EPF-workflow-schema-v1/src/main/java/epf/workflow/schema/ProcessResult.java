package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Describes the result of a process.")
public class ProcessResult {

	@NotNull
	@JsonPropertyDescription("The process's exit code.")
	private Integer code;
	
	@NotNull
	@JsonPropertyDescription("The process's STDOUT output.")
	private String stdout;
	
	@NotNull
	@JsonPropertyDescription("The process's STDERR output.")
	private String stderr;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStdout() {
		return stdout;
	}

	public void setStdout(String stdout) {
		this.stdout = stdout;
	}

	public String getStderr() {
		return stderr;
	}

	public void setStderr(String stderr) {
		this.stderr = stderr;
	}
}
