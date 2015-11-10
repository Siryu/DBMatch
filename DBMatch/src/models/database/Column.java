package models.database;

import java.io.Serializable;
import java.util.Set;

public class Column implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private DataType dataType;
	private boolean nullable;
	private boolean isForeignKey = false;
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public DataType getDataType()
	{
		return dataType;
	}
	public void setDataType(DataType dataType)
	{
		this.dataType = dataType;
		//DataType[] values = DataType.values();
		//values.toString()
	}
	public boolean isNullable()
	{
		return nullable;
	}
	public void setNullable(boolean nullable)
	{
		this.nullable = nullable;
	}
	
	public void setForeignKey(boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}
	
	public boolean getIsForeignKey() {
		return isForeignKey;
	}
}
