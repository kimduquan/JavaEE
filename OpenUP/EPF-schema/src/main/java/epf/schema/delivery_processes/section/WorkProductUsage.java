package epf.schema.delivery_processes.section;

import java.io.Serializable;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;
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
