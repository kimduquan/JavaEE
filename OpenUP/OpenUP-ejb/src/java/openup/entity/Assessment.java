/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author FOXCONN
 */
@Entity
public class Assessment implements Serializable {

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
        if (!(object instanceof Assessment)) {
            return false;
        }
        Assessment other = (Assessment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Assessment[ id=" + id + " ]";
    }
    
    private String AssessmentTarget;

    /**
     * Get the value of AssessmentTarget
     *
     * @return the value of AssessmentTarget
     */
    public String getAssessmentTarget() {
        return AssessmentTarget;
    }

    /**
     * Set the value of AssessmentTarget
     *
     * @param AssessmentTarget new value of AssessmentTarget
     */
    public void setAssessmentTarget(String AssessmentTarget) {
        this.AssessmentTarget = AssessmentTarget;
    }

    private Date AssessmentDate;

    /**
     * Get the value of AssessmentDate
     *
     * @return the value of AssessmentDate
     */
    public Date getAssessmentDate() {
        return AssessmentDate;
    }

    /**
     * Set the value of AssessmentDate
     *
     * @param AssessmentDate new value of AssessmentDate
     */
    public void setAssessmentDate(Date AssessmentDate) {
        this.AssessmentDate = AssessmentDate;
    }

    private String Participants;

    /**
     * Get the value of Participants
     *
     * @return the value of Participants
     */
    public String getParticipants() {
        return Participants;
    }

    /**
     * Set the value of Participants
     *
     * @param Participants new value of Participants
     */
    public void setParticipants(String Participants) {
        this.Participants = Participants;
    }

    private String ProjectStatus;

    /**
     * Get the value of ProjectStatus
     *
     * @return the value of ProjectStatus
     */
    public String getProjectStatus() {
        return ProjectStatus;
    }

    /**
     * Set the value of ProjectStatus
     *
     * @param ProjectStatus new value of ProjectStatus
     */
    public void setProjectStatus(String ProjectStatus) {
        this.ProjectStatus = ProjectStatus;
    }

    private String AssessmentAgainstObjectives;

    /**
     * Get the value of AssessmentAgainstObjectives
     *
     * @return the value of AssessmentAgainstObjectives
     */
    public String getAssessmentAgainstObjectives() {
        return AssessmentAgainstObjectives;
    }

    /**
     * Set the value of AssessmentAgainstObjectives
     *
     * @param AssessmentAgainstObjectives new value of
     * AssessmentAgainstObjectives
     */
    public void setAssessmentAgainstObjectives(String AssessmentAgainstObjectives) {
        this.AssessmentAgainstObjectives = AssessmentAgainstObjectives;
    }

    private String WorkItems;

    /**
     * Get the value of WorkItems
     *
     * @return the value of WorkItems
     */
    public String getWorkItems() {
        return WorkItems;
    }

    /**
     * Set the value of WorkItems
     *
     * @param WorkItems new value of WorkItems
     */
    public void setWorkItems(String WorkItems) {
        this.WorkItems = WorkItems;
    }

    private String AssessmentAgainstEvaluationCriteriaTestResults;

    /**
     * Get the value of AssessmentAgainstEvaluationCriteriaTestResults
     *
     * @return the value of AssessmentAgainstEvaluationCriteriaTestResults
     */
    public String getAssessmentAgainstEvaluationCriteriaTestResults() {
        return AssessmentAgainstEvaluationCriteriaTestResults;
    }

    /**
     * Set the value of AssessmentAgainstEvaluationCriteriaTestResults
     *
     * @param AssessmentAgainstEvaluationCriteriaTestResults new value of
     * AssessmentAgainstEvaluationCriteriaTestResults
     */
    public void setAssessmentAgainstEvaluationCriteriaTestResults(String AssessmentAgainstEvaluationCriteriaTestResults) {
        this.AssessmentAgainstEvaluationCriteriaTestResults = AssessmentAgainstEvaluationCriteriaTestResults;
    }

    private String OtherConcernsAndDeviations;

    /**
     * Get the value of OtherConcernsAndDeviations
     *
     * @return the value of OtherConcernsAndDeviations
     */
    public String getOtherConcernsAndDeviations() {
        return OtherConcernsAndDeviations;
    }

    /**
     * Set the value of OtherConcernsAndDeviations
     *
     * @param OtherConcernsAndDeviations new value of OtherConcernsAndDeviations
     */
    public void setOtherConcernsAndDeviations(String OtherConcernsAndDeviations) {
        this.OtherConcernsAndDeviations = OtherConcernsAndDeviations;
    }

}
