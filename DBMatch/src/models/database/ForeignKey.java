package models.database;

import java.io.Serializable;

public class ForeignKey implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Table parentTable;
	private Table childTable;
	private Column parentColumn;
	private Column childColumn;
	
	public ForeignKey(Table parentTable, Column parentCol, Table childTable, Column childCol){
		this.parentTable = parentTable;
		this.childTable = childTable;
		this.parentColumn = parentCol;
		this.childColumn = childCol;
	}
	
	public Table getParentTable() {
		return parentTable;
	}
	public void setParentTable(Table parentTable) {
		this.parentTable = parentTable;
	}
	public Table getChildTable() {
		return childTable;
	}
	public void setChildTable(Table childTable) {
		this.childTable = childTable;
	}
	public Column getParentColumn() {
		return parentColumn;
	}
	public void setParentColumn(Column parentColumn) {
		this.parentColumn = parentColumn;
	}
	public Column getChildColumn() {
		return childColumn;
	}
	public void setChildColumn(Column childColumn) {
		this.childColumn = childColumn;
	}
	
	
	
	
	
}
