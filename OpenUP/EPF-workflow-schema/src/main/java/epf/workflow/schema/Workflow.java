package epf.workflow.schema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "id",
    "name",
    "description",
    "version",
    "annotations",
    "dataInputSchema",
    "start",
    "specVersion",
    "expressionLang",
    "keepActive",
    "autoRetries",
    "metadata",
    "events",
    "functions",
    "errors",
    "retries",
    "secrets",
    "constants",
    "timeouts",
    "auth",
    "states",
    "extensions"
})
public class Workflow implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Workflow unique identifier
     * (Required)
     * 
     */
    @JsonbProperty("id")
    @Size(min = 1)
    @NotNull
    private String id;
    /**
     * Workflow name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    @Size(min = 1)
    @NotNull
    private String name;
    /**
     * Workflow description
     * 
     */
    @JsonbProperty("description")
    private String description;
    /**
     * Workflow version
     * (Required)
     * 
     */
    @JsonbProperty("version")
    @NotNull
    private String version;
    /**
     * List of helpful terms describing the workflows intended purpose, subject areas, or other important qualities
     * 
     */
    @JsonbProperty("annotations")
    @Size(min = 1)
    @Valid
    private List<String> annotations = new ArrayList<String>();
    /**
     * Workflow data input schema
     * 
     */
    @JsonbProperty("dataInputSchema")
    @Valid
    private DataInputSchema dataInputSchema;
    /**
     * State start definition
     * 
     */
    @JsonbProperty("start")
    @Valid
    private Start start;
    /**
     * Serverless Workflow schema version
     * 
     */
    @JsonbProperty("specVersion")
    private String specVersion;
    /**
     * Identifies the expression language used for workflow expressions. Default is 'jq'
     * 
     */
    @JsonbProperty("expressionLang")
    @Size(min = 1)
    private String expressionLang = "jq";
    /**
     * If 'true', workflow instances is not terminated when there are no active execution paths. Instance can be terminated via 'terminate end definition' or reaching defined 'execTimeout'
     * 
     */
    @JsonbProperty("keepActive")
    private boolean keepActive = false;
    /**
     * If set to true, actions should automatically be retried on unchecked errors. Default is false
     * 
     */
    @JsonbProperty("autoRetries")
    private boolean autoRetries = false;
    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    @Valid
    private Map<String, String> metadata;
    /**
     * Workflow event definitions
     * 
     */
    @JsonbProperty("events")
    @Valid
    private Events events;
    /**
     * Workflow function definitions
     * 
     */
    @JsonbProperty("functions")
    @Valid
    private Functions functions;
    /**
     * Workflow error definitions
     * 
     */
    @JsonbProperty("errors")
    @Valid
    private Errors errors;
    /**
     * Workflow retry definitions
     * 
     */
    @JsonbProperty("retries")
    @Valid
    private Retries retries;
    /**
     * Workflow secrets definitions
     * 
     */
    @JsonbProperty("secrets")
    @Valid
    private Secrets secrets;
    /**
     * Workflow constants definitions
     * 
     */
    @JsonbProperty("constants")
    @Valid
    private Constants constants;
    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    @Valid
    private TimeoutsDefinition timeouts;
    /**
     * Auth Definition
     * 
     */
    @JsonbProperty("auth")
    @Valid
    private AuthDefinition auth;
    /**
     * State Definitions
     * (Required)
     * 
     */
    @JsonbProperty("states")
    @Valid
    @NotNull
    private List<State> states = new ArrayList<State>();
    /**
     * Workflow Extensions
     * 
     */
    @JsonbProperty("extensions")
    @Valid
    private List<Extension> extensions = new ArrayList<Extension>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public Workflow() {
    }

    /**
     * 
     * @param name
     * @param id
     * @param version
     * @param states
     */
    public Workflow(String id, String name, String version, List<State> states) {
        super();
        this.id = id;
        this.name = name;
        this.version = version;
        this.states = states;
    }

    /**
     * Workflow unique identifier
     * (Required)
     * 
     */
    @JsonbProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Workflow unique identifier
     * (Required)
     * 
     */
    @JsonbProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Workflow withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Workflow name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Workflow name
     * (Required)
     * 
     */
    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Workflow withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Workflow description
     * 
     */
    @JsonbProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Workflow description
     * 
     */
    @JsonbProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Workflow withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Workflow version
     * (Required)
     * 
     */
    @JsonbProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * Workflow version
     * (Required)
     * 
     */
    @JsonbProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public Workflow withVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * List of helpful terms describing the workflows intended purpose, subject areas, or other important qualities
     * 
     */
    @JsonbProperty("annotations")
    public List<String> getAnnotations() {
        return annotations;
    }

    /**
     * List of helpful terms describing the workflows intended purpose, subject areas, or other important qualities
     * 
     */
    @JsonbProperty("annotations")
    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }

    public Workflow withAnnotations(List<String> annotations) {
        this.annotations = annotations;
        return this;
    }

    /**
     * Workflow data input schema
     * 
     */
    @JsonbProperty("dataInputSchema")
    public DataInputSchema getDataInputSchema() {
        return dataInputSchema;
    }

    /**
     * Workflow data input schema
     * 
     */
    @JsonbProperty("dataInputSchema")
    public void setDataInputSchema(DataInputSchema dataInputSchema) {
        this.dataInputSchema = dataInputSchema;
    }

    public Workflow withDataInputSchema(DataInputSchema dataInputSchema) {
        this.dataInputSchema = dataInputSchema;
        return this;
    }

    /**
     * State start definition
     * 
     */
    @JsonbProperty("start")
    public Start getStart() {
        return start;
    }

    /**
     * State start definition
     * 
     */
    @JsonbProperty("start")
    public void setStart(Start start) {
        this.start = start;
    }

    public Workflow withStart(Start start) {
        this.start = start;
        return this;
    }

    /**
     * Serverless Workflow schema version
     * 
     */
    @JsonbProperty("specVersion")
    public String getSpecVersion() {
        return specVersion;
    }

    /**
     * Serverless Workflow schema version
     * 
     */
    @JsonbProperty("specVersion")
    public void setSpecVersion(String specVersion) {
        this.specVersion = specVersion;
    }

    public Workflow withSpecVersion(String specVersion) {
        this.specVersion = specVersion;
        return this;
    }

    /**
     * Identifies the expression language used for workflow expressions. Default is 'jq'
     * 
     */
    @JsonbProperty("expressionLang")
    public String getExpressionLang() {
        return expressionLang;
    }

    /**
     * Identifies the expression language used for workflow expressions. Default is 'jq'
     * 
     */
    @JsonbProperty("expressionLang")
    public void setExpressionLang(String expressionLang) {
        this.expressionLang = expressionLang;
    }

    public Workflow withExpressionLang(String expressionLang) {
        this.expressionLang = expressionLang;
        return this;
    }

    /**
     * If 'true', workflow instances is not terminated when there are no active execution paths. Instance can be terminated via 'terminate end definition' or reaching defined 'execTimeout'
     * 
     */
    @JsonbProperty("keepActive")
    public boolean isKeepActive() {
        return keepActive;
    }

    /**
     * If 'true', workflow instances is not terminated when there are no active execution paths. Instance can be terminated via 'terminate end definition' or reaching defined 'execTimeout'
     * 
     */
    @JsonbProperty("keepActive")
    public void setKeepActive(boolean keepActive) {
        this.keepActive = keepActive;
    }

    public Workflow withKeepActive(boolean keepActive) {
        this.keepActive = keepActive;
        return this;
    }

    /**
     * If set to true, actions should automatically be retried on unchecked errors. Default is false
     * 
     */
    @JsonbProperty("autoRetries")
    public boolean isAutoRetries() {
        return autoRetries;
    }

    /**
     * If set to true, actions should automatically be retried on unchecked errors. Default is false
     * 
     */
    @JsonbProperty("autoRetries")
    public void setAutoRetries(boolean autoRetries) {
        this.autoRetries = autoRetries;
    }

    public Workflow withAutoRetries(boolean autoRetries) {
        this.autoRetries = autoRetries;
        return this;
    }

    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public Workflow withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Workflow event definitions
     * 
     */
    @JsonbProperty("events")
    public Events getEvents() {
        return events;
    }

    /**
     * Workflow event definitions
     * 
     */
    @JsonbProperty("events")
    public void setEvents(Events events) {
        this.events = events;
    }

    public Workflow withEvents(Events events) {
        this.events = events;
        return this;
    }

    /**
     * Workflow function definitions
     * 
     */
    @JsonbProperty("functions")
    public Functions getFunctions() {
        return functions;
    }

    /**
     * Workflow function definitions
     * 
     */
    @JsonbProperty("functions")
    public void setFunctions(Functions functions) {
        this.functions = functions;
    }

    public Workflow withFunctions(Functions functions) {
        this.functions = functions;
        return this;
    }

    /**
     * Workflow error definitions
     * 
     */
    @JsonbProperty("errors")
    public Errors getErrors() {
        return errors;
    }

    /**
     * Workflow error definitions
     * 
     */
    @JsonbProperty("errors")
    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Workflow withErrors(Errors errors) {
        this.errors = errors;
        return this;
    }

    /**
     * Workflow retry definitions
     * 
     */
    @JsonbProperty("retries")
    public Retries getRetries() {
        return retries;
    }

    /**
     * Workflow retry definitions
     * 
     */
    @JsonbProperty("retries")
    public void setRetries(Retries retries) {
        this.retries = retries;
    }

    public Workflow withRetries(Retries retries) {
        this.retries = retries;
        return this;
    }

    /**
     * Workflow secrets definitions
     * 
     */
    @JsonbProperty("secrets")
    public Secrets getSecrets() {
        return secrets;
    }

    /**
     * Workflow secrets definitions
     * 
     */
    @JsonbProperty("secrets")
    public void setSecrets(Secrets secrets) {
        this.secrets = secrets;
    }

    public Workflow withSecrets(Secrets secrets) {
        this.secrets = secrets;
        return this;
    }

    /**
     * Workflow constants definitions
     * 
     */
    @JsonbProperty("constants")
    public Constants getConstants() {
        return constants;
    }

    /**
     * Workflow constants definitions
     * 
     */
    @JsonbProperty("constants")
    public void setConstants(Constants constants) {
        this.constants = constants;
    }

    public Workflow withConstants(Constants constants) {
        this.constants = constants;
        return this;
    }

    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    public TimeoutsDefinition getTimeouts() {
        return timeouts;
    }

    /**
     * Timeouts Definition
     * 
     */
    @JsonbProperty("timeouts")
    public void setTimeouts(TimeoutsDefinition timeouts) {
        this.timeouts = timeouts;
    }

    public Workflow withTimeouts(TimeoutsDefinition timeouts) {
        this.timeouts = timeouts;
        return this;
    }

    /**
     * Auth Definition
     * 
     */
    @JsonbProperty("auth")
    public AuthDefinition getAuth() {
        return auth;
    }

    /**
     * Auth Definition
     * 
     */
    @JsonbProperty("auth")
    public void setAuth(AuthDefinition auth) {
        this.auth = auth;
    }

    public Workflow withAuth(AuthDefinition auth) {
        this.auth = auth;
        return this;
    }

    /**
     * State Definitions
     * (Required)
     * 
     */
    @JsonbProperty("states")
    public List<State> getStates() {
        return states;
    }

    /**
     * State Definitions
     * (Required)
     * 
     */
    @JsonbProperty("states")
    public void setStates(List<State> states) {
        this.states = states;
    }

    public Workflow withStates(List<State> states) {
        this.states = states;
        return this;
    }

    /**
     * Workflow Extensions
     * 
     */
    @JsonbProperty("extensions")
    public List<Extension> getExtensions() {
        return extensions;
    }

    /**
     * Workflow Extensions
     * 
     */
    @JsonbProperty("extensions")
    public void setExtensions(List<Extension> extensions) {
        this.extensions = extensions;
    }

    public Workflow withExtensions(List<Extension> extensions) {
        this.extensions = extensions;
        return this;
    }

}