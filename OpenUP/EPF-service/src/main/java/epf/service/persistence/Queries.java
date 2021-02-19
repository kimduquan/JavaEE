/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.persistence;

import openup.schema.QueryNames;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import epf.client.persistence.SearchData;
import epf.client.persistence.Target;
import epf.schema.EPF;
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
@RolesAllowed(EPF.Role)
@RequestScoped
public class Queries implements epf.client.persistence.Queries {
    
    @Inject
    private Request cache;
    
    @Context
    private SecurityContext context;
    
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
            String unit,
            List<PathSegment> paths,
            Integer firstResult,
            Integer maxResults
            ) throws Exception{
        ResponseBuilder response = Response.status(Response.Status.NOT_FOUND);
        
        if(!paths.isEmpty()){
            PathSegment rootSegment = paths.get(0);
            Principal principal = context.getUserPrincipal();
            EntityManager manager = cache.getManager(unit, principal);
            Entity<Object> entity = cache.findEntity(unit, principal, rootSegment.getPath());
            if(entity.getType() != null){
                EntityType<Object> rootType = entity.getType();
                Class<Object> rootClass = rootType.getJavaType();
                
                CriteriaBuilder builder = manager.getCriteriaBuilder();
                CriteriaQuery<Object> rootQuery = builder.createQuery(rootClass);
                Root<Object> rootFrom = rootQuery.from(rootClass);
                List<Predicate> allParams = new ArrayList<>();
                
                List<Predicate> rootParams = new ArrayList<>();
                rootSegment.getMatrixParameters().forEach((name, values) -> {
                    rootParams.add(
                            builder.isMember(
                                    values,
                                    rootFrom.get(name)
                            )
                    );
                });
                allParams.addAll(rootParams);

                Var<ManagedType<?>> parentType = new Var<>(rootType);
                Root<?> parentFrom = rootFrom;
                Var<Join<?,?>> parentJoin = new Var<>();
                
                paths.subList(1, paths.size()).forEach(segment -> {
                    Attribute<?,?> attribute = parentType.get().getAttribute(segment.getPath());
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

                        List<Predicate> params = new ArrayList<>();
                        segment.getMatrixParameters().forEach((name, values) -> {
                            params.add(
                                    builder.isMember(
                                            values,
                                            subJoin.get(name)
                                    )
                            );
                        });
                        allParams.addAll(params);
                        
                        ManagedType<?> subType = manager.getMetamodel().managedType(subClass);
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
                    rootQuery.where(allParams.toArray(new Predicate[allParams.size()]));
                }
                
                Query query = manager.createQuery(rootQuery);
            
                if(firstResult != null){
                    query.setFirstResult(firstResult);
                }
                if(maxResults != null){
                    query.setMaxResults(maxResults);
                }
                Stream<?> result = query.getResultStream();
                response.status(Status.OK).entity(
                            		result
                                    .collect(Collectors.toList())
                );
            }
        }
        return response.build();
    }

	@Override
	public List<Target> search(String text, Integer firstResult, Integer maxResults) throws Exception {
		Map<String, EntityType<?>> entityTables = new HashMap<>();
		Map<String, Map<String, Attribute<?,?>>> entityAttributes = new HashMap<>();
		cache.getEntities(EPF.Schema, context.getUserPrincipal(), entityTables, entityAttributes);
		TypedQuery<SearchData> query = cache.createNamedQuery(
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
		return query.getResultStream()
				.map(item -> {
					Target target = null;
					if(entityTables.containsKey(item.getTable())) {
						target = new Target();
						StringBuilder builder = new StringBuilder();
						builder.append(EPF.Schema);
						builder.append('/');
						EntityType<?> entityType = entityTables.get(item.getTable());
						builder.append(entityType.getName());
						builder.append('?');
						Iterator<String> column = item.getColumns().iterator();
						Iterator<String> key = item.getKeys().iterator();
						while(column.hasNext() && key.hasNext()) {
							builder.append(column.next());
							builder.append('=');
							String value = URLEncoder.encode(key.next(), Charset.forName("UTF-8"));
							builder.append(value);
							builder.append(';');
						}
						target.setPath(builder.toString());
					}
					return target;
					}
				)
				.filter(target -> target != null)
				.collect(Collectors.toList());
	}
}
