package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_url")
@Description("Action URL")
@NodeEntity("Action URL")
public class ActUrl extends Actions {
	
	/**
	 * 
	 */
	public enum ActionTarget {
		/**
		 * 
		 */
		_new,
		/**
		 * 
		 */
		self,
		/**
		 * 
		 */
		download
	}
	
	/**
	 * 
	 */
	public class ActionTargetAttributeConverter extends EnumAttributeConverter<ActionTarget> {
		/**
		 * 
		 */
		public ActionTargetAttributeConverter() {
			super(ActionTarget.class, "_", null, null);
		}
	}

	/**
	 * 
	 */
	@Column
	@DefaultValue("ir.actions.act_url")
	@Property
	private String type = "ir.actions.act_url";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Action URL")
	@Property
	private String url;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = ActionTargetAttributeConverter.class)
	@NotNull
	@Description("Action Target")
	@DefaultValue("new")
	@Property
	private ActionTarget target = ActionTarget._new;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ActionTarget getTarget() {
		return target;
	}

	public void setTarget(ActionTarget target) {
		this.target = target;
	}
}
