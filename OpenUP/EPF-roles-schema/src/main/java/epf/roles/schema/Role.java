package epf.roles.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.json.JsonObject;
import jakarta.persistence.Embedded;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.roles.schema.section.MoreInformation;
import epf.roles.schema.section.Relationships;
import epf.roles.schema.section.Staffing;
import epf.schema.utility.EPFEntity;

/**
 *
 * @author FOXCONN
 */
@Type(Roles.ROLE)
@Schema(name = Roles.ROLE, title = "Role")
@Entity(name = Roles.ROLE)
@Table(schema = Roles.SCHEMA, name = "EPF_ROLE")
public class Role extends EPFEntity {
	
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
