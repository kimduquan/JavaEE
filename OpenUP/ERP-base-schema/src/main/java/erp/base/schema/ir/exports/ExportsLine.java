package erp.base.schema.ir.exports;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_exports_line")
@Description("Exports Line")
@NodeEntity("Exports Line")
public class ExportsLine {

	/**
	 * 
	 */
	@Column
	@Description("Field Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Exports.class)
	@Description("Export")
	@Property
	@Relationship(type = "EXPORT")
	private String export_id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExport_id() {
		return export_id;
	}

	public void setExport_id(String export_id) {
		this.export_id = export_id;
	}
}
