package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_users_deletion")
@Description("Users Deletion Request")
public class Deletion {
	
	public enum State {
		@Description("To Do")
		todo,
		@Description("Done")
		done,
		@Description("Failed")
		fail
	}
	
	@Id
	private int id;

	@Transient
	private Integer user_id;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Description("User")
	private Users user;
	
	@Column
	@Description("User Id")
	private Integer user_id_int;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("todo")
	@Description("State")
	private State state = State.todo;
}
