package common.util;


import com.ndktools.javamd5.Mademd5;

public class Test {
	public static void main(String[] args) {
		Mademd5 md5 = new Mademd5();
		String s = md5.toMd5("123456");
		System.out.println(s);
	}
}
