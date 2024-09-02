package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "res_users_log")
@Description("Users Log")
@NodeEntity("Users Log")
public class Log {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
