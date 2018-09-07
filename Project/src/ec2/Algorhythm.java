package ec2;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Algorhythm {

	public static String getMD5(String input1) {

		// パスワードとパスワード(確認)を暗号化
		  String source1 = input1;
		  Charset charset = StandardCharsets.UTF_8;
		  String algorithm = "MD5";
		  byte[] bytes1 = null;
		try {
			bytes1 = MessageDigest.getInstance(algorithm).digest(source1.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		  String resultA= DatatypeConverter.printHexBinary(bytes1);

		  System.out.println(resultA);

		return resultA; //戻り値(resultA)は、UserDao.jspのfindByUserSignupInfo()メソッドに行く。

	//*パスワードはwatanabe(全て)
	}

}
