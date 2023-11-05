package epf.workflow.schema;

import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Convert;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.adapter.StartDefinitionAdapter;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.mapping.StartDefinitionConverter;
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
	private List<Object> annotations;
	
	/**
	 * 
	 */
	@Column
	private DataSchema dataInputSchema;
	
	/**
	 * 
	 */
	@Column
	private Object constants;
	
	/**
	 * 
	 */
	@Column
	private List<Object> secrets;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StartDefinitionAdapter.class)
	@Convert(StartDefinitionConverter.class)
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
	private List<WorkflowError> errors;
	
	/**
	 * 
	 */
	@Column
	private Boolean keepActive;
	
	/**
	 * 
	 */
	@Column
	private List<AuthDefinition> auth;
	
	/**
	 * 
	 */
	@Column
	private List<EventDefinition> events;
	
	/**
	 * 
	 */
	@Column
	private List<FunctionDefinition> functions;
	
	/**
	 * 
	 */
	@Column
	private Boolean autoRetries = false;
	
	/**
	 * 
	 */
	@Column
	private List<RetryDefinition> retries;
	
	/**
	 * 
	 */
	@Column
	private List<State> states;
	
	/**
	 * 
	 */
	@Column
	private List<Object> extensions;
	
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

	public List<Object> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<Object> annotations) {
		this.annotations = annotations;
	}

	public DataSchema getDataInputSchema() {
		return dataInputSchema;
	}

	public void setDataInputSchema(DataSchema dataInputSchema) {
		this.dataInputSchema = dataInputSchema;
	}

	public Object getConstants() {
		return constants;
	}

	public void setConstants(Object constants) {
		this.constants = constants;
	}

	public List<Object> getSecrets() {
		return secrets;
	}

	public void setSecrets(List<Object> secrets) {
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

	public List<WorkflowError> getErrors() {
		return errors;
	}

	public void setErrors(List<WorkflowError> errors) {
		this.errors = errors;
	}

	public Boolean isKeepActive() {
		return keepActive;
	}

	public void setKeepActive(Boolean keepActive) {
		this.keepActive = keepActive;
	}

	public List<AuthDefinition> getAuth() {
		return auth;
	}

	public void setAuth(List<AuthDefinition> auth) {
		this.auth = auth;
	}

	public List<EventDefinition> getEvents() {
		return events;
	}

	public void setEvents(List<EventDefinition> events) {
		this.events = events;
	}

	public List<FunctionDefinition> getFunctions() {
		return functions;
	}

	public void setFunctions(List<FunctionDefinition> functions) {
		this.functions = functions;
	}

	public Boolean isAutoRetries() {
		return autoRetries;
	}

	public void setAutoRetries(Boolean autoRetries) {
		this.autoRetries = autoRetries;
	}

	public List<RetryDefinition> getRetries() {
		return retries;
	}

	public void setRetries(List<RetryDefinition> retries) {
		this.retries = retries;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public List<Object> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<Object> extensions) {
		this.extensions = extensions;
	}

	public Object getMetadata() {
		return metadata;
	}

	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
}
