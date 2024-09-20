package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.schema.util.EnumAttributeConverter;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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
	public enum Status {
		/**
		 * 
		 */
		open,
		/**
		 * 
		 */
		done
	}
	
	/**
	 * 
	 */
	public class StatusAttributeConverter extends EnumAttributeConverter<Status> {
		/**
		 * 
		 */
		public StatusAttributeConverter() {
			super(Status.class, null, null, null);
		}
	}
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false, insertable = false, updatable = false)
	@NotNull
	@Description("Action")
	@Transient
	private Integer action_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Actions.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id", nullable = false)
	@NotNull
	@Relationship(type = "Action")
	private Actions action;
	
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
	@jakarta.persistence.Convert(converter = StatusAttributeConverter.class)
	@NotNull
	@Description("Status")
	@DefaultValue("open")
	@Property
	private Status state = Status.open;
	
	/**
	 * 
	 */
	@Column
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;

	public Integer getAction_id() {
		return action_id;
	}

	public void setAction_id(Integer action_id) {
		this.action_id = action_id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Status getState() {
		return state;
	}

	public void setState(Status state) {
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

	public Actions getAction() {
		return action;
	}

	public void setAction(Actions action) {
		this.action = action;
	}
}
