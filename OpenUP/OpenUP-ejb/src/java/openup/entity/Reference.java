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
public class Reference implements Serializable {

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
        if (!(object instanceof Reference)) {
            return false;
        }
        Reference other = (Reference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Reference[ id=" + id + " ]";
    }
    
    private String ReferenceName;

    /**
     * Get the value of ReferenceName
     *
     * @return the value of ReferenceName
     */
    public String getReferenceName() {
        return ReferenceName;
    }

    /**
     * Set the value of ReferenceName
     *
     * @param ReferenceName new value of ReferenceName
     */
    public void setReferenceName(String ReferenceName) {
        this.ReferenceName = ReferenceName;
    }

    private String OwnerAuthor;

    /**
     * Get the value of OwnerAuthor
     *
     * @return the value of OwnerAuthor
     */
    public String getOwnerAuthor() {
        return OwnerAuthor;
    }

    /**
     * Set the value of OwnerAuthor
     *
     * @param OwnerAuthor new value of OwnerAuthor
     */
    public void setOwnerAuthor(String OwnerAuthor) {
        this.OwnerAuthor = OwnerAuthor;
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
