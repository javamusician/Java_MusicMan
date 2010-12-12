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
	private static final long serialVersionUID = 1L;

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
			int cnt = targetDataLine.read(tempBuffer, 0, tempBuffer.length);
			if (cnt > 0) {
				byteArrayOutputStream.write(tempBuffer, 0, cnt);
				totaldatasize += cnt;
			}
		}
	}

	class PlayThread extends Thread {
		byte tempBuffer[] = new byte[10000];

		public void run() {
			try {
				int cnt;
				cnt = audioInputStream.read(tempBuffer, 0, tempBuffer.length);
				while (cnt != -1) {
					if (cnt > 0)
						sourceDataLine.write(tempBuffer, 0, cnt);
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
		final Button captureBtn = new Button("¼��");
		final Button stopBtn = new Button("ֹͣ");
		final Button playBtn = new Button("����");
		final Button saveBtn = new Button("����");
		captureBtn.setEnabled(true);
		stopBtn.setEnabled(false);
		playBtn.setEnabled(false);
		saveBtn.setEnabled(false);
		// ע��¼���¼�
		captureBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureBtn.setEnabled(false);
				stopBtn.setEnabled(true);
				playBtn.setEnabled(false);
				saveBtn.setEnabled(false);
				// ��ʼ¼��
				capture();
			}
		});
		add(captureBtn);

		// ע��ֹͣ�¼�
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureBtn.setEnabled(true);
				stopBtn.setEnabled(false);
				playBtn.setEnabled(true);
				saveBtn.setEnabled(true);
				// ֹͣ¼��
				stop();
			}
		});
		add(stopBtn);

		// ע�Ქ���¼�
		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����¼��
				play();
			}
		});
		add(playBtn);

		// ע�ᱣ���¼�
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����¼��
				save();
			}
		});
		add(saveBtn);

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

	private void capture() {
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
			e.printStackTrace();
			System.exit(0);
		}
	}

	private void play() {
		try {
			byte audioData[] = byteArrayOutputStream.toByteArray();
			translateToInputStream(audioData);

			sourceDataLine.start();
			Thread playThread = new Thread(new PlayThread());
			playThread.start();
		} catch (Exception e) {
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

	public void stop() {
		stopCapture = true;
	}

	public void save() {
		AudioFormat audioFormat = getAudioFormat();
		byte audioData[] = byteArrayOutputStream.toByteArray();
		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		initAudioInputStream(audioData, byteArrayInputStream, audioFormat);
		try {
			writeIntoFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeIntoFile() throws IOException {
		File file = new File("res/recordfile/test.wav");
		AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
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
}
