package models.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Table implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<Column> columns;
	private List<Column> primaryKey;
	
	public Table()
	{
		this.columns = new ArrayList<Column>();
		this.primaryKey = new ArrayList<Column>();
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<Column> getColumns()
	{
		return columns;
	}
	public void setColumns(List<Column> columns)
	{
		this.columns = columns;
	}
	public void addColumns(Column... columns){
		if(this.columns == null) this.columns = new ArrayList<Column>();
		for(Column c : columns) this.columns.add(c);
	}
	public void removeColumn(String column){
		Column removeMe = null;
		for(Column c : columns)
		{
			if(c.getName().equals(column)) 
			{
				removeMe = c;
				break;
			}
		}
		this.columns.remove(removeMe);
	}
	public List<Column> getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(List<Column> primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	public boolean getIsPrimary(Column c) {
		for(Column col : primaryKey) {
			if(c.equals(col)) {
				return true;
			}
		}
		return false;
	}
	
	public Column getColumnByName(String s)
	{
		for(Column c : this.columns)
		{
			if(c.getName().equals(s))
			{
				return c;
			}
		}
		return null;
	}
}
