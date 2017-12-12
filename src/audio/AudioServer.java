package audio;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioServer extends Thread {

	public final static float AUDIO_SAMPLERATE = 44100.0F; // Sample Rate
	public final int INT_AUDIO_SAMPLERATE = 44100; // Sample Rate
	public final static int NUMBER_OF_BITS_IN_CHANNEL = 16; // Number of bits
	public final static int NUMBER_OF_CHANNELS = 2; // Number of channels
	public final int NUMBER_OF_BYTES_IN_FRAME = 4; // Number of bytes in
	public final float NUMBER_OF_FRAME_PER_SECOND = 44100.0F; // Number
	public final static int FRAME_SIZE = (int) AUDIO_SAMPLERATE * NUMBER_OF_CHANNELS * NUMBER_OF_BITS_IN_CHANNEL
			/ Byte.SIZE / 50 * 5;
	private static final boolean ENDIAN = false;
	public static int writeIndex; // write buffer index

	private DatagramSocket sendSocket;
	private DatagramPacket sendPacket;

	boolean stopaudioCapture = false;
	private AudioFormat adFormat;
	private TargetDataLine targetDataLine;
	private AudioInputStream audioInputStream;
	private byte buffer[] = new byte[FRAME_SIZE];
	
	
	private DatagramPacket sendPackets[];
	private Vector<DatagramPacket> packetVector;
	private int port;
	
	public AudioServer(InetAddress address, int port) {
		debug.Debug.log("AudioSender Create      Address : " + address.getHostName() + "   port : " + port);
		this.port = port;
		packetVector = new Vector<DatagramPacket>();
		adFormat = getAudioFormat();
		DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, adFormat);
		try {
			sendSocket = new DatagramSocket();
			sendPacket = new DatagramPacket(buffer, FRAME_SIZE, address, port);
			packetVector.add(sendPacket);
			DatagramPacket tempPackets[] = { sendPacket };
			sendPackets = tempPackets;
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
			sendPacket.setData(buffer, 0, buffer.length);
			while (true) {
				readSize = audioInputStream.read(buffer, 0, FRAME_SIZE);
				for(int i=0; i<sendPackets.length; i++) {
					sendPackets[i].setData(buffer, 0, buffer.length);
					sendSocket.send(sendPackets[i]);
					//debug.Debug.log("sendPakcets["+i+"] Send");
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			sendSocket.close();
			Thread.interrupted();
		}
	}
	
	public void remove() {
		sendSocket.close();
		this.interrupt();
	}
	
	public void addUser(InetAddress address) {
		DatagramPacket packet = new DatagramPacket(buffer, FRAME_SIZE, address, port);
		packetVector.add(packet);
		sendPackets = (DatagramPacket[])packetVector.toArray(sendPackets);
	}

	public void removeUser(InetAddress disAddress) {
		for(int i=0; i<packetVector.size(); i++) {
			if(packetVector.get(i).getAddress().equals(disAddress)) 
				packetVector.remove(i);
		}
		sendPackets = (DatagramPacket[])packetVector.toArray(sendPackets);
	}
	
}
