package epf.workflow.event.schema;

import javax.validation.constraints.NotNull;
import epf.workflow.function.schema.Invoke;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class EventRefDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String produceEventRef;
	
	/**
	 * 
	 */
	@Column
	private String consumeEventRef;
	
	/**
	 * 
	 */
	@Column
	private String consumeEventTimeout;
	
	/**
	 * 
	 */
	@Column
	private Object data;
	
	/**
	 * 
	 */
	@Column
	private Object contextAttributes;
	
	/**
	 * 
	 */
	@Column
	private Invoke invoke;

	public String getProduceEventRef() {
		return produceEventRef;
	}

	public void setProduceEventRef(String produceEventRef) {
		this.produceEventRef = produceEventRef;
	}

	public String getConsumeEventRef() {
		return consumeEventRef;
	}

	public void setConsumeEventRef(String consumeEventRef) {
		this.consumeEventRef = consumeEventRef;
	}

	public String getConsumeEventTimeout() {
		return consumeEventTimeout;
	}

	public void setConsumeEventTimeout(String consumeEventTimeout) {
		this.consumeEventTimeout = consumeEventTimeout;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getContextAttributes() {
		return contextAttributes;
	}

	public void setContextAttributes(Object contextAttributes) {
		this.contextAttributes = contextAttributes;
	}

	public Invoke getInvoke() {
		return invoke;
	}

	public void setInvoke(Invoke invoke) {
		this.invoke = invoke;
	}
}
