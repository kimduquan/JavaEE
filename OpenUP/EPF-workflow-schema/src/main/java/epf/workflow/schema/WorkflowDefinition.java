package epf.workflow.schema;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.StringOrArray;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.adapter.StringOrArrayExtensionAdapter;
import epf.workflow.schema.adapter.StringOrArrayRetryDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrDataSchemaAdapter;
import epf.workflow.schema.adapter.StringOrStartDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrWorkflowTimeoutDefinitionAdapter;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.auth.adapter.StringOrArrayAuthDefinitionAdapter;
import epf.workflow.schema.error.ErrorHandlingConfiguration;
import epf.workflow.schema.error.adapter.StringOrErrorHandlingConfigurationAdapter;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.adapter.StringOrArrayEventDefinitionAdapter;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.adapter.StringOrArrayFunctionDefinitionAdapter;
import epf.workflow.schema.state.State;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class WorkflowDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Id
	@Description("The name that identifies the workflow definition, and which, when combined with its version, forms a unique identifier.")
	private String name;
	
	@Column
	@Description("Workflow version. MUST respect the semantic versioning format. If not provided, latest is assumed")
	@DefaultValue("latest")
	private String version = "latest";
	
	@Column
	@Description("Workflow description")
	private String description;
	
	@Column
	@Description("Optional expression that will be used to generate a domain-specific workflow instance identifier")
	private String key;
	
	@Column
	@Description("List of helpful terms describing the workflows intended purpose, subject areas, or other important qualities")
	private List<String> annotations;
	
	@Column
	@Description("Used to validate the workflow data input against a defined JSON Schema")
	@JsonbTypeAdapter(value = StringOrDataSchemaAdapter.class)
	private StringOrObject<DataSchema> dataInputSchema;
	
	@Column
	@Description("Used to validate the workflow data output against a defined JSON Schema")
	@JsonbTypeAdapter(value = StringOrDataSchemaAdapter.class)
	private StringOrObject<DataSchema> dataOutputSchema;
	
	@Column
	@Description("Workflow constants")
	private StringOrObject<Object> constants;
	
	@Column
	@Description("Workflow secrets")
	private StringOrArray<String> secrets;
	
	@Column
	@Description("Workflow start definition")
	@JsonbTypeAdapter(value = StringOrStartDefinitionAdapter.class)
	private StringOrObject<StartDefinition> start;
	
	@NotNull
	@Column
	@Description("Serverless Workflow specification release version")
	private String specVersion;
	
	@Column
	@Description("Identifies the expression language used for workflow expressions.")
	@DefaultValue("jq")
	private String expressionLang = "jq";
	
	@Column
	@Description("Defines the workflow default timeout settings")
	@JsonbTypeAdapter(value = StringOrWorkflowTimeoutDefinitionAdapter.class)
	private StringOrObject<WorkflowTimeoutDefinition> timeouts;
	
	@Column
	@Description("Defines the workflow's error handling configuration, including error definitions, error handlers, and error policies")
	@JsonbTypeAdapter(value = StringOrErrorHandlingConfigurationAdapter.class)
	private StringOrObject<ErrorHandlingConfiguration> errors;
	
	@Column
	@Description("If true, workflow instances is not terminated when there are no active execution paths. Instance can be terminated with \"terminate end definition\" or reaching defined \"workflowExecTimeout\"")
	private Boolean keepActive;
	
	@Column
	@Description("Workflow authentication definitions")
	@JsonbTypeAdapter(value = StringOrArrayAuthDefinitionAdapter.class)
	private StringOrArray<AuthDefinition> auth;
	
	@Column
	@Description("Workflow event definitions.")
	@JsonbTypeAdapter(value = StringOrArrayEventDefinitionAdapter.class)
	private StringOrArray<EventDefinition> events;
	
	@Column
	@Description("Workflow function definitions. Can be either inline function definitions (if array) or URI pointing to a resource containing json/yaml function definitions (if string)")
	@JsonbTypeAdapter(value = StringOrArrayFunctionDefinitionAdapter.class)
	private StringOrArray<FunctionDefinition> functions;
	
	@Column
	@Description("Workflow retries definitions. Can be either inline retries definitions (if array) or URI pointing to a resource containing json/yaml retry definitions (if string)")
	@JsonbTypeAdapter(value = StringOrArrayRetryDefinitionAdapter.class)
	private StringOrArray<RetryDefinition> retries;
	
	@NotNull
	@Column
	@Description("Workflow states")
	private List<State> states;
	
	@Column
	@Description("Workflow extensions definitions")
	@JsonbTypeAdapter(value = StringOrArrayExtensionAdapter.class)
	private StringOrArray<Extension> extensions;
	
	@Column
	@Description("Metadata information")
	private Map<String, String> metadata;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

	public StringOrObject<DataSchema> getDataInputSchema() {
		return dataInputSchema;
	}

	public void setDataInputSchema(StringOrObject<DataSchema> dataInputSchema) {
		this.dataInputSchema = dataInputSchema;
	}

	public StringOrObject<DataSchema> getDataOutputSchema() {
		return dataOutputSchema;
	}

	public void setDataOutputSchema(StringOrObject<DataSchema> dataOutputSchema) {
		this.dataOutputSchema = dataOutputSchema;
	}

	public StringOrObject<Object> getConstants() {
		return constants;
	}

	public void setConstants(StringOrObject<Object> constants) {
		this.constants = constants;
	}

	public StringOrArray<String> getSecrets() {
		return secrets;
	}

	public void setSecrets(StringOrArray<String> secrets) {
		this.secrets = secrets;
	}

	public StringOrObject<StartDefinition> getStart() {
		return start;
	}

	public void setStart(StringOrObject<StartDefinition> start) {
		this.start = start;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	public String getExpressionLang() {
		return expressionLang;
	}

	public void setExpressionLang(String expressionLang) {
		this.expressionLang = expressionLang;
	}

	public StringOrObject<WorkflowTimeoutDefinition> getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(StringOrObject<WorkflowTimeoutDefinition> timeouts) {
		this.timeouts = timeouts;
	}

	public StringOrObject<ErrorHandlingConfiguration> getErrors() {
		return errors;
	}

	public void setErrors(StringOrObject<ErrorHandlingConfiguration> errors) {
		this.errors = errors;
	}

	public Boolean getKeepActive() {
		return keepActive;
	}

	public void setKeepActive(Boolean keepActive) {
		this.keepActive = keepActive;
	}

	public StringOrArray<AuthDefinition> getAuth() {
		return auth;
	}

	public void setAuth(StringOrArray<AuthDefinition> auth) {
		this.auth = auth;
	}

	public StringOrArray<EventDefinition> getEvents() {
		return events;
	}

	public void setEvents(StringOrArray<EventDefinition> events) {
		this.events = events;
	}

	public StringOrArray<FunctionDefinition> getFunctions() {
		return functions;
	}

	public void setFunctions(StringOrArray<FunctionDefinition> functions) {
		this.functions = functions;
	}

	public StringOrArray<RetryDefinition> getRetries() {
		return retries;
	}

	public void setRetries(StringOrArray<RetryDefinition> retries) {
		this.retries = retries;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public StringOrArray<Extension> getExtensions() {
		return extensions;
	}

	public void setExtensions(StringOrArray<Extension> extensions) {
		this.extensions = extensions;
	}

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
