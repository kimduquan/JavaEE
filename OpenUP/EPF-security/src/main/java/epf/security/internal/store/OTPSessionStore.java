package epf.security.internal.store;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.security.internal.Session;
import epf.security.internal.token.TokenIdGenerator;
import epf.util.MapUtil;

@ApplicationScoped
public class OTPSessionStore {
	
	private transient final Map<String, Session> sessions = new ConcurrentHashMap<>();
	
	@Inject
	@ConfigProperty(name = Naming.Security.OTP.EXPIRE_DURATION)
	private String expireDuration;
	
	public String putSession(final String name, final Set<String> groups, final Map<String, Object> claims) {
		final TokenIdGenerator generator = new TokenIdGenerator();
		final String tokenId = generator.generate();
		sessions.put(tokenId, new Session(name, groups, claims));
		return tokenId;
	}
	
	public Optional<Session> removeSession(final String oneTimePassword) {
		return MapUtil.remove(sessions, oneTimePassword);
	}
}
