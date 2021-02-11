/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import openup.schema.Role;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.persistence.Query;
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

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class Queries implements openup.client.persistence.Queries {
    
    @Inject
    private Request cache;
    
    @Context
    private SecurityContext context;
    
    @Override
    @PermitAll
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

                ManagedType<?> parentType = rootType;
                Root<?> parentFrom = rootFrom;
                Join<?,?> parentJoin = null;
                
                try{
                    for(PathSegment segment : paths.subList(1, paths.size())){
                        Attribute<?,?> attribute = parentType.getAttribute(segment.getPath());
                        if (attribute.getPersistentAttributeType() != PersistentAttributeType.BASIC) {
                            Class<?> subClass = null;
                            if(attribute.isCollection()){
                                if(attribute.getJavaType() == List.class){
                                    subClass = parentType.getList(segment.getPath()).getBindableJavaType();
                                }
                                else if(attribute.getJavaType() == Map.class){
                                    subClass = parentType.getMap(segment.getPath()).getBindableJavaType();
                                }
                                else if(attribute.getJavaType() == Set.class){
                                    subClass = parentType.getSet(segment.getPath()).getBindableJavaType();
                                }
                                else if(attribute.getJavaType() == Collection.class){
                                    subClass = parentType.getCollection(segment.getPath()).getBindableJavaType();
                                }
                            }
                            else if(attribute.isAssociation()){
                                subClass = parentType.getSingularAttribute(segment.getPath()).getBindableJavaType();
                            }
                            
                            Join<?,?> subJoin;
                            if(parentJoin == null){
                                subJoin = parentFrom.join(segment.getPath());
                            }
                            else{
                                subJoin = parentJoin.join(segment.getPath());
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
                            parentType = subType;
                            parentJoin = subJoin;
                        }
                    }
                    
                    if(parentJoin == null){
                    	rootQuery.select(rootFrom);
                    }
                    else{
                        rootQuery.select(parentJoin);//
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
                catch(IllegalArgumentException ex){

                }
            }
        }
        return response.build();
    }
}
