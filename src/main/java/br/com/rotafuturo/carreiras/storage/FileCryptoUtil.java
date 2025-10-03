package br.com.rotafuturo.carreiras.storage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
public class FileCryptoUtil {
	private static final String ALGO = "AES";
	private static final byte[] KEY = "rotafuturo123456".getBytes();
	public static byte[] encrypt(byte[] data) throws Exception {
		Key key = new SecretKeySpec(KEY, ALGO);
		Cipher cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}
	public static byte[] decrypt(byte[] encrypted) throws Exception {
		Key key = new SecretKeySpec(KEY, ALGO);
		Cipher cipher = Cipher.getInstance(ALGO);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(encrypted);
	}
	public static void encryptAndSave(Path filePath, byte[] data) throws Exception {
		byte[] encrypted = encrypt(data);
		Files.write(filePath, encrypted);
	}
	public static byte[] loadAndDecrypt(Path filePath) throws Exception {
		byte[] encrypted = Files.readAllBytes(filePath);
		return decrypt(encrypted);
	}
}
