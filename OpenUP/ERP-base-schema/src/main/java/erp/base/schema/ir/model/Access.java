package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.res.groups.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_access")
@Description("Model Access")
@NodeEntity("Model Access")
public class Access {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
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
	@ManyToOne(targetEntity = Groups.class)
	@Description("Group")
	@Property
	@Relationship(type = "GROUP")
	private String group_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Read Access")
	@Property
	private Boolean perm_read;
	
	/**
	 * 
	 */
	@Column
	@Description("Write Access")
	@Property
	private Boolean perm_write;
	
	/**
	 * 
	 */
	@Column
	@Description("Create Access")
	@Property
	private Boolean perm_create;
	
	/**
	 * 
	 */
	@Column
	@Description("Delete Access")
	@Property
	private Boolean perm_unlink;

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

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
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
