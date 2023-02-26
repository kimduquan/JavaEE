package epf.workflow.state.schema.adapter;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import epf.util.json.JsonUtil;
import epf.workflow.state.schema.CallbackState;
import epf.workflow.state.schema.EventState;
import epf.workflow.state.schema.ForEachState;
import epf.workflow.state.schema.InjectState;
import epf.workflow.state.schema.OperationState;
import epf.workflow.state.schema.ParallelState;
import epf.workflow.state.schema.SleepState;
import epf.workflow.state.schema.State;
import epf.workflow.state.schema.SwitchState;
import epf.workflow.state.schema.Type;
import epf.workflow.util.EnumUtil;

/**
 * @author PC
 *
 */
public class StateAdapter implements JsonbAdapter<State, JsonObject> {

	@Override
	public JsonObject adaptToJson(final State obj) throws Exception {
		return JsonUtil.toJsonObject(obj);
	}

	@Override
	public State adaptFromJson(final JsonObject obj) throws Exception {
		final Type type = EnumUtil.valueOf(Type.class, obj.getString("type"));
		State state = null;
		switch(type) {
			case callback:
				state = JsonUtil.fromJson(obj.toString(), CallbackState.class);
				break;
			case event:
				state = JsonUtil.fromJson(obj.toString(), EventState.class);
				break;
			case foreach:
				state = JsonUtil.fromJson(obj.toString(), ForEachState.class);
				break;
			case inject:
				state = JsonUtil.fromJson(obj.toString(), InjectState.class);
				break;
			case operation:
				state = JsonUtil.fromJson(obj.toString(), OperationState.class);
				break;
			case parallel:
				state = JsonUtil.fromJson(obj.toString(), ParallelState.class);
				break;
			case sleep:
				state = JsonUtil.fromJson(obj.toString(), SleepState.class);
				break;
			case Switch:
				state = JsonUtil.fromJson(obj.toString(), SwitchState.class);
				break;
			default:
				break;
		}
		return state;
	}
}
