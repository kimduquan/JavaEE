package erp.base.schema.ir.exports;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public List<String> getExport_fields() {
		return export_fields;
	}

	public void setExport_fields(List<String> export_fields) {
		this.export_fields = export_fields;
	}
}
