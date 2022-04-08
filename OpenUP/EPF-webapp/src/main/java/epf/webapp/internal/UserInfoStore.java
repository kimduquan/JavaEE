package epf.webapp.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import epf.webapp.security.TokenPrincipal;
import epf.webapp.security.auth.IDTokenPrincipal;
import epf.webapp.security.util.JwtUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class UserInfoStore {

	/**
	 * 
	 */
	private transient final Map<String, Map<String, Object>> userInfos = new ConcurrentHashMap<>();
	
	/**
	 * @param name
	 * @param userInfo
	 */
	public void putUserInfo(final String name, final Map<String, Object> userInfo) {
		userInfos.put(name, userInfo);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Map<String, Object> getUserInfo(final String name) {
		return userInfos.get(name);
	}
	
	/**
	 * @param principal
	 * @throws Exception 
	 */
	public void putUserInfo(@Observes final IDTokenPrincipal principal) throws Exception {
		userInfos.put(principal.getName(), JwtUtil.decode(principal.getId_token()).getClaimsMap());
	}
	
	/**
	 * @param principal
	 * @throws Exception
	 */
	public void putUserInfo(@Observes final TokenPrincipal principal) throws Exception {
		userInfos.put(principal.getName(), JwtUtil.decode(principal.getRememberToken().orElse(principal.getRawToken())).getClaimsMap());
	}
}
