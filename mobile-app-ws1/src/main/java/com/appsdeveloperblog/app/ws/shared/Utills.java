package com.appsdeveloperblog.app.ws.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utills {
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private final int ITERATIONS = 1000;
	private final int KEY_LENGTH = 256;

	public String generatedUserId(int length) {
		return generatedRandomString(length);
	}

	public String generateAddressId(int length) {
		return generatedRandomString(length);
	}

	private String generatedRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}
}
