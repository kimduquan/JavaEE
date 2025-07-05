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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public Model getParent() {
		return parent;
	}

	public void setParent(Model parent) {
		this.parent = parent;
	}

	public Integer getParent_field_id() {
		return parent_field_id;
	}

	public void setParent_field_id(Integer parent_field_id) {
		this.parent_field_id = parent_field_id;
	}

	public Fields getParent_field() {
		return parent_field;
	}

	public void setParent_field(Fields parent_field) {
		this.parent_field = parent_field;
	}
}
