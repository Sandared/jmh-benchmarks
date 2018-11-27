package io.jatoms.jmh;

public class RTSJReceiver {
	private int value;

	public int receive(int value, int calldepth, int casepos) {
		switch (casepos) {
		case 1:
			return doSomething(value, calldepth, casepos);
		case 2:
			return doSomething(value, calldepth, casepos);
		case 3:
			return doSomething(value, calldepth, casepos);
		case 4:
			return doSomething(value, calldepth, casepos);
		case 5:
			return doSomething(value, calldepth, casepos);
		case 6:
			return doSomething(value, calldepth, casepos);
		case 7:
			return doSomething(value, calldepth, casepos);
		case 8:
			return doSomething(value, calldepth, casepos);
		case 9:
			return doSomething(value, calldepth, casepos);
		default:
			return doSomething(value, calldepth, casepos);
		}
	}

	private int doSomething(int value, int calldepth, int casepos) {
		if(calldepth == 0) {
			this.value = value;
			return this.value;
		}
		return receive(value, --calldepth, casepos);
	}

}
