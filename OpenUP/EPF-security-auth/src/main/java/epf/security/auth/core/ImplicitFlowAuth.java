package epf.security.auth.core;

/**
 * @author PC
 *
 */
public interface ImplicitFlowAuth {

	/**
	 * @param authRequest
	 * @return
	 * @throws ImplicitAuthError
	 */
	ImplicitAuthResponse authenticate(final ImplicitAuthRequest authRequest) throws ImplicitAuthError;
}
