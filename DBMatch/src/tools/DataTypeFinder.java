package tools;

import models.database.DataType;

public class DataTypeFinder
{	
	public DataTypeFinder()
	{
		
	}
	
	public static DataType findNumberDataType(long lowest, long highest)
	{
		DataType dataType = null;
		if(lowest < 0)
		{
			if(lowest >= -128)
			{
				lowest += 128;
				highest += 128;
			}
			else if(lowest >= -32768)
			{
				lowest += 32768;
				highest += 32768;
			}
			else if(lowest >= -8388608)
			{
				lowest += 8388608;
				highest += 8388608;
			}
			else if(lowest >= -2147483648L)
			{
				lowest += 2147483648L;
				highest += 2147483648L;
			}
		}
		
		if(highest <= 255)
		{
			dataType = DataType.TINYINT;
		}
		else if (highest <= 65535)
		{
			dataType = DataType.SMALLINT;
		}
		/*else if (highest <= 16777215)
		{
			dataType = DataType.MEDIUMINT;
		}
		*/
		else if (highest <= 4294967295L)
		{
			dataType = DataType.INT;
		}
		else
		{
			dataType = DataType.BIGINT;
		}
		
		return dataType;
	}
	
	public static DataType findStringDataType(String s)
	{
		return DataType.VARCHAR;
	}
}
