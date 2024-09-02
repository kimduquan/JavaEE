package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_users_apikeys")
@Description("Users API Keys")
@NodeEntity("Users API Keys")
public class APIKeys {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@NotNull
	@Description("Description")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@ManyToOne(targetEntity = Users.class)
	@NotNull
	@Property
	@Relationship(type = "USER")
	private String user_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Scope")
	@Property
	private String scope;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Creation Date")
	@Property
	private String create_date;

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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
