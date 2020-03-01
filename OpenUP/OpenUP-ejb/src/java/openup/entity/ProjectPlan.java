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
public class ProjectPlan implements Serializable {

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
        if (!(object instanceof ProjectPlan)) {
            return false;
        }
        ProjectPlan other = (ProjectPlan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.ProjectPlan[ id=" + id + " ]";
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

    private String ProjectOrganization;

    /**
     * Get the value of ProjectOrganization
     *
     * @return the value of ProjectOrganization
     */
    public String getProjectOrganization() {
        return ProjectOrganization;
    }

    /**
     * Set the value of ProjectOrganization
     *
     * @param ProjectOrganization new value of ProjectOrganization
     */
    public void setProjectOrganization(String ProjectOrganization) {
        this.ProjectOrganization = ProjectOrganization;
    }

    private String ProjectPracticesAndMeasurements;

    /**
     * Get the value of ProjectPracticesAndMeasurements
     *
     * @return the value of ProjectPracticesAndMeasurements
     */
    public String getProjectPracticesAndMeasurements() {
        return ProjectPracticesAndMeasurements;
    }

    /**
     * Set the value of ProjectPracticesAndMeasurements
     *
     * @param ProjectPracticesAndMeasurements new value of
     * ProjectPracticesAndMeasurements
     */
    public void setProjectPracticesAndMeasurements(String ProjectPracticesAndMeasurements) {
        this.ProjectPracticesAndMeasurements = ProjectPracticesAndMeasurements;
    }

    private List<ProjectMilestone> ProjectMilestonesAndObjectives;

    /**
     * Get the value of ProjectMilestonesAndObjectives
     *
     * @return the value of ProjectMilestonesAndObjectives
     */
    public List<ProjectMilestone> getProjectMilestonesAndObjectives() {
        return ProjectMilestonesAndObjectives;
    }

    /**
     * Set the value of ProjectMilestonesAndObjectives
     *
     * @param ProjectMilestonesAndObjectives new value of
     * ProjectMilestonesAndObjectives
     */
    public void setProjectMilestonesAndObjectives(List<ProjectMilestone> ProjectMilestonesAndObjectives) {
        this.ProjectMilestonesAndObjectives = ProjectMilestonesAndObjectives;
    }

    private String Deployment;

    /**
     * Get the value of Deployment
     *
     * @return the value of Deployment
     */
    public String getDeployment() {
        return Deployment;
    }

    /**
     * Set the value of Deployment
     *
     * @param Deployment new value of Deployment
     */
    public void setDeployment(String Deployment) {
        this.Deployment = Deployment;
    }

    private String LessonsLearned;

    /**
     * Get the value of LessonsLearned
     *
     * @return the value of LessonsLearned
     */
    public String getLessonsLearned() {
        return LessonsLearned;
    }

    /**
     * Set the value of LessonsLearned
     *
     * @param LessonsLearned new value of LessonsLearned
     */
    public void setLessonsLearned(String LessonsLearned) {
        this.LessonsLearned = LessonsLearned;
    }

}
