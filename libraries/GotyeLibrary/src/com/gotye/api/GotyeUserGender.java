package com.gotye.api;

/**
 * 性别
 *
 */
public enum GotyeUserGender {
	Femal(0), Male(1), NotSet(2),SEX_IGNORE(-1);
	private int _value;

	private GotyeUserGender(int value) {
		_value = value;
	}

	public int value() {
		return _value;
	}
}
