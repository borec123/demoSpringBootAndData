package com.borec.backend.entity;

public enum ZpravaArchiveCreated {

	USER, THREAD;

	/*
	 * public static char getChar(ZpravaArchiveCreated created1) { switch (created1)
	 * { case USER: return 'U'; case THREAD: return 'T';
	 * 
	 * } return 0; }
	 */
	
	
	@Override
	public String toString() {
		switch (this) {
		/*
		 * case USER: return "U";
		 */
		case THREAD:
			return "T";
		default:
			return "U";
		}
	}
}
