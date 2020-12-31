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
import javax.json.JsonObject;
import javax.persistence.Embedded;
import javax.persistence.NamedNativeQuery;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.Role)
@Schema(name = EPF.Role, title = "Role")
@Entity(name = EPF.Role)
@Table(schema = EPF.Schema, name = "_ROLE")
@JsonbPropertyOrder({
    "name",
    "additionallyPerforms",
    "modifies"
})
@NamedNativeQuery(
        name = Role.GET_USER_ROLES, 
        query = "SELECT GRANTEDROLE\n" +
                "FROM INFORMATION_SCHEMA.RIGHTS\n" +
                "WHERE GRANTEETYPE = 'ROLE'\n" +
                "    AND GRANTEDROLE <> ''\n" +
                "    AND GRANTEE\n" +
                "        IN  (\n" +
                "            SELECT GRANTEDROLE \n" +
                "            FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "            WHERE GRANTEETYPE = 'USER'\n" +
                "                AND GRANTEE = ?\n" +
                "                AND GRANTEDROLE <> ''\n" +
                "                )\n" +
                "UNION\n" +
                "    (\n" +
                "    SELECT GRANTEDROLE \n" +
                "    FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "    WHERE GRANTEETYPE = 'USER'\n" +
                "        AND GRANTEE = ?\n" +
                "        AND GRANTEDROLE <> ''\n" +
                ");"
)
@NamedNativeQuery(
        name = Role.IS_ADMIN,
        query = "SELECT ADMIN FROM INFORMATION_SCHEMA.USERS WHERE NAME = ? AND ADMIN = ?;"
)
public class Role {

    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @Embedded
    private Relationships relationships;
    
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
    @Embedded
    private Staffing staffing;
    
    @Column(name = "KEY_CONSIDERATIONS")
    private JsonObject keyConsiderations;
    
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

    public Relationships getRelationships() {
        return relationships;
    }

    public void setRelationships(Relationships relationships) {
        this.relationships = relationships;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }

    public Staffing getStaffing() {
        return staffing;
    }

    public void setStaffing(Staffing staffing) {
        this.staffing = staffing;
    }

    public JsonObject getKeyConsiderations() {
        return keyConsiderations;
    }

    public void setKeyConsiderations(JsonObject keyConsiderations) {
        this.keyConsiderations = keyConsiderations;
    }

    public MoreInformation getMoreInformation() {
        return moreInformation;
    }

    public void setMoreInformation(MoreInformation moreInformation) {
        this.moreInformation = moreInformation;
    }
    
    public static final String GET_USER_ROLES = "Role.GetUserRoles";
    public static final String IS_ADMIN = "Role.IsAdmin";
}
