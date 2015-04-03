package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;

public class Main {
	
	private static NoteEnum key = NoteEnum.G;
	private static Random RNG = new Random();

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
		notes.add(new Note(key.f(1), NoteLengthEnum.HALF));
		for (int i = 1; i <= 21; i++) {
			notes.add(new Note(key.addMinor(key, RNG.nextInt(7), 1), NoteLengthEnum.values()[RNG.nextInt(2)]));
			//notes.add(new Note(key.addMinor(key, i, 0), NoteLengthEnum.QUARTER));
		}
		notes.add(new Note(key.f(1), NoteLengthEnum.WHOLE));
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


