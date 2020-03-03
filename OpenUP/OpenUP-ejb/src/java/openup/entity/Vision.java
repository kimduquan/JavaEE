/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import openup.entity.embeddable.Need;
import openup.entity.embeddable.Requirement;

/**
 *
 * @author FOXCONN
 */
@Entity
public class Vision implements Serializable {

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
        if (!(object instanceof Vision)) {
            return false;
        }
        Vision other = (Vision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.Vision[ id=" + id + " ]";
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

    private String ProblemStatement;

    /**
     * Get the value of ProblemStatement
     *
     * @return the value of ProblemStatement
     */
    public String getProblemStatement() {
        return ProblemStatement;
    }

    /**
     * Set the value of ProblemStatement
     *
     * @param ProblemStatement new value of ProblemStatement
     */
    public void setProblemStatement(String ProblemStatement) {
        this.ProblemStatement = ProblemStatement;
    }

    private String ProductPositionStatement;

    /**
     * Get the value of ProductPositionStatement
     *
     * @return the value of ProductPositionStatement
     */
    public String getProductPositionStatement() {
        return ProductPositionStatement;
    }

    /**
     * Set the value of ProductPositionStatement
     *
     * @param ProductPositionStatement new value of ProductPositionStatement
     */
    public void setProductPositionStatement(String ProductPositionStatement) {
        this.ProductPositionStatement = ProductPositionStatement;
    }

    private String StakeholderSummary;

    /**
     * Get the value of StakeholderSummary
     *
     * @return the value of StakeholderSummary
     */
    public String getStakeholderSummary() {
        return StakeholderSummary;
    }

    /**
     * Set the value of StakeholderSummary
     *
     * @param StakeholderSummary new value of StakeholderSummary
     */
    public void setStakeholderSummary(String StakeholderSummary) {
        this.StakeholderSummary = StakeholderSummary;
    }

    private String UserEnvironment;

    /**
     * Get the value of UserEnvironment
     *
     * @return the value of UserEnvironment
     */
    public String getUserEnvironment() {
        return UserEnvironment;
    }

    /**
     * Set the value of UserEnvironment
     *
     * @param UserEnvironment new value of UserEnvironment
     */
    public void setUserEnvironment(String UserEnvironment) {
        this.UserEnvironment = UserEnvironment;
    }

    @ElementCollection
    private List<Need> NeedsAndFeatures;

    /**
     * Get the value of NeedsAndFeatures
     *
     * @return the value of NeedsAndFeatures
     */
    public List<Need> getNeedsAndFeatures() {
        return NeedsAndFeatures;
    }

    /**
     * Set the value of NeedsAndFeatures
     *
     * @param NeedsAndFeatures new value of NeedsAndFeatures
     */
    public void setNeedsAndFeatures(List<Need> NeedsAndFeatures) {
        this.NeedsAndFeatures = NeedsAndFeatures;
    }

    @ElementCollection
    private List<Requirement> OtherProductRequirements;

    /**
     * Get the value of OtherProductRequirements
     *
     * @return the value of OtherProductRequirements
     */
    public List<Requirement> getOtherProductRequirements() {
        return OtherProductRequirements;
    }

    /**
     * Set the value of OtherProductRequirements
     *
     * @param OtherProductRequirements new value of OtherProductRequirements
     */
    public void setOtherProductRequirements(List<Requirement> OtherProductRequirements) {
        this.OtherProductRequirements = OtherProductRequirements;
    }

}
