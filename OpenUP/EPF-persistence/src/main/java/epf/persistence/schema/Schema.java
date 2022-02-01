package epf.persistence.schema;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.Request;
import epf.persistence.internal.Session;
import epf.persistence.schema.util.EmbeddableBuilder;
import epf.persistence.schema.util.EmbeddableComparator;
import epf.persistence.schema.util.EntityBuilder;
import epf.persistence.schema.util.EntityComparator;

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
    private transient Request request;

	@Override
	public Response getEntities(final SecurityContext context) {
		final Session session = request.getSession(context);
		final EntityBuilder builder = new EntityBuilder();
		final EntityComparator comparator = new EntityComparator();
		final Stream<epf.persistence.schema.client.Entity> entities = request
				.getEntities(session)
				.map(builder::build)
				.sorted(comparator);
		return Response.ok(entities.collect(Collectors.toList())).build();
	}

	@Override
	public Response getEmbeddables(final SecurityContext context) {
		final Session session = request.getSession(context);
		final EmbeddableBuilder builder = new EmbeddableBuilder();
		final EmbeddableComparator comparator = new EmbeddableComparator();
		final Stream<epf.persistence.schema.client.Embeddable> embeddables = request.getEmbeddables(session)
				.map(builder::build)
				.sorted(comparator);
		return Response.ok(embeddables.collect(Collectors.toList())).build();
	}
}
