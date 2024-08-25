package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.CorrelationDefinition;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class EventDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@NotNull
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String source;
	
	/**
	 * 
	 */
	@Column
	@NotNull
	private String type;
	
	/**
	 * 
	 */
	@Column
	private Kind kind = Kind.consumed;
	
	/**
	 * 
	 */
	@Column
	private List<CorrelationDefinition> correlation;
	
	/**
	 * 
	 */
	@Column
	private Boolean dataOnly = true;
	
	/**
	 * 
	 */
	@Column
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

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
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
}
