/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.micro;

import javax.enterprise.context.RequestScoped;
import openup.micro.roles.Roles;
import openup.micro.tasks.Tasks;
import openup.micro.work_products.WorkProducts;
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
public class OpenUp {
    
    @Query
    @Name("Roles")
    @Description("This category lists roles organized by role set.")
    public Roles getRoles(){
        return null;
    }
    
    @Query
    @Name("Work_Products")
    @Description("List of work products organized by domain.")
    public WorkProducts getWorkProducts(){
        return null;
    }
    
    @Query
    @Name("Tasks")
    @Description("List of tasks organized by discipline.")
    public Tasks getTasks(){
        return null;
    }
}
