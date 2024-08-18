package erp.base.schema.res;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * 
 */
@Entity
public class APIKeysUser extends Users {

	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = APIKeys.class)
	@ElementCollection(targetClass = APIKeys.class)
	@CollectionTable(name = "res_users_apikeys")
	@Description("API Keys")
	private List<String> api_key_ids;
}
