/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.templates;

import openup.embeddable.Assessment;
import openup.embeddable.Issue;
import openup.embeddable.WorkItem;
import openup.embeddable.Milestone;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author FOXCONN
 */
@Entity
public class IterationPlan implements Serializable {

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
        if (!(object instanceof IterationPlan)) {
            return false;
        }
        IterationPlan other = (IterationPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.IterationPlan[ id=" + id + " ]";
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

    @Temporal(TemporalType.DATE)
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

    @ElementCollection
    private List<Milestone> KeyMilestones;

    /**
     * Get the value of KeyMilestones
     *
     * @return the value of KeyMilestones
     */
    public List<Milestone> getKeyMilestones() {
        return KeyMilestones;
    }

    /**
     * Set the value of KeyMilestones
     *
     * @param KeyMilestones new value of KeyMilestones
     */
    public void setKeyMilestones(List<Milestone> KeyMilestones) {
        this.KeyMilestones = KeyMilestones;
    }

    @ElementCollection
    private List<String> HighLevelObjectives;

    /**
     * Get the value of HighLevelObjectives
     *
     * @return the value of HighLevelObjectives
     */
    public List<String> getHighLevelObjectives() {
        return HighLevelObjectives;
    }

    /**
     * Set the value of HighLevelObjectives
     *
     * @param HighLevelObjectives new value of HighLevelObjectives
     */
    public void setHighLevelObjectives(List<String> HighLevelObjectives) {
        this.HighLevelObjectives = HighLevelObjectives;
    }

    @ElementCollection
    private List<WorkItem> WorkItemAssignments;

    /**
     * Get the value of WorkItemAssignments
     *
     * @return the value of WorkItemAssignments
     */
    public List<WorkItem> getWorkItemAssignments() {
        return WorkItemAssignments;
    }

    /**
     * Set the value of WorkItemAssignments
     *
     * @param WorkItemAssignments new value of WorkItemAssignments
     */
    public void setWorkItemAssignments(List<WorkItem> WorkItemAssignments) {
        this.WorkItemAssignments = WorkItemAssignments;
    }

    @ElementCollection
    private List<Issue> Issues;

    /**
     * Get the value of Issues
     *
     * @return the value of Issues
     */
    public List<Issue> getIssues() {
        return Issues;
    }

    /**
     * Set the value of Issues
     *
     * @param Issues new value of Issues
     */
    public void setIssues(List<Issue> Issues) {
        this.Issues = Issues;
    }

    private String EvaluationCriteria;

    /**
     * Get the value of EvaluationCriteria
     *
     * @return the value of EvaluationCriteria
     */
    public String getEvaluationCriteria() {
        return EvaluationCriteria;
    }

    /**
     * Set the value of EvaluationCriteria
     *
     * @param EvaluationCriteria new value of EvaluationCriteria
     */
    public void setEvaluationCriteria(String EvaluationCriteria) {
        this.EvaluationCriteria = EvaluationCriteria;
    }

    @Embedded
    private Assessment Assessment;

    /**
     * Get the value of Assessment
     *
     * @return the value of Assessment
     */
    public Assessment getAssessment() {
        return Assessment;
    }

    /**
     * Set the value of Assessment
     *
     * @param Assessment new value of Assessment
     */
    public void setAssessment(Assessment Assessment) {
        this.Assessment = Assessment;
    }

}
