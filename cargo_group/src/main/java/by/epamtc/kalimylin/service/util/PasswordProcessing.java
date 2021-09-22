/*
 * Copyright (c) 2016, Taylor Hornby
 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:

 * 1. Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE. 
 */
package by.epamtc.kalimylin.service.util;

import static by.epamtc.kalimylin.service.util.MessageService.*;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import by.epamtc.kalimylin.service.exception.CannotPerformOperationException;
import by.epamtc.kalimylin.service.exception.InvalidHashException;

/**
 * This class contains peer-reviewed library for password storage. 
 * Passwords are "hashed" with PBKDF2 (64,000 iterations of SHA1 by default) 
 * using a cryptographically-random salt.
 * Each implementation provides several constants that can be changed. 
 * Only change these if you know what you are doing, and have help from 
 * an expert:
 * <p>
 * PBKDF2_HASH_ALGORITHM: The hash function PBKDF2 uses. By default, it is SHA1 
 * for compatibility across implementations, but you may change it to SHA256 
 * if you don't care about compatibility. Although SHA1 has been 
 * cryptographically broken as a collision-resistant function, it is still 
 * perfectly safe for password storage with PBKDF2
 * <p>
 * PBKDF2_ITERATIONS: The number of PBKDF2 iterations. By default, it is 32,000. 
 * To provide greater protection of passwords, at the expense of needing more 
 * processing power to validate passwords, increase the number of iterations. 
 * The number of iterations should not be decreased.
 * <p>
 * PBKDF2_SALT_BYTES: The number of bytes of salt. By default, 24 bytes, which 
 * is 192 bits. This is more than enough. This constant should not be changed.
 * <p>
 * PBKDF2_HASH_BYTES: The number of PBKDF2 output bytes. By default, 18 bytes, 
 * which is 144 bits. While it may seem useful to increase the number of output 
 * bytes, doing so can actually give an advantage to the attacker, as it 
 * introduces unnecessary (avoidable) slowness to the PBKDF2 computation. 
 * 144 bits was chosen because it is (1) Less than SHA1's 160-bit output 
 * (to avoid unnecessary PBKDF2 overhead), and (2) A multiple of 6 bits, 
 * so that the base64 encoding is optimal.
 * <p>
 * Note that these constants are encoded into the hash string when it is created 
 * with CreateHash so that they can be changed without breaking existing hashes.
 * The new (changed) values will apply only to newly-created hashes.
 * <p>
 * The hash format is five fields separated by the colon (':') character.
 * algorithm:iterations:hashSize:salt:hash
 * <p>
 * The hash length in bytes is included to prevent an accident where the hash 
 * gets truncated. For instance, if the hash were stored in a database column 
 * that wasn't big enough, and the database was configured to truncate it, 
 * the result when the hash gets read back would be an easy-to-break hash, 
 * since the PBKDF2 output is right at the end. Therefore, the length of the 
 * hash should not be determined solely from the length of the last field; 
 * it must be compared against the stored length.
 */
public class PasswordProcessing {

	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

	// These constants may be changed without breaking existing hashes.
	private static final int SALT_BYTE_SIZE = 24;
	private static final int HASH_BYTE_SIZE = 18;
	private static final int PBKDF2_ITERATIONS = 64000;

	// These constants define the encoding and may not be changed.
	private static final int HASH_SECTIONS = 5;
	private static final int HASH_ALGORITHM_INDEX = 0;
	private static final int ITERATION_INDEX = 1;
	private static final int HASH_SIZE_INDEX = 2;
	private static final int SALT_INDEX = 3;
	private static final int PBKDF2_INDEX = 4;

	public static String createHash(String password) 
			throws CannotPerformOperationException {
		return createHash(password.toCharArray());
	}
	
	public static boolean verifyPassword(String password, String correctHash)
			throws CannotPerformOperationException, InvalidHashException {
		return verifyPassword(password.toCharArray(), correctHash);
	}

	private static String createHash(char[] password) 
			throws CannotPerformOperationException {
		// Generate a random salt
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);

		// Hash the password
		byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		int hashSize = hash.length;

		// format: algorithm:iterations:hashSize:salt:hash
		String parts = "sha1:" + PBKDF2_ITERATIONS 
						+ ":" + hashSize + ":" + toBase64(salt) 
						+ ":" + toBase64(hash);
		return parts;
	}

	private static boolean verifyPassword(char[] password, String correctHash)
			throws CannotPerformOperationException, InvalidHashException {
		// Decode the hash into its parameters
		String[] params = correctHash.split(":");
		if (params.length != HASH_SECTIONS) {
			throw new InvalidHashException(MISSING_FIELDS);
		}

		// Currently, Java only supports SHA1.
		if (!params[HASH_ALGORITHM_INDEX].equals("sha1")) {
			throw new CannotPerformOperationException(UNSUPPORTED_HASH_TYPE);
		}

		int iterations = 0;
		
		try {
			iterations = Integer.parseInt(params[ITERATION_INDEX]);
		} catch (NumberFormatException ex) {
			throw new InvalidHashException(PARSE_ITERATION_FAILED);
		}

		if (iterations < 1) {
			throw new InvalidHashException(ITERATION_FAILED);
		}

		byte[] salt = null;
		
		try {
			salt = fromBase64(params[SALT_INDEX]);
		} catch (IllegalArgumentException ex) {
			throw new InvalidHashException(SALT_FAILED);
		}

		byte[] hash = null;
		
		try {
			hash = fromBase64(params[PBKDF2_INDEX]);
		} catch (IllegalArgumentException ex) {
			throw new InvalidHashException(DECODING_FAILED);
		}

		int storedHashSize = 0;
		
		try {
			storedHashSize = Integer.parseInt(params[HASH_SIZE_INDEX]);
		} catch (NumberFormatException ex) {
			throw new InvalidHashException(PARSE_HASH_FAILED);
		}

		if (storedHashSize != hash.length) {
			throw new InvalidHashException(INVALID_HASH_LENGTH);
		}

		// Compute the hash of the provided password, using the same salt,
		// iteration count, and hash length
		byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
		// Compare the hashes in constant time. The password is correct if
		// both hashes match.
		return slowEquals(hash, testHash);
	}

	private static boolean slowEquals(byte[] a, byte[] b) {
		
		int diff = a.length ^ b.length;
		
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		
		return diff == 0;
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, 
			int bytes) throws CannotPerformOperationException {
		
		try {
			PBEKeySpec spec = 
					new PBEKeySpec(password, salt, iterations, bytes * 8);
			SecretKeyFactory skf = 
					SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
			return skf.generateSecret(spec).getEncoded();
			
		} catch (NoSuchAlgorithmException ex) {
			throw new CannotPerformOperationException(INVALID_HASH_ALGORITHM);
		} catch (InvalidKeySpecException ex) {
			throw new CannotPerformOperationException(INVALID_KEY);
		}
	}

	private static byte[] fromBase64(String hex) 
			throws IllegalArgumentException {
		return Base64.getDecoder().decode(hex);
	}

	private static String toBase64(byte[] array) {
		return Base64.getEncoder().encodeToString(array);
	}
}
