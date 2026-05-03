package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("A correlation is a link between events and data, established by mapping event attributes to specific data attributes, allowing for coordinated processing or handling based on event characteristics.")
public class Correlation {

	@NotNull
	@JsonPropertyDescription("A runtime expression used to extract the correlation value from the filtered event.")
	private String from;
	
	@JsonPropertyDescription("A constant or a runtime expression, if any, used to determine whether or not the extracted correlation value matches expectations. If not set, the first extracted value will be used as the correlation's expectation.")
	private String expect;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getExpect() {
		return expect;
	}

	public void setExpect(String expect) {
		this.expect = expect;
	}
}
