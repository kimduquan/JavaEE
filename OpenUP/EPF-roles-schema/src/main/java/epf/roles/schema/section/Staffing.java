/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.roles.schema.section;

import java.io.Serializable;
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
public class Staffing implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Column(name = "SKILLS")
    private JsonObject skills;
    
    /**
     * 
     */
    @Column(name = "ASSIGNMENT_APPROACHES")
    private JsonObject assignmentApproaches;

    public JsonObject getSkills() {
        return skills;
    }

    public void setSkills(final JsonObject skills) {
        this.skills = skills;
    }

    public JsonObject getAssignmentApproaches() {
        return assignmentApproaches;
    }

    public void setAssignmentApproaches(final JsonObject assignmentApproaches) {
        this.assignmentApproaches = assignmentApproaches;
    }
}
