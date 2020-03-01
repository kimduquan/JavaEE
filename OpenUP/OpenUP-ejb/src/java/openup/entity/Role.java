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
public class Role implements Serializable {

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
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Role[ id=" + id + " ]";
    }
    
    private String Role;

    /**
     * Get the value of Role
     *
     * @return the value of Role
     */
    public String getRole() {
        return Role;
    }

    /**
     * Set the value of Role
     *
     * @param Role new value of Role
     */
    public void setRole(String Role) {
        this.Role = Role;
    }

    private String ProcessRole;

    /**
     * Get the value of ProcessRole
     *
     * @return the value of ProcessRole
     */
    public String getProcessRole() {
        return ProcessRole;
    }

    /**
     * Set the value of ProcessRole
     *
     * @param ProcessRole new value of ProcessRole
     */
    public void setProcessRole(String ProcessRole) {
        this.ProcessRole = ProcessRole;
    }

    private String Applicability;

    /**
     * Get the value of Applicability
     *
     * @return the value of Applicability
     */
    public String getApplicability() {
        return Applicability;
    }

    /**
     * Set the value of Applicability
     *
     * @param Applicability new value of Applicability
     */
    public void setApplicability(String Applicability) {
        this.Applicability = Applicability;
    }

    private String ResponsibilitiesDifferentFromProcess;

    /**
     * Get the value of ResponsibilitiesDifferentFromProcess
     *
     * @return the value of ResponsibilitiesDifferentFromProcess
     */
    public String getResponsibilitiesDifferentFromProcess() {
        return ResponsibilitiesDifferentFromProcess;
    }

    /**
     * Set the value of ResponsibilitiesDifferentFromProcess
     *
     * @param ResponsibilitiesDifferentFromProcess new value of
     * ResponsibilitiesDifferentFromProcess
     */
    public void setResponsibilitiesDifferentFromProcess(String ResponsibilitiesDifferentFromProcess) {
        this.ResponsibilitiesDifferentFromProcess = ResponsibilitiesDifferentFromProcess;
    }

}
