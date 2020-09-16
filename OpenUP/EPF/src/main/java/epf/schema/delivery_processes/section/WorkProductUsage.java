package epf.schema.delivery_processes.section;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class WorkProductUsage {

    @Column(name = "WORK_PRODUCT_BREAKDOWN")
    private JsonObject workProductBreakdown;

    public JsonObject getWorkProductBreakdown() {
        return workProductBreakdown;
    }

    public void setWorkProductBreakdown(JsonObject workProductBreakdown) {
        this.workProductBreakdown = workProductBreakdown;
    }
}
