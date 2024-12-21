package erp.base.schema.ir.model;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import erp.base.schema.ir.Model;
import erp.base.schema.ir.module.Module;

@Entity
@Table(name = "ir_model_relation")
@Description("Relation Model")
public class Relation {
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Relation Name")
	private String name;
	
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "model", nullable = false)
	@NotNull
	private Model model;
	
	@ManyToOne(targetEntity = Module.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "module", nullable = false)
	@NotNull
	private Module module;
	
	@Column
	private Date write_date;
	
	@Column
	private Date create_date;
}
