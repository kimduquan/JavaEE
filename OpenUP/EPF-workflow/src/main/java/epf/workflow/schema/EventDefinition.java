package epf.workflow.schema;

import javax.validation.constraints.NotNull;

/**
 * @author PC
 *
 */
public class EventDefinition {

	/**
	 * 
	 */
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	private String source;
	
	/**
	 * 
	 */
	private String type;
	
	/**
	 * 
	 */
	private Kind kind = Kind.consumed;
	
	/**
	 * 
	 */
	private Object[] correlation;
	
	/**
	 * 
	 */
	private boolean dataOnly = true;
	
	/**
	 * 
	 */
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

	public Object[] getCorrelation() {
		return correlation;
	}

	public void setCorrelation(Object[] correlation) {
		this.correlation = correlation;
	}

	public boolean isDataOnly() {
		return dataOnly;
	}

	public void setDataOnly(boolean dataOnly) {
		this.dataOnly = dataOnly;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
