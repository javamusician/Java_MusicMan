package JavaMusician.MusicMan.RecordPlay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Button;
import java.awt.Frame;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class RecordPlay extends Frame {
	class CaptureThread extends Thread {
		// ��ʱ����
		byte tempBuffer[] = new byte[10000];
		public void run() {
			byteArrayOutputStream = new ByteArrayOutputStream();
			totaldatasize = 0;
			stopCapture = false;
			try {
				while (!stopCapture) {
					readFromInputStream();
				}
				byteArrayOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		private void readFromInputStream() {
			int streamLength = targetDataLine.read(tempBuffer, 0, tempBuffer.length);
			if (streamLength > 0) {
				byteArrayOutputStream.write(tempBuffer, 0, streamLength);
				totaldatasize += streamLength;
			}
		}
	}

	class PlayThread extends Thread {
		byte tempBuffer[] = new byte[10000];
		public void run() {
			try {
				int streamLength;
				streamLength = audioInputStream.read(tempBuffer, 0, tempBuffer.length);
				while (streamLength != -1) {
					if (streamLength > 0)
						sourceDataLine.write(tempBuffer, 0, streamLength);
				}
				clearTempData();
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		private void clearTempData() {
			sourceDataLine.drain();
			sourceDataLine.close();
		}
	}

	boolean stopCapture = false; // ����¼����־
	AudioFormat audioFormat; // ¼����ʽ
	// ��ȡ���ݣ���TargetDataLineд��ByteArrayOutputStream¼��
	ByteArrayOutputStream byteArrayOutputStream;
	int totaldatasize = 0;
	TargetDataLine targetDataLine;
	// �������ݣ���AudioInputStreamд��SourceDataLine����
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;

	public RecordPlay() {
		// ������ť
		final Button captureButton = new Button("¼��");
		final Button stopButton = new Button("ֹͣ");
		final Button playButton = new Button("����");
		final Button saveButton = new Button("����");
		captureButton.setEnabled(true);
		stopButton.setEnabled(false);
		playButton.setEnabled(false);
		saveButton.setEnabled(false);
		// ע��¼���¼�
		captureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureButton.setEnabled(false);
				stopButton.setEnabled(true);
				playButton.setEnabled(false);
				saveButton.setEnabled(false);
				// ��ʼ¼��
				captureRecord();
			}
		});
		add(captureButton);

		// ע��ֹͣ�¼�
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureButton.setEnabled(true);
				stopButton.setEnabled(false);
				playButton.setEnabled(true);
				saveButton.setEnabled(true);
				// ֹͣ¼��
				stopRecord();
			}
		});
		add(stopButton);

		// ע�Ქ���¼�
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����¼��
				playRecord();
			}
		});
		add(playButton);

		// ע�ᱣ���¼�
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����¼��
				saveRecord();
			}
		});
		add(saveButton);

		// ע�ᴰ��ر��¼�
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// ���ô�������
		setLayout(new FlowLayout());
		setTitle("¼��������");
		setSize(350, 70);
		setVisible(true);
	}

	private void captureRecord() {
		try {
			audioFormat = getAudioFormat();
			DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, audioFormat);
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			targetDataLine.open(audioFormat);
			targetDataLine.start();
			Thread captureThread = new Thread(new CaptureThread());
			captureThread.start();
		} catch (Exception e) {
			System.err.println("Capture error!");
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void playRecord() {
		try {
			byte audioData[] = byteArrayOutputStream.toByteArray();
			translateToInputStream(audioData);
			sourceDataLine.start();
			Thread playThread = new Thread(new PlayThread());
			playThread.start();
		} catch (Exception e) {
			System.err.println("Play error!");
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void translateToInputStream(byte[] audioData)
			throws LineUnavailableException {
		for (int i = 0; i < audioData.length / 5; i++)
			audioData[i] = audioData[i * 5];
		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		AudioFormat audioFormat = getAudioFormat();
		initAudioInputStream(audioData, byteArrayInputStream, audioFormat);
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
				audioFormat);
		sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
		sourceDataLine.open(audioFormat);
	}

	private void initAudioInputStream(byte[] audioData,
			InputStream byteArrayInputStream, AudioFormat audioFormat) {
		audioInputStream = new AudioInputStream(byteArrayInputStream,
				audioFormat, audioData.length / audioFormat.getFrameSize());
	}

	public void stopRecord() {
		stopCapture = true;
	}

	public void saveRecord() {
		AudioFormat audioFormat = getAudioFormat();
		byte audioData[] = byteArrayOutputStream.toByteArray();
//		showTheFigureOfSound(audioData);
		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		initAudioInputStream(audioData, byteArrayInputStream, audioFormat);
		try {
			writeIntoFile();
		} catch (Exception e) {
			System.err.println("Capture error!");
			e.printStackTrace();
		}
	}

	private void showTheFigureOfSound(byte[] audioData) {
		for(int i=0;i<audioData.length;i++){
			for(int j=-20;j<audioData[i];j++)
				System.out.print(" ");
			System.out.println(".");
		}
	}

	private void writeIntoFile() throws IOException {
		try{
		File file = new File("res/recordfile/test.wav");
		AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
		}catch (Exception e) {
			System.err.println("Can't open file!");
		}
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 16000.0F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

	public static void main(String args[]) {
		new RecordPlay();
	}

}
