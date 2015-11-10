package testing;

import java.util.ArrayList;
import java.util.List;

import tools.ScriptGenerator;
import models.database.Column;
import models.database.DataType;
import models.database.Database;
import models.database.ForeignKey;
import models.database.Table;
import models.database.Database;

public class ScriptGeneratorTest {
	
	public static void main(String[] args) {
		Database d = new Database();
		d.setName("ReviewDB");
		
		List<Table> tables = new ArrayList<>();
		Table one = new Table();
		one.setName("Topics");
		
		Column c1 = new Column();
		c1.setName("id");
		c1.setDataType(DataType.INT);
		
		Column c2 = new Column();
		c2.setName("name");
		c2.setDataType(DataType.VARCHAR);
		c2.setNullable(false);
		
		Column c3 = new Column();
		c3.setName("code");
		c3.setDataType(DataType.VARCHAR);
		c3.setNullable(false);
		
		Column c4 = new Column();
		c4.setName("level");
		c4.setDataType(DataType.TINYINT);
		c4.setNullable(false);
		
		one.addColumns(c1, c2, c3, c4);
		
		List<Column> pk = new ArrayList<Column>();
		pk.add(c1);
		one.setPrimaryKey(pk);
		
		tables.add(one);
		
		d.setTables(tables);
		d.addForeignKey(one, c1, one, c2);
		
		System.out.println(ScriptGenerator.generateScript(d));
	}

}
