package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class WorkflowDefinition {

	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	private String key;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private String description;
	
	/**
	 * 
	 */
	private String version;
	
	/**
	 * 
	 */
	private Object[] annotations;
	
	/**
	 * 
	 */
	private Object dataInputSchema;
	
	/**
	 * 
	 */
	private Object constants;
	
	/**
	 * 
	 */
	private Object[] secrets;
	
	/**
	 * 
	 */
	private Object start;
	
	/**
	 * 
	 */
	private String specVersion;
	
	/**
	 * 
	 */
	private String expressionLang = "jq";
	
	/**
	 * 
	 */
	private Object timeouts;
	
	/**
	 * 
	 */
	private Object errors;
	
	/**
	 * 
	 */
	private boolean keepActive;
	
	/**
	 * 
	 */
	private Object auth;
	
	/**
	 * 
	 */
	private Object events;
	
	/**
	 * 
	 */
	private Object functions;
	
	/**
	 * 
	 */
	private boolean autoRetries = false;
	
	/**
	 * 
	 */
	private Object[] retries;
	
	/**
	 * 
	 */
	private WorkflowState[] states;
	
	/**
	 * 
	 */
	private Object[] extensions;
	
	/**
	 * 
	 */
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

	public Object[] getRetries() {
		return retries;
	}

	public void setRetries(Object[] retries) {
		this.retries = retries;
	}

	public WorkflowState[] getStates() {
		return states;
	}

	public void setStates(WorkflowState[] states) {
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
