package epf.cache.persistence;

import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public class EntityKey {

	/**
	 * 
	 */
	private final String schema;
	/**
	 * 
	 */
	private final String entity;
	/**
	 * 
	 */
	private final Object id;
	
	/**
	 * @param schema
	 * @param entity
	 * @param id
	 */
	public EntityKey(final String schema, final String entity, final Object id) {
		this.schema = schema;
		this.entity = entity;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return StringUtil.join(schema, entity, String.valueOf(id));
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static EntityKey valueOf(final String key) {
		final String[] fragments = StringUtil.split(key);
		if(fragments.length == 3) {
			return new EntityKey(fragments[0], fragments[1], fragments[2]);
		}
		return null;
	}

	public String getSchema() {
		return schema;
	}

	public String getEntity() {
		return entity;
	}

	public Object getId() {
		return id;
	}
}
