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
public class WorkItem implements Serializable {

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
        if (!(object instanceof WorkItem)) {
            return false;
        }
        WorkItem other = (WorkItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.WorkItem[ id=" + id + " ]";
    }
    
    private String NameOrKeyWordsOfDescription;

    /**
     * Get the value of NameOrKeyWordsOfDescription
     *
     * @return the value of NameOrKeyWordsOfDescription
     */
    public String getNameOrKeyWordsOfDescription() {
        return NameOrKeyWordsOfDescription;
    }

    /**
     * Set the value of NameOrKeyWordsOfDescription
     *
     * @param NameOrKeyWordsOfDescription new value of
     * NameOrKeyWordsOfDescription
     */
    public void setNameOrKeyWordsOfDescription(String NameOrKeyWordsOfDescription) {
        this.NameOrKeyWordsOfDescription = NameOrKeyWordsOfDescription;
    }

    private String Priority;

    /**
     * Get the value of Priority
     *
     * @return the value of Priority
     */
    public String getPriority() {
        return Priority;
    }

    /**
     * Set the value of Priority
     *
     * @param Priority new value of Priority
     */
    public void setPriority(String Priority) {
        this.Priority = Priority;
    }

    private int SizeEstimate;

    /**
     * Get the value of SizeEstimate
     *
     * @return the value of SizeEstimate
     */
    public int getSizeEstimate() {
        return SizeEstimate;
    }

    /**
     * Set the value of SizeEstimate
     *
     * @param SizeEstimate new value of SizeEstimate
     */
    public void setSizeEstimate(int SizeEstimate) {
        this.SizeEstimate = SizeEstimate;
    }

    private String State;

    /**
     * Get the value of State
     *
     * @return the value of State
     */
    public String getState() {
        return State;
    }

    /**
     * Set the value of State
     *
     * @param State new value of State
     */
    public void setState(String State) {
        this.State = State;
    }

    private String ReferenceMaterial;

    /**
     * Get the value of ReferenceMaterial
     *
     * @return the value of ReferenceMaterial
     */
    public String getReferenceMaterial() {
        return ReferenceMaterial;
    }

    /**
     * Set the value of ReferenceMaterial
     *
     * @param ReferenceMaterial new value of ReferenceMaterial
     */
    public void setReferenceMaterial(String ReferenceMaterial) {
        this.ReferenceMaterial = ReferenceMaterial;
    }

    private String TargetIteration;

    /**
     * Get the value of TargetIteration
     *
     * @return the value of TargetIteration
     */
    public String getTargetIteration() {
        return TargetIteration;
    }

    /**
     * Set the value of TargetIteration
     *
     * @param TargetIteration new value of TargetIteration
     */
    public void setTargetIteration(String TargetIteration) {
        this.TargetIteration = TargetIteration;
    }

    private String AssignedTo;

    /**
     * Get the value of AssignedTo
     *
     * @return the value of AssignedTo
     */
    public String getAssignedTo() {
        return AssignedTo;
    }

    /**
     * Set the value of AssignedTo
     *
     * @param AssignedTo new value of AssignedTo
     */
    public void setAssignedTo(String AssignedTo) {
        this.AssignedTo = AssignedTo;
    }

    private int HoursWorked;

    /**
     * Get the value of HoursWorked
     *
     * @return the value of HoursWorked
     */
    public int getHoursWorked() {
        return HoursWorked;
    }

    /**
     * Set the value of HoursWorked
     *
     * @param HoursWorked new value of HoursWorked
     */
    public void setHoursWorked(int HoursWorked) {
        this.HoursWorked = HoursWorked;
    }

    private int EstimateOfHoursRemaining;

    /**
     * Get the value of EstimateOfHoursRemaining
     *
     * @return the value of EstimateOfHoursRemaining
     */
    public int getEstimateOfHoursRemaining() {
        return EstimateOfHoursRemaining;
    }

    /**
     * Set the value of EstimateOfHoursRemaining
     *
     * @param EstimateOfHoursRemaining new value of EstimateOfHoursRemaining
     */
    public void setEstimateOfHoursRemaining(int EstimateOfHoursRemaining) {
        this.EstimateOfHoursRemaining = EstimateOfHoursRemaining;
    }

}
