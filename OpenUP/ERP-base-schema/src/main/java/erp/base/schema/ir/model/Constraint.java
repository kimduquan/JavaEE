package erp.base.schema.ir.model;

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
@Table(name = "ir_model_constraint")
@Description("Model Constraint")
public class Constraint {
	
	@Id
	private int id;

	@Column(nullable = false, updatable = false)
	@NotNull
	@Description("Constraint")
	private String name;
	
	@Column(updatable = false)
	@Description("PostgreSQL constraint definition")
	private String definition;
	
	@Column
	private String message;
	
	@JoinColumn(name = "model", nullable = false, updatable = false)
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@NotNull
	private Model model;
	
	@JoinColumn(name = "module", nullable = false, updatable = false)
	@ManyToOne(targetEntity = Module.class, fetch = FetchType.LAZY)
	@NotNull
	private Module module;
	
	@Column(nullable = false, updatable = false, length = 1)
	@NotNull
	@Description("Constraint Type")
	private String type;

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

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
