package yt.study.dameng;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadData {
	/**
	 * low用户测试：
	 * 是否可以插入数据----可以往 HIGH.SYN 里插入数据
	 * 是否可以删 HIGH.SYN 的数据----不能
	 * 是否可以新建/删除 low 用户的表-----不能（没有创建表权限）+++++++
	 */
	static final String synInsertSql = "insert into HIGH.SYN(COLUMN_1) values(120)";
	static final String synQueryLowSql = "select * from HIGH.SYN where COLUMN_1 = 100";
	static final String deleteHighkey = "delete from HIGH.SYN where COLUMN_1=110";//不能删？
	static final String updateHighkey = "update HIGH.SYN set COLUMN_1=120 where COLUMN_1=110";
	
	static final String dropLowTable = "drop table LOW.TABLE_LOW";//删表
	static final String createLowTable = "create table LOW.TABLE_LOW(COLUMN_1 int primary key)";//创建表
	static final String deleteLowkey = "delete from LOW.TABLE_LOW where COLUMN_1 = 1";//删数据
	static final String insertLowkey = "insert into TABLE_LOW(COLUMN_1) values(1)";//插入数据
	
	
	static final String synDeletehighSql = "delete from HIGH.TABLE_HIGHLOCK where COLUMN_1=2";
	static final String synInsertLowSql = "insert into LOW.TABLE_LOW(COLUMN_1) values(3)";
//	public static void showData(Connection conn, String tableName) {
//			
//	}
	
	public static void main(String[] args) {
		Low low = new Low() {
			public void run() {
				System.out.println(connectDB.executeUpdate(synInsertLowSql, conn, null));
				System.out.println(connectDB.executeUpdate(synDeletehighSql, conn, null));
//				ResultSet result = connectDB.executeQuery(synQueryLowSql, conn, null);
//				try {
//					//result.first();报错 原因：只能往高用户写数据，不能读
//					//System.out.println(result.isFirst());
//					
//					while (result.next()) {
//						int flag = result.getInt("COLUMN_1");
//						System.out.println(flag);
//					}
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		};
		low.start();
		
		High high = new High() {
			public void run() {
				System.out.println(connectDB.executeUpdate(synDeletehighSql, conn, null));
//				ResultSet result = connectDB.executeQuery(synQueryLowSql, conn, null);
//				try {
//					//result.first();报错 原因：只能往高用户写数据，不能读
//					//System.out.println(result.isFirst());
//					
//					while (result.next()) {
//						int flag = result.getInt("COLUMN_1");
//						System.out.println(flag);
//					}
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		};
		high.start();
		
	}

}
