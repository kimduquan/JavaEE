/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.embeddable;

import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class ProjectMilestone {
    
    private String Iteration;

    /**
     * Get the value of Iteration
     *
     * @return the value of Iteration
     */
    public String getIteration() {
        return Iteration;
    }

    /**
     * Set the value of Iteration
     *
     * @param Iteration new value of Iteration
     */
    public void setIteration(String Iteration) {
        this.Iteration = Iteration;
    }

    @ElementCollection
    private List<String> PrimaryObjectives;

    /**
     * Get the value of PrimaryObjectives
     *
     * @return the value of PrimaryObjectives
     */
    public List<String> getPrimaryObjectives() {
        return PrimaryObjectives;
    }

    /**
     * Set the value of PrimaryObjectives
     *
     * @param PrimaryObjectives new value of PrimaryObjectives
     */
    public void setPrimaryObjectives(List<String> PrimaryObjectives) {
        this.PrimaryObjectives = PrimaryObjectives;
    }

    private String ScheduledStartOrMilestone;

    /**
     * Get the value of ScheduledStartOrMilestone
     *
     * @return the value of ScheduledStartOrMilestone
     */
    public String getScheduledStartOrMilestone() {
        return ScheduledStartOrMilestone;
    }

    /**
     * Set the value of ScheduledStartOrMilestone
     *
     * @param ScheduledStartOrMilestone new value of ScheduledStartOrMilestone
     */
    public void setScheduledStartOrMilestone(String ScheduledStartOrMilestone) {
        this.ScheduledStartOrMilestone = ScheduledStartOrMilestone;
    }

    private int TargetVelocity;

    /**
     * Get the value of TargetVelocity
     *
     * @return the value of TargetVelocity
     */
    public int getTargetVelocity() {
        return TargetVelocity;
    }

    /**
     * Set the value of TargetVelocity
     *
     * @param TargetVelocity new value of TargetVelocity
     */
    public void setTargetVelocity(int TargetVelocity) {
        this.TargetVelocity = TargetVelocity;
    }

}
