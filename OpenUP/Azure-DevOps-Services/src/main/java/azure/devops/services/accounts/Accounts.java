package azure.devops.services.accounts;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author PC
 *
 */
@Path("accounts")
public interface Accounts {

	/**
	 * Get a list of accounts for a specific owner or a specific member. 
	 * One of the following parameters is required: ownerId, memberId.
	 * @param api_version Version of the API to use. This should be set to '6.1-preview.1' to use this version of the api.
	 * @param memberId ID for a member of the accounts.
	 * @param ownerId ID for the owner of the accounts.
	 * @param properties
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Account[] List(
			@QueryParam("api-version") 
			final String api_version,
			@QueryParam("memberId")
			final String memberId,
			@QueryParam("ownerId")
			final String ownerId,
			@QueryParam("properties")
			final String properties);
}
