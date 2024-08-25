package epf.work_products.schema.section;

import java.io.Serializable;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.eclipse.microprofile.graphql.Type;

/**
 *
 * @author FOXCONN
 */
@Type
@Embeddable
public class MoreInformation implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Column(name = "CHECKLISTS")
    private JsonObject checklists;
    
    /**
     * 
     */
    @Column(name = "CONCEPTS")
    private JsonObject concepts;
    
    /**
     * 
     */
    @Column(name = "GUIDELINES")
    private JsonObject guidelines;

    public JsonObject getChecklists() {
        return checklists;
    }

    public void setChecklists(final JsonObject checklists) {
        this.checklists = checklists;
    }

    public JsonObject getConcepts() {
        return concepts;
    }

    public void setConcepts(final JsonObject concepts) {
        this.concepts = concepts;
    }

    public JsonObject getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(final JsonObject guidelines) {
        this.guidelines = guidelines;
    }
}
