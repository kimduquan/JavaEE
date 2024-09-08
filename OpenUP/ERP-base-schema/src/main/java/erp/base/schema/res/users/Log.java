package erp.base.schema.res.users;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import jakarta.persistence.Column;
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
	
	/**
	 * 
	 */
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
