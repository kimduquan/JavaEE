package epf.persistence.schema;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.schema.client.Embeddable;
import epf.persistence.schema.client.Entity;
import epf.persistence.schema.util.EmbeddableBuilder;
import epf.persistence.schema.util.EmbeddableComparator;
import epf.persistence.schema.util.EntityBuilder;
import epf.persistence.schema.util.EntityComparator;
import epf.persistence.util.EntityManagerFactory;
import epf.persistence.util.SchemaUtil;

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
    transient EntityManagerFactory factory;

	@Override
	public CompletionStage<List<Entity>> getEntities(final SecurityContext context) {
		final EntityBuilder builder = new EntityBuilder();
		final EntityComparator comparator = new EntityComparator();
		final Stream<Entity> entities = SchemaUtil
				.getEntities(factory.getMetamodel())
				.map(builder::build)
				.sorted(comparator);
		return CompletableFuture.completedStage(entities.collect(Collectors.toList()));
	}

	@Override
	public CompletionStage<List<Embeddable>> getEmbeddables(final SecurityContext context) {
		final EmbeddableBuilder builder = new EmbeddableBuilder();
		final EmbeddableComparator comparator = new EmbeddableComparator();
		final Stream<Embeddable> embeddables = SchemaUtil
				.getEmbeddables(factory.getMetamodel())
				.map(builder::build)
				.sorted(comparator);
		return CompletableFuture.completedStage(embeddables.collect(Collectors.toList()));
	}
}
