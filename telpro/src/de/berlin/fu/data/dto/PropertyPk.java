/*
 * This source file was generated by FireStorm/DAO.
 * 
 * If you purchase a full license for FireStorm/DAO you can customize this header file.
 * 
 * For more information please visit http://www.codefutures.com/products/firestorm
 */

package de.berlin.fu.data.dto;

import java.io.Serializable;

/** 
 * This class represents the primary key of the Property table.
 */
public class PropertyPk implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8518982585298828931L;

	protected int idProperty;

	protected int propertytypeIdpropertytype;

	protected String sensorIdsensor;

	/** 
	 * This attribute represents whether the primitive attribute idProperty is null.
	 */
	protected boolean idPropertyNull;

	/** 
	 * This attribute represents whether the primitive attribute propertytypeIdpropertytype is null.
	 */
	protected boolean propertytypeIdpropertytypeNull;

	/** 
	 * Sets the value of idProperty
	 */
	public void setIdProperty(int idProperty)
	{
		this.idProperty = idProperty;
	}

	/** 
	 * Gets the value of idProperty
	 */
	public int getIdProperty()
	{
		return idProperty;
	}

	/** 
	 * Sets the value of propertytypeIdpropertytype
	 */
	public void setPropertytypeIdpropertytype(int propertytypeIdpropertytype)
	{
		this.propertytypeIdpropertytype = propertytypeIdpropertytype;
	}

	/** 
	 * Gets the value of propertytypeIdpropertytype
	 */
	public int getPropertytypeIdpropertytype()
	{
		return propertytypeIdpropertytype;
	}

	/** 
	 * Sets the value of sensorIdsensor
	 */
	public void setSensorIdsensor(String sensorIdsensor)
	{
		this.sensorIdsensor = sensorIdsensor;
	}

	/** 
	 * Gets the value of sensorIdsensor
	 */
	public String getSensorIdsensor()
	{
		return sensorIdsensor;
	}

	/**
	 * Method 'PropertyPk'
	 * 
	 */
	public PropertyPk()
	{
	}

	/**
	 * Method 'PropertyPk'
	 * 
	 * @param idProperty
	 * @param propertytypeIdpropertytype
	 * @param sensorIdsensor
	 */
	public PropertyPk(final int idProperty, final int propertytypeIdpropertytype, final String sensorIdsensor)
	{
		this.idProperty = idProperty;
		this.propertytypeIdpropertytype = propertytypeIdpropertytype;
		this.sensorIdsensor = sensorIdsensor;
	}

	/** 
	 * Sets the value of idPropertyNull
	 */
	public void setIdPropertyNull(boolean idPropertyNull)
	{
		this.idPropertyNull = idPropertyNull;
	}

	/** 
	 * Gets the value of idPropertyNull
	 */
	public boolean isIdPropertyNull()
	{
		return idPropertyNull;
	}

	/** 
	 * Sets the value of propertytypeIdpropertytypeNull
	 */
	public void setPropertytypeIdpropertytypeNull(boolean propertytypeIdpropertytypeNull)
	{
		this.propertytypeIdpropertytypeNull = propertytypeIdpropertytypeNull;
	}

	/** 
	 * Gets the value of propertytypeIdpropertytypeNull
	 */
	public boolean isPropertytypeIdpropertytypeNull()
	{
		return propertytypeIdpropertytypeNull;
	}

	/**
	 * Method 'equals'
	 * 
	 * @param _other
	 * @return boolean
	 */
	@Override
	public boolean equals(Object _other)
	{
		if (_other == null) {
			return false;
		}
		
		if (_other == this) {
			return true;
		}
		
		if (!(_other instanceof PropertyPk)) {
			return false;
		}
		
		final PropertyPk _cast = (PropertyPk) _other;
		if (idProperty != _cast.idProperty) {
			return false;
		}
		
		if (propertytypeIdpropertytype != _cast.propertytypeIdpropertytype) {
			return false;
		}
		
		if (sensorIdsensor == null ? _cast.sensorIdsensor != sensorIdsensor : !sensorIdsensor.equals( _cast.sensorIdsensor )) {
			return false;
		}
		
		if (idPropertyNull != _cast.idPropertyNull) {
			return false;
		}
		
		if (propertytypeIdpropertytypeNull != _cast.propertytypeIdpropertytypeNull) {
			return false;
		}
		
		return true;
	}

	/**
	 * Method 'hashCode'
	 * 
	 * @return int
	 */
	@Override
	public int hashCode()
	{
		int _hashCode = 0;
		_hashCode = 29 * _hashCode + idProperty;
		_hashCode = 29 * _hashCode + propertytypeIdpropertytype;
		if (sensorIdsensor != null) {
			_hashCode = 29 * _hashCode + sensorIdsensor.hashCode();
		}
		
		_hashCode = 29 * _hashCode + (idPropertyNull ? 1 : 0);
		_hashCode = 29 * _hashCode + (propertytypeIdpropertytypeNull ? 1 : 0);
		return _hashCode;
	}

	/**
	 * Method 'toString'
	 * 
	 * @return String
	 */
	@Override
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		ret.append( "de.berlin.fu.data.dto.PropertyPk: " );
		ret.append( "idProperty=" + idProperty );
		ret.append( ", propertytypeIdpropertytype=" + propertytypeIdpropertytype );
		ret.append( ", sensorIdsensor=" + sensorIdsensor );
		return ret.toString();
	}

}
