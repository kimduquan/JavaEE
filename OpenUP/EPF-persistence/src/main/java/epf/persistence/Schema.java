package epf.persistence;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import epf.naming.Naming;
import epf.persistence.internal.util.SchemaUtil;
import epf.persistence.schema.Embeddable;
import epf.persistence.schema.Entity;
import epf.persistence.schema.internal.EmbeddableBuilder;
import epf.persistence.schema.internal.EmbeddableComparator;
import epf.persistence.schema.internal.EntityBuilder;
import epf.persistence.schema.internal.EntityComparator;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * @author PC
 *
 */
@Path(Naming.SCHEMA)
@ApplicationScoped
public class Schema implements epf.persistence.client.Schema {
	
	/**
	 * 
	 */
	@Inject
    transient EntityManager manager;

	@Override
	@RunOnVirtualThread
	public List<Entity> getEntities() {
		final EntityBuilder builder = new EntityBuilder();
		final EntityComparator comparator = new EntityComparator();
		final Stream<Entity> entities = SchemaUtil
				.getEntities(manager.getMetamodel())
				.map(builder::build)
				.sorted(comparator);
		return entities.collect(Collectors.toList());
	}

	@Override
	@RunOnVirtualThread
	public List<Embeddable> getEmbeddables() {
		final EmbeddableBuilder builder = new EmbeddableBuilder();
		final EmbeddableComparator comparator = new EmbeddableComparator();
		final Stream<Embeddable> embeddables = SchemaUtil
				.getEmbeddables(manager.getMetamodel())
				.map(builder::build)
				.sorted(comparator);
		return embeddables.collect(Collectors.toList());
	}
}
