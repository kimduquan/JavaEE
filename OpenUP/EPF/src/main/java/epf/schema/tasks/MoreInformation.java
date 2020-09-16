/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.tasks;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class MoreInformation {
    
    @Column(name = "CHECKLISTS")
    private JsonObject checklists;
    
    @Column(name = "CONCEPTS")
    private JsonObject concepts;
    
    @Column(name = "GUIDELINES")
    private JsonObject guidelines;
    
    @Column(name = "SUPPORTING_MATERIALS")
    private JsonObject supportingMaterials;
    
    @Column(name = "TOOL_MENTORS")
    private JsonObject toolMentors;

    public JsonObject getChecklists() {
        return checklists;
    }

    public void setChecklists(JsonObject checklists) {
        this.checklists = checklists;
    }

    public JsonObject getConcepts() {
        return concepts;
    }

    public void setConcepts(JsonObject concepts) {
        this.concepts = concepts;
    }

    public JsonObject getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(JsonObject guidelines) {
        this.guidelines = guidelines;
    }

    public JsonObject getSupportingMaterials() {
        return supportingMaterials;
    }

    public void setSupportingMaterials(JsonObject supportingMaterials) {
        this.supportingMaterials = supportingMaterials;
    }

    public JsonObject getToolMentors() {
        return toolMentors;
    }

    public void setToolMentors(JsonObject toolMentors) {
        this.toolMentors = toolMentors;
    }
}
