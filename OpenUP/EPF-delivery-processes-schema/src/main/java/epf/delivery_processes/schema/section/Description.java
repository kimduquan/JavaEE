package epf.delivery_processes.schema.section;

import java.io.Serializable;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.graphql.Type;

/**
 * @author PC
 *
 */
@Type
@Embeddable
public class Description implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Column(name = "PURPOSE")
    private JsonObject purpose;

    /**
     * 
     */
    @Column(name = "RELATIONSHIPS")
    private JsonObject relationships;

    /**
     * 
     */
    @Column(name = "DESCRIPTION")
    private JsonObject description;

    /**
     * 
     */
    @Embedded
    @NotNull
    private Properties properties;

    /**
     * 
     */
    @Column(name = "ALTERNATIVES")
    private JsonObject alternatives;
    
    public JsonObject getPurpose() {
        return purpose;
    }

    public void setPurpose(final JsonObject purpose) {
        this.purpose = purpose;
    }

    public JsonObject getRelationships() {
        return relationships;
    }

    public void setRelationships(final JsonObject relationships) {
        this.relationships = relationships;
    }

    public JsonObject getDescription() {
        return description;
    }

    public void setDescription(final JsonObject description) {
        this.description = description;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(final Properties properties) {
        this.properties = properties;
    }

    public JsonObject getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(final JsonObject alternatives) {
        this.alternatives = alternatives;
    }
}
