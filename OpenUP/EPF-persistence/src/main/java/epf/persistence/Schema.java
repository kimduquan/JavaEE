package epf.persistence;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
public class Schema {
	
	/**
	 * 
	 */
	@Inject
    transient EntityManager manager;

	/**
	 * @return
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
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

	/**
	 * @return
	 */
	@GET
    @Path("embeddable")
    @Produces(MediaType.APPLICATION_JSON)
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
