import java.io.File;
import java.io.IOException;

/**
 * 
 */

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2018年1月3日
 * @time             下午2:06:26
 * @project_name     TtsServer
 * @package_name     
 * @file_name        Util.java
 * @type_name        Util
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class Util
{
	public static ReadIniFile readIniFile;
	public static String SECRETKEY = "8848@jzb";
	public static String rootPath = null;
	public static String ttsRootPath = null;
	public static String ttsPath = null;
	
	/**
	 * 获取项目tts路径
	 * @return
	 */
	public static String getTtsPath()
	{
		readIniFile = new ReadIniFile("config");
		ttsRootPath = readIniFile.getValue("path" , "ttsRootPath");
//		System.out.println("ttsRootPath:" + ttsRootPath + "*");
		if(ttsRootPath.trim().equals(""))
		{
			ttsRootPath = getRootPath() + "tts" + File.separatorChar;
			if(creatPath(ttsRootPath))
			{
				return ttsRootPath;
			}
			return null;
		}
		
		ttsPath = readIniFile.getValue("path" , "ttsPath");
//		System.out.println("ttsPath:" + ttsPath + "*");
		if(ttsPath.trim().equals(""))
		{
			if(creatPath(ttsRootPath))
			{
				return ttsRootPath;
			}
			return null;
		}
		else
		{
//			System.out.println(ttsRootPath + ttsPath);
			if(creatPath(ttsRootPath + ttsPath))
			{
				return ttsRootPath + ttsPath;
			}
			return null;
		}
	}
	
	/**
	 * 获取项目根路径
	 * @return
	 */
	public static String getRootPath()
	{
		try
		{
			rootPath = new File(".").getCanonicalPath().toString() + File.separatorChar;
		}
		catch(IOException e)
		{
			rootPath = System.getProperty("user.dir") + File.separatorChar;
		}
		
		if(creatPath(rootPath))
		{
			return rootPath;
		}
		return null;
	}
	
	private static Boolean creatPath(String path)
	{
		File file = new File(path);
//		System.out.println(path);
		if(!file.exists())
		{
			file.getParentFile().mkdirs();
		}
		return true;
	}
	
	/**
	 * 获取服务器接收端口
	 * @return
	 */
	public static String getAcceptPort()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("port" , "acceptPort");
	}
	/**
	 * 获取服务器发送端口
	 * @return
	 */
	public static String getSendPort()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("port" , "sendPort");
	}
	/**
	 * 获取服务器发送IP
	 * @return
	 */
	public static String getServerIP()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("ip" , "sendIP");
	}
	/**
	 * 获取科大讯飞项目id
	 * @return
	 */
	public static String getKDXFid()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("id" , "kdxfID");
	}
	/**
	 * 获取音频过期时间
	 * @return
	 */
	public static String getDELTime()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("time" , "delTime");
	}
	/**
	 * 获取发音人
	 * @return
	 */
	public static String getVoiceName()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("voicename" , "voicename");
	}
	/**
	 * 获取语速
	 * @return
	 */
	public static String getSpeed()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("speed" , "speed");
	}
	/**
	 * 获取语调
	 * @return
	 */
	public static String getPitch()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("pitch" , "pitch");
	}
	/**
	 * 获取音量
	 * @return
	 */
	public static String getVolume()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("volume" , "volume");
	}
	/**
	 * 获取四位随机码
	 * @return
	 */
	public static String getEncryptFileName()
	{
		String content = "";
		long currentTime = System.currentTimeMillis();
		content += currentTime;
		int random = (int) ( 10 * Math.random() );
		content += "#" + random;
		int num = 3;
		while(num -- != 0)
		{
			random = (int) ( 10 * Math.random() );
			content += random;
		}
		return content;
	}
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String [] args)
	{
//		System.out.println("ttsPath00:" + getTtsPath());
//		System.out.println("rootPath00:" + getRootPath());
	}
	
}
