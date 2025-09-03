package epf.persistence.remote;

import java.rmi.Remote;
import java.sql.Savepoint;

public interface RemoteSavepoint extends Savepoint, Remote {

}
