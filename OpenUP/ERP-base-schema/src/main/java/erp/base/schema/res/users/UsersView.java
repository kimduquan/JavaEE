package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * 
 */
@Entity
@NodeEntity
public class UsersView extends Users {

	/**
	 * 
	 */
	@Column
	@Description("User Group Warning")
	@Property
	private String user_group_warning;

	public String getUser_group_warning() {
		return user_group_warning;
	}

	public void setUser_group_warning(String user_group_warning) {
		this.user_group_warning = user_group_warning;
	}
}
