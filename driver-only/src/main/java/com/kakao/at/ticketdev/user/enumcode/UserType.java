package com.kakao.at.ticketdev.user.enumcode;

public enum UserType implements Root {
	Admin("어드민"),
	ProdMaster("상품마스터"),
	VenueMaster("현장마스터"),
	Developer("개발자");

	private final String value;

	UserType(String userTypeName) {
		this.value = userTypeName;
	}

	@Override
	public String getKey() {
		return name();
	}

	@Override
	public String getValue() {
		return value;
	}
}
