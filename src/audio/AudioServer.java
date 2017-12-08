package audio;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.SocketException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioServer extends Thread {

	public final static float AUDIO_SAMPLERATE = 44100.0F; // Sample Rate
	public final int INT_AUDIO_SAMPLERATE = 44100; // Sample Rate
	public final static int NUMBER_OF_BITS_IN_CHANNEL = 16; // Number of bits
	public final static int NUMBER_OF_CHANNELS = 2; // Number of channels
	public final int NUMBER_OF_BYTES_IN_FRAME = 4; // Number of bytes in
	public final float NUMBER_OF_FRAME_PER_SECOND = 44100.0F; // Number
	public final static int FRAME_SIZE = (int) AUDIO_SAMPLERATE * NUMBER_OF_CHANNELS * NUMBER_OF_BITS_IN_CHANNEL
			/ Byte.SIZE / 50 * 6 + 1600;
	private static final boolean ENDIAN = false;
	public static int writeIndex; // write buffer index

	private DatagramSocket sendSocket;
	private DatagramPacket sendPacket;
	InetAddress address = null;

	boolean stopaudioCapture = false;
	AudioFormat adFormat;
	TargetDataLine targetDataLine;
	AudioInputStream InputStream;
	SourceDataLine sourceLine;
	private AudioInputStream audioInputStream;
	private byte buffer[] = new byte[FRAME_SIZE];

	public AudioServer(InetAddress address, int port) {
		debug.Debug.log("AudioSender : Create");
		adFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, adFormat);
		try {
			sendSocket = new DatagramSocket();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
			audioInputStream = new AudioInputStream(this.targetDataLine);
			targetDataLine.open(adFormat);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private AudioFormat getAudioFormat() {
		return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, AUDIO_SAMPLERATE, NUMBER_OF_BITS_IN_CHANNEL,
				NUMBER_OF_CHANNELS, NUMBER_OF_BYTES_IN_FRAME, NUMBER_OF_FRAME_PER_SECOND, ENDIAN);
	}

	public void run() {
		try {
			int readSize;
			targetDataLine.start();
			while (true) {
				readSize = audioInputStream.read(buffer, 0, FRAME_SIZE);
				debug.Debug.log("Audio Write	Index : " + writeIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
