package epf.query;

import epf.schema.utility.EntityEvent;
import epf.schema.utility.EntityTransaction;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.json.ext.Adapter;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection(targets = {
		EntityEvent.class,EntityTransaction.class,PostPersist.class,PostRemove.class,PostUpdate.class,Adapter.class
})
public interface Config {

}
