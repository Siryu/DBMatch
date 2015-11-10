package tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import models.database.Database;
import models.database.Table;

public class Serializer
{
	public boolean serializeObject(Object obj, String Filepath)
	{
		boolean succeeded = false;
		if(obj instanceof Database)
		{
			Database db = (Database) obj;
			try
			{					
				FileOutputStream fout = new FileOutputStream(Filepath);
				OutputStream buffer = new BufferedOutputStream(fout);
				ObjectOutputStream oos = new ObjectOutputStream(buffer); 
				oos.writeObject(db);
				oos.close();
				succeeded = true;
			}
			catch(IOException ex)
			{
			   ex.printStackTrace();
			}
		} else if(obj instanceof Table){
			Table tab = (Table) obj;
			try
			{					
				FileOutputStream fout = new FileOutputStream(Filepath);
				OutputStream buffer = new BufferedOutputStream(fout);
				ObjectOutputStream oos = new ObjectOutputStream(buffer); 
				oos.writeObject(tab);
				oos.close();
				succeeded = true;
			}
			catch(IOException ex)
			{
			   ex.printStackTrace();
			}
		}
		return succeeded;
	}
	
	public Object deserializeObject(String filePath)
	{
		Object returnObject = null;
		try
		{
			FileInputStream fis = new FileInputStream(filePath);
			InputStream buffer = new BufferedInputStream(fis);
			ObjectInput objectInput = new ObjectInputStream(buffer);
			returnObject = objectInput.readObject();
		}
		catch (IOException | ClassNotFoundException ex)
		{
			returnObject = null;
		}
		return returnObject;
	}
}
