package epf.workflow.schema;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.adapter.StringOrArrayEventDefinitionAdapter;
import epf.nosql.schema.StringOrArray;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.adapter.StringOrArrayExtensionAdapter;
import epf.workflow.schema.adapter.StringOrArrayRetryDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrArrayWorkflowErrorAdapter;
import epf.workflow.schema.adapter.StringOrDataSchemaAdapter;
import epf.workflow.schema.adapter.StringOrStartDefinitionAdapter;
import epf.workflow.schema.adapter.StringOrWorkflowTimeoutDefinitionAdapter;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.auth.adapter.StringOrArrayAuthDefinitionAdapter;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.adapter.StringOrArrayFunctionDefinitionAdapter;
import epf.workflow.schema.state.State;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

/**
 * @author PC
 *
 */
@Entity
public class WorkflowDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private String id;
	
	/**
	 * 
	 */
	@Column
	private String key;
	
	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String description;
	
	/**
	 * 
	 */
	@Column
	private String version;
	
	/**
	 * 
	 */
	@Column
	private List<String> annotations;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrDataSchemaAdapter.class)
	private StringOrObject<DataSchema> dataInputSchema;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrDataSchemaAdapter.class)
	private StringOrObject<DataSchema> dataOutputSchema;
	
	/**
	 * 
	 */
	@Column
	private StringOrObject<Object> constants;
	
	/**
	 * 
	 */
	@Column
	private StringOrArray<String> secrets;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrStartDefinitionAdapter.class)
	private StringOrObject<StartDefinition> start;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private String specVersion;
	
	/**
	 * 
	 */
	@Column
	private String expressionLang = "jq";
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrWorkflowTimeoutDefinitionAdapter.class)
	private StringOrObject<WorkflowTimeoutDefinition> timeouts;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrArrayWorkflowErrorAdapter.class)
	private StringOrArray<WorkflowError> errors;
	
	/**
	 * 
	 */
	@Column
	private Boolean keepActive;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrArrayAuthDefinitionAdapter.class)
	private StringOrArray<AuthDefinition> auth;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrArrayEventDefinitionAdapter.class)
	private StringOrArray<EventDefinition> events;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrArrayFunctionDefinitionAdapter.class)
	private StringOrArray<FunctionDefinition> functions;
	
	/**
	 * 
	 */
	@Column
	private Boolean autoRetries = false;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrArrayRetryDefinitionAdapter.class)
	private StringOrArray<RetryDefinition> retries;
	
	/**
	 * 
	 */
	@Column
	private List<State> states;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrArrayExtensionAdapter.class)
	private StringOrArray<Extension> extensions;
	
	/**
	 * 
	 */
	@Column
	private Map<String, String> metadata;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

	public StringOrArray<WorkflowError> getErrors() {
		return errors;
	}

	public void setErrors(StringOrArray<WorkflowError> errors) {
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

	public Boolean getAutoRetries() {
		return autoRetries;
	}

	public void setAutoRetries(Boolean autoRetries) {
		this.autoRetries = autoRetries;
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
}
