import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Properties;

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
				catch(Exception e)
				{
					String str = "\n ReadIniFile类：配置文件不存在或者格式不正确0！！！\n";
					System.out.println(str);
					PrintLog.printLog(str);
					System.exit(0);
				}
				reader = new BufferedReader(new FileReader(filename));
			}
			else if(filename.equals("config"))
			{
				filename = "./config.ini";
				try
				{
					new File(filename);
				}
				catch(Exception e)
				{
					String str = "\n ReadIniFile类：配置文件不存在或者格式不正确0！！！\n";
					System.out.println(str);
					PrintLog.printLog(str);
					System.exit(0);
				}
				reader = new BufferedReader(new FileReader(filename));
			}
			else
			{
				String str = "\n ReadIniFile类：配置文件不存在或者格式不正确1！！！\n";
				PrintLog.printLog(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile类：配置文件不存在或者格式不正确2！！！\n";
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
//					System.out.println("0:" + line);
					if(line.matches("\\[.*\\]"))
					{
//						System.out.println("1:" + line);
						currentSecion = line.replaceFirst("\\[(.*)\\]" , "$1");
//						System.out.println("2:" + currentSecion);
						current = new Properties();
						sections.put(currentSecion , current);
					}
					else if(line.matches(".*=.*"))
					{
//						System.out.println("3:" + line);
						if(current != null)
						{
//							System.out.println("4:" + current);
							int i = line.indexOf('=');
							String name = line.substring(0 , i);
							String value = line.substring(i + 1);
//							System.out.println("5:" + name + ":" + value);
							current.setProperty(name , value);
						}
					}
				}
			}
			catch(Exception e)
			{
				String str = "\n ReadIniFile类：配置文件读取异常！！！\n";
				str += e.getMessage();
				PrintLog.printLog(str);
				System.out.println(str);
				System.exit(0);
			}
		}
		catch(Exception e)
		{
			String str = "\n ReadIniFile类：配置文件内容不合法！！！\n";
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
				String str = "\n ReadIniFile类：配置文件关闭异常！！！\n";
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
	
	public static void main(String [] args)
	{
		ReadIniFile readIniFile = new ReadIniFile("config");
		System.out.println(readIniFile.getValue("voicename" , "voicename"));
	}
}
