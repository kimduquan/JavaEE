package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_exports")
@Description("Exports")
public class Exports {

	/**
	 * 
	 */
	@Column
	@Description("Export Name")
	private String name;
	
	/**
	 * 
	 */
	@Column
	private String resource;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = ExportsLine.class)
	@ElementCollection(targetClass = ExportsLine.class)
	@CollectionTable
	@Description("Export")
	private List<String> export_fields;
}
