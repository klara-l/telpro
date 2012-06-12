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

public interface ActionDao
{
	/** 
	 * Inserts a new row in the Action table.
	 */
	public ActionPk insert(Action dto) throws ActionDaoException;

	/** 
	 * Updates a single row in the Action table.
	 */
	public void update(ActionPk pk, Action dto) throws ActionDaoException;

	/** 
	 * Deletes a single row in the Action table.
	 */
	public void delete(ActionPk pk) throws ActionDaoException;

	/** 
	 * Returns the rows from the Action table that matches the specified primary-key value.
	 */
	public Action findByPrimaryKey(ActionPk pk) throws ActionDaoException;

	/** 
	 * Returns all rows from the Action table that match the criteria 'idAction = :idAction'.
	 */
	public Action findByPrimaryKey(int idAction) throws ActionDaoException;

	/** 
	 * Returns all rows from the Action table that match the criteria ''.
	 */
	public Action[] findAll() throws ActionDaoException;

	/** 
	 * Returns all rows from the Action table that match the criteria 'idAction = :idAction'.
	 */
	public Action[] findWhereIdActionEquals(int idAction) throws ActionDaoException;

	/** 
	 * Returns all rows from the Action table that match the criteria 'Name = :name'.
	 */
	public Action[] findWhereNameEquals(String name) throws ActionDaoException;

	/** 
	 * Returns all rows from the Action table that match the criteria 'Description = :description'.
	 */
	public Action[] findWhereDescriptionEquals(String description) throws ActionDaoException;

	/** 
	 * Sets the value of maxRows
	 */
	public void setMaxRows(int maxRows);

	/** 
	 * Gets the value of maxRows
	 */
	public int getMaxRows();

	/** 
	 * Returns all rows from the Action table that match the specified arbitrary SQL statement
	 */
	public Action[] findByDynamicSelect(String sql, Object[] sqlParams) throws ActionDaoException;

	/** 
	 * Returns all rows from the Action table that match the specified arbitrary SQL statement
	 */
	public Action[] findByDynamicWhere(String sql, Object[] sqlParams) throws ActionDaoException;

}
