package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class TransitionDefinition {

	/**
	 * 
	 */
	private String nextState;
	/**
	 * 
	 */
	private boolean compensate = false;
	/**
	 * 
	 */
	private ProducedEventDefinition[] produceEvents;
	
	public String getNextState() {
		return nextState;
	}
	public void setNextState(String nextState) {
		this.nextState = nextState;
	}
	public boolean isCompensate() {
		return compensate;
	}
	public void setCompensate(boolean compensate) {
		this.compensate = compensate;
	}
	public ProducedEventDefinition[] getProduceEvents() {
		return produceEvents;
	}
	public void setProduceEvents(ProducedEventDefinition[] produceEvents) {
		this.produceEvents = produceEvents;
	}
}
