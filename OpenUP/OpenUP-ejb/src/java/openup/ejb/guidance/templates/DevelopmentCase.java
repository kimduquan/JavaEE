/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.templates;

import openup.embeddable.GuidelineProcedure;
import openup.embeddable.Role;
import openup.embeddable.Report;
import openup.embeddable.Reference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class DevelopmentCase implements Serializable {

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
        if (!(object instanceof DevelopmentCase)) {
            return false;
        }
        DevelopmentCase other = (DevelopmentCase) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.DevelopmentCase[ id=" + id + " ]";
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

    private String Introduction;

    /**
     * Get the value of Introduction
     *
     * @return the value of Introduction
     */
    public String getIntroduction() {
        return Introduction;
    }

    /**
     * Set the value of Introduction
     *
     * @param Introduction new value of Introduction
     */
    public void setIntroduction(String Introduction) {
        this.Introduction = Introduction;
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

    private String Scope;

    /**
     * Get the value of Scope
     *
     * @return the value of Scope
     */
    public String getScope() {
        return Scope;
    }

    /**
     * Set the value of Scope
     *
     * @param Scope new value of Scope
     */
    public void setScope(String Scope) {
        this.Scope = Scope;
    }

    private String DefinitionsAcronymsAndAbbreviations;

    /**
     * Get the value of DefinitionsAcronymsAndAbbreviations
     *
     * @return the value of DefinitionsAcronymsAndAbbreviations
     */
    public String getDefinitionsAcronymsAndAbbreviations() {
        return DefinitionsAcronymsAndAbbreviations;
    }

    /**
     * Set the value of DefinitionsAcronymsAndAbbreviations
     *
     * @param DefinitionsAcronymsAndAbbreviations new value of
     * DefinitionsAcronymsAndAbbreviations
     */
    public void setDefinitionsAcronymsAndAbbreviations(String DefinitionsAcronymsAndAbbreviations) {
        this.DefinitionsAcronymsAndAbbreviations = DefinitionsAcronymsAndAbbreviations;
    }

    @ElementCollection
    private List<Reference> References;

    /**
     * Get the value of References
     *
     * @return the value of References
     */
    public List<Reference> getReferences() {
        return References;
    }

    /**
     * Set the value of References
     *
     * @param References new value of References
     */
    public void setReferences(List<Reference> References) {
        this.References = References;
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

    private String LifecycleModel;

    /**
     * Get the value of LifecycleModel
     *
     * @return the value of LifecycleModel
     */
    public String getLifecycleModel() {
        return LifecycleModel;
    }

    /**
     * Set the value of LifecycleModel
     *
     * @param LifecycleModel new value of LifecycleModel
     */
    public void setLifecycleModel(String LifecycleModel) {
        this.LifecycleModel = LifecycleModel;
    }

    @ElementCollection
    private List<IterationPlan> InceptionPhase;

    /**
     * Get the value of InceptionPhase
     *
     * @return the value of InceptionPhase
     */
    public List<IterationPlan> getInceptionPhase() {
        return InceptionPhase;
    }

    /**
     * Set the value of InceptionPhase
     *
     * @param InceptionPhase new value of InceptionPhase
     */
    public void setInceptionPhase(List<IterationPlan> InceptionPhase) {
        this.InceptionPhase = InceptionPhase;
    }

    @ElementCollection
    private List<IterationPlan> ElaborationPhase;

    /**
     * Get the value of ElaborationPhase
     *
     * @return the value of ElaborationPhase
     */
    public List<IterationPlan> getElaborationPhase() {
        return ElaborationPhase;
    }

    /**
     * Set the value of ElaborationPhase
     *
     * @param ElaborationPhase new value of ElaborationPhase
     */
    public void setElaborationPhase(List<IterationPlan> ElaborationPhase) {
        this.ElaborationPhase = ElaborationPhase;
    }

    @ElementCollection
    private List<IterationPlan> ConstructionPhase;

    /**
     * Get the value of ConstructionPhase
     *
     * @return the value of ConstructionPhase
     */
    public List<IterationPlan> getConstructionPhase() {
        return ConstructionPhase;
    }

    /**
     * Set the value of ConstructionPhase
     *
     * @param ConstructionPhase new value of ConstructionPhase
     */
    public void setConstructionPhase(List<IterationPlan> ConstructionPhase) {
        this.ConstructionPhase = ConstructionPhase;
    }

    @ElementCollection
    private List<IterationPlan> TransitionPhase;

    /**
     * Get the value of TransitionPhase
     *
     * @return the value of TransitionPhase
     */
    public List<IterationPlan> getTransitionPhase() {
        return TransitionPhase;
    }

    /**
     * Set the value of TransitionPhase
     *
     * @param TransitionPhase new value of TransitionPhase
     */
    public void setTransitionPhase(List<IterationPlan> TransitionPhase) {
        this.TransitionPhase = TransitionPhase;
    }

    private String NotesOnInceptionPhaseWorkflow;

    /**
     * Get the value of NotesOnInceptionPhaseWorkflow
     *
     * @return the value of NotesOnInceptionPhaseWorkflow
     */
    public String getNotesOnInceptionPhaseWorkflow() {
        return NotesOnInceptionPhaseWorkflow;
    }

    /**
     * Set the value of NotesOnInceptionPhaseWorkflow
     *
     * @param NotesOnInceptionPhaseWorkflow new value of
     * NotesOnInceptionPhaseWorkflow
     */
    public void setNotesOnInceptionPhaseWorkflow(String NotesOnInceptionPhaseWorkflow) {
        this.NotesOnInceptionPhaseWorkflow = NotesOnInceptionPhaseWorkflow;
    }

    private String NotesOnElaborationPhaseWorkflow;

    /**
     * Get the value of NotesOnElaborationPhaseWorkflow
     *
     * @return the value of NotesOnElaborationPhaseWorkflow
     */
    public String getNotesOnElaborationPhaseWorkflow() {
        return NotesOnElaborationPhaseWorkflow;
    }

    /**
     * Set the value of NotesOnElaborationPhaseWorkflow
     *
     * @param NotesOnElaborationPhaseWorkflow new value of
     * NotesOnElaborationPhaseWorkflow
     */
    public void setNotesOnElaborationPhaseWorkflow(String NotesOnElaborationPhaseWorkflow) {
        this.NotesOnElaborationPhaseWorkflow = NotesOnElaborationPhaseWorkflow;
    }

    private String NotesOnConstructionPhaseWorkflow;

    /**
     * Get the value of NotesOnConstructionPhaseWorkflow
     *
     * @return the value of NotesOnConstructionPhaseWorkflow
     */
    public String getNotesOnConstructionPhaseWorkflow() {
        return NotesOnConstructionPhaseWorkflow;
    }

    /**
     * Set the value of NotesOnConstructionPhaseWorkflow
     *
     * @param NotesOnConstructionPhaseWorkflow new value of
     * NotesOnConstructionPhaseWorkflow
     */
    public void setNotesOnConstructionPhaseWorkflow(String NotesOnConstructionPhaseWorkflow) {
        this.NotesOnConstructionPhaseWorkflow = NotesOnConstructionPhaseWorkflow;
    }

    private String NotesOnTransitionPhaseWorkflow;

    /**
     * Get the value of NotesOnTransitionPhaseWorkflow
     *
     * @return the value of NotesOnTransitionPhaseWorkflow
     */
    public String getNotesOnTransitionPhaseWorkflow() {
        return NotesOnTransitionPhaseWorkflow;
    }

    /**
     * Set the value of NotesOnTransitionPhaseWorkflow
     *
     * @param NotesOnTransitionPhaseWorkflow new value of
     * NotesOnTransitionPhaseWorkflow
     */
    public void setNotesOnTransitionPhaseWorkflow(String NotesOnTransitionPhaseWorkflow) {
        this.NotesOnTransitionPhaseWorkflow = NotesOnTransitionPhaseWorkflow;
    }

    private String WorkProducts;

    /**
     * Get the value of WorkProducts
     *
     * @return the value of WorkProducts
     */
    public String getWorkProducts() {
        return WorkProducts;
    }

    /**
     * Set the value of WorkProducts
     *
     * @param WorkProducts new value of WorkProducts
     */
    public void setWorkProducts(String WorkProducts) {
        this.WorkProducts = WorkProducts;
    }

    @ElementCollection
    private List<Report> Reports;

    /**
     * Get the value of Reports
     *
     * @return the value of Reports
     */
    public List<Report> getReports() {
        return Reports;
    }

    /**
     * Set the value of Reports
     *
     * @param Reports new value of Reports
     */
    public void setReports(List<Report> Reports) {
        this.Reports = Reports;
    }

    @ElementCollection
    private List<Role> Roles;

    /**
     * Get the value of Roles
     *
     * @return the value of Roles
     */
    public List<Role> getRoles() {
        return Roles;
    }

    /**
     * Set the value of Roles
     *
     * @param Roles new value of Roles
     */
    public void setRoles(List<Role> Roles) {
        this.Roles = Roles;
    }

    @ElementCollection
    private List<GuidelineProcedure> ProjectSpecificGuidelinesAndProcedures;

    /**
     * Get the value of ProjectSpecificGuidelinesAndProcedures
     *
     * @return the value of ProjectSpecificGuidelinesAndProcedures
     */
    public List<GuidelineProcedure> getProjectSpecificGuidelinesAndProcedures() {
        return ProjectSpecificGuidelinesAndProcedures;
    }

    /**
     * Set the value of ProjectSpecificGuidelinesAndProcedures
     *
     * @param ProjectSpecificGuidelinesAndProcedures new value of
     * ProjectSpecificGuidelinesAndProcedures
     */
    public void setProjectSpecificGuidelinesAndProcedures(List<GuidelineProcedure> ProjectSpecificGuidelinesAndProcedures) {
        this.ProjectSpecificGuidelinesAndProcedures = ProjectSpecificGuidelinesAndProcedures;
    }

}
