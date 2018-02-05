/**
 * 
 */

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

/**
 * @author           Administrator
 * @copyright        wgcwgc
 * @date             2017��9��30��
 * @time             ����11:00:55
 * @project_name     kdxf
 * @package_name     kdxf_tts
 * @file_name        Text2Speech.java
 * @type_name        Text2Speech
 * @enclosing_type   
 * @tags             
 * @todo             
 * @others           
 *
 */

public class Text2SpeechMain
{
	
	public static boolean working;
	
	public Text2SpeechMain()
	{
		
	}
	
	/**
	 * 
	 */
	public static void creat(String contents , String path)
	{
		working = true;
//		System.out.println("contents:" + contents + "\npath:" + path);
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		mTts.setParameter(SpeechConstant.VOICE_NAME , Util.getVoiceName());// ���÷�����
		mTts.setParameter(SpeechConstant.SPEED , Util.getSpeed());// ��������
		mTts.setParameter(SpeechConstant.PITCH , Util.getPitch());// �����������Χ0~100
		mTts.setParameter(SpeechConstant.VOLUME , Util.getVolume());// ������������Χ0~100
		mTts.synthesizeToUri(contents , path , synthesizeToUriListener);
//		System.out.println(contents + " " + path );
	}
	
	// �ϳɼ�����
	private static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progressΪ�ϳɽ���0~100
		public void onBufferProgress(int progress)
		{
//			System.out.println(progress);
		}
		
		// �Ự�ϳ���ɻص��ӿ�
		// uriΪ�ϳɱ����ַ��errorΪ������Ϣ��Ϊnullʱ��ʾ�ϳɻỰ�ɹ�
		public void onSynthesizeCompleted(String uri , SpeechError error)
		{
			working = false;
			if(error != null)
			{
				System.out.println(error);
			}
			else
			{
				System.out.println("Success! saved as : " + uri.substring(0 , uri.lastIndexOf(".pcm")) + ".mp3");
			}
//			System.out.println(error);
//			System.out.println(uri);
		}
		
		@Override
		public void onEvent(int arg0 , int arg1 , int arg2 , int arg3 ,
				Object arg4 , Object arg5)
		{
			
		}
	};
	
}
