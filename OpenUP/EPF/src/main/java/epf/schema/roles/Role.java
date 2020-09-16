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

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Role",
        title = "Role"
)
@Entity
@Table(name = "_ROLE", schema = "EPF")
@JsonbPropertyOrder({
    "name",
    "additionallyPerforms",
    "modifies"
})
@NamedNativeQuery(
        name = "Role.GetUserRoles", 
        query = "SELECT GRANTEDROLE \n" +
                "FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "WHERE \n" +
                "    GRANTEETYPE = 'ROLE' \n" +
                "    AND (\n" +
                "        GRANTEE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        OR\n" +
                "        GRANTEDROLE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        )\n" +
                "UNION\n" +
                "SELECT GRANTEE \n" +
                "FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "WHERE \n" +
                "    GRANTEETYPE = 'ROLE' \n" +
                "    AND (\n" +
                "        GRANTEE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        OR\n" +
                "        GRANTEDROLE \n" +
                "            IN (\n" +
                "                SELECT GRANTEDROLE \n" +
                "                FROM INFORMATION_SCHEMA.RIGHTS \n" +
                "                WHERE GRANTEETYPE = 'USER' \n" +
                "                    AND GRANTEE = ?\n" +
                "                )\n" +
                "        );"
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
}
