package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_inherit")
@Description("Model Inheritance Tree")
@NodeEntity("Model Inheritance Tree")
public class Inherit {
	
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
	@Transient
	private String model_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "model_id", nullable = false)
	@NotNull
	@Relationship(type = "MODEL")
	private Model model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Transient
	private String parent_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "parent_id", nullable = false)
	@NotNull
	@Relationship(type = "PARENT")
	private Model parent;
	
	/**
	 * 
	 */
	@Column
	@Transient
	private String parent_field_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Fields.class)
	@JoinColumn(name = "parent_field_id")
	@Relationship(type = "PARENT_FIELD")
	private Fields parent_field;

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getParent_field_id() {
		return parent_field_id;
	}

	public void setParent_field_id(String parent_field_id) {
		this.parent_field_id = parent_field_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Model getParent() {
		return parent;
	}

	public void setParent(Model parent) {
		this.parent = parent;
	}

	public Fields getParent_field() {
		return parent_field;
	}

	public void setParent_field(Fields parent_field) {
		this.parent_field = parent_field;
	}
}
