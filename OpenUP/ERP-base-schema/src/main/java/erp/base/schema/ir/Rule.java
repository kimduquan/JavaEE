package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.ir.model.Model;
import erp.base.schema.res.groups.Groups;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_rule")
@Description("Record Rule")
@NodeEntity("Record Rule")
public class Rule {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Model.class)
	@NotNull
	@Description("Model")
	@Property
	@Relationship(type = "MODEL")
	private String model_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups")
	@Property
	@Relationship(type = "GROUPS")
	private List<String> groups;
	
	/**
	 * 
	 */
	@Column
	@Description("Domain")
	@Property
	private String domain_force;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Read")
	@Property
	private Boolean perm_read = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Write")
	@Property
	private Boolean perm_write = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Create")
	@Property
	private Boolean perm_create = true;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Description("Delete")
	@Property
	private Boolean perm_unlink = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public String getDomain_force() {
		return domain_force;
	}

	public void setDomain_force(String domain_force) {
		this.domain_force = domain_force;
	}

	public Boolean getPerm_read() {
		return perm_read;
	}

	public void setPerm_read(Boolean perm_read) {
		this.perm_read = perm_read;
	}

	public Boolean getPerm_write() {
		return perm_write;
	}

	public void setPerm_write(Boolean perm_write) {
		this.perm_write = perm_write;
	}

	public Boolean getPerm_create() {
		return perm_create;
	}

	public void setPerm_create(Boolean perm_create) {
		this.perm_create = perm_create;
	}

	public Boolean getPerm_unlink() {
		return perm_unlink;
	}

	public void setPerm_unlink(Boolean perm_unlink) {
		this.perm_unlink = perm_unlink;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
