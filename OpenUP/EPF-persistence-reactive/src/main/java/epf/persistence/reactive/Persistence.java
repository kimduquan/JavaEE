package epf.persistence.reactive;

import java.io.InputStream;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import io.smallrye.mutiny.Uni;

@Path(Naming.PERSISTENCE)
@ApplicationScoped
public class Persistence implements epf.persistence.client.RxPersistence {
	
	/**
	 * 
	 */
	@Inject
	transient RxEntityManager entityManager;

	@Override
	public Uni<Object> persist(final String schema, final String entity, final SecurityContext context, final InputStream body)
			throws Exception {
		return null;
	}

	@Override
	public Uni<Void> merge(final String schema, final String entity, final String entityId, final SecurityContext context, final InputStream body)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uni<Void> remove(final String schema, final String entity, final String entityId, final SecurityContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uni<Response> find(final String schema, final String entity, final String entityId, final SecurityContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uni<Response> executeQuery(final String schema, final List<PathSegment> paths, final Integer firstResult, final Integer maxResults,
			final SecurityContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
