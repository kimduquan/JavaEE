package epf.workflow.schema.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "name",
    "source",
    "type",
    "correlation",
    "dataOnly",
    "kind",
    "metadata"
})
public class EventDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Event Definition unique name
     * 
     */
    @JsonbProperty("name")
    @Size(min = 1)
    private String name;
    /**
     * CloudEvent source UUID
     * 
     */
    @JsonbProperty("source")
    private String source;
    /**
     * CloudEvent type
     * 
     */
    @JsonbProperty("type")
    private String type;
    /**
     * CloudEvent correlation definitions
     * 
     */
    @JsonbProperty("correlation")
    @Size(min = 1)
    @Valid
    private List<CorrelationDef> correlation = new ArrayList<CorrelationDef>();
    /**
     * If `true`, only the Event payload is accessible to consuming Workflow states. If `false`, both event payload and context attributes should be accessible 
     * 
     */
    @JsonbProperty("dataOnly")
    @JsonbPropertyDescription("If `true`, only the Event payload is accessible to consuming Workflow states. If `false`, both event payload and context attributes should be accessible ")
    private boolean dataOnly = true;
    /**
     * Defines the events as either being consumed or produced by the workflow. Default is consumed
     * 
     */
    @JsonbProperty("kind")
    private EventDefinition.Kind kind = EventDefinition.Kind.fromValue("consumed");
    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    @Valid
    private Map<String, String> metadata;

    /**
     * Event Definition unique name
     * 
     */
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Event Definition unique name
     * 
     */
    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public EventDefinition withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * CloudEvent source UUID
     * 
     */
    @JsonbProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * CloudEvent source UUID
     * 
     */
    @JsonbProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    public EventDefinition withSource(String source) {
        this.source = source;
        return this;
    }

    /**
     * CloudEvent type
     * 
     */
    @JsonbProperty("type")
    public String getType() {
        return type;
    }

    /**
     * CloudEvent type
     * 
     */
    @JsonbProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public EventDefinition withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * CloudEvent correlation definitions
     * 
     */
    @JsonbProperty("correlation")
    public List<CorrelationDef> getCorrelation() {
        return correlation;
    }

    /**
     * CloudEvent correlation definitions
     * 
     */
    @JsonbProperty("correlation")
    public void setCorrelation(List<CorrelationDef> correlation) {
        this.correlation = correlation;
    }

    public EventDefinition withCorrelation(List<CorrelationDef> correlation) {
        this.correlation = correlation;
        return this;
    }

    /**
     * If `true`, only the Event payload is accessible to consuming Workflow states. If `false`, both event payload and context attributes should be accessible 
     * 
     */
    @JsonbProperty("dataOnly")
    public boolean isDataOnly() {
        return dataOnly;
    }

    /**
     * If `true`, only the Event payload is accessible to consuming Workflow states. If `false`, both event payload and context attributes should be accessible 
     * 
     */
    @JsonbProperty("dataOnly")
    public void setDataOnly(boolean dataOnly) {
        this.dataOnly = dataOnly;
    }

    public EventDefinition withDataOnly(boolean dataOnly) {
        this.dataOnly = dataOnly;
        return this;
    }

    /**
     * Defines the events as either being consumed or produced by the workflow. Default is consumed
     * 
     */
    @JsonbProperty("kind")
    public EventDefinition.Kind getKind() {
        return kind;
    }

    /**
     * Defines the events as either being consumed or produced by the workflow. Default is consumed
     * 
     */
    @JsonbProperty("kind")
    public void setKind(EventDefinition.Kind kind) {
        this.kind = kind;
    }

    public EventDefinition withKind(EventDefinition.Kind kind) {
        this.kind = kind;
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

    public EventDefinition withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public enum Kind {

        CONSUMED("consumed"),
        PRODUCED("produced");
        private final String value;
        private final static Map<String, EventDefinition.Kind> CONSTANTS = new HashMap<String, EventDefinition.Kind>();

        static {
            for (EventDefinition.Kind c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Kind(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public String value() {
            return this.value;
        }

        @JsonbCreator
        public static EventDefinition.Kind fromValue(String value) {
            EventDefinition.Kind constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}