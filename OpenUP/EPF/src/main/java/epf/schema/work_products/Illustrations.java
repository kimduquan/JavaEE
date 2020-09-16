/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Illustrations {
    
    @Column(name = "TEMPLATES")
    private JsonObject templates;
    
    @Column(name = "REPORTS")
    private JsonObject reports;
    
    @Column(name = "EXAMPLES")
    private JsonObject examples;

    public JsonObject getTemplates() {
        return templates;
    }

    public void setTemplates(JsonObject templates) {
        this.templates = templates;
    }

    public JsonObject getReports() {
        return reports;
    }

    public void setReports(JsonObject reports) {
        this.reports = reports;
    }

    public JsonObject getExamples() {
        return examples;
    }

    public void setExamples(JsonObject examples) {
        this.examples = examples;
    }
}
