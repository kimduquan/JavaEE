package epf.query.internal;

import java.util.Optional;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public class QueryKey {
	
	/**
	 *
	 */
	private final String tenant;

	/**
	 * 
	 */
	private final String schema;
	/**
	 * 
	 */
	private final String entity;
	
	/**
	 * @param tenant
	 * @param schema
	 * @param entity
	 */
	public QueryKey(final String tenant,final String schema, final String entity) {
		this.tenant = tenant;
		this.schema = schema;
		this.entity = entity;
	}
	
	@Override
	public String toString() {
		if(tenant != null) {
			return StringUtil.join(tenant, schema, entity);
		}
		else {
			return StringUtil.join(schema, entity);
		}
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static Optional<QueryKey> parseString(final String key) {
		final String[] fragments = StringUtil.split(key);
		if(fragments.length == 3) {
			return Optional.of(new QueryKey(fragments[0], fragments[1], fragments[2]));
		}
		else if(fragments.length == 2) {
			return Optional.of(new QueryKey(null, fragments[0], fragments[1]));
		}
		return Optional.empty();
	}

	public String getSchema() {
		return schema;
	}

	public String getEntity() {
		return entity;
	}

	public String getTenant() {
		return tenant;
	}
}
