package epf.work_products.schema;

import java.io.Serializable;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.utility.EntityListener;

/**
 *
 * @author FOXCONN
 */
@Type(WorkProducts.TEMPLATE)
@Schema(name = WorkProducts.TEMPLATE, title = "Template")
@Entity(name = WorkProducts.TEMPLATE)
@Table(schema = WorkProducts.SCHEMA, name = "TEMPLATE")
@NamedEntityGraph(includeAllAttributes = true)
@EntityListeners(EntityListener.class)
public class Template implements Serializable {
	
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
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
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

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(final JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }
}
