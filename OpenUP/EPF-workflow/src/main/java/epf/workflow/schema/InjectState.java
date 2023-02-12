package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class InjectState extends State {

	/**
	 * 
	 */
	private Object data;
	/**
	 * 
	 */
	private Object stateDataFilter;
	/**
	 * 
	 */
	private Object transition;
	/**
	 * 
	 */
	private String compensatedBy;
	/**
	 * 
	 */
	private boolean usedForCompensation;
	/**
	 * 
	 */
	private Object metadata;
	/**
	 * 
	 */
	private Object end;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(Object stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}
	public Object getTransition() {
		return transition;
	}
	public void setTransition(Object transition) {
		this.transition = transition;
	}
	public String getCompensatedBy() {
		return compensatedBy;
	}
	public void setCompensatedBy(String compensatedBy) {
		this.compensatedBy = compensatedBy;
	}
	public boolean isUsedForCompensation() {
		return usedForCompensation;
	}
	public void setUsedForCompensation(boolean usedForCompensation) {
		this.usedForCompensation = usedForCompensation;
	}
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
	public Object getEnd() {
		return end;
	}
	public void setEnd(Object end) {
		this.end = end;
	}
}
