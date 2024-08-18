package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
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
public class Deletion {

	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Users.class)
	@Description("User")
	private String user_id;
	
	/**
	 * 
	 */
	@Column
	@Description("User Id")
	private Integer user_id_int;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("todo")
	@Description("State")
	private String state = "todo";
}
