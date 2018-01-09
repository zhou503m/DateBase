package yt.study.dameng;

import java.sql.Connection;

public class High extends Thread {
	static ConnectDB connectDB = new ConnectDB();
	static Connection conn = connectDB.getConnect("HIGH", "123456789");
	static String result = "";
	static {
		String username = "HIGH";
		String password = "123456789";
		
		byte[] usernameByte = username.getBytes();
		byte[] passwordByte = password.getBytes();
		
		for(int i = 0; i < usernameByte.length; i++) {
			result += 0+Integer.toBinaryString(usernameByte[i]);
			//System.out.println(result);
		}
		result += "00001010"; //以\n为间断点
		
		for(int i = 0; i < passwordByte.length; i++) {
			result += "00"+Integer.toBinaryString(passwordByte[i]);
			//System.out.println(result+"一段");
		}
		result += "00001010"; //以\n为间断点
		
		System.out.println("high用户要传递的二进制串为："+result);
		System.out.println();
//		try {
//		if(conn.isClosed()) {
//			System.out.println("connect null");
//		}
//		}catch (Exception e){
//			e.printStackTrace();
//		}
	}
	

	
	

}
