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
public class Report implements Serializable {

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
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Report[ id=" + id + " ]";
    }
    
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
