import java.io.File;
import java.io.IOException;

/**
 * 
 */

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2018��1��3��
 * @time             ����2:06:26
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
	/**
	 * ��ȡ��Ŀtts·��
	 * @return
	 */
	public static String getPath()
	{
		try
		{
			rootPath = new File(".").getCanonicalPath().toString() + "\\";
		}
		catch(IOException e)
		{
			rootPath = System.getProperty("user.dir") + "\\";
		}
		return rootPath + "tts\\";
	}
	/**
	 * ��ȡ��Ŀ��·��
	 * @return
	 */
	public static String getRootPath()
	{
		try
		{
			rootPath = new File(".").getCanonicalPath().toString() + "\\";
		}
		catch(IOException e)
		{
			rootPath = System.getProperty("user.dir") + "\\";
		}
		return rootPath;
	}
	/**
	 * ��ȡ���������ն˿�
	 * @return
	 */
	public static String getAcceptPort()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("port" , "acceptPort");
	}
	/**
	 * ��ȡ���������Ͷ˿�
	 * @return
	 */
	public static String getSendPort()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("port" , "sendPort");
	}
	/**
	 * ��ȡ����������IP
	 * @return
	 */
	public static String getServerIP()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("ip" , "sendIP");
	}
	/**
	 * ��ȡ�ƴ�Ѷ����Ŀid
	 * @return
	 */
	public static String getKDXFid()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("id" , "kdxfID");
	}
	/**
	 * ��ȡ��Ƶ����ʱ��
	 * @return
	 */
	public static String getDELTime()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("time" , "delTime");
	}
	/**
	 * ��ȡ������
	 * @return
	 */
	public static String getVoiceName()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("voicename" , "voicename");
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public static String getSpeed()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("speed" , "speed");
	}
	/**
	 * ��ȡ���
	 * @return
	 */
	public static String getPitch()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("pitch" , "pitch");
	}
	/**
	 * ��ȡ����
	 * @return
	 */
	public static String getVolume()
	{
		readIniFile = new ReadIniFile("config");
		return readIniFile.getValue("volume" , "volume");
	}
	/**
	 * ��ȡ��λ�����
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
	 * ����
	 * @param args
	 */
	public static void main(String [] args)
	{
		System.out.println(getEncryptFileName());
	}
	
}
