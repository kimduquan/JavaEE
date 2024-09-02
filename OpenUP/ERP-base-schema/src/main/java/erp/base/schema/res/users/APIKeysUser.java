package erp.base.schema.res.users;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

/**
 * 
 */
@Entity
@NodeEntity
public class APIKeysUser extends Users {

	/**
	 * 
	 */
	@ElementCollection(targetClass = APIKeys.class)
	@CollectionTable(name = "res_users_apikeys", joinColumns = {
			@JoinColumn(name = "user_id")
	})
	@Description("API Keys")
	@Transient
	private List<String> api_key_ids;

	/**
	 * 
	 */
	@OneToMany(targetEntity = APIKeys.class, mappedBy = "user_id")
	@Relationship(type = "API_KEYS")
	private List<APIKeys> api_keys;

	public List<String> getApi_key_ids() {
		return api_key_ids;
	}

	public void setApi_key_ids(List<String> api_key_ids) {
		this.api_key_ids = api_key_ids;
	}

	public List<APIKeys> getApi_keys() {
		return api_keys;
	}

	public void setApi_keys(List<APIKeys> api_keys) {
		this.api_keys = api_keys;
	}
}
