package epf.persistence.remote;

import java.rmi.RemoteException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class RemoteConnectionObject extends RemoteWrapperObject implements RemoteConnection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final Connection connection;
	final RemoteConnection _connection;

	public RemoteConnectionObject(final Connection connection, final RemoteConnection _connection) throws RemoteException {
		super(connection, _connection);
		this.connection = connection;
		this._connection = _connection;
	}

	@Override
	public Statement createStatement() throws SQLException {
		try {
			return _connection._createStatement();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		try {
			return _connection._prepareStatement(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		try {
			return _connection._prepareCall(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		try {
			return _connection._nativeSQL(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		try {
			_connection._setAutoCommit(autoCommit);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		try {
			return _connection._getAutoCommit();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void commit() throws SQLException {
		try {
			_connection._commit();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void rollback() throws SQLException {
		try {
			_connection._rollback();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void close() throws SQLException {
		try {
			_connection._close();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isClosed() throws SQLException {
		try {
			return _connection._isClosed();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		try {
			return _connection._getMetaData();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {
		try {
			_connection._setReadOnly(readOnly);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		try {
			return _connection._isReadOnly();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setCatalog(String catalog) throws SQLException {
		try {
			_connection._setCatalog(catalog);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String getCatalog() throws SQLException {
		try {
			return _connection._getCatalog();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {
		try {
			_connection._setTransactionIsolation(level);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		try {
			return _connection._getTransactionIsolation();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		try {
			return _connection._getWarnings();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void clearWarnings() throws SQLException {
		try {
			_connection._clearWarnings();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		try {
			return _connection._createStatement(resultSetType, resultSetConcurrency);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		try {
			return _connection._prepareStatement(sql, resultSetType, resultSetConcurrency);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		try {
			return _connection._prepareCall(sql, resultSetType, resultSetConcurrency);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		try {
			return _connection._getTypeMap();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		try {
			_connection._setTypeMap(map);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setHoldability(int holdability) throws SQLException {
		try {
			_connection._setHoldability(holdability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getHoldability() throws SQLException {
		try {
			return _connection._getHoldability();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		try {
			return _connection._setSavepoint();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {
		try {
			return _connection._setSavepoint(name);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {
		try {
			_connection._rollback(savepoint);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		try {
			_connection._releaseSavepoint(savepoint);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		try {
			return _connection._createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		try {
			return _connection._prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		try {
			return _connection._prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		try {
			return _connection._prepareStatement(sql, autoGeneratedKeys);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		try {
			return _connection._prepareStatement(sql, columnIndexes);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		try {
			return _connection._prepareStatement(sql, columnNames);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Clob createClob() throws SQLException {
		try {
			return _connection._createClob();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Blob createBlob() throws SQLException {
		try {
			return _connection._createBlob();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public NClob createNClob() throws SQLException {
		try {
			return _connection._createNClob();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		try {
			return _connection._createSQLXML();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		try {
			return _connection._isValid(timeout);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		try {
			_connection._setClientInfo(name, value);
		}
		catch(RemoteException ex) {
			throw new SQLClientInfoException();
		}
	}

	@Override
	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		try {
			_connection._setClientInfo(properties);
		}
		catch(RemoteException ex) {
			throw new SQLClientInfoException();
		}
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		try {
			return _connection._getClientInfo(name);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		try {
			return _connection._getClientInfo();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		try {
			return _connection._createArrayOf(typeName, elements);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		try {
			return _connection._createStruct(typeName, attributes);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		try {
			_connection._setSchema(schema);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String getSchema() throws SQLException {
		try {
			return _connection._getSchema();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		try {
			_connection._abort(executor);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		try {
			_connection._setNetworkTimeout(executor, milliseconds);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		try {
			return _connection._getNetworkTimeout();
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public RemoteStatement _createStatement() throws RemoteException, SQLException {
		return new RemoteStatementObject(connection.createStatement(), null);
	}

	@Override
	public PreparedStatement _prepareStatement(String sql) throws RemoteException, SQLException {
		try {
			return _connection._prepareStatement(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public CallableStatement _prepareCall(String sql) throws RemoteException, SQLException {
		try {
			return _connection._prepareCall(sql);
		}
		catch(RemoteException ex) {
			throw new SQLException();
		}
	}

	@Override
	public String _nativeSQL(String sql) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _setAutoCommit(boolean autoCommit) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _getAutoCommit() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _commit() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _rollback() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _close() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _isClosed() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DatabaseMetaData _getMetaData() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _setReadOnly(boolean readOnly) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _isReadOnly() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _setCatalog(String catalog) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String _getCatalog() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _setTransactionIsolation(int level) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getTransactionIsolation() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
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
	public Statement _createStatement(int resultSetType, int resultSetConcurrency)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement _prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement _prepareCall(String sql, int resultSetType, int resultSetConcurrency)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Class<?>> _getTypeMap() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _setTypeMap(Map<String, Class<?>> map) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _setHoldability(int holdability) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getHoldability() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Savepoint _setSavepoint() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Savepoint _setSavepoint(String name) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _rollback(Savepoint savepoint) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _releaseSavepoint(Savepoint savepoint) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Statement _createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement _prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CallableStatement _prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement _prepareStatement(String sql, int autoGeneratedKeys) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement _prepareStatement(String sql, int[] columnIndexes) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PreparedStatement _prepareStatement(String sql, String[] columnNames) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob _createClob() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob _createBlob() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob _createNClob() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML _createSQLXML() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean _isValid(int timeout) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _setClientInfo(String name, String value) throws RemoteException, SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _setClientInfo(Properties properties) throws RemoteException, SQLClientInfoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String _getClientInfo(String name) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties _getClientInfo() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array _createArrayOf(String typeName, Object[] elements) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct _createStruct(String typeName, Object[] attributes) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _setSchema(String schema) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String _getSchema() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _abort(Executor executor) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _setNetworkTimeout(Executor executor, int milliseconds) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getNetworkTimeout() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
