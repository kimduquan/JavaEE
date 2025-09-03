package epf.persistence.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLType;
import java.sql.ShardingKey;
import java.sql.ShardingKeyBuilder;

public interface RemoteShardingKeyBuilder extends Remote {
	ShardingKeyBuilder subkey(Object subkey, SQLType subkeyType) throws RemoteException;
	ShardingKey build() throws RemoteException;
}
