package com.coinhunter.utils.text;

import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

public class RandomStringMaker {

	public static String generate(char minCodePoint, char maxCodePoint, int length) {
		RandomStringGenerator randomStringGenerator =
			new RandomStringGenerator.Builder()
				.withinRange(minCodePoint, maxCodePoint)
				.filteredBy(CharacterPredicates.ASCII_LOWERCASE_LETTERS, CharacterPredicates.DIGITS)
				.build();
		return randomStringGenerator.generate(length);
	}

}
