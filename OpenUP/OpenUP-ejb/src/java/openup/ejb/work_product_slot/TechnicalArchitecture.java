/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.work_product_slot;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import openup.ejb.guidance.templates.ArchitectureNotebook;

/**
 *
 * @author FOXCONN
 */
@Entity
public class TechnicalArchitecture implements Serializable {

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
        if (!(object instanceof TechnicalArchitecture)) {
            return false;
        }
        TechnicalArchitecture other = (TechnicalArchitecture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.ejb.work_product_slot.TechnicalArchitecture[ id=" + id + " ]";
    }
   
    @ElementCollection
    @OneToMany
    private List<ArchitectureNotebook> FulfillingWorkProducts;

    /**
     * Get the value of FulfillingWorkProducts
     *
     * @return the value of FulfillingWorkProducts
     */
    public List<ArchitectureNotebook> getFulfillingWorkProducts() {
        return FulfillingWorkProducts;
    }

    /**
     * Set the value of FulfillingWorkProducts
     *
     * @param FulfillingWorkProducts new value of FulfillingWorkProducts
     */
    public void setFulfillingWorkProducts(List<ArchitectureNotebook> FulfillingWorkProducts) {
        this.FulfillingWorkProducts = FulfillingWorkProducts;
    }

}
