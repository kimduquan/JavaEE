package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

public class RemoteStatementObject<U extends Statement, V extends RemoteStatement> extends RemoteWrapperObject<U, V> implements RemoteStatement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public RemoteStatementObject(final U statement, final V _statement) throws RemoteException {
		super(statement, _statement);
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		try {
			return _object._executeQuery(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		try {
			return _object._executeUpdate(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void close() throws SQLException {
		try {
			_object._close();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getMaxFieldSize() throws SQLException {
		try {
			return _object._getMaxFieldSize();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setMaxFieldSize(int max) throws SQLException {
		try {
			_object._setMaxFieldSize(max);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getMaxRows() throws SQLException {
		try {
			return _object._getMaxRows();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setMaxRows(int max) throws SQLException {
		try {
			_object._setMaxRows(max);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setEscapeProcessing(boolean enable) throws SQLException {
		try {
			_object._setEscapeProcessing(enable);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getQueryTimeout() throws SQLException {
		try {
			return _object._getQueryTimeout();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setQueryTimeout(int seconds) throws SQLException {
		try {
			_object._setQueryTimeout(seconds);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void cancel() throws SQLException {
		try {
			_object._cancel();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		try {
			return _object._getWarnings();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void clearWarnings() throws SQLException {
		try {
			_object._clearWarnings();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setCursorName(String name) throws SQLException {
		try {
			_object._setCursorName(name);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		try {
			return _object._execute(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		try {
			return _object._getResultSet();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getUpdateCount() throws SQLException {
		try {
			return _object._getUpdateCount();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean getMoreResults() throws SQLException {
		try {
			return _object._getMoreResults();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		try {
			_object._setFetchDirection(direction);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getFetchDirection() throws SQLException {
		try {
			return _object._getFetchDirection();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		try {
			_object._setFetchSize(rows);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getFetchSize() throws SQLException {
		try {
			return _object._getFetchSize();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getResultSetConcurrency() throws SQLException {
		try {
			return _object._getResultSetConcurrency();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getResultSetType() throws SQLException {
		try {
			return _object._getResultSetType();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void addBatch(String sql) throws SQLException {
		try {
			_object._addBatch(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void clearBatch() throws SQLException {
		try {
			_object._clearBatch();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int[] executeBatch() throws SQLException {
		try {
			return _object._executeBatch();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		try {
			return _object._getConnection();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean getMoreResults(int current) throws SQLException {
		try {
			return _object._getMoreResults(current);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public ResultSet getGeneratedKeys() throws SQLException {
		try {
			return _object._getGeneratedKeys();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			return _object._executeUpdate(sql, autoGeneratedKeys);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		try {
			return _object._executeUpdate(sql, columnIndexes);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		try {
			return _object._executeUpdate(sql, columnNames);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			return _object._execute(sql, autoGeneratedKeys);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		try {
			return _object._execute(sql, columnIndexes);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean execute(String sql, String[] columnNames) throws SQLException {
		try {
			return _object._execute(sql, columnNames);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getResultSetHoldability() throws SQLException {
		try {
			return _object._getResultSetHoldability();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isClosed() throws SQLException {
		try {
			return _object._isClosed();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		try {
			_object._setPoolable(poolable);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isPoolable() throws SQLException {
		try {
			return _object._isPoolable();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		try {
			_object._closeOnCompletion();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		try {
			return _object._isCloseOnCompletion();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public RemoteResultSet _executeQuery(String sql) throws RemoteException, SQLException {
		return new RemoteResultSetObject<>(object.executeQuery(sql), null);
	}

	@Override
	public int _executeUpdate(String sql) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void _close() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getMaxFieldSize() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void _setMaxFieldSize(int max) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getMaxRows() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void _setMaxRows(int max) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _setEscapeProcessing(boolean enable) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getQueryTimeout() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void _setQueryTimeout(int seconds) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _cancel() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SQLWarning _getWarnings() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _clearWarnings() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _setCursorName(String name) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _execute(String sql) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RemoteResultSet _getResultSet() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int _getUpdateCount() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean _getMoreResults() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _setFetchDirection(int direction) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getFetchDirection() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void _setFetchSize(int rows) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getFetchSize() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int _getResultSetConcurrency() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int _getResultSetType() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void _addBatch(String sql) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _clearBatch() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int[] _executeBatch() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RemoteConnection _getConnection() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean _getMoreResults(int current) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RemoteResultSet _getGeneratedKeys() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int _executeUpdate(String sql, int autoGeneratedKeys) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int _executeUpdate(String sql, int[] columnIndexes) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int _executeUpdate(String sql, String[] columnNames) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean _execute(String sql, int autoGeneratedKeys) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _execute(String sql, int[] columnIndexes) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _execute(String sql, String[] columnNames) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int _getResultSetHoldability() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean _isClosed() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _setPoolable(boolean poolable) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _isPoolable() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _closeOnCompletion() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _isCloseOnCompletion() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
