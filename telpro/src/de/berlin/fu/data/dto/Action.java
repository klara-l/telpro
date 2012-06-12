/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package de.berlin.fu.data.dto;

import de.berlin.fu.data.dao.*;
import de.berlin.fu.data.factory.*;
import de.berlin.fu.data.exceptions.*;
import java.io.Serializable;
import java.util.*;

public class Action implements Serializable
{
	/** 
	 * This attribute maps to the column idAction in the Action table.
	 */
	protected int idAction;

	/** 
	 * This attribute maps to the column Name in the Action table.
	 */
	protected String name;

	/** 
	 * This attribute maps to the column Description in the Action table.
	 */
	protected String description;

	/**
	 * Method 'Action'
	 * 
	 */
	public Action()
	{
	}

	/**
	 * Method 'getIdAction'
	 * 
	 * @return int
	 */
	public int getIdAction()
	{
		return idAction;
	}

	/**
	 * Method 'setIdAction'
	 * 
	 * @param idAction
	 */
	public void setIdAction(int idAction)
	{
		this.idAction = idAction;
	}

	/**
	 * Method 'getName'
	 * 
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Method 'setName'
	 * 
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Method 'getDescription'
	 * 
	 * @return String
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Method 'setDescription'
	 * 
	 * @param description
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof Action)) {
			return false;
		}
		
		final Action _cast = (Action) _other;
		if (idAction != _cast.idAction) {
			return false;
		}
		
		if (name == null ? _cast.name != name : !name.equals( _cast.name )) {
			return false;
		}
		
		if (description == null ? _cast.description != description : !description.equals( _cast.description )) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + idAction;
		if (name != null) {
			_hashCode = 29 * _hashCode + name.hashCode();
		}
		
		if (description != null) {
			_hashCode = 29 * _hashCode + description.hashCode();
		}
		
		return _hashCode;
	}

	/**
	 * Method 'createPk'
	 * 
	 * @return ActionPk
	 */
	public ActionPk createPk()
	{
		return new ActionPk(idAction);
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "de.berlin.fu.data.dto.Action: " );
		ret.append( "idAction=" + idAction );
		ret.append( ", name=" + name );
		ret.append( ", description=" + description );
		return ret.toString();
	}

}
