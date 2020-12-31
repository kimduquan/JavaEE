/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products;

import epf.schema.work_products.section.Tailoring;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Embedded;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.Artifact)
@Schema(name = EPF.Artifact, title = "Artifact")
@Entity(name = EPF.Artifact)
@Table(schema = EPF.Schema, name = "ARTIFACT")
@JsonbPropertyOrder({
    "name",
    "fulfilledSlots"
})
public class Artifact {

    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @Column(name = "PURPOSE")
    private JsonObject purpose;
    
    @Embedded
    private Relationships relationships;
    
    @Embedded
    private Description description;
    
    @Embedded
    private Illustrations illustrations;
    
    @Column(name = "KEY_CONSIDERATIONS")
    private JsonObject keyConsiderations;
    
    @Embedded
    private Tailoring tailoring;
    
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

    public JsonObject getPurpose() {
        return purpose;
    }

    public void setPurpose(JsonObject purpose) {
        this.purpose = purpose;
    }

    public Relationships getRelationships() {
        return relationships;
    }

    public void setRelationships(Relationships relationships) {
        this.relationships = relationships;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Illustrations getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(Illustrations illustrations) {
        this.illustrations = illustrations;
    }

    public JsonObject getKeyConsiderations() {
        return keyConsiderations;
    }

    public void setKeyConsiderations(JsonObject keyConsiderations) {
        this.keyConsiderations = keyConsiderations;
    }

    public Tailoring getTailoring() {
        return tailoring;
    }

    public void setTailoring(Tailoring tailoring) {
        this.tailoring = tailoring;
    }

    public MoreInformation getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(MoreInformation moreInformation) {
        this.moreInformation = moreInformation;
    }
}
