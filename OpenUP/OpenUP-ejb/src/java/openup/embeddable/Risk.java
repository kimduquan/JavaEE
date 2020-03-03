/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.embeddable;

import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Risk {
    
    private int RiskID;

    /**
     * Get the value of RiskID
     *
     * @return the value of RiskID
     */
    public int getRiskID() {
        return RiskID;
    }

    /**
     * Set the value of RiskID
     *
     * @param RiskID new value of RiskID
     */
    public void setRiskID(int RiskID) {
        this.RiskID = RiskID;
    }

    private Date DateIdentified;

    /**
     * Get the value of DateIdentified
     *
     * @return the value of DateIdentified
     */
    public Date getDateIdentified() {
        return DateIdentified;
    }

    /**
     * Set the value of DateIdentified
     *
     * @param DateIdentified new value of DateIdentified
     */
    public void setDateIdentified(Date DateIdentified) {
        this.DateIdentified = DateIdentified;
    }

    private String Headline;

    /**
     * Get the value of Headline
     *
     * @return the value of Headline
     */
    public String getHeadline() {
        return Headline;
    }

    /**
     * Set the value of Headline
     *
     * @param Headline new value of Headline
     */
    public void setHeadline(String Headline) {
        this.Headline = Headline;
    }

    private String Description;

    /**
     * Get the value of Description
     *
     * @return the value of Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Set the value of Description
     *
     * @param Description new value of Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Embedded
    private RiskType Type;

    /**
     * Get the value of Type
     *
     * @return the value of Type
     */
    public RiskType getType() {
        return Type;
    }

    /**
     * Set the value of Type
     *
     * @param Type new value of Type
     */
    public void setType(RiskType Type) {
        this.Type = Type;
    }

    private int Impact;

    /**
     * Get the value of Impact
     *
     * @return the value of Impact
     */
    public int getImpact() {
        return Impact;
    }

    /**
     * Set the value of Impact
     *
     * @param Impact new value of Impact
     */
    public void setImpact(int Impact) {
        this.Impact = Impact;
    }

    private String Probability;

    /**
     * Get the value of Probability
     *
     * @return the value of Probability
     */
    public String getProbability() {
        return Probability;
    }

    /**
     * Set the value of Probability
     *
     * @param Probability new value of Probability
     */
    public void setProbability(String Probability) {
        this.Probability = Probability;
    }

    private String Magnitude;

    /**
     * Get the value of Magnitude
     *
     * @return the value of Magnitude
     */
    public String getMagnitude() {
        return Magnitude;
    }

    /**
     * Set the value of Magnitude
     *
     * @param Magnitude new value of Magnitude
     */
    public void setMagnitude(String Magnitude) {
        this.Magnitude = Magnitude;
    }

    private String Owner;

    /**
     * Get the value of Owner
     *
     * @return the value of Owner
     */
    public String getOwner() {
        return Owner;
    }

    /**
     * Set the value of Owner
     *
     * @param Owner new value of Owner
     */
    public void setOwner(String Owner) {
        this.Owner = Owner;
    }

    private String MitigationStrategy;

    /**
     * Get the value of MitigationStrategy
     *
     * @return the value of MitigationStrategy
     */
    public String getMitigationStrategy() {
        return MitigationStrategy;
    }

    /**
     * Set the value of MitigationStrategy
     *
     * @param MitigationStrategy new value of MitigationStrategy
     */
    public void setMitigationStrategy(String MitigationStrategy) {
        this.MitigationStrategy = MitigationStrategy;
    }

}
