package com.airfrance.ums.utility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants {
	public static final String LOG_MESSAGE_CANNOT = "CAN'T ";
	public static final String ALLOWED_COUNTRY = "ALLOWED_COUNTRY";
	public static final String DEFAULT_COUNTRY = "FR|FRANCE";
	public static final Set<String> DEFAULT_COUNTRIES = new HashSet();
	static {
		DEFAULT_COUNTRIES.addAll(Arrays.asList("FR","FRANCE"));
	}
}
