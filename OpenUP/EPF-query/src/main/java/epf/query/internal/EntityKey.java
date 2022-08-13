package epf.query.internal;

import java.util.Optional;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
public class EntityKey {
	
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
	 * 
	 */
	private final Object id;
	
	/**
	 * @param tenant
	 * @param schema
	 * @param entity
	 * @param id
	 */
	protected EntityKey(final String tenant, final String schema, final String entity, final Object id) {
		this.tenant = tenant;
		this.schema = schema;
		this.entity = entity;
		this.id = id;
	}
	
	@Override
	public String toString() {
		if(tenant != null) {
			return StringUtil.join(tenant, schema, entity, String.valueOf(id));
		}
		else {
			return StringUtil.join(schema, entity, String.valueOf(id));
		}
	}
	
	/**
	 * @param key
	 * @return
	 */
	public static Optional<EntityKey> parseString(final String key) {
		final String[] fragments = StringUtil.split(key);
		if(fragments.length == 4) {
			return Optional.of(new EntityKey(fragments[0], fragments[1], fragments[2], fragments[3]));
		}
		else if(fragments.length == 3) {
			return Optional.of(new EntityKey(null, fragments[0], fragments[1], fragments[2]));
		}
		return Optional.empty();
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

	public String getTenant() {
		return tenant;
	}
}
