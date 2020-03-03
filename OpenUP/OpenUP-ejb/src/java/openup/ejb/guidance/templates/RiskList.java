/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.templates;

import openup.embeddable.Risk;
import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
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
public class RiskList implements Serializable {

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
        if (!(object instanceof RiskList)) {
            return false;
        }
        RiskList other = (RiskList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.RiskList[ id=" + id + " ]";
    }
    
    private String Project;

    /**
     * Get the value of Project
     *
     * @return the value of Project
     */
    public String getProject() {
        return Project;
    }

    /**
     * Set the value of Project
     *
     * @param Project new value of Project
     */
    public void setProject(String Project) {
        this.Project = Project;
    }

    @ElementCollection
    private List<Risk> Risks;

    /**
     * Get the value of Risks
     *
     * @return the value of Risks
     */
    public List<Risk> getRisks() {
        return Risks;
    }

    /**
     * Set the value of Risks
     *
     * @param Risks new value of Risks
     */
    public void setRisks(List<Risk> Risks) {
        this.Risks = Risks;
    }

}
