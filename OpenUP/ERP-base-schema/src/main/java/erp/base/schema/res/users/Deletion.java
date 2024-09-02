package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
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
@Table(name = "res_users_deletion")
@Description("Users Deletion Request")
@NodeEntity("Users Deletion Request")
public class Deletion {
	
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
	@ManyToOne(targetEntity = Users.class)
	@Description("User")
	@Property
	@Relationship(type = "USER")
	private String user_id;
	
	/**
	 * 
	 */
	@Column
	@Description("User Id")
	@Property
	private Integer user_id_int;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("todo")
	@Description("State")
	@Property
	private String state = "todo";

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Integer getUser_id_int() {
		return user_id_int;
	}

	public void setUser_id_int(Integer user_id_int) {
		this.user_id_int = user_id_int;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
