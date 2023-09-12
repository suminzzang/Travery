package com.ssafy.travery.util;

import java.util.Random;

public class SaltMaker {
	public static String getSalt() {
	    String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Random random = new Random();
	    StringBuilder saltBuilder = new StringBuilder();
	    for (int i = 0; i < 20; i++) {
	        int index = random.nextInt(characters.length());
	        saltBuilder.append(characters.charAt(index));
	    }
	    return saltBuilder.toString();
	}
}
