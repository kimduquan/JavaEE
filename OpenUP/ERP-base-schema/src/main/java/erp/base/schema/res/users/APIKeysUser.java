package erp.base.schema.res.users;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class APIKeysUser extends Users {

	@Transient
	private List<Integer> api_key_ids;

	@OneToMany(targetEntity = APIKeys.class, fetch = FetchType.LAZY, mappedBy = "user_id")
	@Description("API Keys")
	private List<APIKeys> api_keys;
}
