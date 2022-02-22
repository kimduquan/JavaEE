package epf.roles.schema.section;

import java.io.Serializable;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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
    @Column(name = "GUIDELINES")
    private JsonObject guidelines;

    public JsonObject getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(final JsonObject guidelines) {
        this.guidelines = guidelines;
    }
}
