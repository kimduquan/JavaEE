/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.roles.section;

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
public class Staffing {
    
    @Column(name = "SKILLS")
    private JsonObject skills;
    
    @Column(name = "ASSIGNMENT_APPROACHES")
    private JsonObject assignmentApproaches;

    public JsonObject getSkills() {
        return skills;
    }

    public void setSkills(JsonObject skills) {
        this.skills = skills;
    }

    public JsonObject getAssignmentApproaches() {
        return assignmentApproaches;
    }

    public void setAssignmentApproaches(JsonObject assignmentApproaches) {
        this.assignmentApproaches = assignmentApproaches;
    }
}
