package epf.query.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("the single result.")
public class SingleResult {
	
	@JsonPropertyDescription("the result, or null if there is no result")
	private Object singleResult;

	public Object getSingleResult() {
		return singleResult;
	}

	public void setSingleResult(Object singleResult) {
		this.singleResult = singleResult;
	}
}
