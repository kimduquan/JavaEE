package epf.workflow.schema.states;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import epf.workflow.schema.events.OnEvents;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "exclusive",
    "onEvents"
})
@Embeddable
public class EventState extends DefaultState
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * If true consuming one of the defined events causes its associated actions to be performed. If false all of the defined events must be consumed in order for actions to be performed
     * 
     */
    @JsonbProperty("exclusive")
    @Column
    private boolean exclusive = true;
    /**
     * Define what events trigger one or more actions to be performed
     * 
     */
    @JsonbProperty("onEvents")
    @Valid
    @Column
    private List<OnEvents> onEvents = new ArrayList<OnEvents>();

    /**
     * If true consuming one of the defined events causes its associated actions to be performed. If false all of the defined events must be consumed in order for actions to be performed
     * 
     */
    @JsonbProperty("exclusive")
    public boolean isExclusive() {
        return exclusive;
    }

    /**
     * If true consuming one of the defined events causes its associated actions to be performed. If false all of the defined events must be consumed in order for actions to be performed
     * 
     */
    @JsonbProperty("exclusive")
    public void setExclusive(final boolean exclusive) {
        this.exclusive = exclusive;
    }

    /**
     * Define what events trigger one or more actions to be performed
     * 
     */
    @JsonbProperty("onEvents")
    public List<OnEvents> getOnEvents() {
        return onEvents;
    }

    /**
     * Define what events trigger one or more actions to be performed
     * 
     */
    @JsonbProperty("onEvents")
    public void setOnEvents(final List<OnEvents> onEvents) {
        this.onEvents = onEvents;
    }
}
