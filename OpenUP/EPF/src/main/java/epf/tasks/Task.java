/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.tasks;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.json.JsonObject;
import javax.persistence.Embedded;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Task",
        title = "Task"
)
@Entity
@Table(name = "TASK")
@JsonbPropertyOrder({
    "name",
    "mandatory",
    "optional",
    "outputs"
})
public class Task {

    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @Column(name = "PURPOSE")
    private String purpose;
    
    @Embedded
    private Relationships relationships;
    
    @Column(name = "MAIN_DESCRIPTION")
    private String mainDescription;
    
    @Column(name = "STEPS")
    private JsonObject steps;
    
    @Column(name = "KEY_CONSIDERATIONS")
    private JsonObject keyConsiderations;
    
    @Column(name = "ALTERNATIVES")
    private String alternatives;
    
    @Embedded
    private MoreInformation moreInformation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Relationships getRelationships() {
        return relationships;
    }

    public void setRelationships(Relationships relationships) {
        this.relationships = relationships;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public JsonObject getSteps() {
        return steps;
    }

    public void setSteps(JsonObject steps) {
        this.steps = steps;
    }

    public JsonObject getKeyConsiderations() {
        return keyConsiderations;
    }

    public void setKeyConsiderations(JsonObject keyConsiderations) {
        this.keyConsiderations = keyConsiderations;
    }

    public String getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(String alternatives) {
        this.alternatives = alternatives;
    }

    public MoreInformation getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(MoreInformation moreInformation) {
        this.moreInformation = moreInformation;
    }
}
