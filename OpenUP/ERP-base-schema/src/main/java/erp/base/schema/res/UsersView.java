package erp.base.schema.res;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * 
 */
@Entity
public class UsersView extends Users {

	/**
	 * 
	 */
	@Column
	@Description("User Group Warning")
	private String user_group_warning;
}
