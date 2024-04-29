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
public class WorkBreakdownStructure implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Column(name = "WORKFLOW")
    private JsonObject workflow;

    /**
     * 
     */
    @Column(name = "WORK_BREAKDOWN")
    private JsonObject workBreakdown;

    public JsonObject getWorkflow() {
        return workflow;
    }

    public void setWorkflow(final JsonObject workflow) {
        this.workflow = workflow;
    }

    public JsonObject getWorkBreakdown() {
        return workBreakdown;
    }

    public void setWorkBreakdown(final JsonObject workBreakdown) {
        this.workBreakdown = workBreakdown;
    }
}
