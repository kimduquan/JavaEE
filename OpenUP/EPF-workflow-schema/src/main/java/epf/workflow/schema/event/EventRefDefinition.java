package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.Map;
import jakarta.nosql.Embeddable;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.function.Invoke;

@Embeddable
public class EventRefDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	private String produceEventRef;
	
	@Column
	@NotNull
	private String consumeEventRef;
	
	@Column
	private String consumeEventTimeout;
	
	@Column
	private StringOrObject<Object> data;
	
	@Column
	private Map<String, Object> contextAttributes;
	
	@Column
	private Invoke invoke = Invoke.sync;

	public String getProduceEventRef() {
		return produceEventRef;
	}

	public void setProduceEventRef(final String produceEventRef) {
		this.produceEventRef = produceEventRef;
	}

	public String getConsumeEventRef() {
		return consumeEventRef;
	}

	public void setConsumeEventRef(final String consumeEventRef) {
		this.consumeEventRef = consumeEventRef;
	}

	public String getConsumeEventTimeout() {
		return consumeEventTimeout;
	}

	public void setConsumeEventTimeout(final String consumeEventTimeout) {
		this.consumeEventTimeout = consumeEventTimeout;
	}

	public StringOrObject<Object> getData() {
		return data;
	}

	public void setData(final StringOrObject<Object> data) {
		this.data = data;
	}

	public Map<String, Object> getContextAttributes() {
		return contextAttributes;
	}

	public void setContextAttributes(final Map<String, Object> contextAttributes) {
		this.contextAttributes = contextAttributes;
	}

	public Invoke getInvoke() {
		return invoke;
	}

	public void setInvoke(final Invoke invoke) {
		this.invoke = invoke;
	}
}
