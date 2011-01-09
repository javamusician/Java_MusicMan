package JavaMusician.MusicMan.UI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import JavaMusician.MusicMan.gui.StartUp.StartUp;

public class RecordPlayUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * ����
	 */
	JLabel mBackground=new JLabel(new ImageIcon("res/drawable/ui/interface_main.png"));
	
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
	final JButton captureButton = new JButton("¼��");
	final JButton stopButton = new JButton("ֹͣ");
	final JButton playButton = new JButton("����");
	final JButton saveButton = new JButton("����");
	final JButton backButton = new JButton("����");

	public RecordPlayUI() {
		super();
		
		setSize(800, 600+30);
		setResizable(false);
		setLayout(null);
		setTitle("RecordPlay");

		setVisible(true);
		
		Container contentPane = getContentPane();
		
		captureButton.setBounds(201, 280, 100, 30);
		stopButton.setBounds(301, 280, 100, 30);
		playButton.setBounds(401, 280, 100, 30);
		saveButton.setBounds(501, 280, 100, 30);
		backButton.setBounds(350, 400, 100, 30);
		contentPane.add(captureButton);
		contentPane.add(stopButton);
		contentPane.add(playButton);
		contentPane.add(saveButton);
		contentPane.add(backButton);
		captureButton.setEnabled(true);
		stopButton.setEnabled(false);
		playButton.setEnabled(false);
		saveButton.setEnabled(false);
		
		mBackground.setBounds(0, 0, 800, 600);
		contentPane.add(mBackground);
		
		backButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				StartUp.mFrame.setVisible(true);
			}
		});
		
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

		// ע�Ქ���¼�
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����¼��
				playRecord();
			}
		});
		
		// ע�ᱣ���¼�
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ����¼��
				saveRecord();
			}
		});

		// ע�ᴰ��ر��¼�
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
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
		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		initAudioInputStream(audioData, byteArrayInputStream, audioFormat);
		try {
			writeIntoFile();
		} catch (Exception e) {
			System.err.println("Capture error!");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
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
}
