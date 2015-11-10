package tools;

import java.util.List;

import models.database.Column;
import models.database.Database;
import models.database.ForeignKey;
import models.database.Table;

public class ScriptGenerator {

	private static final String[] keywords = { "database", "value","constraint", "address", "action", "array", 
		"backup", "blob", "call", "commit", "copy", "count", "data", "date", "day", "datetime", "dayofmonth", "dayofweek", "dayofyear", "descriptor", "degree", 
		"dictionary", "domain", "dump", "free", "foreign", "general", "global", "generated", "identity", "index", "key", "level", "limit", "map", "method", 
		"member", "month", "monthname", "name", "object", "online", "open", "order", "Order", "password", "path", "percent", "power", "plan", "prepared", "primary", 
		"privileges", "prefix", "quote", "real", "release", "require", "repeat", "role", "rollback", "result", "restrict", "routine", "row", "rowid", "rownum", 
		"rule", "savepoint", "scale", "schema", "scope", "search", "second", "section", "sensitive", "session", "set", "size", "space", "state", "statement", 
		"statistics", "status", "style", "structure", "sum", "successful", "table", "template", "text", "time", "timestamp", "toast", "top", "transaction", 
		"treat", "type", "union", "usage", "use", "user", "view", "window", "work", "year", "zone"};
	
	public static String generateScript(Database database) {
		StringBuilder sb = new StringBuilder();
		sb.append("use master;\ngo\n");

		//let it fail if the database already exists
		
		sb.append("create database " + replaceKeywordsAndSpaces(database.getName()) + ";\ngo\n");
		sb.append("use " + replaceKeywordsAndSpaces(database.getName()) + ";\ngo\n");
		
		//for every table in the database
		for(Table t : database.getTables()) {
			//create the table set up script
			sb.append("\n" + generateTable(t));
		}
		//constraints here?
		for(ForeignKey fk : database.getForeignKeys()) {
			sb.append("\n" + generateForeignKey(fk));
		}
		//return script
		return sb.toString();
	}
	
	public static String generateForeignKey(ForeignKey fk) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("ALTER TABLE " + replaceKeywordsAndSpaces(fk.getChildTable().getName()));
		sb.append("\nADD FOREIGN KEY (" + replaceKeywordsAndSpaces(fk.getChildColumn().getName()));
		sb.append(") \nREFERENCES " + replaceKeywordsAndSpaces(fk.getParentTable().getName()) + "(");
		sb.append(replaceKeywordsAndSpaces(fk.getParentColumn().getName()) + ");\ngo");
		
		return sb.toString();
	}

	public static String generateTable(Table table) {
		StringBuilder sb = new StringBuilder();
		//let it fail if table exists

		sb.append("create table " + replaceKeywordsAndSpaces(table.getName()) + "\n(\n");
		//for each column in the table
		for(Column c : table.getColumns()) {
			//create the column
			sb.append("\t" + generateColumn(c) + ",\n");
		}
		List<Column> pk = table.getPrimaryKey();
		if(pk != null && pk.size() > 0) {
			sb.append("\tConstraint PK");
			for(Column c : pk) {
				sb.append("_" + c.getName().replace(' ', '_'));
			}
			sb.append(" Primary Key(");
			for(Column c : pk) {
				sb.append(replaceKeywordsAndSpaces(c.getName()) + ",");
			}
			sb.setCharAt(sb.length() - 1, ')');
		} else {
			sb.deleteCharAt(sb.length() - 2);
		}
		sb.append("\n);\ngo\n");
		//return table set up script
		return sb.toString();
	}
	
	private static String generateColumn(Column col) {
		StringBuilder sb = new StringBuilder();
		//create column
		sb.append(replaceKeywordsAndSpaces(col.getName()));
		
		sb.append(" ");
		sb.append(col.getDataType().name());
		
		if(!col.isNullable()) {
			sb.append(" not null");
		}
		//return the column
		return sb.toString();
	}
	
	private static boolean isKeyword(String name) {
		if(name != null) {
			for(String s : keywords) {
				if(s.equals(name.toLowerCase())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static String replaceKeywordsAndSpaces(String toReplace) {
		if(isKeyword(toReplace)) {
			return ("[" + toReplace + "]").replace(' ', '_');
		}  else {
			return toReplace.replace(' ', '_');
		}
	}

}
