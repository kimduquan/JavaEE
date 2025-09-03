package epf.persistence.remote;

import java.rmi.Remote;
import java.sql.ShardingKey;

public interface RemoteShardingKey extends ShardingKey, Remote {

}
