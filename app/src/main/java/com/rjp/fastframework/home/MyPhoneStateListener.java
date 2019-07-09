package com.rjp.fastframework.home;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyPhoneStateListener extends PhoneStateListener {
	private String phoneNumber; // 来电号码
	private static final String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/bqt_callRecords";
	private MediaRecorder mediaRecorder;
	private AudioRecord audioRecord;
	private boolean flag = true;

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);
		Log.i("bqt", "state=" + state + "   Number=" + incomingNumber);
		switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://响铃状态，拿到来电号码
				phoneNumber = incomingNumber;//只有这里能拿到来电号码，在CALL_STATE_OFFHOOK状态是拿不到来电号码的
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK://通话状态，开始录音
				record();
//				audioRecord();
				break;
			case TelephonyManager.CALL_STATE_IDLE://空闲状态，释放资源
				release();
//				audioRelease();
				break;
		}
	}

	private void audioRelease() {
		flag = false;
		audioRecord.stop();
		audioRecord.release();
	}

	private void audioRecord(){
		int sampleRateInHz = 8000;// 44100, 22050 and 11025
		int channelConfig = AudioFormat.CHANNEL_IN_MONO;
		int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

		//int bufferSize =11025 +
		int bufferSize = AudioRecord.getMinBufferSize(sampleRateInHz,channelConfig, audioFormat);


		short[] buffer = new short[bufferSize];

		audioRecord = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, sampleRateInHz,channelConfig, audioFormat, bufferSize);

		if(audioRecord.getState()== AudioRecord.STATE_INITIALIZED){
			audioRecord.startRecording();
			Log.e("recording", "before");

			while (flag) {
				int bufferReadResult = audioRecord.read(buffer, 0, bufferSize);
				System.out.println(String.format("读取到了数据%s", bufferReadResult));
				System.out.println(buffer);
			}
		}
	}
	
	private void record() {
		if (mediaRecorder == null) mediaRecorder = new MediaRecorder();
		if (phoneNumber == null) phoneNumber = "null_";
		File directory = new File(FILE_PATH);
		if (!directory.exists()) directory.mkdir();
		String data = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault()).format(new Date());
		File file = new File(FILE_PATH + File.separator + phoneNumber + data);
		//if (!file.exists()) file.createNewFile();
		
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);//指定录音机的声音源
		//MIC只获取自己说话的声音；VOICE_CALL双方的声音都可以录取，但是由于外国法律的限制，某些大牌手机不支持此参数
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);//设置录制文件的输出格式，如AMR-NB，MPEG-4等
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频的编码，如AAC，AMR-NB等
		mediaRecorder.setOutputFile(file.getAbsolutePath());//存储路径
		try {
			mediaRecorder.prepare();//准备，一定要放在设置后、开始前，否则会产生异常
		} catch (IOException e) {
			e.printStackTrace();
		}
		mediaRecorder.start();
		Log.i("bqt", "开始录音！");
	}
	
	private void release() {
		if (mediaRecorder != null) {
			mediaRecorder.stop();
			//mediaRecorder.reset(); //重设
			mediaRecorder.release();
			mediaRecorder = null;
		}

		Log.i("bqt", "结束录音！");
	}
}