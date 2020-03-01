/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.tasks;

import epf.ejb.roles.Role;
import epf.ejb.work_products.Artifact;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Task {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
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

    private Role PrimaryPerformer;

    /**
     * Get the value of PrimaryPerformer
     *
     * @return the value of PrimaryPerformer
     */
    public Role getPrimaryPerformer() {
        return PrimaryPerformer;
    }

    /**
     * Set the value of PrimaryPerformer
     *
     * @param PrimaryPerformer new value of PrimaryPerformer
     */
    public void setPrimaryPerformer(Role PrimaryPerformer) {
        this.PrimaryPerformer = PrimaryPerformer;
    }

    private List<Role> AdditionalPerformers;

    /**
     * Get the value of AdditionalPerformers
     *
     * @return the value of AdditionalPerformers
     */
    public List<Role> getAdditionalPerformers() {
        return AdditionalPerformers;
    }

    /**
     * Set the value of AdditionalPerformers
     *
     * @param AdditionalPerformers new value of AdditionalPerformers
     */
    public void setAdditionalPerformers(List<Role> AdditionalPerformers) {
        this.AdditionalPerformers = AdditionalPerformers;
    }

    private List<Artifact> Mandatory;

    /**
     * Get the value of Mandatory
     *
     * @return the value of Mandatory
     */
    public List<Artifact> getMandatory() {
        return Mandatory;
    }

    /**
     * Set the value of Mandatory
     *
     * @param Mandatory new value of Mandatory
     */
    public void setMandatory(List<Artifact> Mandatory) {
        this.Mandatory = Mandatory;
    }

    private List<Artifact> Optional;

    /**
     * Get the value of Optional
     *
     * @return the value of Optional
     */
    public List<Artifact> getOptional() {
        return Optional;
    }

    /**
     * Set the value of Optional
     *
     * @param Optional new value of Optional
     */
    public void setOptional(List<Artifact> Optional) {
        this.Optional = Optional;
    }

    private List<Artifact> Outputs;

    /**
     * Get the value of Outputs
     *
     * @return the value of Outputs
     */
    public List<Artifact> getOutputs() {
        return Outputs;
    }

    /**
     * Set the value of Outputs
     *
     * @param Outputs new value of Outputs
     */
    public void setOutputs(List<Artifact> Outputs) {
        this.Outputs = Outputs;
    }

    private String MainDescription;

    /**
     * Get the value of MainDescription
     *
     * @return the value of MainDescription
     */
    public String getMainDescription() {
        return MainDescription;
    }

    /**
     * Set the value of MainDescription
     *
     * @param MainDescription new value of MainDescription
     */
    public void setMainDescription(String MainDescription) {
        this.MainDescription = MainDescription;
    }

    private List Steps;

    /**
     * Get the value of Steps
     *
     * @return the value of Steps
     */
    public List getSteps() {
        return Steps;
    }

    /**
     * Set the value of Steps
     *
     * @param Steps new value of Steps
     */
    public void setSteps(List Steps) {
        this.Steps = Steps;
    }

    private String KeyConsiderations;

    /**
     * Get the value of KeyConsiderations
     *
     * @return the value of KeyConsiderations
     */
    public String getKeyConsiderations() {
        return KeyConsiderations;
    }

    /**
     * Set the value of KeyConsiderations
     *
     * @param KeyConsiderations new value of KeyConsiderations
     */
    public void setKeyConsiderations(String KeyConsiderations) {
        this.KeyConsiderations = KeyConsiderations;
    }

    private String Alternatives;

    /**
     * Get the value of Alternatives
     *
     * @return the value of Alternatives
     */
    public String getAlternatives() {
        return Alternatives;
    }

    /**
     * Set the value of Alternatives
     *
     * @param Alternatives new value of Alternatives
     */
    public void setAlternatives(String Alternatives) {
        this.Alternatives = Alternatives;
    }

}
