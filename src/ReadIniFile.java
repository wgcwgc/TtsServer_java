
import java.io.*;
import java.util.*;

public class ReadIniFile
{
	@SuppressWarnings("rawtypes")
	HashMap sections = new HashMap();
	String currentSecion;
	Properties current;
	
	@SuppressWarnings("unchecked")
	public ReadIniFile(String filename)
	{
		BufferedReader reader = null;
		try
		{
			if(filename.equals("wgc"))
			{
				filename = "./wgc.ini";
				try
				{
					new File(filename);
				}
				catch (Exception e)
				{
					String str = "\n ReadIniFile�ࣺ�����ļ������ڻ��߸�ʽ����ȷ0������\n";
					System.out.println(str);
					PrintLog.printLog(str);
					System.exit(0);
				}
				reader = new BufferedReader(new FileReader(filename));
			}
			else if (filename.equals("config"))
			{
				filename = "./config.ini";
				try
				{
					new File(filename);
				}
				catch (Exception e)
				{
					String str = "\n ReadIniFile�ࣺ�����ļ������ڻ��߸�ʽ����ȷ0������\n";
					System.out.println(str);
					PrintLog.printLog(str);
					System.exit(0);
				}
				reader = new BufferedReader(new FileReader(filename));
			}
			else
			{
				String str = "\n ReadIniFile�ࣺ�����ļ������ڻ��߸�ʽ����ȷ1������\n";
				PrintLog.printLog(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile�ࣺ�����ļ������ڻ��߸�ʽ����ȷ2������\n";
			PrintLog.printLog(str);
			str += e.getMessage();
			System.out.println(str);
			System.exit(0);
		}
		
		try
		{
			String line;
			try
			{
				while( ( line = reader.readLine() ) != null)
				{
					line = line.trim();
					if(line.matches("\\[.*\\]"))
					{
						currentSecion = line.replaceFirst("\\[(.*)\\]" , "$1");
						current = new Properties();
						sections.put(currentSecion , current);
					}
					else if(line.matches(".*=.*"))
					{
						if(current != null)
						{
							int i = line.indexOf('=');
							String name = line.substring(0 , i);
							String value = line.substring(i + 1);
							current.setProperty(name , value);
						}
					}
				}
			}
			catch(Exception e)
			{
				String str = "\n ReadIniFile�ࣺ�����ļ���ȡ�쳣������\n";
				str += e.getMessage();
				PrintLog.printLog(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile�ࣺ�����ļ����ݲ��Ϸ�������\n";
			str += e.getMessage();
			PrintLog.printLog(str);
			System.out.println(str);
			System.exit(0);
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch(Exception e)
			{
				String str = "\n ReadIniFile�ࣺ�����ļ��ر��쳣������\n";
				str += e.getMessage();
				PrintLog.printLog(str);
				System.out.println(str);
				System.exit(0);
			}
		}
	}
	
	public String getValue(String section , String name)
	{
		Properties p = (Properties) sections.get(section);
		if(p == null)
		{
			return null;
		}
		String value = p.getProperty(name);
		return value;
	}
}

