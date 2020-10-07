/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

/**
 *
 * @author FOXCONN
 */
@GraphQLApi
@RequestScoped
@PermitAll
public class EPFSchema {
    
    @Inject
    private Roles roles;
    
    @Inject
    private WorkProducts workProducts;
    
    @Inject
    private Tasks tasks;
    
    @Query
    @Name("Roles")
    @Description("This category lists roles organized by role set.")
    public Roles getRoles(){
        return roles;
    }
    
    @Query
    @Name("Work_Products")
    @Description("List of work products organized by domain.")
    public WorkProducts getWorkProducts(){
        return workProducts;
    }
    
    @Query
    @Name("Tasks")
    @Description("List of tasks organized by discipline.")
    public Tasks getTasks(){
        return tasks;
    }
}
