package uk.co.haxyshideout.haxylib.utils;

import net.minecraft.util.EnumChatFormatting;

import java.util.regex.Pattern;

/**
 * Created by clienthax on 5/6/2015.
 */
public class FormattingHelper {

	/**
	 * Converts the formatting oodes from {@literal &}x format to the internal format used by mc.
	 * @param readable the string to swap the control codes for
	 */
	public static String convertFormattingCodes(String readable) {
		readable = readable.replaceAll(Pattern.quote("&l"), EnumChatFormatting.BOLD+"");
		readable = readable.replaceAll(Pattern.quote("&n"), EnumChatFormatting.UNDERLINE+"");
		readable = readable.replaceAll(Pattern.quote("&o"), EnumChatFormatting.ITALIC+"");
		readable = readable.replaceAll(Pattern.quote("&m"), EnumChatFormatting.STRIKETHROUGH+"");
		readable = readable.replaceAll(Pattern.quote("&r"), EnumChatFormatting.RESET+"");

		readable = readable.replaceAll(Pattern.quote("&0"), EnumChatFormatting.BLACK+"");
		readable = readable.replaceAll(Pattern.quote("&1"), EnumChatFormatting.DARK_BLUE+"");
		readable = readable.replaceAll(Pattern.quote("&2"), EnumChatFormatting.DARK_GREEN+"");
		readable = readable.replaceAll(Pattern.quote("&3"), EnumChatFormatting.DARK_AQUA+"");
		readable = readable.replaceAll(Pattern.quote("&4"), EnumChatFormatting.DARK_RED+"");
		readable = readable.replaceAll(Pattern.quote("&5"), EnumChatFormatting.DARK_PURPLE+"");
		readable = readable.replaceAll(Pattern.quote("&6"), EnumChatFormatting.GOLD+"");
		readable = readable.replaceAll(Pattern.quote("&7"), EnumChatFormatting.GRAY+"");
		readable = readable.replaceAll(Pattern.quote("&8"), EnumChatFormatting.DARK_GRAY+"");
		readable = readable.replaceAll(Pattern.quote("&9"), EnumChatFormatting.BLUE+"");
		readable = readable.replaceAll(Pattern.quote("&a"), EnumChatFormatting.GREEN+"");
		readable = readable.replaceAll(Pattern.quote("&b"), EnumChatFormatting.AQUA+"");
		readable = readable.replaceAll(Pattern.quote("&c"), EnumChatFormatting.RED+"");
		readable = readable.replaceAll(Pattern.quote("&d"), EnumChatFormatting.LIGHT_PURPLE+"");
		readable = readable.replaceAll(Pattern.quote("&e"), EnumChatFormatting.YELLOW+"");
		readable = readable.replaceAll(Pattern.quote("&f"), EnumChatFormatting.WHITE+"");


		return readable;
	}
}
