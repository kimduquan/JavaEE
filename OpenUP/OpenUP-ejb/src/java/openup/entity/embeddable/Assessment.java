/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity.embeddable;

import java.util.Date;
import java.util.List;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Assessment {
    
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

    private List<String> OtherConcernsAndDeviations;

    /**
     * Get the value of OtherConcernsAndDeviations
     *
     * @return the value of OtherConcernsAndDeviations
     */
    public List<String> getOtherConcernsAndDeviations() {
        return OtherConcernsAndDeviations;
    }

    /**
     * Set the value of OtherConcernsAndDeviations
     *
     * @param OtherConcernsAndDeviations new value of OtherConcernsAndDeviations
     */
    public void setOtherConcernsAndDeviations(List<String> OtherConcernsAndDeviations) {
        this.OtherConcernsAndDeviations = OtherConcernsAndDeviations;
    }

}
