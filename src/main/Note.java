package main;

public class Note {

	double freq;
	NoteLengthEnum length;
	
	public Note(double freq, NoteLengthEnum length) {
		this.freq = freq;
		this.length = length;
	}
	
	public int getNumberSamples(double bpm, int sampleRate) {
		double bps = bpm / 60; //This many quarter notes per second in x/4 time sig
		return (int)(sampleRate / bps * length.frac) - getSpacing(bpm, sampleRate);						
	}
	
	public static int getSpacing(double bpm, int sampleRate) {
		double bps = bpm / 60; //This many quarter notes per second in x/4 time sig
		return (int)(sampleRate / bps * (1.0/128));
	}
	
	@Override
	public String toString() {
		return length.toString();
	}
}


