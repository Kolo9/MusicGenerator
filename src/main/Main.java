package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;

public class Main {
	
	private static NoteEnum base = NoteEnum.Eb;
	private static KeyEnum key = KeyEnum.MAJOR;
	private static Random RNG = new Random(System.currentTimeMillis());
	private static int[] positionProbabilityGrid = new int[]{0, 2, 4, 6, 7};
	private static int[] lengthProbabilityGrid = new int[]{0, 1, 2, 0, 1, 0, 1, 0, 1};

	public static void main(String[] args) {
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
		notes.add(new Note(base.f(1), NoteLengthEnum.HALF));
		
		/*
		for (int i = 1; i <= 21; i++) {
			notes.add(new Note(base.addMajor(base, i, 1), NoteLengthEnum.QUARTER));
		}
		*/
		
		double beatsElapsed = 2.0;
		while (beatsElapsed < 7) {
			NoteLengthEnum length = NoteLengthEnum.values()[lengthProbabilityGrid[RNG.nextInt(lengthProbabilityGrid.length)]];
			notes.add(new Note(base.add(key, base, positionProbabilityGrid[RNG.nextInt(positionProbabilityGrid.length)], 1), length));
			beatsElapsed += length.frac; 
		}
		while (beatsElapsed < 8) {
			notes.add(new Note(base.add(key, base, positionProbabilityGrid[RNG.nextInt(positionProbabilityGrid.length)], 1), NoteLengthEnum.EIGTH));
			beatsElapsed += NoteLengthEnum.EIGTH.frac;
		}
		while (beatsElapsed < 11) {
			NoteLengthEnum length = NoteLengthEnum.values()[lengthProbabilityGrid[RNG.nextInt(lengthProbabilityGrid.length)]];
			notes.add(new Note(base.add(key, base, positionProbabilityGrid[RNG.nextInt(positionProbabilityGrid.length)], 1), length));
			beatsElapsed += length.frac; 
		}
		while (beatsElapsed < 12) {
			notes.add(new Note(base.add(key, base, positionProbabilityGrid[RNG.nextInt(positionProbabilityGrid.length)], 1), NoteLengthEnum.EIGTH));
			beatsElapsed += NoteLengthEnum.EIGTH.frac;
		}
		notes.add(new Note(base.f(1), NoteLengthEnum.WHOLE));
		try {
			new SoundPlayer(140, notes);
			System.exit(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


