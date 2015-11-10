package dataStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import server.ThreadLocalUser;
import tools.Serializer;
import models.User;
import models.database.Database;
import models.database.Table;

@Stateless
@LocalBean
public class TemplateFileStorage implements FileStorageConnection
{
	private static final String filesRoot = "/var/lib/tomcat7/data/templates/";

//	private static final String filesRoot = "C:\\Users\\Corey Massey\\workspace\\EE\\dbmatch\\templates\\";
//	private static final String filesRoot = "C:\\Users\\Joshua Ellington\\GD\\Homework\\Persistent Web App Lab\\DBMatch\\dbmatch\\templates\\";
//	private static final String filesRoot = "C:\\Users\\Kelsey St Clair\\workspace\\dbmatch\\templates";
//	private static final String filesRoot = "C:\\Users\\Sean Palmer\\class-use\\templates";

	@Inject private Serializer serializer;

	@Override
	public boolean createItem(User user, Object object)
	{
		if(object instanceof Table && user.isAdmin())
		{
			Table newTemplate = (Table) object;
			String filePath = getFilePathForTemplate(newTemplate.getName());
			serializer.serializeObject(newTemplate, filePath);
			return true;
		}
		return false;
	}

	@Override
	public void deleteItem(Object object)
	{
		if(object instanceof String)
		{
			File file = new File(this.getFilePathForTemplate((String)object));
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
				System.out.println("error deleting template!");
			}
			*/
		}
	}

	@Override
	public boolean updateItem(Object object)
	{
		return createItem(ThreadLocalUser.getUser(), (Table) object);
	}

	@Override
	public Object retrieveItem(Object object)
	{
		Table template = null;
		if (object instanceof String)
		{
			String objectName = (String) object;
			template = (Table) serializer.deserializeObject(getFilePathForTemplate(objectName));
		}
		return template;
	}

	@Override
	public List<Table> retrieveAll()
	{
		File folder = new File(filesRoot);
		File[] listOfFiles = folder.listFiles();
		List<Table> tables = new ArrayList<Table>();
		if(null != listOfFiles && listOfFiles.length > 0)
		{
			for(File file : listOfFiles)
			{
				tables.add((Table) serializer.deserializeObject(file.getAbsolutePath() + "/" + file.getName() + ".db"));
			}
		}
		return tables;
	}
	
	private String getFilePathForTemplate(String databaseName)
	{
		StringBuilder filePathBuilder = new StringBuilder(filesRoot);
		filePathBuilder.append(databaseName + "/");
		File dbDir = new File(filePathBuilder.toString());
		if(!dbDir.exists()) dbDir.mkdirs();
		filePathBuilder.append(databaseName + ".db");
		dbDir = null;
		return filePathBuilder.toString();
	}
}
