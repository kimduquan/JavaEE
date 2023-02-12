package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class SleepState extends State {

	/**
	 * 
	 */
	private String duration;
	/**
	 * 
	 */
	private Object transition;
	/**
	 * 
	 */
	private Object end;
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Object getTransition() {
		return transition;
	}
	public void setTransition(Object transition) {
		this.transition = transition;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
	}
}
