package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Savepoint;

public class RemoteSavepointObject<U extends Savepoint, V extends RemoteSavepoint> extends RemoteObject<U, V> implements RemoteSavepoint {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected RemoteSavepointObject(final U savepoint, final V _savepoint) throws RemoteException {
		super(savepoint, _savepoint);
	}

	@Override
	public int getSavepointId() throws SQLException {
		try {
			return _object._getSavepointId();
		} 
		catch (RemoteException e) {
			throw new SQLException(e);
		}
	}

	@Override
	public String getSavepointName() throws SQLException {
		return _object.getSavepointName();
	}

	@Override
	public int _getSavepointId() throws RemoteException, SQLException {
		return object.getSavepointId();
	}

	@Override
	public String _getSavepointName() throws RemoteException, SQLException {
		return object.getSavepointName();
	}

}
