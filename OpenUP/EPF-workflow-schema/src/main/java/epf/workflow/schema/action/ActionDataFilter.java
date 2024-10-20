package epf.workflow.schema.action;

import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class ActionDataFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	@Description("Workflow expression that filters state data that can be used by the action")
	private String fromStateData;

	@Column
	@Description("If set to false, action data results are not added/merged to state data. In this case 'results' and 'toStateData' should be ignored.")
	@DefaultValue("true")
	private Boolean useResults = true;

	@Column
	@Description("Workflow expression that filters the actions data results")
	private String results;
	
	@Column
	@Description("Workflow expression that selects a state data element to which the action results should be added/merged. If not specified denotes the top-level state data element. In case it is not specified and the result of the action is not an object, that result should be merged as the value of an automatically generated key. That key name will be the result of concatenating the action name with -output suffix.")
	private String toStateData;
	
	public String getFromStateData() {
		return fromStateData;
	}
	public void setFromStateData(String fromStateData) {
		this.fromStateData = fromStateData;
	}
	public Boolean isUseResults() {
		return useResults;
	}
	public void setUseResults(Boolean useResults) {
		this.useResults = useResults;
	}
	public String getResults() {
		return results;
	}
	public void setResults(String results) {
		this.results = results;
	}
	public String getToStateData() {
		return toStateData;
	}
	public void setToStateData(String toStateData) {
		this.toStateData = toStateData;
	}
}
