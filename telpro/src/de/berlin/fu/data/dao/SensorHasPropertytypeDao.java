/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package de.berlin.fu.data.dao;

import de.berlin.fu.data.dto.*;
import de.berlin.fu.data.exceptions.*;

public interface SensorHasPropertytypeDao
{
	/** 
	 * Inserts a new row in the Sensor_has_PropertyType table.
	 */
	public SensorHasPropertytypePk insert(SensorHasPropertytype dto) throws SensorHasPropertytypeDaoException;

	/** 
	 * Updates a single row in the Sensor_has_PropertyType table.
	 */
	public void update(SensorHasPropertytypePk pk, SensorHasPropertytype dto) throws SensorHasPropertytypeDaoException;

	/** 
	 * Deletes a single row in the Sensor_has_PropertyType table.
	 */
	public void delete(SensorHasPropertytypePk pk) throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns the rows from the Sensor_has_PropertyType table that matches the specified primary-key value.
	 */
	public SensorHasPropertytype findByPrimaryKey(SensorHasPropertytypePk pk) throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'Sensor_idSensor = :sensorIdsensor AND PropertyType_idPropertyType = :propertytypeIdpropertytype'.
	 */
	public SensorHasPropertytype findByPrimaryKey(String sensorIdsensor, int propertytypeIdpropertytype) throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria ''.
	 */
	public SensorHasPropertytype[] findAll() throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'PropertyType_idPropertyType = :propertytypeIdpropertytype'.
	 */
	public SensorHasPropertytype[] findByPropertyType(int propertytypeIdpropertytype) throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public SensorHasPropertytype[] findBySensor(String sensorIdsensor) throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public SensorHasPropertytype[] findWhereSensorIdsensorEquals(String sensorIdsensor) throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the criteria 'PropertyType_idPropertyType = :propertytypeIdpropertytype'.
	 */
	public SensorHasPropertytype[] findWherePropertytypeIdpropertytypeEquals(int propertytypeIdpropertytype) throws SensorHasPropertytypeDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the specified arbitrary SQL statement
	 */
	public SensorHasPropertytype[] findByDynamicSelect(String sql, Object[] sqlParams) throws SensorHasPropertytypeDaoException;

	/** 
	 * Returns all rows from the Sensor_has_PropertyType table that match the specified arbitrary SQL statement
	 */
	public SensorHasPropertytype[] findByDynamicWhere(String sql, Object[] sqlParams) throws SensorHasPropertytypeDaoException;

}
