package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import jakarta.persistence.Column;
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
	@NotNull
	@Description("Action Target")
	@DefaultValue("new")
	@Property
	private String target = "new";

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

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
