package epf.schema.delivery_processes.section;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import org.eclipse.microprofile.graphql.Type;

@Type
@Embeddable
public class Description {

    @Column(name = "PURPOSE")
    private JsonObject purpose;

    @Column(name = "RELATIONSHIPS")
    private JsonObject relationships;

    @Column(name = "DESCRIPTION")
    private JsonObject description;

    @Embedded
    private Properties properties;

    @Column(name = "ALTERNATIVES")
    private JsonObject alternatives;
    
    public JsonObject getPurpose() {
        return purpose;
    }

    public void setPurpose(JsonObject purpose) {
        this.purpose = purpose;
    }

    public JsonObject getRelationships() {
        return relationships;
    }

    public void setRelationships(JsonObject relationships) {
        this.relationships = relationships;
    }

    public JsonObject getDescription() {
        return description;
    }

    public void setDescription(JsonObject description) {
        this.description = description;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public JsonObject getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(JsonObject alternatives) {
        this.alternatives = alternatives;
    }
}
