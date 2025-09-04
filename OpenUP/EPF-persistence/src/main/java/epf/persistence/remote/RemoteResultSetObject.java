package epf.persistence.remote;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class RemoteResultSetObject<U extends ResultSet, V extends RemoteResultSet> extends RemoteWrapperObject<ResultSet, RemoteResultSet> implements RemoteResultSet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoteResultSetObject(final ResultSet resultSet, final RemoteResultSet _resultSet) throws RemoteException {
		super(resultSet, _resultSet);
	}

	@Override
	public boolean next() throws SQLException {
		return false;
	}

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean wasNull() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCursorName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFirst() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLast() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void beforeFirst() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterLast() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean first() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean last() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRow() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean previous() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFetchDirection() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFetchSize() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getConcurrency() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Statement getStatement() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean _next() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _close() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _wasNull() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String _getString(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean _getBoolean(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte _getByte(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short _getShort(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int _getInt(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long _getLong(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float _getFloat(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double _getDouble(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal _getBigDecimal(int columnIndex, int scale) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] _getBytes(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date _getDate(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time _getTime(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp _getTimestamp(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream _getAsciiStream(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream _getUnicodeStream(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream _getBinaryStream(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String _getString(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean _getBoolean(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public byte _getByte(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short _getShort(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int _getInt(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long _getLong(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float _getFloat(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double _getDouble(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal _getBigDecimal(String columnLabel, int scale) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] _getBytes(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date _getDate(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time _getTime(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp _getTimestamp(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream _getAsciiStream(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream _getUnicodeStream(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream _getBinaryStream(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
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
	public String _getCursorName() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSetMetaData _getMetaData() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object _getObject(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object _getObject(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int _findColumn(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Reader _getCharacterStream(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader _getCharacterStream(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal _getBigDecimal(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal _getBigDecimal(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean _isBeforeFirst() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _isAfterLast() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _isFirst() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _isLast() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _beforeFirst() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _afterLast() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean _first() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _last() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int _getRow() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean _absolute(int row) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _relative(int rows) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _previous() throws RemoteException, SQLException {
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
	public int _getType() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int _getConcurrency() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean _rowUpdated() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _rowInserted() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean _rowDeleted() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _updateNull(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBoolean(int columnIndex, boolean x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateByte(int columnIndex, byte x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateShort(int columnIndex, short x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateInt(int columnIndex, int x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateLong(int columnIndex, long x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateFloat(int columnIndex, float x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateDouble(int columnIndex, double x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBigDecimal(int columnIndex, BigDecimal x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateString(int columnIndex, String x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBytes(int columnIndex, byte[] x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateDate(int columnIndex, Date x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateTime(int columnIndex, Time x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateTimestamp(int columnIndex, Timestamp x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateAsciiStream(int columnIndex, InputStream x, int length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBinaryStream(int columnIndex, InputStream x, int length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateCharacterStream(int columnIndex, Reader x, int length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateObject(int columnIndex, Object x, int scaleOrLength) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateObject(int columnIndex, Object x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNull(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBoolean(String columnLabel, boolean x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateByte(String columnLabel, byte x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateShort(String columnLabel, short x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateInt(String columnLabel, int x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateLong(String columnLabel, long x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateFloat(String columnLabel, float x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateDouble(String columnLabel, double x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBigDecimal(String columnLabel, BigDecimal x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateString(String columnLabel, String x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBytes(String columnLabel, byte[] x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateDate(String columnLabel, Date x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateTime(String columnLabel, Time x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateTimestamp(String columnLabel, Timestamp x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateAsciiStream(String columnLabel, InputStream x, int length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBinaryStream(String columnLabel, InputStream x, int length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateCharacterStream(String columnLabel, Reader reader, int length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateObject(String columnLabel, Object x, int scaleOrLength) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateObject(String columnLabel, Object x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _insertRow() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateRow() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _deleteRow() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _refreshRow() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _cancelRowUpdates() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _moveToInsertRow() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _moveToCurrentRow() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Statement _getStatement() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object _getObject(int columnIndex, Map<String, Class<?>> map) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref _getRef(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob _getBlob(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob _getClob(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array _getArray(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object _getObject(String columnLabel, Map<String, Class<?>> map) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ref _getRef(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob _getBlob(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clob _getClob(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array _getArray(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date _getDate(int columnIndex, Calendar cal) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date _getDate(String columnLabel, Calendar cal) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time _getTime(int columnIndex, Calendar cal) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Time _getTime(String columnLabel, Calendar cal) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp _getTimestamp(int columnIndex, Calendar cal) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Timestamp _getTimestamp(String columnLabel, Calendar cal) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL _getURL(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL _getURL(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _updateRef(int columnIndex, Ref x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateRef(String columnLabel, Ref x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBlob(int columnIndex, Blob x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBlob(String columnLabel, Blob x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateClob(int columnIndex, Clob x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateClob(String columnLabel, Clob x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateArray(int columnIndex, Array x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateArray(String columnLabel, Array x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RowId _getRowId(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowId _getRowId(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _updateRowId(int columnIndex, RowId x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateRowId(String columnLabel, RowId x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int _getHoldability() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean _isClosed() throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void _updateNString(int columnIndex, String nString) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNString(String columnLabel, String nString) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNClob(int columnIndex, NClob nClob) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNClob(String columnLabel, NClob nClob) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NClob _getNClob(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob _getNClob(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML _getSQLXML(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML _getSQLXML(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _updateSQLXML(int columnIndex, SQLXML xmlObject) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateSQLXML(String columnLabel, SQLXML xmlObject) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String _getNString(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String _getNString(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader _getNCharacterStream(int columnIndex) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reader _getNCharacterStream(String columnLabel) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _updateNCharacterStream(int columnIndex, Reader x, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNCharacterStream(String columnLabel, Reader reader, long length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateAsciiStream(int columnIndex, InputStream x, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBinaryStream(int columnIndex, InputStream x, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateCharacterStream(int columnIndex, Reader x, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateAsciiStream(String columnLabel, InputStream x, long length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBinaryStream(String columnLabel, InputStream x, long length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateCharacterStream(String columnLabel, Reader reader, long length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBlob(int columnIndex, InputStream inputStream, long length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBlob(String columnLabel, InputStream inputStream, long length)
			throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateClob(int columnIndex, Reader reader, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateClob(String columnLabel, Reader reader, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNClob(int columnIndex, Reader reader, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNClob(String columnLabel, Reader reader, long length) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNCharacterStream(int columnIndex, Reader x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNCharacterStream(String columnLabel, Reader reader) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateAsciiStream(int columnIndex, InputStream x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBinaryStream(int columnIndex, InputStream x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateCharacterStream(int columnIndex, Reader x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateAsciiStream(String columnLabel, InputStream x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBinaryStream(String columnLabel, InputStream x) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateCharacterStream(String columnLabel, Reader reader) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBlob(int columnIndex, InputStream inputStream) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateBlob(String columnLabel, InputStream inputStream) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateClob(int columnIndex, Reader reader) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateClob(String columnLabel, Reader reader) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNClob(int columnIndex, Reader reader) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void _updateNClob(String columnLabel, Reader reader) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> T _getObject(int columnIndex, Class<T> type) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T _getObject(String columnLabel, Class<T> type) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
