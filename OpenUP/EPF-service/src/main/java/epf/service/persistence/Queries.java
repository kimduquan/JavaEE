/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.persistence;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import epf.schema.EPF;
import epf.schema.QueryNames;
import epf.schema.roles.Role;
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
@RolesAllowed(Role.DEFAULT_ROLE)
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
    
    /**
     *
     */
    @Override
    @Operation(
            summary = "Native Query", 
            description = "Execute a SELECT query and return the query results."
    )
    @APIResponse(
            description = "Result",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    public Response getCriteriaQueryResult(
            final String unit,
            final List<PathSegment> paths,
            final Integer firstResult,
            final Integer maxResults
            ) {
    	final ResponseBuilder response = Response.status(Response.Status.NOT_FOUND);
        if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	final Principal principal = context.getUserPrincipal();
        	final Entity<Object> entity = cache.findEntity(unit, principal, rootSegment.getPath());
            if(entity.getType() != null){
            	final EntityManager manager = cache.getManager(unit, principal);
            	final CriteriaBuilder builder = manager.getCriteriaBuilder();
            	final EntityType<Object> rootType = entity.getType();
            	final Class<Object> rootClass = rootType.getJavaType();
            	final CriteriaQuery<Object> rootQuery = builder.createQuery(rootClass);
            	final Root<Object> rootFrom = rootQuery.from(rootClass);
            	final List<Predicate> allParams = new ArrayList<>();
                rootSegment.getMatrixParameters().forEach((name, values) -> {
                	allParams.add(
                            builder.isMember(
                                    values,
                                    rootFrom.get(name)
                            )
                    );
                });
                final Var<ManagedType<?>> parentType = new Var<>(rootType);
                final Root<?> parentFrom = rootFrom;
                final Var<Join<?,?>> parentJoin = new Var<>();
                paths.subList(1, paths.size()).forEach(segment -> {
                    final Attribute<?,?> attribute = parentType.get().getAttribute(segment.getPath());
                    if (attribute.getPersistentAttributeType() != PersistentAttributeType.BASIC) {
                        Class<?> subClass = null;
                        if(attribute.isCollection()){
                            if(attribute.getJavaType() == List.class){
                                subClass = parentType.get().getList(segment.getPath()).getBindableJavaType();
                            }
                            else if(attribute.getJavaType() == Map.class){
                                subClass = parentType.get().getMap(segment.getPath()).getBindableJavaType();
                            }
                            else if(attribute.getJavaType() == Set.class){
                                subClass = parentType.get().getSet(segment.getPath()).getBindableJavaType();
                            }
                            else if(attribute.getJavaType() == Collection.class){
                                subClass = parentType.get().getCollection(segment.getPath()).getBindableJavaType();
                            }
                        }
                        else if(attribute.isAssociation()){
                            subClass = parentType.get().getSingularAttribute(segment.getPath()).getBindableJavaType();
                        }
                        
                        Join<?,?> subJoin;
                        if(parentJoin.get() == null){
                            subJoin = parentFrom.join(segment.getPath());
                        }
                        else{
                            subJoin = parentJoin.get().join(segment.getPath());
                        }

                        segment.getMatrixParameters().forEach((name, values) -> {
                        	allParams.add(
                                    builder.isMember(
                                            values,
                                            subJoin.get(name)
                                    )
                            );
                        });
                        
                        final ManagedType<?> subType = manager.getMetamodel().managedType(subClass);
                        parentType.set(subType);
                        parentJoin.set(subJoin);
                    }
            	});
                if(parentJoin.get() == null){
                	rootQuery.select(rootFrom);
                }
                else{
                    rootQuery.select(parentJoin.get());
                }
                
                if(!allParams.isEmpty()){
                    rootQuery.where(allParams.toArray(new Predicate[0]));
                }
                
                final Query query = manager.createQuery(rootQuery);
            
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
            }
        }
        return response.build();
    }

	@Override
	public Response search(final UriInfo uriInfo, final String text, final Integer firstResult, final Integer maxResults) {
		final Map<String, EntityType<?>> entityTables = new ConcurrentHashMap<>();
		final Map<String, Map<String, Attribute<?,?>>> entityAttributes = new ConcurrentHashMap<>();
		cache.mapEntities(EPF.Schema, context.getUserPrincipal(), entityTables, entityAttributes);
		final TypedQuery<SearchData> query = cache.createNamedQuery(
				EPF.Schema, 
				context.getUserPrincipal(), 
				QueryNames.FULL_TEXT_SEARCH, 
				SearchData.class
				);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		query.setParameter(1, text);
		query.setParameter(2, maxResults);
		query.setParameter(3, firstResult);
		ResponseBuilder response = Response.ok();
		final UriBuilder baseUri = uriInfo.getBaseUriBuilder();
		final Iterator<Link> linksIt = query
				.getResultStream()
				.map(
						searchData -> {
							Link entityLink = null;
							if(entityTables.containsKey(searchData.getTable())) {
								EntityType<?> entityType = entityTables.get(searchData.getTable());
								UriBuilder linkBuilder = baseUri
										.clone()
										.path(EPF.Schema)
										.path(entityType.getName());
								Iterator<String> column = searchData.getColumns().iterator();
								Iterator<String> key = searchData.getKeys().iterator();
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
				.filter(link -> link != null)
				.iterator();
		while(linksIt.hasNext()) {
			response = response.links(linksIt.next());
		}
		return response.build();
	}
}
