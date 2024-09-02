package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_actions_todo")
@Description("Configuration Wizards")
@NodeEntity("Configuration Wizards")
public class Todo {
	
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
	@ManyToOne(targetEntity = Actions.class)
	@NotNull
	@Description("Action")
	@Property
	@Relationship(type = "Action")
	private String action_id;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("10")
	@Property
	private Integer sequence = 10;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("Status")
	@DefaultValue("open")
	@Property
	private String state = "open";
	
	/**
	 * 
	 */
	@Column
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;

	public String getAction_id() {
		return action_id;
	}

	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
