/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.templates;

import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import openup.embeddable.WorkItem;

/**
 *
 * @author FOXCONN
 */
@Entity
public class WorkItemsList implements Serializable {

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
        if (!(object instanceof WorkItemsList)) {
            return false;
        }
        WorkItemsList other = (WorkItemsList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.WorkItemsList[ id=" + id + " ]";
    }
    
    @ElementCollection
    private List<WorkItem> WorkItems;

    /**
     * Get the value of WorkItems
     *
     * @return the value of WorkItems
     */
    public List<WorkItem> getWorkItems() {
        return WorkItems;
    }

    /**
     * Set the value of WorkItems
     *
     * @param WorkItems new value of WorkItems
     */
    public void setWorkItems(List<WorkItem> WorkItems) {
        this.WorkItems = WorkItems;
    }

}
