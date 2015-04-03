package main;

import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class MainOld {
	
	public static int HZ = 110;

	public static void main(String[] args) {
		AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, true);
		SourceDataLine speakers;
		Random RNG = new Random();
		try {
			byte[] data = new byte[(int)format.getSampleRate()*5];
			int i;
			/*
			RNG.nextBytes(data);
			for (i = 1; i < 8000; i++) {
				data[i] = (byte) (data[i-1] + 8);
				//data[i] = (byte) (data[i-1] + (RNG.nextInt(30) - 15));
			}
			for (; i < 8000 * 2; i++) {
				data[i] = (byte) (data[i-1] - 8);
			}
			for (; i < 8000 * 3; i++) {
				data[i] = (byte) (data[i-1] - 7);
			}
			for (; i < 8000 * 5; i++) {
				data[i] = (byte) (data[i-1] - 8);
			}
			for (; i < data.length; i++) {
				data[i] = (byte) (data[i-1] - 6);
			}
			*/
			for (i = 0; i < data.length; i++) {
				data[i] = (byte)(Math.sin(Math.PI*(HZ/format.getSampleRate()) * i) * 127);
				if (i == 1000) System.out.println(i + ": " + data[i]);
			}
			

			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
			speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			speakers.open(format);
			speakers.start();
			speakers.write(data, 0, data.length);
			speakers.drain();
			speakers.close();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} 

	}

}


