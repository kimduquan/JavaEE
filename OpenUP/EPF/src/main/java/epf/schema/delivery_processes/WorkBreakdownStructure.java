package epf.schema.delivery_processes;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WorkBreakdownStructure {

    @Column(name = "WORKFLOW")
    private JsonObject workflow;

    @Column(name = "WORK_BREAKDOWN")
    private JsonObject workBreakdown;

    public JsonObject getWorkflow() {
        return workflow;
    }

    public void setWorkflow(JsonObject workflow) {
        this.workflow = workflow;
    }

    public JsonObject getWorkBreakdown() {
        return workBreakdown;
    }

    public void setWorkBreakdown(JsonObject workBreakdown) {
        this.workBreakdown = workBreakdown;
    }
}
