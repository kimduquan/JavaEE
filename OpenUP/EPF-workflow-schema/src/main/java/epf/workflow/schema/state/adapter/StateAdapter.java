package epf.workflow.schema.state.adapter;

import java.util.Map;
import javax.json.bind.adapter.JsonbAdapter;
import epf.util.json.JsonUtil;
import epf.workflow.schema.state.CallbackState;
import epf.workflow.schema.state.EventState;
import epf.workflow.schema.state.ForEachState;
import epf.workflow.schema.state.InjectState;
import epf.workflow.schema.state.OperationState;
import epf.workflow.schema.state.ParallelState;
import epf.workflow.schema.state.SleepState;
import epf.workflow.schema.state.State;
import epf.workflow.schema.state.SwitchState;
import epf.workflow.schema.state.Type;
import epf.workflow.schema.util.EnumUtil;

/**
 * @author PC
 *
 */
public class StateAdapter implements JsonbAdapter<State, Map<String, Object>> {

	@Override
	public Map<String, Object> adaptToJson(final State obj) throws Exception {
		return JsonUtil.toMap(obj);
	}

	@Override
	public State adaptFromJson(final Map<String, Object> obj) throws Exception {
		final Type type = EnumUtil.valueOf(Type.class, obj.get("type").toString());
		State state = null;
		switch(type) {
			case callback:
				state = JsonUtil.fromMap(obj, CallbackState.class);
				break;
			case event:
				state = JsonUtil.fromMap(obj, EventState.class);
				break;
			case foreach:
				state = JsonUtil.fromMap(obj, ForEachState.class);
				break;
			case inject:
				state = JsonUtil.fromMap(obj, InjectState.class);
				break;
			case operation:
				state = JsonUtil.fromMap(obj, OperationState.class);
				break;
			case parallel:
				state = JsonUtil.fromMap(obj, ParallelState.class);
				break;
			case sleep:
				state = JsonUtil.fromMap(obj, SleepState.class);
				break;
			case Switch:
				state = JsonUtil.fromMap(obj, SwitchState.class);
				break;
			default:
				break;
		}
		return state;
	}
}
