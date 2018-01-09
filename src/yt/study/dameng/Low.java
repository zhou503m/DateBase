package yt.study.dameng;

import java.sql.Connection;

public class Low extends Thread {
	static ConnectDB connectDB = new ConnectDB();
	static Connection conn = connectDB.getConnect("LOW", "123456789");
	
	static String result = "";
	static int endCount = 0;
	
	public static char bin2Char(String str) {
		char[] temp = str.toCharArray();
		int[] intOfChar = new int[temp.length];
		for(int i = 0; i < temp.length; i++) {
			intOfChar[i] = temp[i] - 48;
		}
		int sum = 0;
		for(int i = 0; i < intOfChar.length; i++) {
			sum += intOfChar[intOfChar.length-1-i]<<i;  //相当于是求二进制串的和
		}
		//System.out.println((char)sum);
		return (char)sum;
	}
	
	public static void binString2String(String str) {
		String username = "";
		String password = "";
		int count = 0;
		
		while(count<2) {
			String charOfSubString = str.substring(0, 8);
			if(charOfSubString.equals("00001010")) {
				str = str.substring(8);
				count++;
			} else {
				char a = bin2Char(charOfSubString);
				System.out.println(charOfSubString+"转码之后就是："+a);
				if(count == 0) {
					username += a;
				} else {
					password += a;
				}
				str = str.substring(8);
			}
			
		}
		
		System.out.println();
		System.out.println("最后结果：");
		System.out.println("HIGH用户的账户是："+username);
		System.out.println("HIGH用户的密码是："+password);
	}
}
