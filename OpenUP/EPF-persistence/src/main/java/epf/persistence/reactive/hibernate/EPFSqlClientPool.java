package epf.persistence.reactive.hibernate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.hibernate.reactive.pool.impl.DefaultSqlClientPool;
import io.vertx.sqlclient.Pool;

/**
 * @author PC
 *
 */
public class EPFSqlClientPool extends DefaultSqlClientPool {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final Map<String, Pool> clientPool = new ConcurrentHashMap<>();
	
	@Override
	protected Pool getTenantPool(final String tenantId) {
		return clientPool.computeIfAbsent(tenantId, ternant -> getPool());
	}
}
