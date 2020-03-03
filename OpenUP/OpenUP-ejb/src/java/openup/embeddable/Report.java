/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.embeddable;

import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Report {
    
    private String Report;

    /**
     * Get the value of Report
     *
     * @return the value of Report
     */
    public String getReport() {
        return Report;
    }

    /**
     * Set the value of Report
     *
     * @param Report new value of Report
     */
    public void setReport(String Report) {
        this.Report = Report;
    }

    private String Audience;

    /**
     * Get the value of Audience
     *
     * @return the value of Audience
     */
    public String getAudience() {
        return Audience;
    }

    /**
     * Set the value of Audience
     *
     * @param Audience new value of Audience
     */
    public void setAudience(String Audience) {
        this.Audience = Audience;
    }

    private String HowCreatedWhereStored;

    /**
     * Get the value of HowCreatedWhereStored
     *
     * @return the value of HowCreatedWhereStored
     */
    public String getHowCreatedWhereStored() {
        return HowCreatedWhereStored;
    }

    /**
     * Set the value of HowCreatedWhereStored
     *
     * @param HowCreatedWhereStored new value of HowCreatedWhereStored
     */
    public void setHowCreatedWhereStored(String HowCreatedWhereStored) {
        this.HowCreatedWhereStored = HowCreatedWhereStored;
    }

}
