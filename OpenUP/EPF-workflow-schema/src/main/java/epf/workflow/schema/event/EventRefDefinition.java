package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;
import epf.workflow.schema.function.Invoke;

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
	@NotNull
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
	private Invoke invoke = Invoke.sync;

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
