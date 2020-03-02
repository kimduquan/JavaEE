/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity.embeddable;

import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Requirement {
    
    private String Requirement;

    /**
     * Get the value of Requirement
     *
     * @return the value of Requirement
     */
    public String getRequirement() {
        return Requirement;
    }

    /**
     * Set the value of Requirement
     *
     * @param Requirement new value of Requirement
     */
    public void setRequirement(String Requirement) {
        this.Requirement = Requirement;
    }

    private String Priority;

    /**
     * Get the value of Priority
     *
     * @return the value of Priority
     */
    public String getPriority() {
        return Priority;
    }

    /**
     * Set the value of Priority
     *
     * @param Priority new value of Priority
     */
    public void setPriority(String Priority) {
        this.Priority = Priority;
    }

    private String PlannedRelease;

    /**
     * Get the value of PlannedRelease
     *
     * @return the value of PlannedRelease
     */
    public String getPlannedRelease() {
        return PlannedRelease;
    }

    /**
     * Set the value of PlannedRelease
     *
     * @param PlannedRelease new value of PlannedRelease
     */
    public void setPlannedRelease(String PlannedRelease) {
        this.PlannedRelease = PlannedRelease;
    }

}
