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
	
	public double add(KeyEnum key, NoteEnum base, int num, int octave) {
		int position = positionInKey(key, base);
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
		NoteEnum noteEnum = getSpecifiedPositionInKey(key, base, (position + num) % 7);		
		if (num < 0 && noteEnum.ordinal() > ordinal()) {
			octave--;
		} else if (num > 0 && noteEnum.ordinal() < ordinal()) {
			octave++;
		}
		
		return noteEnum.f(octave);
	}
	
	private int positionInKey(KeyEnum key, NoteEnum base) {
		int position = 0;
		int i = base.ordinal();
		if (i == ordinal()) return position;
		for (; i != base.ordinal() - key.steps[key.steps.length-1]; i += key.steps[position]) {
			if (i == ordinal()) return position;
			position++;
		}
		return -1;
	}
	
	private NoteEnum getSpecifiedPositionInKey(KeyEnum key, NoteEnum base, int position) {
		int stepsFromBase = 0;
		for (int i = 0; i < position; i++) {
			stepsFromBase += key.steps[i];
		}
		int reqOrdinal = base.ordinal() + stepsFromBase;
		reqOrdinal %= NoteEnum.values().length;
		return NoteEnum.values()[reqOrdinal];
	}
}


