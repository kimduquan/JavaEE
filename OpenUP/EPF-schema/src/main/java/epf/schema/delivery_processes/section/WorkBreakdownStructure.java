package epf.schema.delivery_processes.section;

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
public class WorkBreakdownStructure {

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
