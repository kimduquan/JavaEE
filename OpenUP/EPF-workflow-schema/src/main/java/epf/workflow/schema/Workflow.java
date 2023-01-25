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
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.datainputschema.DataInputSchema;
import epf.workflow.schema.interfaces.Extension;
import epf.workflow.schema.interfaces.State;
import epf.workflow.schema.start.Start;
import epf.workflow.schema.timeouts.TimeoutsDefinition;

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
    public void setId(final String id) {
        this.id = id;
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
    public void setName(final String name) {
        this.name = name;
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
    public void setDescription(final String description) {
        this.description = description;
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
    public void setVersion(final String version) {
        this.version = version;
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
    public void setAnnotations(final List<String> annotations) {
        this.annotations = annotations;
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
    public void setDataInputSchema(final DataInputSchema dataInputSchema) {
        this.dataInputSchema = dataInputSchema;
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
    public void setStart(final Start start) {
        this.start = start;
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
    public void setSpecVersion(final String specVersion) {
        this.specVersion = specVersion;
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
    public void setExpressionLang(final String expressionLang) {
        this.expressionLang = expressionLang;
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
    public void setKeepActive(final boolean keepActive) {
        this.keepActive = keepActive;
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
    public void setAutoRetries(final boolean autoRetries) {
        this.autoRetries = autoRetries;
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
    public void setMetadata(final Map<String, String> metadata) {
        this.metadata = metadata;
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
    public void setEvents(final Events events) {
        this.events = events;
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
    public void setFunctions(final Functions functions) {
        this.functions = functions;
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
    public void setErrors(final Errors errors) {
        this.errors = errors;
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
    public void setRetries(final Retries retries) {
        this.retries = retries;
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
    public void setSecrets(final Secrets secrets) {
        this.secrets = secrets;
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
}