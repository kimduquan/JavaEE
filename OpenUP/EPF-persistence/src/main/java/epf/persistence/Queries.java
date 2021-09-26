/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import epf.client.persistence.SearchData;
import epf.client.security.Security;
import epf.persistence.impl.Entity;
import epf.persistence.impl.QueryBuilder;
import epf.roles.schema.internal.QueryNames;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
@RolesAllowed(Security.DEFAULT_ROLE)
@RequestScoped
public class Queries implements epf.client.persistence.Queries {
    
    /**
     * 
     */
    @Inject
    private transient Request cache;
    
    /**
     * 
     */
    @Context
    private transient SecurityContext context;
    
    @Override
    public Response executeQuery(
    		final String schema,
            final List<PathSegment> paths,
            final Integer firstResult,
            final Integer maxResults
            ) {
    	final ResponseBuilder response = Response.status(Response.Status.NOT_FOUND);
    	Entity<Object> entity = null;
    	final Principal principal = context.getUserPrincipal();
        if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	entity = cache.findEntity(principal, rootSegment.getPath());
        }
        if(entity != null && entity.getType() != null){
        	final EntityManager manager = cache.getManager(principal);
        	final QueryBuilder queryBuilder = new QueryBuilder();
        	final Query query = queryBuilder
        			.manager(manager)
        			.entity(entity)
        			.paths(paths)
        			.build();
            getQueryResult(query, firstResult, maxResults, response);
        }
        return response.build();
    }
    
    /**
     * @param query
     * @param firstResult
     * @param maxResults
     * @param response
     * @return
     */
    protected static ResponseBuilder getQueryResult(
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

	@Override
	public Response search(final UriInfo uriInfo, final String text, final Integer firstResult, final Integer maxResults) {
		final Map<String, EntityType<?>> entityTables = new ConcurrentHashMap<>();
		final Map<String, Map<String, Attribute<?,?>>> entityAttributes = new ConcurrentHashMap<>();
		cache.mapEntities(context.getUserPrincipal(), entityTables, entityAttributes);
		final TypedQuery<SearchData> query = cache.createNamedQuery(
				context.getUserPrincipal(), 
				QueryNames.FT_SEARCH_DATA, 
				SearchData.class
				);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		query.setParameter(1, text);
		query.setParameter(2, maxResults);
		query.setParameter(3, firstResult);
		final List<SearchData> result = query.getResultList();
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
