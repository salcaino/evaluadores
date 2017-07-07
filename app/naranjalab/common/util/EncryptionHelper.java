package naranjalab.common.util;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptionHelper {
	private final Logger logger = LoggerFactory.getLogger(EncryptionHelper.class);
	private Cipher aesCipher;
    //AES key size
    private static int AES_Key_Size = 256;
    //fixed data used in creating the AES key
    byte[] salt = {
        (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c,
        (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99,
        (byte) 0x41, (byte) 0x38, (byte) 0x18, (byte) 0x74,
        (byte) 0xbc, (byte) 0xc4, (byte) 0x55, (byte) 0x6b
    };
    private SecretKeySpec keySpec = null;
    private void makeFixedKey() {
    	try {
			aesCipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			logger.warn("Failed to create cipher instance", e);
		}
        keySpec = new SecretKeySpec(salt, "AES");
    }
	public EncryptionHelper() {
		makeFixedKey();
	}
	public String encrypt(String input)
            throws GeneralSecurityException {
        byte[] inputBytes = input.getBytes();
        aesCipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = aesCipher.doFinal(inputBytes);
        return byteArrayToHexString(encrypted);
    }
	private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }
	public String decrypt(String input)
            throws GeneralSecurityException {
        byte[] decryptedBytes = null;
        synchronized (aesCipher) {
            aesCipher.init(Cipher.DECRYPT_MODE, keySpec);
            decryptedBytes = aesCipher.doFinal(hexStringToByteArray(input));
        }
        return new String(decryptedBytes);
    }
	private static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }
}
