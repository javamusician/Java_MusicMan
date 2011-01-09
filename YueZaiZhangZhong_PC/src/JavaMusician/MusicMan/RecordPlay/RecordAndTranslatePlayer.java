package JavaMusician.MusicMan.RecordPlay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

import JavaMusician.MusicMan.MIDIPlayer.MIDIPlayer;

import wh.XMLParser.XMLParser;

@SuppressWarnings("serial")
public class RecordAndTranslatePlayer extends Frame {
	private class CaptureThread extends Thread {
		byte tempBuffer[] = new byte[4096];
		public void run() {
			byteArrayOutputStream = new ByteArrayOutputStream();
			totaldatasize = 0;
			stopCapture = false;
			capture();
		}

		private void capture() {
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
			int streamLength = targetDataLine.read(tempBuffer, 0,
					tempBuffer.length);
			if (streamLength > 0) {
				byteArrayOutputStream.write(tempBuffer, 0, streamLength);
				totaldatasize += streamLength;
			}
		}
	}

	private class PlayThread extends Thread {
		byte tempBuffer[] = new byte[4096];

		public void run() {
			try {
				int streamLength;
				streamLength = audioInputStream.read(tempBuffer, 0,
						tempBuffer.length);
				while (streamLength != -1) {
					if (streamLength > 0)
						sourceDataLine.write(tempBuffer, 0, streamLength);
					streamLength = audioInputStream.read(tempBuffer, 0,
							tempBuffer.length);
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

	boolean stopCapture = false;
	AudioFormat audioFormat;
	ByteArrayOutputStream byteArrayOutputStream;
	int totaldatasize = 0;
	TargetDataLine targetDataLine;
	AudioInputStream audioInputStream;
	SourceDataLine sourceDataLine;

	final int LengthForOneTone = 4096;

	MIDIPlayer midPlayer;
	List<String> keyIdList, valueList;

	public static void main(String args[]) {
		new RecordAndTranslatePlayer();
	}

	public RecordAndTranslatePlayer() {
		final Button captureButton = new Button("Â¼Òô");
		final Button stopButton = new Button("Í£Ö¹");
		final Button playButton = new Button("²¥·Å");
		final Button saveButton = new Button("±£´æ");
		initButtom(captureButton, stopButton, playButton, saveButton);
		addListener(captureButton, stopButton, playButton, saveButton);
		setWindowLayout();
	}

	public void stopRecord() {
		stopCapture = true;
	}

	public void saveRecord() {
		AudioFormat audioFormat = getAudioFormat();
		byte audioData[] = byteArrayOutputStream.toByteArray();
		if (audioData.length == 0)
			return;
		sendOutMidiSound(audioData);
		InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
		initAudioInputStream(audioData, byteArrayInputStream, audioFormat);
		try {
			writeIntoFile();
		} catch (Exception e) {
			System.err.println("Capture error!");
			e.printStackTrace();
		}
	}

	public void captureRecord() {
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

	public void playRecord() {
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

	private void addListener(final Button captureButton,
			final Button stopButton, final Button playButton,
			final Button saveButton) {
		captureButtonAddActionListner(captureButton, stopButton, playButton,
				saveButton);
		stopButtonAddActionListner(captureButton, stopButton, playButton,
				saveButton);
		playButtonAddActionListner(playButton);
		saveButtonAddActionListner(saveButton);
		windosAddListner();
	}

	private void setWindowLayout() {
		setLayout(new FlowLayout());
		setTitle("Â¼Òô»ú³ÌÐò");
		setSize(350, 70);
		setVisible(true);
	}

	private void windosAddListner() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void saveButtonAddActionListner(final Button saveButton) {
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRecord();
			}
		});
		add(saveButton);
	}

	private void playButtonAddActionListner(final Button playButton) {
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playRecord();
			}
		});
		add(playButton);
	}

	private void stopButtonAddActionListner(final Button captureButton,
			final Button stopButton, final Button playButton,
			final Button saveButton) {
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureButton.setEnabled(false);
				stopButton.setEnabled(false);
				playButton.setEnabled(true);
				saveButton.setEnabled(true);
				stopRecord();
			}
		});
		add(stopButton);
	}

	private void captureButtonAddActionListner(final Button captureButton,
			final Button stopButton, final Button playButton,
			final Button saveButton) {
		captureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				captureButton.setEnabled(false);
				stopButton.setEnabled(true);
				playButton.setEnabled(false);
				saveButton.setEnabled(false);
				captureRecord();
			}
		});
		add(captureButton);
	}

	private void initButtom(final Button captureButton,
			final Button stopButton, final Button playButton,
			final Button saveButton) {
		captureButton.setEnabled(true);
		stopButton.setEnabled(false);
		playButton.setEnabled(false);
		saveButton.setEnabled(false);
	}

	private void translateToInputStream(byte[] audioData)
			throws LineUnavailableException {
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

	private void writeIntoFile() throws IOException {
		try {
			File file = new File("test.wav");
			AudioSystem
					.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
		} catch (Exception e) {
			System.err.println("Can't open file!");
		}
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 8192 * 16.0F;
		int sampleSizeInBits = 16;
		int channels = 1;
		boolean signed = true;
		boolean bigEndian = false;
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

	private void sendOutMidiSound(byte[] audioData) {
		int times = 0;
		int[] frequency = new int[4];

		int[] toneNum = new int[audioData.length];
		midPlayer = new MIDIPlayer();
		keyIdList = new ArrayList<String>();
		valueList = new ArrayList<String>();
		translate();
		while ((times + 1) * LengthForOneTone < audioData.length) {
			times = analyTheSound(audioData, times, frequency, toneNum);
		}
	}

	private int analyTheSound(byte[] audioData, int times, int[] frequency,
			int[] toneNum) {
		getTone(audioData, times, frequency);
		times++;
		if (times % 4 == 3)
			outputTone(times, frequency, toneNum);

		return times;
	}

	private void outputTone(int times, int[] frequency, int[] toneNum) {
		int tone = findThePlural(frequency);
		tone = optimizeTone(tone);
		System.out.println(tone);
		String tempString = "" + tone;
		MIDIPlayer.play(tempString);
		toneNum[times / 4] = tone;
	}

	private int optimizeTone(int tone) {

		if (tone > 45)
			return 0;
		if (tone < 10)
			return 0;
		tone += 53;
		return tone;
	}

	private void getTone(byte[] audioData, int times, int[] teml) {
		double[] fftResult;

		double[] wave = initWave(audioData, times);
		fftResult = FourierTransform.FFTDb(wave);

		double maxleft = 0;

		for (int i = 0; i < fftResult.length; i++)
			if (maxleft < fftResult[i]) {
				maxleft = fftResult[i];
				teml[times % 4] = i;
			}
	}

	private double[] initWave(byte[] audioData, int times) {
		double[] wave;
		wave = new double[LengthForOneTone / 2];
		int h = 0;
		for (int i = LengthForOneTone * times; i < LengthForOneTone
				* (times + 1); i += 2) {
			wave[h] = (double) audioData[i];
			h++;
		}
		return wave;
	}

	private int findThePlural(int[] teml) {
		int[] counter = new int[teml.length];
		findAllTheCount(teml, counter);
		int flag = findThePluralNum(teml, counter);

		return teml[flag];
	}

	private void findAllTheCount(int[] teml, int[] counter) {
		for (int i = 0; i < teml.length; i++) {
			for (int j = 0; j < teml.length; j++)
				if (teml[i] == teml[j])
					counter[j]++;
		}
	}

	private int findThePluralNum(int[] teml, int[] counter) {
		int max = counter[0];
		int flag = 0;
		for (int i = 1; i < teml.length; i++)
			if (max < counter[i]) {
				max = counter[i];
				flag = i;
			}
		return flag;
	}

	private void translate() {
		XMLParser.ParseXML("main.xml", keyIdList, valueList);
	}
}
