package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
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
	public enum State {
		/**
		 * 
		 */
		todo,
		/**
		 * 
		 */
		done,
		/**
		 * 
		 */
		fail
	}
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false, insertable = false, updatable = false)
	@Description("User")
	@Transient
	private Integer user_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Users.class)
	@JoinColumn(name = "user_id")
	@Relationship(type = "USER")
	private Users user;
	
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
	private State state = State.todo;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getUser_id_int() {
		return user_id_int;
	}

	public void setUser_id_int(Integer user_id_int) {
		this.user_id_int = user_id_int;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
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
