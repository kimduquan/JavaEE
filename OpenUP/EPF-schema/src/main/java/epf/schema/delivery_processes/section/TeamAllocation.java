package epf.schema.delivery_processes.section;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.eclipse.microprofile.graphql.Type;

/**
 * @author PC
 *
 */
@Type
@Embeddable
public class TeamAllocation {

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
