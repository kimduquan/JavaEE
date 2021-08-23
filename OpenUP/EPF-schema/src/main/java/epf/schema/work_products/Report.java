/**
 * 
 */
package epf.schema.work_products;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import epf.schema.EPF;

/**
 * @author PC
 *
 */
@Type(EPF.REPORT)
@Schema(name = EPF.REPORT, title = "Report")
@Entity(name = EPF.REPORT)
@Table(schema = EPF.SCHEMA, name = "REPORT")
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
