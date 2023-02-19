package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

/**
 * @author PC
 *
 */
@Entity
public class WorkflowDefinition {

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
	private Object dataInputSchema;
	
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
	private Object timeouts;
	
	/**
	 * 
	 */
	@Column
	private Object errors;
	
	/**
	 * 
	 */
	@Column
	private boolean keepActive;
	
	/**
	 * 
	 */
	@Column
	private Object auth;
	
	/**
	 * 
	 */
	@Column
	private Object events;
	
	/**
	 * 
	 */
	@Column
	private Object functions;
	
	/**
	 * 
	 */
	@Column
	private boolean autoRetries = false;
	
	/**
	 * 
	 */
	@Column
	private Object retries;
	
	/**
	 * 
	 */
	@Column
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

	public Object getDataInputSchema() {
		return dataInputSchema;
	}

	public void setDataInputSchema(Object dataInputSchema) {
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

	public void setStart(StartDefinition start) {
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

	public Object getTimeouts() {
		return timeouts;
	}

	public void setTimeouts(Object timeouts) {
		this.timeouts = timeouts;
	}

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}

	public boolean isKeepActive() {
		return keepActive;
	}

	public void setKeepActive(boolean keepActive) {
		this.keepActive = keepActive;
	}

	public Object getAuth() {
		return auth;
	}

	public void setAuth(Object auth) {
		this.auth = auth;
	}

	public Object getEvents() {
		return events;
	}

	public void setEvents(Object events) {
		this.events = events;
	}

	public Object getFunctions() {
		return functions;
	}

	public void setFunctions(Object functions) {
		this.functions = functions;
	}

	public boolean isAutoRetries() {
		return autoRetries;
	}

	public void setAutoRetries(boolean autoRetries) {
		this.autoRetries = autoRetries;
	}

	public Object getRetries() {
		return retries;
	}

	public void setRetries(Object retries) {
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
