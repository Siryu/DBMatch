package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import dataStorage.FileStorage;
import models.User;
import models.database.Column;
import models.database.DataType;
import models.database.Table;

public class TemporaryTemplateLoader {
	Scanner scanLee = new Scanner(System.in);
	
	HashMap<String, String[]> tableMap = new HashMap<>();
	{
		tableMap.put("users", new String[]{"user_id", "password", "fname", "lname", "email", "phone", "address_id"});
		tableMap.put("products", new String[]{"product_id", "name", "description", "price", "unit"}); 
		tableMap.put("distributors", new String[]{"distributor_id", "distributor_name"});
		tableMap.put("addresses", new String[]{"address_id", "street", "city", "state", "zip"});
		tableMap.put("vendors", new String[]{"vendor_id", "vendor_name", "point_of_contact", "email", "phone", "fax", "address_id"});
		tableMap.put("customers", new String[]{"customer_id", "user_id", "order_id"});
		tableMap.put("courses", new String[]{"course_code", "course_name", "course_description", "credit_hours"});
		tableMap.put("instructors", new String[]{"instructor_id", "user_id"}); 
		tableMap.put("students", new String[]{"student_id", "user_id", "course_code", "semester", "credits_attempted", "credits_earned", "class"});
		tableMap.put("trainers", new String[]{"trainer_id", "user_id"});
		tableMap.put("departments", new String[]{"department_id", "department_name"}); 
		tableMap.put("managers", new String[]{"manager_id", "user_id", "department_id", "office_number"});
		tableMap.put("projects", new String[]{"project_id", "project_name", "manager_id"});
		tableMap.put("categories", new String[]{"category_id", "category_name"});
		tableMap.put("shopping_cart", new String[]{"cart_id", "customer_id", "item_id", "quantity"});
		tableMap.put("regions", new String[]{"region_id", "region_name"});
		tableMap.put("orders", new String[]{"order_id", "product_id", "quantity", "customer_id", "order_date", "ship_date", "total"});
	}
	
	public List<Table> buildTables(){
		List<Table> tables = new ArrayList<Table>();
		for(String tab : tableMap.keySet()){
			Table newTable = new Table();
			newTable.setName(tab);
			newTable.setColumns(new ArrayList<Column>());
			for(String col : tableMap.get(tab)){
				Column c = new Column();
				c.setName(col);
				System.out.println(col + " on " + tab);
				c.setDataType(promptDataType());
				c.setNullable(isNullable());
				newTable.addColumns(c);
			}
			tables.add(newTable);
		}
		return tables;
	}
	
	public DataType promptDataType(){
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<DataType.values().length; i++){
			sb.append((i+1) + ") " + DataType.values()[i] + "\n");
		}
		sb.append("Choose a data type: ");
		System.out.print(sb);
		return DataType.values()[Integer.parseInt(scanLee.nextLine())];
	}
	
	public boolean isNullable(){
		System.out.print("Nullable: ");
		return Boolean.parseBoolean(scanLee.nextLine());
	}
	
	public static void main(String[] args){
		FileStorage fs = new FileStorage();
		List<Table> tables = new TemporaryTemplateLoader().buildTables();
		User newUser = new User("templates", "password");
		newUser.setUserID(42);
		for(Table t : tables){
			fs.createItem(newUser, t);
		}
	}
}
