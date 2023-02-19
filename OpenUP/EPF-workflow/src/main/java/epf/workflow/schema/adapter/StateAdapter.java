package epf.workflow.schema.adapter;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import epf.util.json.JsonUtil;
import epf.workflow.schema.CallbackState;
import epf.workflow.schema.EventState;
import epf.workflow.schema.ForEachState;
import epf.workflow.schema.InjectState;
import epf.workflow.schema.OperationState;
import epf.workflow.schema.ParallelState;
import epf.workflow.schema.SleepState;
import epf.workflow.schema.State;
import epf.workflow.schema.SwitchState;
import epf.workflow.schema.Type;

/**
 * @author PC
 *
 */
public class StateAdapter implements JsonbAdapter<State, JsonObject> {

	@Override
	public JsonObject adaptToJson(final State obj) throws Exception {
		return JsonUtil.toJson(obj);
	}

	@Override
	public State adaptFromJson(final JsonObject obj) throws Exception {
		final Type type = Type.valueOf(obj.getString("type"));
		State state = null;
		switch(type) {
			case Callback:
				state = JsonUtil.fromJson(obj.toString(), CallbackState.class);
				break;
			case Event:
				state = JsonUtil.fromJson(obj.toString(), EventState.class);
				break;
			case ForEach:
				state = JsonUtil.fromJson(obj.toString(), ForEachState.class);
				break;
			case Inject:
				state = JsonUtil.fromJson(obj.toString(), InjectState.class);
				break;
			case Operation:
				state = JsonUtil.fromJson(obj.toString(), OperationState.class);
				break;
			case Parallel:
				state = JsonUtil.fromJson(obj.toString(), ParallelState.class);
				break;
			case Sleep:
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
