package epf.workflow.schema.state.adapter;

import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;
import epf.util.json.ext.JsonUtil;
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
