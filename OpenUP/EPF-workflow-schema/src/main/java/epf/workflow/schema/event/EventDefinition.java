package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.CorrelationDefinition;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class EventDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Unique event name. Must follow the Serverless Workflow Naming Convention")
	private String name;
	
	@Column
	@Description("CloudEvent source. If not set when producing an event, runtimes are expected to use a default value, such as https://serverlessworkflow.io in order to comply with the CloudEvent spec constraints)")
	private String source;
	
	@NotNull
	@Column
	@Description("CloudEvent type")
	private String type;
	
	@Column
	@Description("Define event correlation rules for this event. Only used for consumed events")
	private List<CorrelationDefinition> correlation;
	
	@Column
	@Description("If true (default value), only the Event payload is accessible to consuming Workflow states. If false, both event payload and context attributes should be accessible")
	@DefaultValue("true")
	private Boolean dataOnly = true;
	
	@Column
	@Description("Metadata information")
	private Map<String, String> metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<CorrelationDefinition> getCorrelation() {
		return correlation;
	}

	public void setCorrelation(List<CorrelationDefinition> correlation) {
		this.correlation = correlation;
	}

	public Boolean isDataOnly() {
		return dataOnly;
	}

	public void setDataOnly(Boolean dataOnly) {
		this.dataOnly = dataOnly;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public Boolean getDataOnly() {
		return dataOnly;
	}
}
