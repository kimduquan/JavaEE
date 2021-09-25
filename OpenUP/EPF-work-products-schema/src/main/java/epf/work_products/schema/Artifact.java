/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.work_products.schema;

import java.io.Serializable;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.persistence.Embedded;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;
import epf.schema.EntityListener;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;

import javax.persistence.EntityListeners;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.ARTIFACT)
@Schema(name = EPF.ARTIFACT, title = "Artifact")
@Entity(name = EPF.ARTIFACT)
@Table(schema = EPF.SCHEMA, name = "ARTIFACT")
@JsonbPropertyOrder({
    "name",
    "fulfilledSlots"
})
@EntityListeners({EntityListener.class})
public class Artifact implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    @Embedded
    @NotNull
    private Description description;
    
    /**
     * 
     */
    @Embedded
    @NotNull
    private Illustrations illustrations;
    
    /**
     * 
     */
    @Column(name = "KEY_CONSIDERATIONS")
    private JsonObject keyConsiderations;
    
    /**
     * 
     */
    @Embedded
    @NotNull
    private Tailoring tailoring;
    
    /**
     * 
     */
    @Embedded
    @NotNull
    private MoreInformation moreInformation;
    
    @Override
    public String toString() {
    	return String.format("%s@%s", getClass().getName(), name);
    }

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

    public Description getDescription() {
        return description;
    }

    public void setDescription(final Description description) {
        this.description = description;
    }

    public Illustrations getIllustrations() {
        return illustrations;
    }

    public void setIllustrations(final Illustrations illustrations) {
        this.illustrations = illustrations;
    }

    public JsonObject getKeyConsiderations() {
        return keyConsiderations;
    }

    public void setKeyConsiderations(final JsonObject keyConsiderations) {
        this.keyConsiderations = keyConsiderations;
    }

    public Tailoring getTailoring() {
        return tailoring;
    }

    public void setTailoring(final Tailoring tailoring) {
        this.tailoring = tailoring;
    }

    public MoreInformation getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(final MoreInformation moreInformation) {
        this.moreInformation = moreInformation;
    }
}
