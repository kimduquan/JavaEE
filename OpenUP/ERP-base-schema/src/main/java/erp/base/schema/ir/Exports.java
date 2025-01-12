package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.exports.Line;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ir_exports")
@Description("Exports")
public class Exports {
	
	@Id
	private int id;

	@Column
	@Description("Export Name")
	private String name;
	
	@Column
	private String resource;
	
	@OneToMany(targetEntity = Line.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "export_id")
	@Description("Export")
	private List<Line> export_fields;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public List<Line> getExport_fields() {
		return export_fields;
	}

	public void setExport_fields(List<Line> export_fields) {
		this.export_fields = export_fields;
	}
}
