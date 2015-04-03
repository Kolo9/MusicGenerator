package main;

public enum NoteLengthEnum {
	EIGTH(1.0/2), QUARTER(1.0), HALF(2.0), WHOLE(4.0);
	
	double frac;
	NoteLengthEnum(double frac) {
		this.frac = frac;
	}
}


