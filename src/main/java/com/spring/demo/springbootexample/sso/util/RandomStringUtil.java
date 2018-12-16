
package com.petecat.interchan.sso.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomStringUtil {
	private static final char[] pattern = new char[] {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' ,
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ,
			};
	
	private static final char[] pattern1 = new char[] {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

	/**
	 *  随机生成一个字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) {
		if(length <= 0){
			length = 10;
		}
		StringBuilder result = new StringBuilder(length);
		int n = pattern.length;
		Random random =  ThreadLocalRandom.current();
		for (int i = 0; i < length; i++) {
			int rnd = random.nextInt(n);
			result.append(pattern[rnd]);
		}
		return result.toString();
	}
	
	

	/**
	 *  随机生成一个数字字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		if(length <= 0){
			length = 10;
		}
		StringBuilder result = new StringBuilder(length);
		int n = pattern1.length;
		Random random = ThreadLocalRandom.current();
		for (int i = 0; i < length; i++) {
			int rnd = random.nextInt(n);
			result.append(pattern1[rnd]);
		}
		return result.toString();
	}
}
