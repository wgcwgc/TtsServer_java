/**
 * 
 */

/**
 * @author Administrator
 * @copyright wgcwgc
 * @date 2017年12月27日
 * @time 上午10:05:26
 * @project_name tts_kdxf_demo
 * @package_name
 * @file_name SingleFileHttpServers.java
 * @type_name SingleFileHttpServers
 * @enclosing_type
 * @tags
 * @todo
 * @others
 * 
 */

/**
 * 
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechUtility;

public class SetWords extends Thread
{
	private static Socket connection = null;
	private static ServerSocket server = null;
	private static String ttsPath;
	private byte [] content;
	private byte [] header;
	private String encoding;
	private String MIMEType;
	private int port;
	
	private SetWords(String data , String encoding , String MIMEType , int port)
			throws UnsupportedEncodingException
	{
		this(data.getBytes(encoding) , encoding , MIMEType , port);
	}
	
	private SetWords(byte [] data , String encoding , String MIMEType , int port)
			throws UnsupportedEncodingException
	{
		this.encoding = encoding;
		this.content = data;
		this.port = port;
		this.MIMEType = MIMEType;
	}
	
	/**
	 * 
	 * @param MIMEType mp3
	 * @param encoding utf-8
	 * @param port 8088
	 */
	private SetWords(String MIMEType , String encoding , int port)
			throws UnsupportedEncodingException
	{
		this.MIMEType = MIMEType;
		this.encoding = encoding;
		this.port = port;
	}
	
	public void run()
	{
		
		try
		{
			server = new ServerSocket(this.port);
//			System.out.println("Accepting connections on port "
//					+ server.getLocalPort());
			PrintLog.printLog("Accepting connections on port "
					+ server.getLocalPort());
			while(true)
			{
				connection = null;
				try
				{
					connection = server.accept();
					OutputStream out = new BufferedOutputStream(
							connection.getOutputStream());
					InputStream in = new BufferedInputStream(
							connection.getInputStream());
					StringBuffer request = new StringBuffer();
					
					ByteArrayOutputStream contentBytes = new ByteArrayOutputStream();
					
					while(true)
					{
						int c = in.read();
						if(c == '\r' || c == '\n' || c == - 1)
						{
							break;
						}
						contentBytes.write(c);
						request.append((char) c);
					}
					
					String str = contentBytes.toString();
//					System.out.println(str);
					PrintLog.printLog(str);
					if(judge(str , 0))
					{
						// get
						if(str.startsWith("GET /"))
						{
							// 请求合法
							if(str.contains("setWords?")
									&& str.contains("words=")
									&& str.contains("&sign="))
							{
								str = str.substring(str.indexOf("=") + 1 ,
										str.indexOf(" HTTP/"));
								String [] list = str.split("&");
								if(list.length != 2)
								{
									System.out.println("请求参数有误00");
									PrintLog.printLog("请求参数有误00");
									writeRespose(request , "请求参数有误" , out , 1);
									continue;
								}
								String string = list[0];
								String sign = list[1].substring(list[1]
										.indexOf("=") + 1);
//								System.out.println(sign);
//								System.out.print(string);
								PrintLog.printLog(string);
								if( ! judge(string , sign))
								{
									System.out.println("请求参数有误01");
									PrintLog.printLog("请求参数有误01");
									writeRespose(request , "请求参数有误" , out , 1);
									continue;
								}
								string = URLDecoder.decode(string , "utf-8");
//								System.out.println(string);
								PrintLog.printLog(string);
								if(judge(string , 1))
								{
//									System.out.println(string);
									PrintLog.printLog(string);
									String encryptFileName = getEncryptFileName();
//									System.out.println(encryptFileName);
									PrintLog.printLog(encryptFileName);
									if( ! new File(ttsPath + encryptFileName
											+ ".mp3").exists())
									{
										Text2SpeechMain.creat(string , ttsPath
												+ encryptFileName + ".pcm");
//								System.out.println("asdf" + encryptFileName);
										Boolean pcmExists = false;
										while(Text2SpeechMain.working)
										{
											if(new File(ttsPath + encryptFileName
													+ ".pcm").exists())
											{
												pcmExists = true;
												break;
											}
										}
										if( ! pcmExists)
										{
											continue;
										}
										try
										{
											Pcm2Wav.convertAudioFiles(ttsPath
													+ encryptFileName + ".pcm" ,
													ttsPath + encryptFileName
															+ ".wav");
											while(true)
											{
												if(new File(ttsPath
														+ encryptFileName
														+ ".wav").exists())
												{
													break;
												}
											}
											wav2mp3.execute(
													new File(ttsPath
															+ encryptFileName
															+ ".wav") , ttsPath
															+ encryptFileName
															+ ".mp3");
											while(true)
											{
												if(new File(ttsPath
														+ encryptFileName
														+ ".mp3").exists())
												{
													break;
												}
											}
											new File(ttsPath + encryptFileName
													+ ".pcm").delete();
											new File(ttsPath + encryptFileName
													+ ".wav").delete();
										}
										catch(Exception e)
										{
											System.out.println("音频文件生成异常");
											PrintLog.printLog("音频文件生成异常");
											System.out.println(e);
											PrintLog.printLog(e.toString());
											writeRespose(request ,
													"服务器异常，请稍候重试！" , out , 1);
											continue;
										}
									}
									writeRespose(request , encryptFileName ,
											out , 0);
									
								}
								else
								{
									System.out.println("请求中包含非法字符");
									PrintLog.printLog("请求中包含非法字符");
									writeRespose(request , "请求中包含非法字符" , out ,
											1);
								}
							}
							else
							{// 请求头不合法
								System.out.println("请求头不合法");
								PrintLog.printLog("请求头不合法");
								writeRespose(request , "请求头不合法" , out , 1);
							}
						}
						// post
						else
						{
							System.out.println("post");
						}
					}
					else
					{
						System.out.println("请求异常" + str);
						PrintLog.printLog("请求异常" + str);
						writeRespose(request , "请求异常" , out , 3);
					}
				}
				catch(IOException e)
				{
					System.out.println(e);
					PrintLog.printLog(e.toString());
					System.out.println("请求出错！");
					PrintLog.printLog("请求出错！");
				}
				finally
				{
					if(connection != null)
					{
						connection.close();
					}
				}
			}
			
		}
		catch(IOException e)
		{
			System.out.println(e.toString());
			System.err.println("Could not start server. Port Occupied");
			PrintLog.printLog("Could not start server. Port Occupied");
		}
		finally
		{
			if(server != null)
			{
				try
				{
					server.close();
				}
				catch(IOException e)
				{
					System.out.println(e);
					PrintLog.printLog(e.toString());
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @return
	 */
	private String getEncryptFileName()
	{
		String content = "";
		long currentTime = System.currentTimeMillis();
		content += currentTime;
		int random = (int) ( 10 * Math.random() );
		content += random;
		int num = 3;
		while(num -- != 0)
		{
			random = (int) ( 10 * Math.random() );
			content += random;
		}
		return content;
	}
	
	/**
	 * request 请求头
	 * string 音频名
	 * out 输出流对象
	 * 
	 * flag：
	 * 		0 正常音频文件
	 * 		1 不正常处理
	 * 		2 不正常
	 */
	private void writeRespose(StringBuffer request , String string ,
			OutputStream out , int flag)
	{
		JSONObject jsonObject = null;
		InputStream localRead = null;
		try
		{
			ByteArrayOutputStream localWrite = new ByteArrayOutputStream();
			if(0 == flag)
			{
//				localRead = new FileInputStream(rootPath + string + ".mp3");
//				int b;
//				while( ( b = localRead.read() ) != - 1)
//				{
//					localWrite.write(b);
//				}
//				this.content = localWrite.toByteArray();
//				MIMEType = "audio/mp3";
				jsonObject = new JSONObject();
				jsonObject.put("result" , 0);
				jsonObject.put("mesg" , "OK");
				jsonObject.put("url" ,
						"http://" + Util.getServerIP().toString() + ":"
								+ Integer.parseInt(Util.getSendPort())
								+ "/tts/" + string + ".mp3");
				string = jsonObject.toString();
				localWrite.write(string.getBytes(this.encoding));
				this.content = localWrite.toByteArray();
				MIMEType = "text/html";
			}
			else if(1 == flag)
			{
				jsonObject = new JSONObject();
				jsonObject.put("result" , - 1);
				jsonObject.put("mesg" , string);
				string = jsonObject.toString();
				localWrite.write(string.getBytes(this.encoding));
				this.content = localWrite.toByteArray();
				MIMEType = "text/html";
			}
			else if(2 == flag)
			{
				jsonObject = new JSONObject();
				jsonObject.put("result" , 0);
				jsonObject.put("mesg" , string);
				string = jsonObject.toString();
				localWrite.write(string.getBytes(this.encoding));
				this.content = localWrite.toByteArray();
				MIMEType = "text/html";
			}
			else if(3 == flag)
			{
				localWrite.write("null".getBytes(this.encoding));
				this.content = localWrite.toByteArray();
				MIMEType = "text/html";
			}
			// 如果检测到是HTTP/1.0及以后的协议，按照规范，需要发送一个MIME首部
			String requestContent = request.toString();
			String header = "HTTP/1.1 200 OK\r\n" + "Server: OneFile 1.0\r\n"
					+ "Content-length: " + ( this.content.length ) + "\r\n"
					+ "Content-type: " + MIMEType + "\r\n\r\n";
			if(3 == flag)
			{
				header = "HTTP/1.1 404\r\n" + "Server: OneFile 1.0\r\n"
						+ "Content-length: " + ( this.content.length ) + "\r\n"
						+ "Content-type: " + MIMEType + "\r\n\r\n";
			}
			this.header = header.getBytes(this.encoding);
			if(requestContent.indexOf("HTTP/") != - 1)
			{
				out.write(this.header);
			}
			
			out.write(this.content);
			out.flush();
		}
		catch(IOException e)
		{
			System.out.println(e);
			PrintLog.printLog(e.toString());
			e.printStackTrace();
		}
		catch(JSONException e)
		{
			System.out.println(e);
			PrintLog.printLog(e.toString());
			e.printStackTrace();
		}
		finally
		{
			if(localRead != null)
			{
				try
				{
					localRead.close();
				}
				catch(IOException e)
				{
					System.out.println("IO流异常");
					PrintLog.printLog("IO流异常");
					PrintLog.printLog(e.toString());
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param string
	 * @param sign
	 * @return 判断md5加密是否正确
	 */
	private boolean judge(String string , String sign)
	{
//		System.out.println(string);
//		System.out.println(sign);
//		System.out.println(MD5.md5(Util.SECRETKEY + string));
		// 判断加密是否正确
		if(sign.equals(MD5.md5(Util.SECRETKEY + string)))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * @param string
	 * @exception 判断字符是否合法
	 * @return
	 */
	private boolean judge(String string , int flag)
	{
		// 判断是否包含有特定字符 “%”和 “HTTP/”
		if(0 == flag)
		{
			if(string.contains("%") && string.contains(" HTTP/"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		// 判断是否有非法字符
		else if(1 == flag)
		{
			if(string.contains("\\") || string.contains("/")
					|| string.contains(":") || string.contains("*")
					|| string.contains("?") || string.contains("\"")
					|| string.contains("|") || string.contains("<")
					|| string.contains("<") || string.contains("？"))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param args 
	 * @throws IOException 
	 * http://localhost:8088/setWords?words=%E7%99%BE%E5%BA%A6&sign=A6FBE3AF49BB2488E536302ED5B9228E
	 */
	@SuppressWarnings("deprecation")
	public static void main(String [] args) throws IOException
	{
		try
		{
			PrintLog.printLog(InetAddress.getLocalHost().getHostName()
					.toString()
					+ "\t"
					+ InetAddress.getLocalHost().getHostAddress().toString());
		}
		catch(UnknownHostException e1)
		{
			PrintLog.printLog(e1.toString());
			System.out.println(e1.toString());
		}
		
		ttsPath = Util.getTtsPath();
		File ttspathFile = new File(ttsPath);
		if( ! ttspathFile.exists())
		{
			ttspathFile.getParentFile().mkdirs();
		}
//		System.out.println(ttsPath);
		PrintLog.printLog(ttsPath);
		SpeechUtility.createUtility(SpeechConstant.APPID + "="
				+ Util.getKDXFid());
		try
		{
			String contentType = "text/html";
			String encoding = "utf-8";
			int port = Integer.parseInt(Util.getAcceptPort());
//			System.out.println(port);
			Thread thread = new SetWords(contentType , encoding , port);
			thread.start();
			
			new RemoveMp3Files(Integer.parseInt(Util.getDELTime())).start();
			Scanner cinScanner = new Scanner(System.in);
			String cancel = cinScanner.next();
			if("q" == cancel || "q".equalsIgnoreCase(cancel))
			{
				if(cinScanner != null)
				{
					cinScanner.close();
				}
				if(connection != null)
				{
					connection.close();
				}
				if(server != null)
				{
					server.close();
				}
				thread.stop();
				PrintLog.printLog("exit");
				System.exit(0);
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			PrintLog.printLog("Usage:java SingleFileHTTPServer filename port encoding");
			System.out
					.println("Usage:java SingleFileHTTPServer filename port encoding");
		}
		catch(Exception e)
		{
			PrintLog.printLog(e.toString());
			System.err.println(e);
		}
	}
}
