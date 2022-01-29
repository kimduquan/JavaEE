/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import epf.client.persistence.SearchData;
import epf.naming.Naming;
import epf.persistence.internal.Entity;
import epf.persistence.internal.QueryBuilder;
import epf.persistence.internal.Session;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@ApplicationScoped
public class Queries implements epf.client.persistence.Queries {
	
	/**
	 * 
	 */
	private static final String FT_SEARCH_DATA = "EPF.FulltextSearch";
    
    /**
     * 
     */
    @Inject
    private transient Request request;
    
    @Override
    public Response executeQuery(
    		final String schema,
            final List<PathSegment> paths,
            final Integer firstResult,
            final Integer maxResults,
            final SecurityContext context
            ) {
    	final Session session = request.getSession(context);
    	final Entity<Object> entity = new Entity<>();
    	if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	final String entityName = rootSegment.getPath();
        	@SuppressWarnings("unchecked")
			final EntityType<Object> entityType = (EntityType<Object>) request.getEntityType(session, entityName);
        	entity.setType(entityType);
        	return session.peekManager(entityManager -> {
        		final Query query = buildQuery(entityManager, entity, paths);
            	final ResponseBuilder response = Response.ok();
                return collectQueryResult(query, firstResult, maxResults, response);
        	})
        	.get()
        	.build();
        }
    	throw new NotFoundException();
    }
    
    /**
     * @param query
     * @param firstResult
     * @param maxResults
     * @param response
     * @return
     */
    protected static ResponseBuilder collectQueryResult(
    		final Query query,
    		final Integer firstResult,
            final Integer maxResults,
    		final ResponseBuilder response
    		) {
    	if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
        final Stream<?> result = query.getResultStream();
        response.status(Status.OK).entity(
                    		result
                            .collect(Collectors.toList())
        );
        return response;
    }
    
    /**
     * @param entityManager
     * @param entity
     * @param paths
     * @return
     */
    protected static Query buildQuery(
    		final EntityManager entityManager, 
    		final Entity<Object> entity, 
    		final List<PathSegment> paths) {
    	final QueryBuilder queryBuilder = new QueryBuilder();
    	return queryBuilder
    			.manager(entityManager)
    			.entity(entity)
    			.paths(paths)
    			.build();
    }

	@Override
	public Response search(final UriInfo uriInfo, final String text, final Integer firstResult, final Integer maxResults, final SecurityContext context) {
		final Map<String, EntityType<?>> entityTables = new ConcurrentHashMap<>();
		final Map<String, Map<String, Attribute<?,?>>> entityAttributes = new ConcurrentHashMap<>();
		final Session session = request.getSession(context);
		request.mapEntities(session, entityTables, entityAttributes);
		final List<SearchData> result = session
				.peekManager(entityManager -> {
					final TypedQuery<SearchData> query = entityManager.createNamedQuery(FT_SEARCH_DATA, SearchData.class);
					query.setFirstResult(firstResult);
					query.setMaxResults(maxResults);
					query.setParameter(1, text);
					query.setParameter(2, maxResults);
					query.setParameter(3, firstResult);
					return query.getResultList();
					}
				)
				.get();
		ResponseBuilder response = Response.ok(result);
		final UriBuilder baseUri = uriInfo.getBaseUriBuilder();
		final Iterator<Link> linksIt = result
				.stream()
				.filter(link -> link != null)
				.map(
						searchData -> {
							Link entityLink = null;
							if(entityTables.containsKey(searchData.getTable())) {
								final EntityType<?> entityType = entityTables.get(searchData.getTable());
								UriBuilder linkBuilder = baseUri
										.clone()
										.path(entityType.getName());
								final Iterator<String> column = searchData.getColumns().iterator();
								final Iterator<String> key = searchData.getKeys().iterator();
								while(column.hasNext() && key.hasNext()) {
									linkBuilder = linkBuilder.matrixParam(column.next(), key.next());
								}
								entityLink = Link
										.fromUriBuilder(linkBuilder)
										.title(entityType.getName())
										.build();
							}
							return entityLink;
							}
						)
				.iterator();
		while(linksIt.hasNext()) {
			response = response.links(linksIt.next());
		}
		return response.build();
	}
}
