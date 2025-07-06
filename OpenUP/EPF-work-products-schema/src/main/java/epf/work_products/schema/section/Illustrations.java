package epf.work_products.schema.section;

import java.io.Serializable;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.eclipse.microprofile.graphql.Type;

@Type
@Embeddable
public class Illustrations implements Serializable {
    
    private static final long serialVersionUID = 1L;

	@Column(name = "TEMPLATES")
    private JsonObject templates;
    
    @Column(name = "REPORTS")
    private JsonObject reports;
    
    @Column(name = "EXAMPLES")
    private JsonObject examples;

    public JsonObject getTemplates() {
        return templates;
    }

    public void setTemplates(final JsonObject templates) {
        this.templates = templates;
    }

    public JsonObject getReports() {
        return reports;
    }

    public void setReports(final JsonObject reports) {
        this.reports = reports;
    }

    public JsonObject getExamples() {
        return examples;
    }

    public void setExamples(final JsonObject examples) {
        this.examples = examples;
    }
}
