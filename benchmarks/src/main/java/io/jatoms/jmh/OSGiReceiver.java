package io.jatoms.jmh;

public class OSGiReceiver {

	private int value;

	public int receive(int value, int calldepth) {
		if(calldepth == 0) {
			this.value = value;
			return this.value;
		}

		return receive(value, --calldepth);
	}

}
