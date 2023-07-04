package epf.workflow.schema;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.validation.constraints.NotNull;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.mapping.EventDefinitionArrayConverter;
import epf.workflow.schema.adapter.StartDefinitionAdapter;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.mapping.FunctionDefinitionArrayConverter;
import epf.workflow.schema.state.State;
import epf.workflow.schema.state.mapping.StateArrayConverter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Convert;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

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
	private Object[] annotations;
	
	/**
	 * 
	 */
	@Column
	private InputSchema dataInputSchema;
	
	/**
	 * 
	 */
	@Column
	private Object constants;
	
	/**
	 * 
	 */
	@Column
	private Object[] secrets;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StartDefinitionAdapter.class)
	private Object start;
	
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
	private WorkflowTimeoutDefinition timeouts;
	
	/**
	 * 
	 */
	@Column
	private WorkflowError[] errors;
	
	/**
	 * 
	 */
	@Column
	private Boolean keepActive;
	
	/**
	 * 
	 */
	@Column
	private AuthDefinition[] auth;
	
	/**
	 * 
	 */
	@Column
	@Convert(EventDefinitionArrayConverter.class)
	private EventDefinition[] events;
	
	/**
	 * 
	 */
	@Column
	@Convert(FunctionDefinitionArrayConverter.class)
	private FunctionDefinition[] functions;
	
	/**
	 * 
	 */
	@Column
	private Boolean autoRetries = false;
	
	/**
	 * 
	 */
	@Column
	private RetryDefinition[] retries;
	
	/**
	 * 
	 */
	@Column
	@Convert(StateArrayConverter.class)
	private State[] states;
	
	/**
	 * 
	 */
	@Column
	private Object[] extensions;
	
	/**
	 * 
	 */
	@Column
	private Object metadata;

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

	public Object[] getAnnotations() {
		return annotations;
	}

	public void setAnnotations(Object[] annotations) {
		this.annotations = annotations;
	}

	public InputSchema getDataInputSchema() {
		return dataInputSchema;
	}

	public void setDataInputSchema(InputSchema dataInputSchema) {
		this.dataInputSchema = dataInputSchema;
	}

	public Object getConstants() {
		return constants;
	}

	public void setConstants(Object constants) {
		this.constants = constants;
	}

	public Object[] getSecrets() {
		return secrets;
	}

	public void setSecrets(Object[] secrets) {
		this.secrets = secrets;
	}

	public Object getStart() {
		return start;
	}

	public void setStart(Object start) {
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

	public WorkflowTimeoutDefinition getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(WorkflowTimeoutDefinition timeouts) {
		this.timeouts = timeouts;
	}

	public WorkflowError[] getErrors() {
		return errors;
	}

	public void setErrors(WorkflowError[] errors) {
		this.errors = errors;
	}

	public Boolean isKeepActive() {
		return keepActive;
	}

	public void setKeepActive(Boolean keepActive) {
		this.keepActive = keepActive;
	}

	public AuthDefinition[] getAuth() {
		return auth;
	}

	public void setAuth(AuthDefinition[] auth) {
		this.auth = auth;
	}

	public EventDefinition[] getEvents() {
		return events;
	}

	public void setEvents(EventDefinition[] events) {
		this.events = events;
	}

	public FunctionDefinition[] getFunctions() {
		return functions;
	}

	public void setFunctions(FunctionDefinition[] functions) {
		this.functions = functions;
	}

	public Boolean isAutoRetries() {
		return autoRetries;
	}

	public void setAutoRetries(Boolean autoRetries) {
		this.autoRetries = autoRetries;
	}

	public RetryDefinition[] getRetries() {
		return retries;
	}

	public void setRetries(RetryDefinition[] retries) {
		this.retries = retries;
	}

	public State[] getStates() {
		return states;
	}

	public void setStates(State[] states) {
		this.states = states;
	}

	public Object[] getExtensions() {
		return extensions;
	}

	public void setExtensions(Object[] extensions) {
		this.extensions = extensions;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
