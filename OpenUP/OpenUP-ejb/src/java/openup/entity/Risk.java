/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class Risk implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Risk)) {
            return false;
        }
        Risk other = (Risk) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Risk[ id=" + id + " ]";
    }
    
    private String DateIdentified;

    /**
     * Get the value of DateIdentified
     *
     * @return the value of DateIdentified
     */
    public String getDateIdentified() {
        return DateIdentified;
    }

    /**
     * Set the value of DateIdentified
     *
     * @param DateIdentified new value of DateIdentified
     */
    public void setDateIdentified(String DateIdentified) {
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
