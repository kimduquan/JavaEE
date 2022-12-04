package epf.cache.util;

import javax.cache.CacheManager;

/**
 * @author PC
 *
 */
public interface CacheProvider {
	
	/**
	 * 
	 */
	String CLIENT_PROVIDER = "com.hazelcast.client.cache.HazelcastClientCachingProvider";
	
	/**
	 * 
	 */
	String MEMBER_PROVIDER = "com.hazelcast.client.cache.HazelcastClientCachingProvider";
	
	CacheManager getManager();
}
