package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_act_url")
@Description("Action URL")
public class Act_Url extends Actions {
	
	public enum ActionTarget {
		@Description("New Window")
		new_,
		@Description("This Window")
		self,
		@Description("Download")
		download
	}
	
	public class ActionTargetAttributeConverter extends EnumAttributeConverter<ActionTarget> {
		public ActionTargetAttributeConverter() {
			super(ActionTarget.class, "_", null, null);
		}
	}

	@Column
	@DefaultValue("ir.actions.act_url")
	private String type = "ir.actions.act_url";
	
	@Column(nullable = false)
	@NotNull
	@Description("Action URL")
	private String url;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = ActionTargetAttributeConverter.class)
	@NotNull
	@Description("Action Target")
	@DefaultValue("new")
	private ActionTarget target = ActionTarget.new_;

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
