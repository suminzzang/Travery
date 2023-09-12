package com.ssafy.travery.util;

import java.util.Random;

public class KeyMaker {
	public static String getKey() {
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Random random = new Random();
	    StringBuilder keyBuilder = new StringBuilder();
	    for (int i = 0; i < 6; i++) {
	        int index = random.nextInt(characters.length());
	        keyBuilder.append(characters.charAt(index));
	    }
	    return keyBuilder.toString();
	}
}
