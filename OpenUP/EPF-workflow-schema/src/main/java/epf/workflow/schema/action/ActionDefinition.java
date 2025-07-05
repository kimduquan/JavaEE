package epf.workflow.schema.action;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.event.EventRefDefinition;
import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.function.adapter.StringOrFunctionRefDefinitionAdapter;
import epf.nosql.schema.StringOrArray;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.adapter.StringOrSubFlowRefDefinitionAdapter;
import epf.workflow.schema.error.ErrorHandlerReference;
import epf.workflow.schema.error.adapter.StringOrArrayErrorHandlerReferenceAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class ActionDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Unique Action name. Must follow the Serverless Workflow Naming Convention")
	private String name;
	
	@Column
	@Description("References a reusable function definition")
	@JsonbTypeAdapter(value = StringOrFunctionRefDefinitionAdapter.class)
	private StringOrObject<FunctionRefDefinition> functionRef;
	
	@Column
	@Description("References a produce and consume reusable event definitions")
	private EventRefDefinition eventRef;
	
	@Column
	@Description("References a workflow to be invoked")
	@JsonbTypeAdapter(value = StringOrSubFlowRefDefinitionAdapter.class)
	private StringOrObject<SubFlowRefDefinition> subFlowRef;
	
	@Column
	@Description("Defines the error handling policy to use")
	@JsonbTypeAdapter(value = StringOrArrayErrorHandlerReferenceAdapter.class)
	private StringOrArray<ErrorHandlerReference> onErrors;
	
	@Column
	@Description("Action data filter definition")
	private ActionDataFilter actionDataFilter;
	
	@Column
	@Description("Defines time periods workflow execution should sleep before / after function execution")
	private Sleep sleep;
	
	@Column
	@Description("Expression, if defined, must evaluate to true for this action to be performed. If false, action is disregarded")
	private String condition;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StringOrObject<FunctionRefDefinition> getFunctionRef() {
		return functionRef;
	}

	public void setFunctionRef(StringOrObject<FunctionRefDefinition> functionRef) {
		this.functionRef = functionRef;
	}

	public EventRefDefinition getEventRef() {
		return eventRef;
	}

	public void setEventRef(EventRefDefinition eventRef) {
		this.eventRef = eventRef;
	}

	public StringOrObject<SubFlowRefDefinition> getSubFlowRef() {
		return subFlowRef;
	}

	public void setSubFlowRef(StringOrObject<SubFlowRefDefinition> subFlowRef) {
		this.subFlowRef = subFlowRef;
	}

	public StringOrArray<ErrorHandlerReference> getOnErrors() {
		return onErrors;
	}

	public void setOnErrors(StringOrArray<ErrorHandlerReference> onErrors) {
		this.onErrors = onErrors;
	}

	public ActionDataFilter getActionDataFilter() {
		return actionDataFilter;
	}

	public void setActionDataFilter(ActionDataFilter actionDataFilter) {
		this.actionDataFilter = actionDataFilter;
	}

	public Sleep getSleep() {
		return sleep;
	}

	public void setSleep(Sleep sleep) {
		this.sleep = sleep;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
