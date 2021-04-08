package hr.fer.oprpp1.hw05.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Klasa kriptira ili dekriptira podatke koristeći AES koje korisnik unese ili
 * provjerava SHA-256 digest.
 * 
 * @author vedran
 *
 */
public class Crypto {

	public static void main(String[] args) {
		switch (args.length) {
		case 2:
			checkSha(args[0], args[1]);
			break;
		case 3:
			encryptDecrypt(args[0], args[1], args[2]);
			break;
		default:
			throw new IllegalArgumentException("Expected 2 or 3 parameters!");
		}
	}

	/**
	 * Metoda provjerava jel uneseni SHA-256 digest jednak kao digest od predane
	 * datoteke
	 * 
	 * @param method naziv metode
	 * @param file   datoteka čiji se digest provjerava
	 */
	private static void checkSha(String method, String file) {
		if (!method.equalsIgnoreCase("checksha"))
			throw new IllegalArgumentException("Illegal method name! (Possible: checksha)");
		System.out.println("Please provide expected sha-256 digest for " + file + ":");
		Scanner sc = new Scanner(System.in);
		String digestFromInput = sc.nextLine();
		sc.close();

		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("./" + file)))) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] buff = new byte[4096];
			while (true) {
				int len = is.read(buff);
				if (len < 0)
					break;
				md.update(buff, 0, len);
			}
			byte[] byteArray = md.digest();
			String digestFromFile = Util.byteToHex(byteArray);

			if (digestFromFile.equals(digestFromInput)) {
				System.out.println("Digesting completed. Digest of " + file + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + file
						+ " does not match the expected digest. Digest was : " + digestFromFile);
			}
		} catch (Exception e) {
		}

	}

	/**
	 * Metoda kriptira ili dekriptira podatke iz prve datoteke u drugu
	 * 
	 * @param process    naziv procesa
	 * @param firstFile  prva datoteka
	 * @param secondFile druga datoteka
	 */
	private static void encryptDecrypt(String process, String firstFile, String secondFile) {
		if (!(process.equalsIgnoreCase("encrypt") || process.equalsIgnoreCase("decrypt")))
			throw new IllegalArgumentException("Illegal method name! (Possible: encrypt, decrypt)");

		boolean encrypt = process.equalsIgnoreCase("encrypt") ? true : false;
		String keyText = "";
		String ivText = "";
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		Scanner sc = new Scanner(System.in);
		keyText = sc.nextLine();
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		ivText = sc.nextLine();
		sc.close();

		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("./" + firstFile)));
			OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get("./" + secondFile)));

			byte[] buff = new byte[4096];
			while (true) {
				int len = is.read(buff);
				if (len < 0)
					break;
				byte[] tmp = cipher.update(buff, 0, len);
				os.write(tmp, 0, tmp.length);
			}
			byte[] byteArray = cipher.doFinal();
			os.write(byteArray, 0, byteArray.length);
			String s = (encrypt ? "Encryption " : "Decryption ");
			System.out.println(s + "completed. Generated file " + secondFile + " based on file " + firstFile);
			is.close();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

}
