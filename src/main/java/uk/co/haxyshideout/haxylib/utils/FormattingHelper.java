package uk.co.haxyshideout.haxylib.utils;

/**
 * Created by clienthax on 5/6/2015.
 */
public class FormattingHelper {

	/**
	 * Converts the formatting oodes from {@literal &}x format to the internal format used by mc.
	 * @param readable the string to swap the control codes for
	 */
	public static String convertFormattingCodes(String readable) {//Thanks diesieben07 fir the regex help
		return readable.replaceAll("(?i)&([0-9A-FK-OR])", "§$1");
	}
}
