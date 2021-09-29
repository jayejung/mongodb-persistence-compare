package com.kakao.at.ticketdev.user.enumcode;

public class EnumValue {
	private final String key;
	private final String value;

	public EnumValue(Root rootEnum) {
		key = rootEnum.getKey();
		value = rootEnum.getValue();
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
