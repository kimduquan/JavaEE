/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import openup.entity.embeddable.Realization;
import openup.entity.embeddable.Pattern;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class Design implements Serializable {

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
        if (!(object instanceof Design)) {
            return false;
        }
        Design other = (Design) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Design[ id=" + id + " ]";
    }
    
    private String DesignStructure;

    /**
     * Get the value of DesignStructure
     *
     * @return the value of DesignStructure
     */
    public String getDesignStructure() {
        return DesignStructure;
    }

    /**
     * Set the value of DesignStructure
     *
     * @param DesignStructure new value of DesignStructure
     */
    public void setDesignStructure(String DesignStructure) {
        this.DesignStructure = DesignStructure;
    }

    private List<String> Subsystems;

    /**
     * Get the value of Subsystems
     *
     * @return the value of Subsystems
     */
    public List<String> getSubsystems() {
        return Subsystems;
    }

    /**
     * Set the value of Subsystems
     *
     * @param Subsystems new value of Subsystems
     */
    public void setSubsystems(List<String> Subsystems) {
        this.Subsystems = Subsystems;
    }

    @Embedded
    private List<Pattern> Patterns;

    /**
     * Get the value of Patterns
     *
     * @return the value of Patterns
     */
    public List<Pattern> getPatterns() {
        return Patterns;
    }

    /**
     * Set the value of Patterns
     *
     * @param Patterns new value of Patterns
     */
    public void setPatterns(List<Pattern> Patterns) {
        this.Patterns = Patterns;
    }

    @Embedded
    private List<Realization> RequirementRealizations;

    /**
     * Get the value of RequirementRealizations
     *
     * @return the value of RequirementRealizations
     */
    public List<Realization> getRequirementRealizations() {
        return RequirementRealizations;
    }

    /**
     * Set the value of RequirementRealizations
     *
     * @param RequirementRealizations new value of RequirementRealizations
     */
    public void setRequirementRealizations(List<Realization> RequirementRealizations) {
        this.RequirementRealizations = RequirementRealizations;
    }

}
