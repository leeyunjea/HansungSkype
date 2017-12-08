package audio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioReceiver extends Thread {

	public static final int BUFFER_SIZE = 2;
	public static final float AUDIO_SAMPLERATE = 44100.0F; // Sample Rate
	public static final int INT_AUDIO_SAMPLERATE = 44100; // Sample Rate
	public static final int NUMBER_OF_BITS_IN_CHANNEL = 16; // Number of bits
	public static final int NUMBER_OF_CHANNELS = 2; // Number of channels
	public static final int NUMBER_OF_BYTES_IN_FRAME = 4; // Number of bytes in
	public static final float NUMBER_OF_FRAME_PER_SECOND = 44100.0F; // Number
	public static final int FRAME_SIZE = (int) AUDIO_SAMPLERATE * NUMBER_OF_CHANNELS * NUMBER_OF_BITS_IN_CHANNEL
			/ Byte.SIZE / 50 * 5;
	private static final boolean ENDIAN = false;

	ByteArrayOutputStream byteOutputStream;
	AudioFormat adFormat;
	TargetDataLine targetDataLine;
	AudioInputStream InputStream;
	SourceDataLine sourceLine;
	DatagramSocket receiverSocket = null;
	
	private int port;

	boolean action = true;

	public AudioReceiver(int port) {
		this.port = port;
		debug.Debug.log("AudioReceiver Create    Port : " + port);
		try {
			receiverSocket = new DatagramSocket(port);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		adFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, adFormat);
		try {
			sourceLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceLine.open(adFormat);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sourceLine.start();
	}

	private AudioFormat getAudioFormat() {
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, AUDIO_SAMPLERATE, NUMBER_OF_BITS_IN_CHANNEL,
				NUMBER_OF_CHANNELS, NUMBER_OF_BYTES_IN_FRAME, NUMBER_OF_FRAME_PER_SECOND, ENDIAN);
	}

	public void run() {
		debug.Debug.log("AudioReceiver Start   Port : " + port);
		try {

			byte[] receiveData = new byte[FRAME_SIZE];
			while (action) {
				// beforeTime = System.currentTimeMillis();
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				receiverSocket.receive(receivePacket);
				System.out.println(
						"RECEIVED: " + receivePacket.getAddress().getHostAddress() + " " + receivePacket.getPort());
				try {
					byte audioData[] = receivePacket.getData();
					java.io.InputStream byteInputStream = new ByteArrayInputStream(audioData);
					InputStream = new AudioInputStream(byteInputStream, adFormat,
							audioData.length / adFormat.getFrameSize());
					Thread playThread = new Thread(new PlayThread());
					playThread.start();
				} catch (Exception e) {
					System.out.println(e);
					return;
				}
			}
			System.out.println("action = false");
		} catch (Exception e) {
			receiverSocket.close();
			return;
		}
	}

	public void threadStop() {
		action = false;
		receiverSocket.close();
		sourceLine.close();
		System.out.println("Socket Close");
		try {
			InputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	class PlayThread extends Thread {

		byte tempBuffer[] = new byte[FRAME_SIZE];

		public void run() {
			try {
				int cnt;
				while ((cnt = InputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
					sourceLine.write(tempBuffer, 0, cnt);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}