package epf.delivery_processes;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TeamAllocation {

    @Column(name = "TEAM_BREAKDOWN")
    private JsonObject teamBreakdown;

    public JsonObject getTeamBreakdown() {
        return teamBreakdown;
    }

    public void setTeamBreakdown(JsonObject teamBreakdown) {
        this.teamBreakdown = teamBreakdown;
    }
}
