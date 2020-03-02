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
public class UseCaseSpecification implements Serializable {

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
        if (!(object instanceof UseCaseSpecification)) {
            return false;
        }
        UseCaseSpecification other = (UseCaseSpecification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "openup.entity.UseCaseSpecification[ id=" + id + " ]";
    }
    
    private String Project;

    /**
     * Get the value of Project
     *
     * @return the value of Project
     */
    public String getProject() {
        return Project;
    }

    /**
     * Set the value of Project
     *
     * @param Project new value of Project
     */
    public void setProject(String Project) {
        this.Project = Project;
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

    private String UseCaseName;

    /**
     * Get the value of UseCaseName
     *
     * @return the value of UseCaseName
     */
    public String getUseCaseName() {
        return UseCaseName;
    }

    /**
     * Set the value of UseCaseName
     *
     * @param UseCaseName new value of UseCaseName
     */
    public void setUseCaseName(String UseCaseName) {
        this.UseCaseName = UseCaseName;
    }

    private String BriefDescription;

    /**
     * Get the value of BriefDescription
     *
     * @return the value of BriefDescription
     */
    public String getBriefDescription() {
        return BriefDescription;
    }

    /**
     * Set the value of BriefDescription
     *
     * @param BriefDescription new value of BriefDescription
     */
    public void setBriefDescription(String BriefDescription) {
        this.BriefDescription = BriefDescription;
    }

    private List ActorBriefDescriptions;

    /**
     * Get the value of ActorBriefDescriptions
     *
     * @return the value of ActorBriefDescriptions
     */
    public List getActorBriefDescriptions() {
        return ActorBriefDescriptions;
    }

    /**
     * Set the value of ActorBriefDescriptions
     *
     * @param ActorBriefDescriptions new value of ActorBriefDescriptions
     */
    public void setActorBriefDescriptions(List ActorBriefDescriptions) {
        this.ActorBriefDescriptions = ActorBriefDescriptions;
    }

    private List Preconditions;

    /**
     * Get the value of Preconditions
     *
     * @return the value of Preconditions
     */
    public List getPreconditions() {
        return Preconditions;
    }

    /**
     * Set the value of Preconditions
     *
     * @param Preconditions new value of Preconditions
     */
    public void setPreconditions(List Preconditions) {
        this.Preconditions = Preconditions;
    }

    private List BasicFlowOfEvents;

    /**
     * Get the value of BasicFlowOfEvents
     *
     * @return the value of BasicFlowOfEvents
     */
    public List getBasicFlowOfEvents() {
        return BasicFlowOfEvents;
    }

    /**
     * Set the value of BasicFlowOfEvents
     *
     * @param BasicFlowOfEvents new value of BasicFlowOfEvents
     */
    public void setBasicFlowOfEvents(List BasicFlowOfEvents) {
        this.BasicFlowOfEvents = BasicFlowOfEvents;
    }

    private List AlternativeFlows;

    /**
     * Get the value of AlternativeFlows
     *
     * @return the value of AlternativeFlows
     */
    public List getAlternativeFlows() {
        return AlternativeFlows;
    }

    /**
     * Set the value of AlternativeFlows
     *
     * @param AlternativeFlows new value of AlternativeFlows
     */
    public void setAlternativeFlows(List AlternativeFlows) {
        this.AlternativeFlows = AlternativeFlows;
    }

    private List Subflows;

    /**
     * Get the value of Subflows
     *
     * @return the value of Subflows
     */
    public List getSubflows() {
        return Subflows;
    }

    /**
     * Set the value of Subflows
     *
     * @param Subflows new value of Subflows
     */
    public void setSubflows(List Subflows) {
        this.Subflows = Subflows;
    }

    private List KeyScenarios;

    /**
     * Get the value of KeyScenarios
     *
     * @return the value of KeyScenarios
     */
    public List getKeyScenarios() {
        return KeyScenarios;
    }

    /**
     * Set the value of KeyScenarios
     *
     * @param KeyScenarios new value of KeyScenarios
     */
    public void setKeyScenarios(List KeyScenarios) {
        this.KeyScenarios = KeyScenarios;
    }

    private List PostConditions;

    /**
     * Get the value of PostConditions
     *
     * @return the value of PostConditions
     */
    public List getPostConditions() {
        return PostConditions;
    }

    /**
     * Set the value of PostConditions
     *
     * @param PostConditions new value of PostConditions
     */
    public void setPostConditions(List PostConditions) {
        this.PostConditions = PostConditions;
    }

    private List SpecialRequirements;

    /**
     * Get the value of SpecialRequirements
     *
     * @return the value of SpecialRequirements
     */
    public List getSpecialRequirements() {
        return SpecialRequirements;
    }

    /**
     * Set the value of SpecialRequirements
     *
     * @param SpecialRequirements new value of SpecialRequirements
     */
    public void setSpecialRequirements(List SpecialRequirements) {
        this.SpecialRequirements = SpecialRequirements;
    }

}
