package azure.devops.services;

import javax.ws.rs.QueryParam;

/**
 * @author PC
 *
 */
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
	Account[] List(
			@QueryParam("api-version") 
			String api_version,
			@QueryParam("memberId")
			String memberId,
			@QueryParam("ownerId")
			String ownerId,
			@QueryParam("properties")
			String properties);
}
