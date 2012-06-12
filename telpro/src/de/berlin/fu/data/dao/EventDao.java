/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package de.berlin.fu.data.dao;

import java.util.Date;
import de.berlin.fu.data.dto.*;
import de.berlin.fu.data.exceptions.*;

public interface EventDao
{
	/** 
	 * Inserts a new row in the Event table.
	 */
	public EventPk insert(Event dto) throws EventDaoException;

	/** 
	 * Updates a single row in the Event table.
	 */
	public void update(EventPk pk, Event dto) throws EventDaoException;

	/** 
	 * Deletes a single row in the Event table.
	 */
	public void delete(EventPk pk) throws EventDaoException;

	/** 
	 * Returns the rows from the Event table that matches the specified primary-key value.
	 */
	public Event findByPrimaryKey(EventPk pk) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'idEvent = :idEvent AND EventType_idEventType = :eventtypeIdeventtype'.
	 */
	public Event findByPrimaryKey(int idEvent, int eventtypeIdeventtype) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria ''.
	 */
	public Event[] findAll() throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'EventType_idEventType = :eventtypeIdeventtype'.
	 */
	public Event[] findByEventType(int eventtypeIdeventtype) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public Event[] findBySensor(String sensorIdsensor) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'Trigger_idTrigger = :triggerIdtrigger'.
	 */
	public Event[] findByTrigger(int triggerIdtrigger) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'idEvent = :idEvent'.
	 */
	public Event[] findWhereIdEventEquals(int idEvent) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'EventType_idEventType = :eventtypeIdeventtype'.
	 */
	public Event[] findWhereEventtypeIdeventtypeEquals(int eventtypeIdeventtype) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'Timestamp = :timestamp'.
	 */
	public Event[] findWhereTimestampEquals(Date timestamp) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'Sensor_idSensor = :sensorIdsensor'.
	 */
	public Event[] findWhereSensorIdsensorEquals(String sensorIdsensor) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the criteria 'Trigger_idTrigger = :triggerIdtrigger'.
	 */
	public Event[] findWhereTriggerIdtriggerEquals(int triggerIdtrigger) throws EventDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the Event table that match the specified arbitrary SQL statement
	 */
	public Event[] findByDynamicSelect(String sql, Object[] sqlParams) throws EventDaoException;

	/** 
	 * Returns all rows from the Event table that match the specified arbitrary SQL statement
	 */
	public Event[] findByDynamicWhere(String sql, Object[] sqlParams) throws EventDaoException;

}