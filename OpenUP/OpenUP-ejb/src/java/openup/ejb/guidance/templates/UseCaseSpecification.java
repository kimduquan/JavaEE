/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.guidance.templates;

import java.io.Serializable;
import java.util.Date;
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

    @ElementCollection
    private List<String> ActorBriefDescriptions;

    /**
     * Get the value of ActorBriefDescriptions
     *
     * @return the value of ActorBriefDescriptions
     */
    public List<String> getActorBriefDescriptions() {
        return ActorBriefDescriptions;
    }

    /**
     * Set the value of ActorBriefDescriptions
     *
     * @param ActorBriefDescriptions new value of ActorBriefDescriptions
     */
    public void setActorBriefDescriptions(List<String> ActorBriefDescriptions) {
        this.ActorBriefDescriptions = ActorBriefDescriptions;
    }

    @ElementCollection
    private List<String> PreConditions;

    /**
     * Get the value of PreConditions
     *
     * @return the value of PreConditions
     */
    public List<String> getPreConditions() {
        return PreConditions;
    }

    /**
     * Set the value of PreConditions
     *
     * @param PreConditions new value of PreConditions
     */
    public void setPreConditions(List<String> PreConditions) {
        this.PreConditions = PreConditions;
    }

    @ElementCollection
    private List<String> BasicFlowOfEvents;

    /**
     * Get the value of BasicFlowOfEvents
     *
     * @return the value of BasicFlowOfEvents
     */
    public List<String> getBasicFlowOfEvents() {
        return BasicFlowOfEvents;
    }

    /**
     * Set the value of BasicFlowOfEvents
     *
     * @param BasicFlowOfEvents new value of BasicFlowOfEvents
     */
    public void setBasicFlowOfEvents(List<String> BasicFlowOfEvents) {
        this.BasicFlowOfEvents = BasicFlowOfEvents;
    }

    @ElementCollection
    private List<String> AlternativeFlows;

    /**
     * Get the value of AlternativeFlows
     *
     * @return the value of AlternativeFlows
     */
    public List<String> getAlternativeFlows() {
        return AlternativeFlows;
    }

    /**
     * Set the value of AlternativeFlows
     *
     * @param AlternativeFlows new value of AlternativeFlows
     */
    public void setAlternativeFlows(List<String> AlternativeFlows) {
        this.AlternativeFlows = AlternativeFlows;
    }

    @ElementCollection
    private List<String> Subflows;

    /**
     * Get the value of Subflows
     *
     * @return the value of Subflows
     */
    public List<String> getSubflows() {
        return Subflows;
    }

    /**
     * Set the value of Subflows
     *
     * @param Subflows new value of Subflows
     */
    public void setSubflows(List<String> Subflows) {
        this.Subflows = Subflows;
    }

    @ElementCollection
    private List<String> KeyScenarios;

    /**
     * Get the value of KeyScenarios
     *
     * @return the value of KeyScenarios
     */
    public List<String> getKeyScenarios() {
        return KeyScenarios;
    }

    /**
     * Set the value of KeyScenarios
     *
     * @param KeyScenarios new value of KeyScenarios
     */
    public void setKeyScenarios(List<String> KeyScenarios) {
        this.KeyScenarios = KeyScenarios;
    }

    @ElementCollection
    private List<String> PostConditions;

    /**
     * Get the value of PostConditions
     *
     * @return the value of PostConditions
     */
    public List<String> getPostConditions() {
        return PostConditions;
    }

    /**
     * Set the value of PostConditions
     *
     * @param PostConditions new value of PostConditions
     */
    public void setPostConditions(List<String> PostConditions) {
        this.PostConditions = PostConditions;
    }

    @ElementCollection
    private List<String> SpecialRequirements;

    /**
     * Get the value of SpecialRequirements
     *
     * @return the value of SpecialRequirements
     */
    public List<String> getSpecialRequirements() {
        return SpecialRequirements;
    }

    /**
     * Set the value of SpecialRequirements
     *
     * @param SpecialRequirements new value of SpecialRequirements
     */
    public void setSpecialRequirements(List<String> SpecialRequirements) {
        this.SpecialRequirements = SpecialRequirements;
    }

}
