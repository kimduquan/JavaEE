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
public class Description implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
    /**
     * 
     */
    @Column(name = "BRIEF_OUTLINE")
    private JsonObject briefOutline;

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(final JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }

    public JsonObject getBriefOutline() {
        return briefOutline;
    }

    public void setBriefOutline(final JsonObject briefOutline) {
        this.briefOutline = briefOutline;
    }
}
