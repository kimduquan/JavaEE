package erp.base.schema.ir.model;

import java.util.Date;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import erp.base.schema.ir.module.Module;

/**
 * 
 */
@Entity
@Table(name = "ir_model_constraint")
@Description("Model Constraint")
@NodeEntity("Model Constraint")
public class Constraint {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Constraint")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("PostgreSQL constraint definition")
	@Property
	private String definition;
	
	/**
	 * 
	 */
	@Column
	@Description("Error message returned when the constraint is violated.")
	@Property
	private String message;
	
	/**
	 * 
	 */
	@JoinColumn(nullable = false)
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@NotNull
	@Relationship(type = "MODEL")
	private Model model;
	
	/**
	 * 
	 */
	@JoinColumn(nullable = false)
	@ManyToOne(targetEntity = Module.class, fetch = FetchType.LAZY)
	@NotNull
	@Relationship(type = "MODULE")
	private Module module;
	
	/**
	 * 
	 */
	@Column(nullable = false, length = 1)
	@NotNull
	@Description("Constraint Type")
	@Property
	private String type;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Date write_date;
	
	/**
	 * 
	 */
	@Column
	@Property
	private Date create_date;

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

	public Date getWrite_date() {
		return write_date;
	}

	public void setWrite_date(Date write_date) {
		this.write_date = write_date;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
