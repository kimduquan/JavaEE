package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "UsersLog")
@Table(name = "res_users_log")
@Description("Users Log")
public class Log {
	
	@Id
	private int id;
	
	@Column
	private Integer create_uid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(Integer create_uid) {
		this.create_uid = create_uid;
	}
}
