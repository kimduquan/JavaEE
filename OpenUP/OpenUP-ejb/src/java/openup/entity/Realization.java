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
public class Realization implements Serializable {

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
        if (!(object instanceof Realization)) {
            return false;
        }
        Realization other = (Realization) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Realization[ id=" + id + " ]";
    }
    
    private String ViewOfParticipants;

    /**
     * Get the value of ViewOfParticipants
     *
     * @return the value of ViewOfParticipants
     */
    public String getViewOfParticipants() {
        return ViewOfParticipants;
    }

    /**
     * Set the value of ViewOfParticipants
     *
     * @param ViewOfParticipants new value of ViewOfParticipants
     */
    public void setViewOfParticipants(String ViewOfParticipants) {
        this.ViewOfParticipants = ViewOfParticipants;
    }

    private String BasicScenario;

    /**
     * Get the value of BasicScenario
     *
     * @return the value of BasicScenario
     */
    public String getBasicScenario() {
        return BasicScenario;
    }

    /**
     * Set the value of BasicScenario
     *
     * @param BasicScenario new value of BasicScenario
     */
    public void setBasicScenario(String BasicScenario) {
        this.BasicScenario = BasicScenario;
    }

    private String AdditionalScenarios;

    /**
     * Get the value of AdditionalScenarios
     *
     * @return the value of AdditionalScenarios
     */
    public String getAdditionalScenarios() {
        return AdditionalScenarios;
    }

    /**
     * Set the value of AdditionalScenarios
     *
     * @param AdditionalScenarios new value of AdditionalScenarios
     */
    public void setAdditionalScenarios(String AdditionalScenarios) {
        this.AdditionalScenarios = AdditionalScenarios;
    }

}
