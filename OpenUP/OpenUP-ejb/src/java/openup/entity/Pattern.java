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
public class Pattern implements Serializable {

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
        if (!(object instanceof Pattern)) {
            return false;
        }
        Pattern other = (Pattern) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Pattern[ id=" + id + " ]";
    }
    
    private String Overview;

    /**
     * Get the value of Overview
     *
     * @return the value of Overview
     */
    public String getOverview() {
        return Overview;
    }

    /**
     * Set the value of Overview
     *
     * @param Overview new value of Overview
     */
    public void setOverview(String Overview) {
        this.Overview = Overview;
    }

    private String Structure;

    /**
     * Get the value of Structure
     *
     * @return the value of Structure
     */
    public String getStructure() {
        return Structure;
    }

    /**
     * Set the value of Structure
     *
     * @param Structure new value of Structure
     */
    public void setStructure(String Structure) {
        this.Structure = Structure;
    }

    private String Behavior;

    /**
     * Get the value of Behavior
     *
     * @return the value of Behavior
     */
    public String getBehavior() {
        return Behavior;
    }

    /**
     * Set the value of Behavior
     *
     * @param Behavior new value of Behavior
     */
    public void setBehavior(String Behavior) {
        this.Behavior = Behavior;
    }

    private String Example;

    /**
     * Get the value of Example
     *
     * @return the value of Example
     */
    public String getExample() {
        return Example;
    }

    /**
     * Set the value of Example
     *
     * @param Example new value of Example
     */
    public void setExample(String Example) {
        this.Example = Example;
    }

}
