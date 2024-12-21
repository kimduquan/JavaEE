package erp.base.schema.ir.exports;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Exports;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ir_exports_line")
@Description("Exports Line")
public class Line {
	
	@Id
	private int id;

	@Column
	@Description("Field Name")
	private String name;
	
	@Transient
	private Integer export_id;
	
	@ManyToOne(targetEntity = Exports.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "export_id")
	@Description("Export")
	private Exports export;
}
