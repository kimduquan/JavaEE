package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Model;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_model_inherit")
@Description("Model Inheritance Tree")
public class Inherit {
	
	@Id
	private int id;

	@Transient
	private Integer model_id;
	
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "model_id", nullable = false)
	@NotNull
	private Model model;
	
	@Transient
	private Integer parent_id;

	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = false)
	@NotNull
	private Model parent;
	
	@Transient
	private Integer parent_field_id;
	
	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_field_id")
	private Fields parent_field;
}
