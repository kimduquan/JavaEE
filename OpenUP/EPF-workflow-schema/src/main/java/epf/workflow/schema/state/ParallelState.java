package epf.workflow.schema.state;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.adapter.BooleanOrEndDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrTransitionDefinitionAdapter;
import epf.workflow.schema.util.BooleanOrObject;
import epf.workflow.schema.util.StringOrNumber;
import epf.workflow.schema.util.StringOrObject;
import jakarta.nosql.Column;
import java.util.List;
import java.util.Map;
import org.eclipse.jnosql.mapping.DiscriminatorValue;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
@DiscriminatorValue(Type.PARALLEL)
public class ParallelState extends State {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private List<ParallelStateBranch> branches;
	/**
	 * 
	 */
	@Column
	private CompletionType completionType = CompletionType.allOf;
	/**
	 * 
	 */
	@Column
	private StringOrNumber numCompleted;
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
	@JsonbTypeAdapter(value = StringOrTransitionDefinitionAdapter.class)
	private StringOrObject<TransitionDefinition> transition;
	/**
	 * 
	 */
	@Column
	private String compensatedBy;
	/**
	 * 
	 */
	@Column
	private Boolean usedForCompensation = false;
	/**
	 * 
	 */
	@Column
	private Map<String, String> metadata;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = BooleanOrEndDefinitionAdapter.class)
	private BooleanOrObject<EndDefinition> end;
	
	/**
	 * 
	 */
	public ParallelState() {
		setType_(Type.parallel);
	}
	
	public List<ParallelStateBranch> getBranches() {
		return branches;
	}
	public void setBranches(List<ParallelStateBranch> branches) {
		this.branches = branches;
	}
	public CompletionType getCompletionType() {
		return completionType;
	}
	public void setCompletionType(CompletionType completionType) {
		this.completionType = completionType;
	}
	public StringOrNumber getNumCompleted() {
		return numCompleted;
	}
	public void setNumCompleted(StringOrNumber numCompleted) {
		this.numCompleted = numCompleted;
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
	public StringOrObject<TransitionDefinition> getTransition() {
		return transition;
	}
	public void setTransition(StringOrObject<TransitionDefinition> transition) {
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
	public Map<String, String> getMetadata() {
		return metadata;
	}
	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}
	public BooleanOrObject<EndDefinition> getEnd() {
		return end;
	}
	public void setEnd(BooleanOrObject<EndDefinition> end) {
		this.end = end;
	}
}
