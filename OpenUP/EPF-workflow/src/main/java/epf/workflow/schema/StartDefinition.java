package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class StartDefinition {

	/**
	 * 
	 */
	private String stateName;
	
	/**
	 * 
	 */
	private Object schedule;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Object getSchedule() {
		return schedule;
	}

	public void setSchedule(Object schedule) {
		this.schedule = schedule;
	}
}
