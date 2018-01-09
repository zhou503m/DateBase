package yt.study.dameng;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {
	static final String synInsertSql = "insert into HIGH.SYN(COLUMN_1) values(110)";
	public static String testStr = "select * from TABLE_LOW;";
	public static String test2 = "insert into LOW.TABLE_LOW(COLUMN_1,COLUMN_2) values(20,10)";
	static final String dropLowTable = "drop table LOW.TABLE_LOW";
	public static void main(String[] args) throws SQLException {
	
	Low low = new Low() {
		
		public void run() {
//			try {
//			Statement statement = conn.createStatement();
//			statement.execute(synInsertSql);
//			}catch (SQLException e) {
//				e.printStackTrace();
//			}
			
		   //boolean fl  =  connectDB.executeUpdate(test2, conn, null);
		//System.out.println(fl);
//			try	{
//			System.out.println(result.getRow());
//			}catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
	};
	
	low.start();
		
		//test anything 
		TestAnything testAny = new TestAnything();
		testAny.test2();
	
	}

}
