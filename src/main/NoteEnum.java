package main;

public enum NoteEnum {
	A(110),
	Bb(116.54),
	B(123.47),
	C(130.81),
	Db(138.59),
	D(146.83),
	Eb(155.56),
	E(164.81),
	F(174.61),
	Gb(185),
	G(196),
	Ab(207.65);
	
	private static int[] minorSteps = new int[]{2, 1, 2, 2, 1, 2};
	private static int[] majorSteps = new int[]{2, 2, 1, 2, 2, 2, 1};
	
	private double freq;
	
	NoteEnum(double freq) {
		this.freq = freq;
	}
	
	public double f() {
		return f(0);
	}
	
	public double f(int octave) {
		double factor = Math.pow(2, octave);
		return freq * factor;
	}
	
	public double addMinor(NoteEnum key, int num, int octave) {
		int position = positionInMinorKey(key);
		if (position == -1) return f(octave); //Not in key
		while (num >= 7) {
			octave++;
			num -= 7;
		}
		while (num <= -7) {
			octave--;
			num += 7;
		}
		position += 7; //To fix mod negative values
		NoteEnum noteEnum = getSpecifiedPositionInMinorKey(key, (position + num) % 7);		
		if (num < 0 && noteEnum.ordinal() > ordinal()) {
			octave--;
		} else if (num > 0 && noteEnum.ordinal() < ordinal()) {
			octave++;
		}
		
		return noteEnum.f(octave);
	}
	
	public double addMajor(NoteEnum key, int num, int octave) {
		int position = positionInMajorKey(key);
		if (position == -1) return f(octave); //Not in key
		while (num >= 7) {
			octave++;
			num -= 7;
		}
		while (num <= -7) {
			octave--;
			num += 7;
		}
		position += 7; //To fix mod negative values
		NoteEnum noteEnum = getSpecifiedPositionInMajorKey(key, (position + num) % 7);		
		if (num < 0 && noteEnum.ordinal() > ordinal()) {
			octave--;
		} else if (num > 0 && noteEnum.ordinal() < ordinal()) {
			octave++;
		}
		
		return noteEnum.f(octave);
	}
	
	private NoteEnum getSpecifiedPositionInMinorKey(NoteEnum key, int position) {
		int stepsFromBase = 0;
		for (int i = 0; i < position; i++) {
			stepsFromBase += minorSteps[i];
		}
		int reqOrdinal = key.ordinal() + stepsFromBase;
		reqOrdinal %= NoteEnum.values().length;
		return NoteEnum.values()[reqOrdinal];
	}
	
	private NoteEnum getSpecifiedPositionInMajorKey(NoteEnum key, int position) {
		int stepsFromBase = 0;
		for (int i = 0; i < position; i++) {
			stepsFromBase += majorSteps[i];
		}
		int reqOrdinal = key.ordinal() + stepsFromBase;
		reqOrdinal %= NoteEnum.values().length;
		return NoteEnum.values()[reqOrdinal];
	}
	
	private int positionInMinorKey(NoteEnum key) {
		int position = 0;
		int i = key.ordinal();
		if (i == ordinal()) return position;
		for (; i != key.ordinal() - minorSteps[minorSteps.length-1]; i += minorSteps[position]) {
			if (i == ordinal()) return position;
			position++;
		}
		return -1;
	}
	
	private int positionInMajorKey(NoteEnum key) {
		int position = 0;
		int i = key.ordinal();
		if (i == ordinal()) return position;
		for (; i != key.ordinal() - majorSteps[majorSteps.length-1]; i += majorSteps[position]) {
			if (i == ordinal()) return position;
			position++;
		}
		return -1;
	}
}


