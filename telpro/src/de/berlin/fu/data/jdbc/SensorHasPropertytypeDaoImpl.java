/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package de.berlin.fu.data.jdbc;

import de.berlin.fu.data.dao.*;
import de.berlin.fu.data.factory.*;
import de.berlin.fu.data.dto.*;
import de.berlin.fu.data.exceptions.*;
import java.sql.Connection;
import java.util.Collection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class SensorHasPropertytypeDaoImpl extends AbstractDAO implements SensorHasPropertytypeDao
{
	/** 
	 * The factory class for this DAO has two versions of the create() method - one that
takes no arguments and one that takes a Connection argument. If the Connection version
is chosen then the connection will be stored in this attribute and will be used by all
calls to this DAO, otherwise a new Connection will be allocated for each operation.
	 */
	protected java.sql.Connection userConn;

	/** 
	 * All finder methods in this class use this SELECT constant to build their queries
	 */
	protected final String SQL_SELECT = "SELECT Sensor_idSensor, PropertyType_idPropertyType FROM " + getTableName() + "";

	/** 
	 * Finder methods will pass this value to the JDBC setMaxRows method
	 */
	protected int maxRows;

	/** 
	 * SQL INSERT statement for this table
	 */
	protected final String SQL_INSERT = "INSERT INTO " + getTableName() + " ( Sensor_idSensor, PropertyType_idPropertyType ) VALUES ( ?, ? )";

	/** 
	 * SQL UPDATE statement for this table
	 */
	protected final String SQL_UPDATE = "UPDATE " + getTableName() + " SET Sensor_idSensor = ?, PropertyType_idPropertyType = ? WHERE Sensor_idSensor = ? AND PropertyType_idPropertyType = ?";

	/** 
	 * SQL DELETE statement for this table
	 */
	protected final String SQL_DELETE = "DELETE FROM " + getTableName() + " WHERE Sensor_idSensor = ? AND PropertyType_idPropertyType = ?";

	/** 
	 * Index of column Sensor_idSensor
	 */
	protected static final int COLUMN_SENSOR_IDSENSOR = 1;

	/** 
	 * Index of column PropertyType_idPropertyType
	 */
	protected static final int COLUMN_PROPERTYTYPE_IDPROPERTYTYPE = 2;

	/** 
	 * Number of columns
	 */
	protected static final int NUMBER_OF_COLUMNS = 2;

	/** 
	 * Index of primary-key column Sensor_idSensor
	 */
	protected static final int PK_COLUMN_SENSOR_IDSENSOR = 1;

	/** 
	 * Index of primary-key column PropertyType_idPropertyType
	 */
	protected static final int PK_COLUMN_PROPERTYTYPE_IDPROPERTYTYPE = 2;

	/** 
	 * Inserts a new row in the Sensor_has_PropertyType table.
	 */
	public SensorHasPropertytypePk insert(SensorHasPropertytype dto) throws SensorHasPropertytypeDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			stmt = conn.prepareStatement( SQL_INSERT );
			int index = 1;
			stmt.setString( index++, dto.getSensorIdsensor() );
			stmt.setInt( index++, dto.getPropertytypeIdpropertytype() );
			System.out.println( "Executing " + SQL_INSERT + " with DTO: " + dto );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
			reset(dto);
			return dto.createPk();
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasPropertytypeDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Updates a single row in the Sensor_has_PropertyType table.
	 */
	public void update(SensorHasPropertytypePk pk, SensorHasPropertytype dto) throws SensorHasPropertytypeDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			System.out.println( "Executing " + SQL_UPDATE + " with DTO: " + dto );
			stmt = conn.prepareStatement( SQL_UPDATE );
			int index=1;
			stmt.setString( index++, dto.getSensorIdsensor() );
			stmt.setInt( index++, dto.getPropertytypeIdpropertytype() );
			stmt.setString( 3, pk.getSensorIdsensor() );
			stmt.setInt( 4, pk.getPropertytypeIdpropertytype() );
			int rows = stmt.executeUpdate();
			reset(dto);
			long t2 = System.currentTimeMillis();
			System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasPropertytypeDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Deletes a single row in the Sensor_has_PropertyType table.
	 */
	public void delete(SensorHasPropertytypePk pk) throws SensorHasPropertytypeDaoException
	{
		long t1 = System.currentTimeMillis();
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			System.out.println( "Executing " + SQL_DELETE + " with PK: " + pk );
			stmt = conn.prepareStatement( SQL_DELETE );
			stmt.setString( 1, pk.getSensorIdsensor() );
			stmt.setInt( 2, pk.getPropertytypeIdpropertytype() );
			int rows = stmt.executeUpdate();
			long t2 = System.currentTimeMillis();
			System.out.println( rows + " rows affected (" + (t2-t1) + " ms)" );
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasPropertytypeDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns the rows from the Sensor_has_PropertyType table that matches the specified primary-key value.
	 */
	public SensorHasPropertytype findByPrimaryKey(SensorHasPropertytypePk pk) throws SensorHasPropertytypeDaoException
	{
		return findByPrimaryKey( pk.getSensorIdsensor(), pk.getPropertytypeIdpropertytype() );
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'Sensor_idSensor = :sensorIdsensor AND PropertyType_idPropertyType = :propertytypeIdpropertytype'.
	 */
	public SensorHasPropertytype findByPrimaryKey(String sensorIdsensor, int propertytypeIdpropertytype) throws SensorHasPropertytypeDaoException
	{
		SensorHasPropertytype ret[] = findByDynamicSelect( SQL_SELECT + " WHERE Sensor_idSensor = ? AND PropertyType_idPropertyType = ?", new Object[] { sensorIdsensor,  new Integer(propertytypeIdpropertytype) } );
		return ret.length==0 ? null : ret[0];
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria ''.
	 */
	public SensorHasPropertytype[] findAll() throws SensorHasPropertytypeDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " ORDER BY Sensor_idSensor, PropertyType_idPropertyType", null );
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'PropertyType_idPropertyType = :propertytypeIdpropertytype'.
	 */
	public SensorHasPropertytype[] findByPropertyType(int propertytypeIdpropertytype) throws SensorHasPropertytypeDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PropertyType_idPropertyType = ?", new Object[] {  new Integer(propertytypeIdpropertytype) } );
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public SensorHasPropertytype[] findBySensor(String sensorIdsensor) throws SensorHasPropertytypeDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE Sensor_idSensor = ?", new Object[] { sensorIdsensor } );
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public SensorHasPropertytype[] findWhereSensorIdsensorEquals(String sensorIdsensor) throws SensorHasPropertytypeDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE Sensor_idSensor = ? ORDER BY Sensor_idSensor", new Object[] { sensorIdsensor } );
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'PropertyType_idPropertyType = :propertytypeIdpropertytype'.
	 */
	public SensorHasPropertytype[] findWherePropertytypeIdpropertytypeEquals(int propertytypeIdpropertytype) throws SensorHasPropertytypeDaoException
	{
		return findByDynamicSelect( SQL_SELECT + " WHERE PropertyType_idPropertyType = ? ORDER BY PropertyType_idPropertyType", new Object[] {  new Integer(propertytypeIdpropertytype) } );
	}

	/**
	 * Method 'SensorHasPropertytypeDaoImpl'
	 * 
	 */
	public SensorHasPropertytypeDaoImpl()
	{
	}

	/**
	 * Method 'SensorHasPropertytypeDaoImpl'
	 * 
	 * @param userConn
	 */
	public SensorHasPropertytypeDaoImpl(final java.sql.Connection userConn)
	{
		this.userConn = userConn;
	}

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows)
	{
		this.maxRows = maxRows;
	}

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows()
	{
		return maxRows;
	}

	/**
	 * Method 'getTableName'
	 * 
	 * @return String
	 */
	public String getTableName()
	{
		return "telpro.Sensor_has_PropertyType";
	}

	/** 
	 * Fetches a single row from the result set
	 */
	protected SensorHasPropertytype fetchSingleResult(ResultSet rs) throws SQLException
	{
		if (rs.next()) {
			SensorHasPropertytype dto = new SensorHasPropertytype();
			populateDto( dto, rs);
			return dto;
		} else {
			return null;
		}
		
	}

	/** 
	 * Fetches multiple rows from the result set
	 */
	protected SensorHasPropertytype[] fetchMultiResults(ResultSet rs) throws SQLException
	{
		Collection resultList = new ArrayList();
		while (rs.next()) {
			SensorHasPropertytype dto = new SensorHasPropertytype();
			populateDto( dto, rs);
			resultList.add( dto );
		}
		
		SensorHasPropertytype ret[] = new SensorHasPropertytype[ resultList.size() ];
		resultList.toArray( ret );
		return ret;
	}

	/** 
	 * Populates a DTO with data from a ResultSet
	 */
	protected void populateDto(SensorHasPropertytype dto, ResultSet rs) throws SQLException
	{
		dto.setSensorIdsensor( rs.getString( COLUMN_SENSOR_IDSENSOR ) );
		dto.setPropertytypeIdpropertytype( rs.getInt( COLUMN_PROPERTYTYPE_IDPROPERTYTYPE ) );
	}

	/** 
	 * Resets the modified attributes in the DTO
	 */
	protected void reset(SensorHasPropertytype dto)
	{
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the specified arbitrary SQL statement
	 */
	public SensorHasPropertytype[] findByDynamicSelect(String sql, Object[] sqlParams) throws SensorHasPropertytypeDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = sql;
		
		
			System.out.println( "Executing " + SQL );
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasPropertytypeDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the specified arbitrary SQL statement
	 */
	public SensorHasPropertytype[] findByDynamicWhere(String sql, Object[] sqlParams) throws SensorHasPropertytypeDaoException
	{
		// declare variables
		final boolean isConnSupplied = (userConn != null);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// get the user-specified connection or get a connection from the ResourceManager
			conn = isConnSupplied ? userConn : ResourceManager.getConnection();
		
			// construct the SQL statement
			final String SQL = SQL_SELECT + " WHERE " + sql;
		
		
			System.out.println( "Executing " + SQL );
			// prepare statement
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows( maxRows );
		
			// bind parameters
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setObject( i+1, sqlParams[i] );
			}
		
		
			rs = stmt.executeQuery();
		
			// fetch the results
			return fetchMultiResults(rs);
		}
		catch (Exception _e) {
			_e.printStackTrace();
			throw new SensorHasPropertytypeDaoException( "Exception: " + _e.getMessage(), _e );
		}
		finally {
			ResourceManager.close(rs);
			ResourceManager.close(stmt);
			if (!isConnSupplied) {
				ResourceManager.close(conn);
			}
		
		}
		
	}

}