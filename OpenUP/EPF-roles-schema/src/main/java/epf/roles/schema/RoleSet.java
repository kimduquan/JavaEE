package epf.roles.schema;

import java.io.Serializable;
import java.util.Set;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import javax.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(Roles.ROLE_SET)
@Schema(name = Roles.ROLE_SET, title = "Role Set")
@Entity(name = Roles.ROLE_SET)
@Table(schema = Roles.SCHEMA, name = "ROLE_SET")
public class RoleSet implements Serializable {

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
    @ManyToMany
    @JoinTable(
            name = "ROLES",
            schema = Roles.SCHEMA,
            joinColumns = @JoinColumn(name = "ROLE_SET"),
            inverseJoinColumns = @JoinColumn(name = "ROLE"),
            indexes = {@Index(columnList = "ROLE_SET")}
    )
    private Set<Role> roles;
    
    /**
     * 
     */
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
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
    
    @Name("Roles")
    public Set<Role> getRoles(){
        return roles;
    }

    public void setRoles(final Set<Role> roles){
        this.roles = roles;
    }

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(final JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }
}
