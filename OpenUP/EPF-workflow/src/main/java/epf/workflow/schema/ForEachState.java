package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ForEachState extends State {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String inputCollection;
	/**
	 * 
	 */
	@Column
	private String outputCollection;
	/**
	 * 
	 */
	@Column
	private String iterationParam;
	/**
	 * 
	 */
	@Column
	private Object batchSize;
	/**
	 * 
	 */
	@Column
	private Mode mode = Mode.parallel;
	/**
	 * 
	 */
	@Column
	private ActionDefinition[] actions;
	/**
	 * 
	 */
	@Column
	private Object timeouts;
	/**
	 * 
	 */
	@Column
	private StateDataFilters stateDataFilter;
	/**
	 * 
	 */
	@Column
	private ErrorDefinition[] onErrors;
	/**
	 * 
	 */
	@Column
	private Object transition;
	/**
	 * 
	 */
	@Column
	private String compensatedBy;
	/**
	 * 
	 */
	@Column
	private boolean usedForCompensation	= false;
	/**
	 * 
	 */
	@Column
	private Object metadata;
	/**
	 * 
	 */
	@Column
	private Object end;
	
	public String getInputCollection() {
		return inputCollection;
	}
	public void setInputCollection(String inputCollection) {
		this.inputCollection = inputCollection;
	}
	public String getOutputCollection() {
		return outputCollection;
	}
	public void setOutputCollection(String outputCollection) {
		this.outputCollection = outputCollection;
	}
	public String getIterationParam() {
		return iterationParam;
	}
	public void setIterationParam(String iterationParam) {
		this.iterationParam = iterationParam;
	}
	public Object getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(Object batchSize) {
		this.batchSize = batchSize;
	}
	public Mode getMode() {
		return mode;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public ActionDefinition[] getActions() {
		return actions;
	}
	public void setActions(ActionDefinition[] actions) {
		this.actions = actions;
	}
	public Object getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(Object timeouts) {
		this.timeouts = timeouts;
	}
	public StateDataFilters getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(StateDataFilters stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}
	public ErrorDefinition[] getOnErrors() {
		return onErrors;
	}
	public void setOnErrors(ErrorDefinition[] onErrors) {
		this.onErrors = onErrors;
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
