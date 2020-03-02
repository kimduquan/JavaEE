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
public class Need {
    
    private String Need;

    /**
     * Get the value of Need
     *
     * @return the value of Need
     */
    public String getNeed() {
        return Need;
    }

    /**
     * Set the value of Need
     *
     * @param Need new value of Need
     */
    public void setNeed(String Need) {
        this.Need = Need;
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

    private String Features;

    /**
     * Get the value of Features
     *
     * @return the value of Features
     */
    public String getFeatures() {
        return Features;
    }

    /**
     * Set the value of Features
     *
     * @param Features new value of Features
     */
    public void setFeatures(String Features) {
        this.Features = Features;
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
