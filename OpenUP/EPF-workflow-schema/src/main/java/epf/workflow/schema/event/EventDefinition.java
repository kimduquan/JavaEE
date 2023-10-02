package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.CorrelationDefinition;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class EventDefinition {

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
	private CorrelationDefinition[] correlation;
	
	/**
	 * 
	 */
	@Column
	private Boolean dataOnly = true;
	
	/**
	 * 
	 */
	@Column
	private Object metadata;

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

	public CorrelationDefinition[] getCorrelation() {
		return correlation;
	}

	public void setCorrelation(CorrelationDefinition[] correlation) {
		this.correlation = correlation;
	}

	public Boolean isDataOnly() {
		return dataOnly;
	}

	public void setDataOnly(Boolean dataOnly) {
		this.dataOnly = dataOnly;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
