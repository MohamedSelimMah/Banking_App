import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil{
	
	private final SecretKey secretKey;

	public EncryptionUtil() throws Exception{
		this.secretKey= generateKey();
	}

	public SecretKey generateKey() throws Exception{
		KeyGenerator KeyG=  KeyGenerator.getInstance("AES");
		KeyG.init(128);
		return KeyG.generateKey();
	}
	public String encrypt(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] encryptedData = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedData);	
	}

	public String decrypt(String encryptedData)throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] decryptedData = Base64.getDecoder().decode(encryptedData);
		return new String(cipher.doFinal(decryptedData));
	}

	public String getSecretKey(){
		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
	}
}
