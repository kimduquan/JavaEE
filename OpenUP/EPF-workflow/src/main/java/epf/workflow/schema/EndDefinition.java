package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class EndDefinition {

	/**
	 * 
	 */
	private boolean terminate;
	/**
	 * 
	 */
	private ProducedEventDefinition[] produceEvents;
	/**
	 * 
	 */
	private boolean compensate = false;
	/**
	 * 
	 */
	private Object continueAs;
	
	public boolean isTerminate() {
		return terminate;
	}
	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}
	public ProducedEventDefinition[] getProduceEvents() {
		return produceEvents;
	}
	public void setProduceEvents(ProducedEventDefinition[] produceEvents) {
		this.produceEvents = produceEvents;
	}
	public boolean isCompensate() {
		return compensate;
	}
	public void setCompensate(boolean compensate) {
		this.compensate = compensate;
	}
	public Object getContinueAs() {
		return continueAs;
	}
	public void setContinueAs(Object continueAs) {
		this.continueAs = continueAs;
	}
}
