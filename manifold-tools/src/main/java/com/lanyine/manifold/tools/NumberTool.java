package com.lanyine.manifold.tools;

import java.util.Calendar;

/**
 * 
 * @author shadow
 *
 */
public class NumberTool {

	public static synchronized String getNextNumber() {
		return Calendar.getInstance().getTimeInMillis() + "";
	}

}
