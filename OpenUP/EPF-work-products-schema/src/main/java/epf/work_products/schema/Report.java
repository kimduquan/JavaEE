/**
 * 
 */
package epf.work_products.schema;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * @author PC
 *
 */
@Type(WorkProducts.REPORT)
@Schema(name = WorkProducts.REPORT, title = "Report")
@Entity(name = WorkProducts.REPORT)
@Table(schema = WorkProducts.SCHEMA, name = "REPORT")
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Id
	@Column(name = "NAME")
	private String name;
    
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

}
