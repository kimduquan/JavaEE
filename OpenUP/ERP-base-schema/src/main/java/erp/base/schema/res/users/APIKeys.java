package erp.base.schema.res.users;

import java.util.Date;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
	@NotNull
	@Transient
	private String user_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Users.class)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	@NotNull
	@Relationship(type = "USER")
	private Users user;
	
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
	private Date create_date;

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

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
}
