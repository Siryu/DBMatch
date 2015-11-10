package dataStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import models.User;
import models.database.Database;
import models.database.Table;
import server.ThreadLocalUser;
import tools.Serializer;

@Stateless
@LocalBean
public class FileStorage implements FileStorageConnection
{
	@Inject private Serializer serializer;

	private static final String filesRoot = "/var/lib/tomcat7/data/users/";
//	private static final String filesRoot = "C:\\Users\\Corey Massey\\workspace\\EE\\dbmatch\\users\\";
	//private static final String filesRoot = "C:\\Users\\Corey Massey\\workspace\\EE\\dbmatch\\users\\";
	//private static final String filesRoot = "C:\\Users\\Sean Palmer\\class-use\\";
//	private static final String filesRoot = "C:\\Users\\Kelsey St Clair\\workspace\\dbmatch";
	//private static final String filesRoot = "C:\\Users\\Kelsey St Clair\\workspace\\dbmatch";

	@Override
	public boolean createItem(User user, Object object)
	{
		boolean itWorked = false;
		
		if(object instanceof Database)
		{
			Database newDatabase = (Database) object;
			String filePath = getFilePathForDB(newDatabase.getName());
			new File(filePath).getParentFile().mkdirs();
			serializer.serializeObject(newDatabase, filePath);
			itWorked = true;
		} 
		else if(object instanceof Table)
		{
			Table newTable = (Table)object;
			String filePath = getFilePathForDB(newTable.getName());
			new File(filePath).getParentFile().mkdirs();
			serializer.serializeObject(newTable, filePath);
			itWorked = true;
		}
		return itWorked;
	}

	@Override
	public void deleteItem(Object object)
	{
		if(object instanceof String)
		{
			File file = new File(this.getFilePathForDB((String)object));
			File[] files = file.getParentFile().listFiles();
			for(int i = 0; i < files.length; i++)
			{
				try
				{
					java.lang.Runtime.getRuntime().exec("rm -f " + files[i].getAbsolutePath());
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				if(files[i].canWrite())
				{
					System.out.println("file can be written to.");
				}
				
				if(!files[i].delete())
				{	
					System.out.println(files[i].getName() + " Didn't delete");
				}
				*/
			}

			try
			{
				java.lang.Runtime.getRuntime().exec("rm -rf " + file.getParentFile().getAbsolutePath());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			if(!file.getParentFile().delete())
			{
				System.out.println("error deleting Database!");
			}
			*/
		}
	}

	@Override
	public boolean updateItem(Object object)
	{
		return createItem(ThreadLocalUser.getUser(), (Database) object);
	}

	@Override
	public Object retrieveItem(Object object)
	{
		Database database = null;
		if (object instanceof String)
		{
			String objectName = (String) object;
			// check the getFilePathForDB to make sure the chosen db is correct........
			database = (Database) serializer.deserializeObject(getFilePathForDB(objectName));
		}
		return database;
	}

	@Override
	public List<Database> retrieveAll()
	{
		File folder = new File(filesRoot + ThreadLocalUser.getUser().getUserID() + "/");
		File[] listOfFiles = folder.listFiles();
		List<Database> databases = new ArrayList<Database>();
		if(null != listOfFiles && listOfFiles.length > 0)
		{
			for(File file : listOfFiles)
			{
				databases.add((Database) serializer.deserializeObject(file.getAbsolutePath() + "/" + file.getName() + ".db"));
			}
		}
		return databases;
	}
	
	private String getFilePathForDB(String databaseName)
	{
		StringBuilder filePathBuilder = new StringBuilder(filesRoot);
		filePathBuilder.append(ThreadLocalUser.getUser().getUserID() + "/");
		filePathBuilder.append(databaseName + "/");
		File dbDir = new File(filePathBuilder.toString());
		if(!dbDir.exists()) dbDir.mkdirs();
		filePathBuilder.append(databaseName + ".db");
		
		return filePathBuilder.toString();
	}

}
