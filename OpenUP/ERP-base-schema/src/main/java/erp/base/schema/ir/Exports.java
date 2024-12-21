package erp.base.schema.ir;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.exports.Line;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
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
	
	@OneToMany(targetEntity = Line.class, fetch = FetchType.LAZY, mappedBy = "export_id")
	@Description("Export")
	private List<Line> export_fields;
}
