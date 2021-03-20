/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.tasks;

import epf.schema.tasks.section.Relationships;
import epf.schema.tasks.section.MoreInformation;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.json.JsonObject;
import javax.persistence.Embedded;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.TASK)
@Schema(name = EPF.TASK, title = "Task")
@Entity(name = EPF.TASK)
@Table(schema = EPF.SCHEMA, name = "TASK")
@JsonbPropertyOrder({
    "name",
    "mandatory",
    "optional",
    "outputs"
})
public class Task {

    /**
     * 
     */
    @Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
    
    /**
     * 
     */
    @Column(name = "SUMMARY")
    private String summary;
    
    /**
     * 
     */
    @Column(name = "PURPOSE")
    private JsonObject purpose;
    
    /**
     * 
     */
    @Embedded
    @NotNull
    private Relationships relationships;
    
    /**
     * 
     */
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
    /**
     * 
     */
    @Column(name = "STEPS")
    private JsonObject steps;
    
    /**
     * 
     */
    @Column(name = "KEY_CONSIDERATIONS")
    private JsonObject keyConsiderations;
    
    /**
     * 
     */
    @Column(name = "ALTERNATIVES")
    private JsonObject alternatives;
    
    /**
     * 
     */
    @Embedded
    @NotNull
    private MoreInformation moreInformation;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }

    public JsonObject getPurpose() {
        return purpose;
    }

    public void setPurpose(final JsonObject purpose) {
        this.purpose = purpose;
    }

    public Relationships getRelationships() {
        return relationships;
    }

    public void setRelationships(final Relationships relationships) {
        this.relationships = relationships;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(final JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }

    public JsonObject getSteps() {
        return steps;
    }

    public void setSteps(final JsonObject steps) {
        this.steps = steps;
    }

    public JsonObject getKeyConsiderations() {
        return keyConsiderations;
    }

    public void setKeyConsiderations(final JsonObject keyConsiderations) {
        this.keyConsiderations = keyConsiderations;
    }

    public JsonObject getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(final JsonObject alternatives) {
        this.alternatives = alternatives;
    }

    public MoreInformation getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(final MoreInformation moreInformation) {
        this.moreInformation = moreInformation;
    }
}
