package epf.workflow.schema.event;

import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class EventDataFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	@Description("If set to false, event payload is not added/merged to state data. In this case 'data' and 'toStateData' should be ignored.")
	@DefaultValue("true")
	private Boolean useData = true;
	
	@Column
	@Description("Workflow expression that filters the event data (payload)")
	private String data;
	
	@Column
	@Description("Workflow expression that selects a state data element to which the action results should be added/merged into. If not specified denotes the top-level state data element")
	private String toStateData;
	
	public Boolean isUseData() {
		return useData;
	}
	public void setUseData(Boolean useData) {
		this.useData = useData;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getToStateData() {
		return toStateData;
	}
	public void setToStateData(String toStateData) {
		this.toStateData = toStateData;
	}
}
