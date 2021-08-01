/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.roles;

import epf.schema.roles.section.Staffing;
import epf.schema.roles.section.MoreInformation;
import epf.schema.roles.section.Relationships;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import javax.json.JsonObject;
import javax.persistence.Embedded;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.ROLE)
@Schema(name = EPF.ROLE, title = "Role")
@Entity(name = EPF.ROLE)
@Table(schema = EPF.SCHEMA, name = "EPF_ROLE")
@JsonbPropertyOrder({
    "name",
    "additionallyPerforms",
    "modifies"
})
public class Role implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public static final String DEFAULT_ROLE = "Any_Role";

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
    @Embedded
    @NotNull
    private Staffing staffing;
    
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

    public Staffing getStaffing() {
        return staffing;
    }

    public void setStaffing(final Staffing staffing) {
        this.staffing = staffing;
    }

    public JsonObject getKeyConsiderations() {
        return keyConsiderations;
    }

    public void setKeyConsiderations(final JsonObject keyConsiderations) {
        this.keyConsiderations = keyConsiderations;
    }

    public MoreInformation getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(final MoreInformation moreInformation) {
        this.moreInformation = moreInformation;
    }
}
