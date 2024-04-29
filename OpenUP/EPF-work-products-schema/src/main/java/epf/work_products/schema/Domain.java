package epf.work_products.schema;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EPFEntity;
import jakarta.persistence.Index;

/**
 *
 * @author FOXCONN
 */
@Type(WorkProducts.DOMAIN)
@Schema(name = WorkProducts.DOMAIN, title = "Domain")
@Entity(name = WorkProducts.DOMAIN)
@Table(schema = WorkProducts.SCHEMA, name = "EPF_DOMAIN")
public class Domain extends EPFEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    private Set<Artifact> workProducts;
    
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
    public Set<Artifact> getWorkProducts(){
        return workProducts;
    }

    public void setWorkProducts(final Set<Artifact> workProducts) {
        this.workProducts = workProducts;
    }
}
