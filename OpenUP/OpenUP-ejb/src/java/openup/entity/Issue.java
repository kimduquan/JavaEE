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
public class Issue implements Serializable {

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
        if (!(object instanceof Issue)) {
            return false;
        }
        Issue other = (Issue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Issue[ id=" + id + " ]";
    }
    
    private String Issue;

    /**
     * Get the value of Issue
     *
     * @return the value of Issue
     */
    public String getIssue() {
        return Issue;
    }

    /**
     * Set the value of Issue
     *
     * @param Issue new value of Issue
     */
    public void setIssue(String Issue) {
        this.Issue = Issue;
    }

    private String Status;

    /**
     * Get the value of Status
     *
     * @return the value of Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * Set the value of Status
     *
     * @param Status new value of Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    private String Notes;

    /**
     * Get the value of Notes
     *
     * @return the value of Notes
     */
    public String getNotes() {
        return Notes;
    }

    /**
     * Set the value of Notes
     *
     * @param Notes new value of Notes
     */
    public void setNotes(String Notes) {
        this.Notes = Notes;
    }

}
