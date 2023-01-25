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
import epf.workflow.schema.correlation.CorrelationDef;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

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
@Entity
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
    @Column
    @Id
    private String name;
    /**
     * CloudEvent source UUID
     * 
     */
    @JsonbProperty("source")
    @Column
    private String source;
    /**
     * CloudEvent type
     * 
     */
    @JsonbProperty("type")
    @Column
    private String type;
    /**
     * CloudEvent correlation definitions
     * 
     */
    @JsonbProperty("correlation")
    @Size(min = 1)
    @Valid
    @Column
    private List<CorrelationDef> correlation = new ArrayList<CorrelationDef>();
    /**
     * If `true`, only the Event payload is accessible to consuming Workflow states. If `false`, both event payload and context attributes should be accessible 
     * 
     */
    @JsonbProperty("dataOnly")
    @Column
    private boolean dataOnly = true;
    /**
     * Defines the events as either being consumed or produced by the workflow. Default is consumed
     * 
     */
    @JsonbProperty("kind")
    @Column
    private EventDefinition.Kind kind = EventDefinition.Kind.fromValue("consumed");
    /**
     * Metadata
     * 
     */
    @JsonbProperty("metadata")
    @Valid
    @Column
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
    public void setName(final String name) {
        this.name = name;
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
    public void setSource(final String source) {
        this.source = source;
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
    public void setType(final String type) {
        this.type = type;
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
    public void setCorrelation(final List<CorrelationDef> correlation) {
        this.correlation = correlation;
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
    public void setDataOnly(final boolean dataOnly) {
        this.dataOnly = dataOnly;
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
    public void setKind(final EventDefinition.Kind kind) {
        this.kind = kind;
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
     * @author PC
     *
     */
    public enum Kind {

        CONSUMED("consumed"),
        PRODUCED("produced");
        /**
         * 
         */
        private final String value;
        /**
         * 
         */
        private final static Map<String, EventDefinition.Kind> CONSTANTS = new HashMap<String, EventDefinition.Kind>();

        static {
            for (EventDefinition.Kind c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        /**
         * @param value
         */
        Kind(final String value) {
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
        public static EventDefinition.Kind fromValue(final String value) {
        	final EventDefinition.Kind constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }
    }
}