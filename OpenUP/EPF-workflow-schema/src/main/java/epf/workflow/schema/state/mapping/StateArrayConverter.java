package epf.workflow.schema.state.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import epf.workflow.schema.mapping.ArrayAttributeConverter;
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
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

/**
 * @author PC
 *
 */
public class StateArrayConverter extends ArrayAttributeConverter<State> {
	
	/**
	 * 
	 */
	public StateArrayConverter() {
		super(State.class, new State[0]);
	}

	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(StateArrayConverter.class.getName());

	@Override
	public State[] convertToEntityAttribute(final JsonValue dbData) {
		try {
			final List<State> list = new ArrayList<>();
			final Iterator<JsonValue> it = dbData.asJsonArray().iterator();
			try(Jsonb jsonb = JsonbBuilder.create()){
				while(it.hasNext()) {
					final Map<String, Object> map = new HashMap<>();
					it.next().asJsonObject().forEach((name, value) -> {
						final Object object = JsonUtil.asValue(value);
						map.put(name, object);
					});
					final State state = newState(map);
					list.add(state);
				}
			}
			return list.toArray(new State[0]);
		} 
		catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "convertToEntityAttribute", ex);
			return null;
		}
	}
	
	private State newState(final Map<String, Object> map) {
		if(map != null) {
			final Type type = EnumUtil.valueOf(Type.class, (String)map.get("type"));
			try {
				switch(type) {
					case callback:
						return JsonUtil.fromMap(map, CallbackState.class);
					case event:
						return JsonUtil.fromMap(map, EventState.class);
					case foreach:
						return JsonUtil.fromMap(map, ForEachState.class);
					case inject:
						return JsonUtil.fromMap(map, InjectState.class);
					case operation:
						return JsonUtil.fromMap(map, OperationState.class);
					case parallel:
						return JsonUtil.fromMap(map, ParallelState.class);
					case sleep:
						return JsonUtil.fromMap(map, SleepState.class);
					case Switch:
						return JsonUtil.fromMap(map, SwitchState.class);
					default:
						break;
				}
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "newState", ex);
				return null;
			}
		}
		return null;
	}
}
