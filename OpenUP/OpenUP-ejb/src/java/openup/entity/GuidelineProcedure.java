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
public class GuidelineProcedure implements Serializable {

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
        if (!(object instanceof GuidelineProcedure)) {
            return false;
        }
        GuidelineProcedure other = (GuidelineProcedure) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.GuidelineProcedure[ id=" + id + " ]";
    }
    
    private String GuidelineOrProcedure;

    /**
     * Get the value of GuidelineOrProcedure
     *
     * @return the value of GuidelineOrProcedure
     */
    public String getGuidelineOrProcedure() {
        return GuidelineOrProcedure;
    }

    /**
     * Set the value of GuidelineOrProcedure
     *
     * @param GuidelineOrProcedure new value of GuidelineOrProcedure
     */
    public void setGuidelineOrProcedure(String GuidelineOrProcedure) {
        this.GuidelineOrProcedure = GuidelineOrProcedure;
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

    private String UsedBy;

    /**
     * Get the value of UsedBy
     *
     * @return the value of UsedBy
     */
    public String getUsedBy() {
        return UsedBy;
    }

    /**
     * Set the value of UsedBy
     *
     * @param UsedBy new value of UsedBy
     */
    public void setUsedBy(String UsedBy) {
        this.UsedBy = UsedBy;
    }

    private String WhereStored;

    /**
     * Get the value of WhereStored
     *
     * @return the value of WhereStored
     */
    public String getWhereStored() {
        return WhereStored;
    }

    /**
     * Set the value of WhereStored
     *
     * @param WhereStored new value of WhereStored
     */
    public void setWhereStored(String WhereStored) {
        this.WhereStored = WhereStored;
    }

}
