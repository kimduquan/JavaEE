package epf.delivery_processes.schema.section;

import java.io.Serializable;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.eclipse.microprofile.graphql.Type;

/**
 * @author PC
 *
 */
@Type
@Embeddable
public class TeamAllocation implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     */
    @Column(name = "TEAM_BREAKDOWN")
    private JsonObject teamBreakdown;

    public JsonObject getTeamBreakdown() {
        return teamBreakdown;
    }

    public void setTeamBreakdown(final JsonObject teamBreakdown) {
        this.teamBreakdown = teamBreakdown;
    }
}
