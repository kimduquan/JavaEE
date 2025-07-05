package epf.workflow.schema.state;

import jakarta.validation.constraints.NotNull;
import jakarta.json.bind.annotation.JsonbSubtype;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbTypeInfo;
import jakarta.nosql.Column;
import java.io.Serializable;
import jakarta.nosql.DiscriminatorColumn;
import jakarta.nosql.MappedSuperclass;
import org.eclipse.microprofile.graphql.Description;

/**
 * @author PC
 *
 */
@MappedSuperclass
@DiscriminatorColumn("type")
@JsonbTypeInfo(key = "type", value = {
		@JsonbSubtype(alias = Type.CALLBACK, type = CallbackState.class),
		@JsonbSubtype(alias = Type.EVENT, type = EventState.class),
		@JsonbSubtype(alias = Type.FOREACH, type = ForEachState.class),
		@JsonbSubtype(alias = Type.INJECT, type = InjectState.class),
		@JsonbSubtype(alias = Type.OPERATION, type = OperationState.class),
		@JsonbSubtype(alias = Type.PARALLEL, type = ParallelState.class),
		@JsonbSubtype(alias = Type.SWITCH, type = SwitchState.class)
})
public class State implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Unique State name. Must follow the Serverless Workflow Naming Convention")
	private String name;
	
	@NotNull
	@JsonbTransient
	private Type type_;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType_() {
		return type_;
	}

	public void setType_(Type type_) {
		this.type_ = type_;
	}
}
