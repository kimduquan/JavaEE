/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.ejb.work_products;

import epf.ejb.guidance.checklists.Checklist;
import epf.ejb.guidance.templates.Template;
import epf.ejb.roles.Role;
import epf.ejb.tasks.Task;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class Artifact {

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

    private Role Responsible;

    /**
     * Get the value of Responsible
     *
     * @return the value of Responsible
     */
    public Role getResponsible() {
        return Responsible;
    }

    /**
     * Set the value of Responsible
     *
     * @param Responsible new value of Responsible
     */
    public void setResponsible(Role Responsible) {
        this.Responsible = Responsible;
    }

    private Role ModifiedBy;

    /**
     * Get the value of ModifiedBy
     *
     * @return the value of ModifiedBy
     */
    public Role getModifiedBy() {
        return ModifiedBy;
    }

    /**
     * Set the value of ModifiedBy
     *
     * @param ModifiedBy new value of ModifiedBy
     */
    public void setModifiedBy(Role ModifiedBy) {
        this.ModifiedBy = ModifiedBy;
    }

    private List<Task> InputTo;

    /**
     * Get the value of InputTo
     *
     * @return the value of InputTo
     */
    public List<Task> getInputTo() {
        return InputTo;
    }

    /**
     * Set the value of InputTo
     *
     * @param InputTo new value of InputTo
     */
    public void setInputTo(List<Task> InputTo) {
        this.InputTo = InputTo;
    }

    private List<Task> OutputFrom;

    /**
     * Get the value of OutputFrom
     *
     * @return the value of OutputFrom
     */
    public List<Task> getOutputFrom() {
        return OutputFrom;
    }

    /**
     * Set the value of OutputFrom
     *
     * @param OutputFrom new value of OutputFrom
     */
    public void setOutputFrom(List<Task> OutputFrom) {
        this.OutputFrom = OutputFrom;
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

    private String BriefOutline;

    /**
     * Get the value of BriefOutline
     *
     * @return the value of BriefOutline
     */
    public String getBriefOutline() {
        return BriefOutline;
    }

    /**
     * Set the value of BriefOutline
     *
     * @param BriefOutline new value of BriefOutline
     */
    public void setBriefOutline(String BriefOutline) {
        this.BriefOutline = BriefOutline;
    }

    private Template Templates;

    /**
     * Get the value of Templates
     *
     * @return the value of Templates
     */
    public Template getTemplates() {
        return Templates;
    }

    /**
     * Set the value of Templates
     *
     * @param Templates new value of Templates
     */
    public void setTemplates(Template Templates) {
        this.Templates = Templates;
    }

    private String ImpactOfNotHaving;

    /**
     * Get the value of ImpactOfNotHaving
     *
     * @return the value of ImpactOfNotHaving
     */
    public String getImpactOfNotHaving() {
        return ImpactOfNotHaving;
    }

    /**
     * Set the value of ImpactOfNotHaving
     *
     * @param ImpactOfNotHaving new value of ImpactOfNotHaving
     */
    public void setImpactOfNotHaving(String ImpactOfNotHaving) {
        this.ImpactOfNotHaving = ImpactOfNotHaving;
    }

    private String ReasonsForNotNeeding;

    /**
     * Get the value of ReasonsForNotNeeding
     *
     * @return the value of ReasonsForNotNeeding
     */
    public String getReasonsForNotNeeding() {
        return ReasonsForNotNeeding;
    }

    /**
     * Set the value of ReasonsForNotNeeding
     *
     * @param ReasonsForNotNeeding new value of ReasonsForNotNeeding
     */
    public void setReasonsForNotNeeding(String ReasonsForNotNeeding) {
        this.ReasonsForNotNeeding = ReasonsForNotNeeding;
    }

    private String RepresentationOptions;

    /**
     * Get the value of RepresentationOptions
     *
     * @return the value of RepresentationOptions
     */
    public String getRepresentationOptions() {
        return RepresentationOptions;
    }

    /**
     * Set the value of RepresentationOptions
     *
     * @param RepresentationOptions new value of RepresentationOptions
     */
    public void setRepresentationOptions(String RepresentationOptions) {
        this.RepresentationOptions = RepresentationOptions;
    }

    private List<Checklist> Checklists;

    /**
     * Get the value of Checklists
     *
     * @return the value of Checklists
     */
    public List<Checklist> getChecklists() {
        return Checklists;
    }

    /**
     * Set the value of Checklists
     *
     * @param Checklists new value of Checklists
     */
    public void setChecklists(List<Checklist> Checklists) {
        this.Checklists = Checklists;
    }

    private Artifact ContainerArtifact;

    /**
     * Get the value of ContainerArtifact
     *
     * @return the value of ContainerArtifact
     */
    public Artifact getContainerArtifact() {
        return ContainerArtifact;
    }

    /**
     * Set the value of ContainerArtifact
     *
     * @param ContainerArtifact new value of ContainerArtifact
     */
    public void setContainerArtifact(Artifact ContainerArtifact) {
        this.ContainerArtifact = ContainerArtifact;
    }

}
