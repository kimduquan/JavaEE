package epf.query.internal;

import java.util.Optional;
import epf.util.StringUtil;

public class EntityKey {

	private final String schema;
	
	private final String entity;
	
	private final Object id;
	
	protected EntityKey(final String schema, final String entity, final Object id) {
		this.schema = schema;
		this.entity = entity;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return StringUtil.join(schema, entity, String.valueOf(id));
	}
	
	public static Optional<EntityKey> parseString(final String key) {
		final String[] fragments = StringUtil.split(key);
		if(fragments.length >= 3) {
			return Optional.of(new EntityKey(fragments[0], fragments[1], fragments[2]));
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
}
