package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;
import epf.workflow.schema.adapter.EndDefinitionAdapter;
import epf.workflow.schema.adapter.TransitionDefinitionAdapter;
import epf.workflow.schema.mapping.EndDefinitionConverter;
import epf.workflow.schema.mapping.TransitionDefinitionConverter;
import jakarta.nosql.Column;
import java.util.List;
import org.eclipse.jnosql.mapping.Convert;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@DiscriminatorValue(Type.FOREACH)
public class ForEachState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	@NotNull
	private List<ActionDefinition> actions;
	/**
	 * 
	 */
	@Column
	private WorkflowTimeoutDefinition timeouts;
	/**
	 * 
	 */
	@Column
	private StateDataFilters stateDataFilter;
	/**
	 * 
	 */
	@Column
	private List<ErrorDefinition> onErrors;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = TransitionDefinitionAdapter.class)
	@Convert(TransitionDefinitionConverter.class)
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
	private Boolean usedForCompensation	= false;
	/**
	 * 
	 */
	@Column
	private Object metadata;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = EndDefinitionAdapter.class)
	@Convert(EndDefinitionConverter.class)
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
	public List<ActionDefinition> getActions() {
		return actions;
	}
	public void setActions(List<ActionDefinition> actions) {
		this.actions = actions;
	}
	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}
	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}
	public StateDataFilters getStateDataFilter() {
		return stateDataFilter;
	}
	public void setStateDataFilter(StateDataFilters stateDataFilter) {
		this.stateDataFilter = stateDataFilter;
	}
	public List<ErrorDefinition> getOnErrors() {
		return onErrors;
	}
	public void setOnErrors(List<ErrorDefinition> onErrors) {
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
	public Boolean isUsedForCompensation() {
		return usedForCompensation;
	}
	public void setUsedForCompensation(Boolean usedForCompensation) {
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
