package com.roller.medicine.info;

import com.android.common.domain.ResponseMessage;

public class PublicOnReturnInfo extends ResponseMessage {

	@Override
	public String toString() {
		return "PublicOnReturnInfo [statusCode=" + statusCode + ", code="
				+ code + ", message=" + message + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
