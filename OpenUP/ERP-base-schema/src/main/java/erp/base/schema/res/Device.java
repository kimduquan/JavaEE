package erp.base.schema.res;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.device.Log;
import jakarta.persistence.Entity;

@Entity
@Description("Devices")
public class Device extends Log {

}
