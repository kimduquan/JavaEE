package epf.mcp.gateway.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Result from backend search operations.")
public class SearchResult {

	@JsonPropertyDescription("Error message on failure, None on success.")
	private String error;
}
