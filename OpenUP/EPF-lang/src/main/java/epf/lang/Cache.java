package epf.lang;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.lang.schema.ollama.ChatRequest;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class Cache implements HealthCheck {
	
	/**
	 * 
	 */
	private javax.cache.Cache<String, ChatRequest> chats;
	
	@PostConstruct
	protected void postConstruct() {
        final MutableConfiguration<String, ChatRequest> config = new MutableConfiguration<>();
		final CacheManager manager = Caching.getCachingProvider().getCacheManager();
		chats = manager.getCache(Naming.Lang.Internal.OLLAMA);
		if(chats == null) {
			chats = manager.createCache(Naming.Lang.Internal.OLLAMA, config);
		}
	}
	
	@PreDestroy
	void preDestroy() {
		chats.close();
	}
	
	/**
	 * @param id
	 * @return
	 */
	public ChatRequest get(final String id) {
		return chats.get(id);
	}
	
	/**
	 * @param id
	 * @param chat
	 */
	public void put(final String id, final ChatRequest chat) {
		chats.put(id, chat);
	}
	
	/**
	 * @param id
	 * @return
	 */
	public ChatRequest remove(final String id) {
		return chats.getAndRemove(id);
	}

	@Override
	public HealthCheckResponse call() {
		if(chats != null && !chats.isClosed()) {
			return HealthCheckResponse.up("EPF-lang-cache");
		}
		return HealthCheckResponse.down("EPF-lang-cache");
	}
}
