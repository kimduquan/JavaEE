/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import java.util.Date;
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
public class ArchitectureNotebook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        if (!(object instanceof ArchitectureNotebook)) {
            return false;
        }
        ArchitectureNotebook other = (ArchitectureNotebook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.ArchitectureNotebook[ id=" + id + " ]";
    }
    
    private String ProjectName;

    /**
     * Get the value of ProjectName
     *
     * @return the value of ProjectName
     */
    public String getProjectName() {
        return ProjectName;
    }

    /**
     * Set the value of ProjectName
     *
     * @param ProjectName new value of ProjectName
     */
    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    private Date Date;

    /**
     * Get the value of Date
     *
     * @return the value of Date
     */
    public Date getDate() {
        return Date;
    }

    /**
     * Set the value of Date
     *
     * @param Date new value of Date
     */
    public void setDate(Date Date) {
        this.Date = Date;
    }

    private String Purpose;

    /**
     * Get the value of Purpose
     *
     * @return the value of Purpose
     */
    public String getPurpose() {
        return Purpose;
    }

    /**
     * Set the value of Purpose
     *
     * @param Purpose new value of Purpose
     */
    public void setPurpose(String Purpose) {
        this.Purpose = Purpose;
    }

    private String ArchitecturalGoalsAndPhilosophy;

    /**
     * Get the value of ArchitecturalGoalsAndPhilosophy
     *
     * @return the value of ArchitecturalGoalsAndPhilosophy
     */
    public String getArchitecturalGoalsAndPhilosophy() {
        return ArchitecturalGoalsAndPhilosophy;
    }

    /**
     * Set the value of ArchitecturalGoalsAndPhilosophy
     *
     * @param ArchitecturalGoalsAndPhilosophy new value of
     * ArchitecturalGoalsAndPhilosophy
     */
    public void setArchitecturalGoalsAndPhilosophy(String ArchitecturalGoalsAndPhilosophy) {
        this.ArchitecturalGoalsAndPhilosophy = ArchitecturalGoalsAndPhilosophy;
    }

    private String AssumptionsAndDependencies;

    /**
     * Get the value of AssumptionsAndDependencies
     *
     * @return the value of AssumptionsAndDependencies
     */
    public String getAssumptionsAndDependencies() {
        return AssumptionsAndDependencies;
    }

    /**
     * Set the value of AssumptionsAndDependencies
     *
     * @param AssumptionsAndDependencies new value of AssumptionsAndDependencies
     */
    public void setAssumptionsAndDependencies(String AssumptionsAndDependencies) {
        this.AssumptionsAndDependencies = AssumptionsAndDependencies;
    }

    private String ArchitecturallySignificantRequirements;

    /**
     * Get the value of ArchitecturallySignificantRequirements
     *
     * @return the value of ArchitecturallySignificantRequirements
     */
    public String getArchitecturallySignificantRequirements() {
        return ArchitecturallySignificantRequirements;
    }

    /**
     * Set the value of ArchitecturallySignificantRequirements
     *
     * @param ArchitecturallySignificantRequirements new value of
     * ArchitecturallySignificantRequirements
     */
    public void setArchitecturallySignificantRequirements(String ArchitecturallySignificantRequirements) {
        this.ArchitecturallySignificantRequirements = ArchitecturallySignificantRequirements;
    }

    private List DecisionsConstraintsAndJustifications;

    /**
     * Get the value of DecisionsConstraintsAndJustifications
     *
     * @return the value of DecisionsConstraintsAndJustifications
     */
    public List getDecisionsConstraintsAndJustifications() {
        return DecisionsConstraintsAndJustifications;
    }

    /**
     * Set the value of DecisionsConstraintsAndJustifications
     *
     * @param DecisionsConstraintsAndJustifications new value of
     * DecisionsConstraintsAndJustifications
     */
    public void setDecisionsConstraintsAndJustifications(List DecisionsConstraintsAndJustifications) {
        this.DecisionsConstraintsAndJustifications = DecisionsConstraintsAndJustifications;
    }

    private List<ArchitecturalMechanism> ArchitecturalMechanisms;

    /**
     * Get the value of ArchitecturalMechanisms
     *
     * @return the value of ArchitecturalMechanisms
     */
    public List<ArchitecturalMechanism> getArchitecturalMechanisms() {
        return ArchitecturalMechanisms;
    }

    /**
     * Set the value of ArchitecturalMechanisms
     *
     * @param ArchitecturalMechanisms new value of ArchitecturalMechanisms
     */
    public void setArchitecturalMechanisms(List<ArchitecturalMechanism> ArchitecturalMechanisms) {
        this.ArchitecturalMechanisms = ArchitecturalMechanisms;
    }

    private String KeyAbstractions;

    /**
     * Get the value of KeyAbstractions
     *
     * @return the value of KeyAbstractions
     */
    public String getKeyAbstractions() {
        return KeyAbstractions;
    }

    /**
     * Set the value of KeyAbstractions
     *
     * @param KeyAbstractions new value of KeyAbstractions
     */
    public void setKeyAbstractions(String KeyAbstractions) {
        this.KeyAbstractions = KeyAbstractions;
    }

    private String LayersOrArchitecturalFramework;

    /**
     * Get the value of LayersOrArchitecturalFramework
     *
     * @return the value of LayersOrArchitecturalFramework
     */
    public String getLayersOrArchitecturalFramework() {
        return LayersOrArchitecturalFramework;
    }

    /**
     * Set the value of LayersOrArchitecturalFramework
     *
     * @param LayersOrArchitecturalFramework new value of
     * LayersOrArchitecturalFramework
     */
    public void setLayersOrArchitecturalFramework(String LayersOrArchitecturalFramework) {
        this.LayersOrArchitecturalFramework = LayersOrArchitecturalFramework;
    }

    private String ArchitecturalViews;

    /**
     * Get the value of ArchitecturalViews
     *
     * @return the value of ArchitecturalViews
     */
    public String getArchitecturalViews() {
        return ArchitecturalViews;
    }

    /**
     * Set the value of ArchitecturalViews
     *
     * @param ArchitecturalViews new value of ArchitecturalViews
     */
    public void setArchitecturalViews(String ArchitecturalViews) {
        this.ArchitecturalViews = ArchitecturalViews;
    }

}
