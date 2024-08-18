package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_users_settings")
@Description("User Settings")
public class Settings {

	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@ManyToOne(targetEntity = Users.class)
	@NotNull
	@Description("User")
	private String user_id;
}
