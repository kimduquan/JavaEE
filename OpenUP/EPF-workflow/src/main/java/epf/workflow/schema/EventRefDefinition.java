package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class EventRefDefinition {

	/**
	 * 
	 */
	private String produceEventRef;
	
	/**
	 * 
	 */
	private String consumeEventRef;
	
	/**
	 * 
	 */
	private String consumeEventTimeout;
	
	/**
	 * 
	 */
	private Object data;
	
	/**
	 * 
	 */
	private Object contextAttributes;
	
	/**
	 * 
	 */
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
