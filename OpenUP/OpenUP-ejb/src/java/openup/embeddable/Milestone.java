/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.embeddable;

import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Milestone {
    
    private String Milestone;

    /**
     * Get the value of Milestone
     *
     * @return the value of Milestone
     */
    public String getMilestone() {
        return Milestone;
    }

    /**
     * Set the value of Milestone
     *
     * @param Milestone new value of Milestone
     */
    public void setMilestone(String Milestone) {
        this.Milestone = Milestone;
    }

    @Temporal(TemporalType.DATE)
    private Date Date;

    /**
     * Get the value of Date
     *
     * @return the value of Date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * Set the value of Date
     *
     * @param Date new value of Date
     */
    public void setDate(Date Date) {
        this.Date = Date;
    }

}
