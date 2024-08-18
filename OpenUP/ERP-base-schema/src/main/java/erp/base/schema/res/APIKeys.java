package erp.base.schema.res;

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
@Table(name = "res_users_apikeys")
@Description("Users API Keys")
public class APIKeys {

	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@NotNull
	@Description("Description")
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false, updatable = false)
	@ManyToOne(targetEntity = Users.class)
	@NotNull
	private String user_id;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Scope")
	private String scope;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Creation Date")
	private String create_date;
}
