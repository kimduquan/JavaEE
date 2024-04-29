package epf.roles.schema.section;

import java.io.Serializable;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
