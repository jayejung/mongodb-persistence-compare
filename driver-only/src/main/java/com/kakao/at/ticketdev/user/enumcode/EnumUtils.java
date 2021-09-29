package com.kakao.at.ticketdev.user.enumcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumUtils {
	public static List<EnumValue> toValues(Class<? extends Root> e) {
		return Arrays.stream(e.getEnumConstants())
				.map(EnumValue::new)
				.collect(Collectors.toList());
	}
}
