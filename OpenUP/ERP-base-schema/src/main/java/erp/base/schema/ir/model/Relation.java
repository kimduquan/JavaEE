package erp.base.schema.ir.model;

import java.util.Date;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_model_relation")
@Description("Relation Model")
@NodeEntity("Relation Model")
public class Relation {
	
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
	@Description("Relation Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Model.class)
	@NotNull
	@Relationship(type = "MODEL")
	private Model model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Module.class)
	@NotNull
	@Relationship(type = "MODULE")
	private Module module;
	
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
