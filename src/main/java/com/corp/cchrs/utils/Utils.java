package com.corp.cchrs.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Utils {
	private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//used in AssetHistory
	public static LocalDate dateConventer(String date) {
		return LocalDate.parse(date, dateTimeFormatter);
	}
	
	public static String dateConventer(LocalDate date) {
		return date.format(dateTimeFormatter);
	}
	
	public static String generateSerialNumber() {
		Random r = new Random();
		String sn = "";

		for (int i = 0; i < 4; i++) sn += (char)(r.nextInt(26) + 'A');
		sn += r.nextInt(10);
		for (int i = 0; i < 2; i++) sn += (char)(r.nextInt(26) + 'A');
		for (int i = 0; i < 15; i++) sn += r.nextInt(10);
		
		return sn;
	}
	
	public static String generateSerialNumberWithDashes() {
		Random r = new Random();
		String sn = "";

		for (int i = 0; i < 4; i++) sn += (char)(r.nextInt(26) + 'A');
		sn += "-";
		sn += r.nextInt(10);
		sn += "-";
		for (int i = 0; i < 2; i++) sn += (char)(r.nextInt(26) + 'A');
		sn += "-";
		for (int i = 0; i < 15; i++) sn += r.nextInt(10);
		
		return sn;
	}

	/**
	 * Values have lowercase letters and all underlines are replaced with space character.
	 * @param obj array. You must use values() on your enum class.
	 * @return sequential list of Strings
	 */
	public static ArrayList<String> getFormattedNames(Enum<?> obj[]) {
		ArrayList<String> res = new ArrayList<>();
		for(Enum<?> type : new ArrayList<Enum<?>>(Arrays.asList(obj)))
			res.add(type.name().replace('_', ' ').toLowerCase());
		return res;
	}
	
	public static String getOriginalName(String formattedEnumName) {
			return formattedEnumName.replace(' ', '_').toUpperCase();
	}
	
	public static ArrayList<String> getFormattedNames(ArrayList<String> listToFormat) {
		ArrayList<String> res = new ArrayList<>();
		for(String type : listToFormat)
			res.add(type.replace('_', ' ').toLowerCase());
		return res;
	}
}
