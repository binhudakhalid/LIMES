package org.aksw.limes.core.ml.algorithm.wombat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class CheckType {
	public static String check(String input) {
		// Strip Leading and Trailing Spaces
		String trimInput = input.trim();

		// Check if its a date. eg 2015-05-20T08
		if (checkDate(trimInput) == "date") {
			//System.out.println("*Res* :" + "date");
			return "date";
		}

		// Check if its a point. eg (24,55)
		if (checkPoint(trimInput) == "point") {
			//System.out.println("*Res* :" + "point");
			return "point";
		}
		

		// Check if its a number. eg 33 or 33.4
		if (checkNumber(trimInput) == "int" || checkNumber(trimInput) == "double") {
			//System.out.println("*Res* :" + "number");
			return "number";
		}
		

		return "string";

	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static String checkNumber(String i) {
		 
		if (i.matches("-?\\d+")) {
			return "int";
		}
		if (isNumeric(i)) {
			return "double";
		}

		return i;

		// TODO Auto-generated method stub

	}

	static String checkPoint(String i) {

		// remove all the spaces
		String i1 = i.replaceAll("\\s+", "");

		String firstCharater = i1.substring(0, 1);
		String lastCharater = i1.substring(i1.length() - 1);

		// Remove first and last string
		i1 = i1.substring(1, i1.length() - 1);

		// Break the string with comma
		if (i1.contains(",")) {
			List<String> list = Arrays.asList(i1.split(","));

			// str.matches("^[+-]?\\d+$") for + sign infront of number
			if (list.get(0).matches("-?\\d+") && list.get(1).matches("-?\\d+")) {
				//System.out.println("000000000");
				return "point";
			}

		}
		return "NotAPoint";
	}

	public static String checkDate(String string) {

		String[] patterns = { "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", "yyyy-MM-dd'T'HH:mm:ss.SSS", "yyyy-MM-dd'T'HH:mm:ssXXX",
				"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HH:mmXXX", "yyyy-MM-dd'T'HH:mm", "yyyy-MM-dd", "yyyy-MM" };

		for (String pattern : patterns) {
			try {
				new SimpleDateFormat(pattern).parse(string);
				// System.out.println("Correct date");

				return "date";
			} catch (ParseException e) {
				// System.out.println("Incorrect date");
				// return "notADate";
			}
		}
		return "notADate";

	}
 



}
