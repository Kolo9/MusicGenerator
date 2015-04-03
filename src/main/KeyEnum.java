package main;

public enum KeyEnum {
	MAJOR(2, 2, 1, 2, 2, 2),
	MINOR(2, 1, 2, 2, 1, 2);	
	
	int[] steps;
	KeyEnum(int... steps) {
		this.steps = steps;
	}
}


