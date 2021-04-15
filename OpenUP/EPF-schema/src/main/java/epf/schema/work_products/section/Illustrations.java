/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products.section;

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
public class Illustrations {
    
    /**
     * 
     */
    @Column(name = "TEMPLATES")
    private JsonObject templates;
    
    /**
     * 
     */
    @Column(name = "REPORTS")
    private JsonObject reports;
    
    /**
     * 
     */
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
