package epf.cache.persistence;

import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public class QueryKey {

	/**
	 * 
	 */
	private final String schema;
	/**
	 * 
	 */
	private final String entity;
	
	/**
	 * @param schema
	 * @param entity
	 */
	public QueryKey(final String schema, final String entity) {
		this.schema = schema;
		this.entity = entity;
	}
	
	@Override
	public String toString() {
		return StringUtil.join(schema, entity);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static QueryKey valueOf(final String key) {
		final String[] fragments = StringUtil.split(key);
		if(fragments.length == 2) {
			return new QueryKey(fragments[0], fragments[1]);
		}
		return null;
	}

	public String getSchema() {
		return schema;
	}

	public String getEntity() {
		return entity;
	}
}
