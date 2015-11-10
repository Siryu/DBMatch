package models.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<Table> tables;
	private List<ForeignKey> foreignKeys;
	
	public Database()
	{
		tables = new ArrayList<Table>();
		foreignKeys = new ArrayList<ForeignKey>();
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public List<Table> getTables()
	{
		return tables;
	}
	public void setTables(List<Table> tables)
	{
		this.tables = tables;
	}
	public void addTable(Table table)
	{
		this.tables.add(table);
	}
	
	public List<ForeignKey> getForeignKeys(){
		return foreignKeys;
	}
	
	public void addForeignKey(Table parentTbl, Column parentCol, Table childTbl,  Column childCol)
	{
		ForeignKey key = new ForeignKey(parentTbl, parentCol, childTbl, childCol);
		this.foreignKeys.add(key);
	}
	
	public Table getTableByName(String s)
	{
		for(Table t : this.tables)
		{
			if(t.getName().equals(s))
			{
				return t;
			}
		}
		return null;
	}

	public void removeForeignKey(String columnName) {
		List<ForeignKey> toRemove = new ArrayList<ForeignKey>();
		for(ForeignKey fk : foreignKeys) {
			if(fk.getChildColumn().getName().equals(columnName)) {
				toRemove.add(fk);
			}
		}
		for(ForeignKey fk : toRemove) {
			foreignKeys.remove(fk);
		}
	}

	public void removeTableByName(String name)
	{
		Table removeMe = null;
		for(Table t : this.tables)
		{
			if(t.getName().equals(name))
			{
				removeMe = t;
			}
		}
		this.tables.remove(removeMe);
	}
	
	public String getForeignKeyReference(String childColumn) {
		for(ForeignKey fk : foreignKeys) {
			if(fk.getChildColumn().getName().equals(childColumn)) {
				return "table " + fk.getParentTable().getName() + ", column " + fk.getParentColumn().getName();
			}
		}
		return null;
	}
}
