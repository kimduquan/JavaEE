/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import epf.DeliveryProcesses;
import epf.schema.roles.RoleSet;
import epf.schema.tasks.Discipline;
import epf.schema.work_products.Domain;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import openup.webapp.persistence.Persistence;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author FOXCONN
 */
@ViewScoped
@Named("webapp_view")
public class View implements Serializable {
    
    private List<DeliveryProcesses> deliveryProcesses;
    private List<RoleSet> roleSets;
    private List<Domain> domains;
    private List<Discipline> disciplines;
    
    private TreeNode rootDeliveryProcesses;
    private TreeNode rootRoles;
    private TreeNode rootWorkProducts;
    private TreeNode rootTasks;
    
    @Inject
    private Persistence persistence;
    
    @PostConstruct
    void postConstruct(){
        try {
            deliveryProcesses = persistence.getDeliveryProcesses().collect(Collectors.toList());
            domains = persistence.getDomains().collect(Collectors.toList());
            roleSets = persistence.getRoleSets().collect(Collectors.toList());
            disciplines = persistence.getDisciplines().collect(Collectors.toList());
        } 
        catch (Exception ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TreeNode getDeliveryProcesses() {
        if(this.rootDeliveryProcesses == null){
            this.rootDeliveryProcesses = new DefaultTreeNode();
            this.deliveryProcesses.forEach(this::addDeliveryProcess);
        }
        return rootDeliveryProcesses;
    }

    public TreeNode getRoles() {
        if(this.rootRoles == null){
            this.rootRoles = new DefaultTreeNode();
            this.roleSets.forEach(this::addRoleSet);
        }
        return rootRoles;
    }

    public TreeNode getWorkProducts() {
        if(this.rootWorkProducts == null){
            this.rootWorkProducts = new DefaultTreeNode();
            this.domains.forEach(this::addDomain);
        }
        return rootWorkProducts;
    }
    
    public TreeNode getTasks(){
        if(this.rootTasks == null){
            this.rootTasks = new DefaultTreeNode();
            this.disciplines.forEach(this::addDiscipline);
        }
        return this.rootTasks;
    }
    
    TreeNode addDeliveryProcess(DeliveryProcesses dp){
        return new DefaultTreeNode("delivery-process", dp, this.rootDeliveryProcesses);
    }
    
    TreeNode addRoleSet(RoleSet rs){
        return new DefaultTreeNode("role-set", rs, this.rootRoles);
    }
    
    TreeNode addDomain(Domain d){
        return new DefaultTreeNode("domain", d, this.rootWorkProducts);
    }
    
    TreeNode addDiscipline(Discipline d){
        return new DefaultTreeNode("discipline", d, this.rootTasks);
    }
}
