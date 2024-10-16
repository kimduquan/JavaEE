package epf.roles.schema;

import java.util.Set;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;
import jakarta.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(Roles.ROLE_SET)
@Schema(name = Roles.ROLE_SET, title = "Role Set")
@Entity(name = Roles.ROLE_SET)
@Table(schema = Roles.SCHEMA, name = "ROLE_SET")
public class RoleSet extends EPFEntity {

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
