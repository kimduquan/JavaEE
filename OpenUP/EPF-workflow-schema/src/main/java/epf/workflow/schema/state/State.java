package epf.workflow.schema.state;

import jakarta.validation.constraints.NotNull;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.DiscriminatorColumn;
import org.eclipse.jnosql.mapping.MappedSuperclass;

/**
 * @author PC
 *
 */
@MappedSuperclass
@DiscriminatorColumn("type")
public class State implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@NotNull
	@Column
	private String name;
	
	/**
	 * 
	 */
	@NotNull
	@Column
	private Type type;
	
	/**
	 * @param type
	 * @param name
	 * @return
	 */
	@JsonbCreator
	public static State newState(@JsonbProperty("type") final String type, @JsonbProperty("name") final String name) {
		final Type stateType = "switch".equals(type) ? Type.Switch : Type.valueOf(type.toLowerCase());
		State state = null;
		switch(stateType) {
			case Switch:
				state = new SwitchState();
				break;
			case callback:
				state = new CallbackState();
				break;
			case event:
				state = new EventState();
				break;
			case foreach:
				state = new ForEachState();
				break;
			case inject:
				state = new InjectState();
				break;
			case operation:
				state = new OperationState();
				break;
			case parallel:
				state = new ParallelState();
				break;
			case sleep:
				state = new SleepState();
				break;
			default:
				state = new State();
				break;
		}
		state.type = stateType;
		state.name = name;
		return state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
