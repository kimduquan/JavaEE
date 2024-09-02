package erp.base.schema.ir.exports;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
	@jakarta.persistence.Id
	@Id
	private int id;

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
	@Description("Export")
	@Transient
	private String export_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Exports.class)
	@JoinColumn(name = "export_id")
	@Relationship(type = "EXPORT")
	private Exports export;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Exports getExport() {
		return export;
	}

	public void setExport(Exports export) {
		this.export = export;
	}
}
