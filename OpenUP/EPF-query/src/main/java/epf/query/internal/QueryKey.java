package epf.query.internal;

import java.util.Optional;
import epf.util.StringUtil;

public class QueryKey {

	private final String schema;
	
	private final String entity;
	
	public QueryKey(final String schema, final String entity) {
		this.schema = schema;
		this.entity = entity;
	}
	
	@Override
	public String toString() {
		return StringUtil.join(schema, entity);
	}
	
	public static Optional<QueryKey> parseString(final String key) {
		final String[] fragments = StringUtil.split(key);
		if(fragments.length >= 2) {
			return Optional.of(new QueryKey(fragments[0], fragments[1]));
		}
		return Optional.empty();
	}

	public String getSchema() {
		return schema;
	}

	public String getEntity() {
		return entity;
	}
}
