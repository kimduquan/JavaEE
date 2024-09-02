package erp.base.schema.ir.exports;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_exports")
@Description("Exports")
@NodeEntity("Exports")
public class Exports {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column
	@Description("Export Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Property
	private String resource;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = ExportsLine.class, mappedBy = "export_id")
	@Description("Export")
	@Relationship(type = "EXPORT")
	private List<ExportsLine> export_fields;

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

	public List<ExportsLine> getExport_fields() {
		return export_fields;
	}

	public void setExport_fields(List<ExportsLine> export_fields) {
		this.export_fields = export_fields;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
