/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class ProjectMilestone implements Serializable {

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
        if (!(object instanceof ProjectMilestone)) {
            return false;
        }
        ProjectMilestone other = (ProjectMilestone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.ProjectMilestone[ id=" + id + " ]";
    }
    
    private String Iteration;

    /**
     * Get the value of Iteration
     *
     * @return the value of Iteration
     */
    public String getIteration() {
        return Iteration;
    }

    /**
     * Set the value of Iteration
     *
     * @param Iteration new value of Iteration
     */
    public void setIteration(String Iteration) {
        this.Iteration = Iteration;
    }

    private List PrimaryObjectives;

    /**
     * Get the value of PrimaryObjectives
     *
     * @return the value of PrimaryObjectives
     */
    public List getPrimaryObjectives() {
        return PrimaryObjectives;
    }

    /**
     * Set the value of PrimaryObjectives
     *
     * @param PrimaryObjectives new value of PrimaryObjectives
     */
    public void setPrimaryObjectives(List PrimaryObjectives) {
        this.PrimaryObjectives = PrimaryObjectives;
    }

    private String ScheduledStartOrMilestone;

    /**
     * Get the value of ScheduledStartOrMilestone
     *
     * @return the value of ScheduledStartOrMilestone
     */
    public String getScheduledStartOrMilestone() {
        return ScheduledStartOrMilestone;
    }

    /**
     * Set the value of ScheduledStartOrMilestone
     *
     * @param ScheduledStartOrMilestone new value of ScheduledStartOrMilestone
     */
    public void setScheduledStartOrMilestone(String ScheduledStartOrMilestone) {
        this.ScheduledStartOrMilestone = ScheduledStartOrMilestone;
    }

    private String TargetVelocity;

    /**
     * Get the value of TargetVelocity
     *
     * @return the value of TargetVelocity
     */
    public String getTargetVelocity() {
        return TargetVelocity;
    }

    /**
     * Set the value of TargetVelocity
     *
     * @param TargetVelocity new value of TargetVelocity
     */
    public void setTargetVelocity(String TargetVelocity) {
        this.TargetVelocity = TargetVelocity;
    }

}
