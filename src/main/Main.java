package main;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;

public class Main {
	
	private static String name = "";
	
	private static NoteEnum base = NoteEnum.B;
	private static KeyEnum key = KeyEnum.MAJOR;
	private static int BPM = 140;
	
	//private static Random RNG = new Random(System.currentTimeMillis());
	private static int[][] majorPositionProbabilityGrid = new int[][]{
		{2, 4, 6, 7, 0, 2, 4, 6, 7},
		{},
		{0, 4, 6, 7, 0, 2, 4, 6, 7},
		{},
		{0, 2, 6, 7, 0, 2, 4, 6, 7},
		{},
		{0, 2, 4, 7, 0, 2, 4, 6, 7},
		{0, 2, 4, 6, 0, 2, 4, 6, 7}
	};
	private static int[][] minorPositionProbabilityGrid = new int[][]{
		{1, 2, 3, 4, 5, 6, 7},
		{0, 2, 3, 4, 5, 6, 7},
		{0, 1, 3, 4, 5, 6, 7},
		{0, 1, 2, 4, 5, 6, 7},
		{0, 1, 2, 3, 5, 6, 7},
		{0, 1, 2, 3, 4, 6, 7},
		{0, 1, 2, 3, 4, 5, 7},
		{0, 1, 2, 3, 4, 5, 6},
	};
	
	
	private static int[][] positionProbabilityGrid = (key == KeyEnum.MAJOR) ? majorPositionProbabilityGrid : minorPositionProbabilityGrid;
	private static int[] lengthProbabilityGrid = new int[]{0, 1, 2, 0, 1, 0, 1, 0, 1};

	public static void main(String[] args) {
		//Random nameRNG = new Random(Long.parseLong(md5(name), 16));
		//System.out.println(Long.parseLong(md5(name), 16));
		//System.out.println(nameRNG.nextInt());
		//BigInteger bi = new BigInteger(md5(name), 16);
		//System.out.println(bi.);
		name = name.toUpperCase();
		String md5 = md5(name);
		String firstHalf = md5.substring(0, 14); //Positions
		String secondHalf = md5.substring(14, 28); //Lengths
		int rngKey = (int) (Long.parseLong(md5.substring(28, 30), 16) % KeyEnum.values().length);
		int rngBase = (int) (Long.parseLong(md5.substring(30), 16) % NoteEnum.values().length); //273 values with 2 hex digits, divisible by 13, so one repeat at end (not too much of a problem)
		key = KeyEnum.values()[rngKey];
		base = NoteEnum.values()[rngBase];
		Random RNGP = new Random(Long.parseLong(firstHalf, 16));
		Random RNGL = new Random(Long.parseLong(secondHalf, 16));
		List<Note> notes = new ArrayList<Note>(10);
		/*Twinkle
		notes.add(new Note(NoteEnum.C.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.C.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.G.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.G.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.A.f(1), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.A.f(1), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.G.f(), NoteLengthEnum.HALF));
		notes.add(new Note(NoteEnum.F.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.F.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.E.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.E.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.D.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.D.f(), NoteLengthEnum.QUARTER));
		notes.add(new Note(NoteEnum.C.f(), NoteLengthEnum.HALF));
		*/
		notes.add(new Note(base.add(key, base, 2, 1), NoteLengthEnum.EIGTH));
		notes.add(new Note(base.add(key, base, 1, 1), NoteLengthEnum.EIGTH));
		notes.add(new Note(base.f(1), NoteLengthEnum.HALF));
		
		/*
		for (int i = 1; i <= 21; i++) {
			notes.add(new Note(base.addMajor(base, i, 1), NoteLengthEnum.QUARTER));
		}
		*/
		
		double beatsElapsed = 2.0;
		int lastRandom = 0;
		while (beatsElapsed < 7) {
			System.out.println(beatsElapsed);
			NoteLengthEnum length = NoteLengthEnum.values()[lengthProbabilityGrid[RNGL.nextInt(lengthProbabilityGrid.length)]];
			notes.add(new Note(base.add(key, base, lastRandom = positionProbabilityGrid[lastRandom][RNGP.nextInt(positionProbabilityGrid[lastRandom].length)], 1), length));
			beatsElapsed += length.frac; 
		}
		while (beatsElapsed < 8) {
			System.out.println(beatsElapsed);
			notes.add(new Note(base.add(key, base, lastRandom = positionProbabilityGrid[lastRandom][RNGP.nextInt(positionProbabilityGrid[lastRandom].length)], 1), NoteLengthEnum.EIGTH));
			beatsElapsed += NoteLengthEnum.EIGTH.frac;
		}
		while (beatsElapsed < 11) {
			System.out.println(beatsElapsed);
			NoteLengthEnum length = NoteLengthEnum.values()[lengthProbabilityGrid[RNGL.nextInt(lengthProbabilityGrid.length)]];
			notes.add(new Note(base.add(key, base, lastRandom = positionProbabilityGrid[lastRandom][RNGP.nextInt(positionProbabilityGrid[lastRandom].length)], 1), length));
			beatsElapsed += length.frac; 
		}
		while (beatsElapsed < 12) {
			System.out.println(beatsElapsed);
			notes.add(new Note(base.add(key, base, lastRandom = positionProbabilityGrid[lastRandom][RNGP.nextInt(positionProbabilityGrid[lastRandom].length)], 1), NoteLengthEnum.EIGTH));
			beatsElapsed += NoteLengthEnum.EIGTH.frac;
		}
		notes.add(new Note(base.f(1), NoteLengthEnum.WHOLE));
		for (Note n: notes) {
			System.out.println(n);
		}
		try {
			new SoundPlayer(BPM, notes);
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String md5(final String md5) {
        try {
            final java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            final byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (final java.security.NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

}


