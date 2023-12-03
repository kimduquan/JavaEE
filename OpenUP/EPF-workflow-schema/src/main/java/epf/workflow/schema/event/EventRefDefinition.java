package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import epf.workflow.schema.function.Invoke;
import epf.workflow.schema.util.StringOrObject;

/**
 * @author PC
 *
 */
@Embeddable
public class EventRefDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private StringOrObject<Object> data;
	
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

	public StringOrObject<Object> getData() {
		return data;
	}

	public void setData(StringOrObject<Object> data) {
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
