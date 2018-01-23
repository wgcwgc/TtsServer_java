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
	public Text2SpeechMain()
	{
		
	}
	
	/**
	 * 
	 */
	public static void creat(String contents , String path)
	{
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		mTts.setParameter(SpeechConstant.VOICE_NAME , "xiaoyan");// ���÷�����
		mTts.setParameter(SpeechConstant.SPEED , "50");// ��������
		mTts.setParameter(SpeechConstant.PITCH , "50");// �����������Χ0~100
		mTts.setParameter(SpeechConstant.VOLUME , "80");// ������������Χ0~100
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
