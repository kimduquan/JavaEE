package epf.persistence.schema;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.internal.util.SchemaUtil;
import epf.persistence.schema.client.Embeddable;
import epf.persistence.schema.client.Entity;
import epf.persistence.schema.internal.EmbeddableBuilder;
import epf.persistence.schema.internal.EmbeddableComparator;
import epf.persistence.schema.internal.EntityBuilder;
import epf.persistence.schema.internal.EntityComparator;

/**
 * @author PC
 *
 */
@Path(Naming.SCHEMA)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@ApplicationScoped
public class Schema implements epf.persistence.schema.client.Schema {
	
	/**
	 * 
	 */
	@Inject
    transient EntityManager manager;

	@Override
	public List<Entity> getEntities(final SecurityContext context) {
		final EntityBuilder builder = new EntityBuilder();
		final EntityComparator comparator = new EntityComparator();
		final Stream<Entity> entities = SchemaUtil
				.getEntities(manager.getMetamodel())
				.map(builder::build)
				.sorted(comparator);
		return entities.collect(Collectors.toList());
	}

	@Override
	public List<Embeddable> getEmbeddables(final SecurityContext context) {
		final EmbeddableBuilder builder = new EmbeddableBuilder();
		final EmbeddableComparator comparator = new EmbeddableComparator();
		final Stream<Embeddable> embeddables = SchemaUtil
				.getEmbeddables(manager.getMetamodel())
				.map(builder::build)
				.sorted(comparator);
		return embeddables.collect(Collectors.toList());
	}
}
