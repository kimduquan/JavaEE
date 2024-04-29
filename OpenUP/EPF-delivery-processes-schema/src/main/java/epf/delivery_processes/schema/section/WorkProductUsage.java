package epf.delivery_processes.schema.section;

import java.io.Serializable;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.eclipse.microprofile.graphql.Type;

/**
 * @author PC
 *
 */
@Type
@Embeddable
public class WorkProductUsage implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     */
    @Column(name = "WORK_PRODUCT_BREAKDOWN")
    private JsonObject workProductBreakdown;

    public JsonObject getWorkProductBreakdown() {
        return workProductBreakdown;
    }

    public void setWorkProductBreakdown(final JsonObject workProductBreakdown) {
        this.workProductBreakdown = workProductBreakdown;
    }
}
