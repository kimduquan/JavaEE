/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.work_products.schema;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import javax.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(WorkProducts.DOMAIN)
@Schema(name = WorkProducts.DOMAIN, title = "Domain")
@Entity(name = WorkProducts.DOMAIN)
@Table(schema = WorkProducts.SCHEMA, name = "EPF_DOMAIN")
@NamedQuery(
        name = Domain.DOMAINS, 
        query = "SELECT d FROM EPF_Domain AS d")
public class Domain implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    public static final String DOMAINS = "EPF_Domain.Domains";

	/**
     * 
     */
    @Column(name = "NAME")
    @Id
    @NotBlank
    private String name;
    
    /**
     * 
     */
    @Column(name = "SUMMARY")
    private String summary;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "WORK_PRODUCTS",
            schema = WorkProducts.SCHEMA,
            joinColumns = @JoinColumn(name = "DOMAIN"),
            inverseJoinColumns = @JoinColumn(name = "ARTIFACT"),
            indexes = {@Index(columnList = "DOMAIN")}
    )
    private List<Artifact> workProducts;
    
    @Override
    public String toString() {
    	return String.format("%s@%s", getClass().getName(), name);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(final String summary) {
        this.summary = summary;
    }
    
    @Name("Work_Products")
    public List<Artifact> getWorkProducts(){
        return workProducts;
    }

    public void setWorkProducts(final List<Artifact> workProducts) {
        this.workProducts = workProducts;
    }
}
