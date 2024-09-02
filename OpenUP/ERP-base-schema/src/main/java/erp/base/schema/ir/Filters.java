package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.ir.actions.Actions;
import erp.base.schema.res.users.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_filters")
@Description("Filters")
@NodeEntity("Filters")
public class Filters {
	
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
	@Description("Filter Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Users.class)
	@Description("User")
	@Property
	@Relationship(type = "USER")
	private String user_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("[]")
	@Property
	private String domain = "[]";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("{}")
	@Property
	private String context = "{}";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("[]")
	@Property
	private String sort = "[]";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Model")
	@Property
	private String model_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Default Filter")
	@Property
	private Boolean is_default;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Actions.class)
	@Description("Action")
	@Property
	@Relationship(type = "ACTION")
	private String action_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean active = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public Boolean getIs_default() {
		return is_default;
	}

	public void setIs_default(Boolean is_default) {
		this.is_default = is_default;
	}

	public String getAction_id() {
		return action_id;
	}

	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
