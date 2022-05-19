package epf.query.search;

import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import epf.client.search.SearchEntity;
import epf.naming.Naming;

/**
 * 
 */
@ApplicationScoped
@Path(Naming.SEARCH)
public class Search implements epf.client.search.Search {
	
	/**
	 *
	 */
	private static final String FULLTEXT_SEARCH = "SELECT * FROM FTL_SEARCH_DATA(?, ?, ?);";
	
	/**
	 *
	 */
	@PersistenceContext(unitName = "EPF-query")
	private transient EntityManager manager;

	@Override
	public Response search(final String text, final Integer firstResult, final Integer maxResults) {
		final Query query = manager.createNativeQuery(FULLTEXT_SEARCH)
				.setParameter(0, text)
				.setParameter(1, maxResults != null ? maxResults : 0)
				.setParameter(2, firstResult != null ? firstResult : 0);
		final List<?> resultList = query.getResultList();
		final List<SearchEntity> entities = resultList.stream().map(object -> new SearchEntity()).collect(Collectors.toList());
		return Response.ok(entities).header(ENTITY_COUNT, resultList.size()).build();
	}

	@Override
	public Response count(final String text) {
		final Query query = manager.createNativeQuery(FULLTEXT_SEARCH)
				.setParameter(0, text)
				.setParameter(1, 0)
				.setParameter(2, 0);
		final List<?> resultList = query.getResultList();
		return Response.ok().header(ENTITY_COUNT, resultList.size()).build();
	}
	
}
