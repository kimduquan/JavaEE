package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "res_users_log")
@Description("Users Log")
public class Log {
	
	@Id
	private int id;
	
	@Column
	private Integer create_uid;
}
