package yt.study.dameng;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class TestAnything {
	
	static final String createLowTable = "create table LOW.TABLE_LOW(COLUMN_1 int primary key)";
	static final String createLowTable_1 = "create table LOW.TABLE_LOW_1(COLUMN_1 int primary key)";//zm
	static final String dropLowTable = "drop table LOW.TABLE_LOW";
	static final String createHighTable = "create table HIGH.TABLE_HIGH(COLUMN_1 int primary key, COLUMN_2 int)";
	static final String dropHighTable = "drop table HIGH.TABLE_HIGH";
	static final String createForeignKey = "ALTER TABLE HIGH.TABLE_HIGH ADD constraint CONS_HIGH FOREIGN KEY(COLUMN_2)  REFERENCES LOW.TABLE_LOW(COLUMN_1)";
	static final String dropForeignKey = "ALTER TABLE HIGH.TABLE_HIGH DROP constraint CONS_HIGH";
	
	static final String insertForeignKey = "insert into HIGH.TABLE_HIGH(COLUMN_1) values(1)"; //插入一行数据表示存在外键
	static final String existForeignKey = "select * from HIGH.TABLE_HIGH where COLUMN_1 = 1";  //查看是否有外键存在
	
	static ConnectDB connectDB = new ConnectDB();
	static Connection highConn = connectDB.getConnect("HIGH", "123456789");
	static Connection lowConn = connectDB.getConnect("LOW", "123456789");
	
	@Test
	public void reStart() {
		String a = "\n";
		byte[] b = a.getBytes();
		System.out.println(b[0]);
		
		char[] temp = "00001010".toCharArray();
		int[] intOfChar = new int[temp.length];
		for(int i = 0; i < temp.length; i++) {
			intOfChar[i] = temp[i] - 48;
		}
		int sum = 0;
		for(int i = 0; i < intOfChar.length; i++) {
			sum += intOfChar[intOfChar.length-1-i]<<i;  //相当于是求二进制串的和
		}
		System.out.println((char)sum == '\n');
		
	}
	
	@Test
	public void test1() throws SQLException {
		boolean flag;
		//插入外键
		flag =connectDB.executeUpdate(createForeignKey, highConn, null);
		connectDB.executeQuery(insertForeignKey, highConn, null);  //插入外键的标示
//		System.out.println(flag);
		
//		//删除外键
//		flag =connectDB.executeUpdate(dropForeignKey, highConn, null);
//		System.out.println(flag1);
		
		//删除表
//		flag = connectDB.executeUpdate(dropLowTable, highConn, null);
		
		//外键是否存在
//		flag = connectDB.executeQuery(existForeignKey, highConn, null).next();
		connectDB.closeAll();
		System.out.println(flag);
	}
	
	@Test
	public void testBin() {
		
	}
	//zm
	public void test2() throws SQLException{
	  String testStr = "insert into LOW.TABLE_LOW(COLUMN_1,COLUMN_2) values(300,400)";
	  String testStr1 = "select * from LOW.TABLE_LOW";
	  String synInsertSql = "insert into HIGH.SYN(COLUMN_1) values(110)";
		boolean flag;
		//插入外键
		//flag =connectDB.executeUpdate(testStr, lowConn, null);
		ResultSet rs = connectDB.executeQuery(synInsertSql, lowConn, null);
		
		
	}
}
