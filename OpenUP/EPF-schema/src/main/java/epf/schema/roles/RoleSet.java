/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.roles;

import java.util.List;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;
import javax.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.ROLE_SET)
@Schema(name = EPF.ROLE_SET, title = "Role Set")
@Entity(name = EPF.ROLE_SET)
@Table(schema = EPF.SCHEMA, name = "ROLE_SET")
@JsonbPropertyOrder({
    "name",
    "roles"
})
@NamedQuery(
        name = RoleSet.ROLES, 
        query = "SELECT rs FROM EPF_RoleSet AS rs")
public class RoleSet {

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
    @ManyToMany
    @JoinTable(
            name = "ROLES",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(name = "ROLE_SET"),
            inverseJoinColumns = @JoinColumn(name = "ROLE"),
            indexes = {@Index(columnList = "ROLE_SET")}
    )
    private List<Role> roles;
    
    /**
     * 
     */
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
    /**
     * 
     */
    public static final String ROLES = "EPF_RoleSet.Roles";

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
    
    @Name("Roles")
    public List<Role> getRoles(){
        return roles;
    }

    public void setRoles(final List<Role> roles){
        this.roles = roles;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(final JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }
}
