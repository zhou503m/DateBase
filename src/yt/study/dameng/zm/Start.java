package yt.study.dameng.zm;

import yt.study.dameng.*;

import java.sql.SQLException;


public class Start {

	//Lock
	static final String insertLowLock = "insert into LOW.TABLE_LOW_Lock(COLUMN_1) values(3)";//Low插入数据1
	static final String deleteLowLock = "delete from LOW.TABLE_LOW_Lock where COLUMN_1=3";//Low删除数据1
	static final String insertForeignLock = "insert into HIGH.TABLE_HIGH_Lock(COLUMN_1,COLUMN_2) values(3,100)"; //插入一行数据表示存在外键
	static final String existForeignLock = "select * from HIGH.TABLE_HIGH_Lock where COLUMN_1 = 3";  //查看是否有外键存在
	static final String deleteForeignLock = "delete from HIGH.TABLE_HIGH_Lock where COLUMN_1 = 3";  //删除外键

	//transmit Bit
    static final String insertLowkey = "insert into LOW.TABLE_LOW(COLUMN_1) values(1)";//Low插入数据1
    static final String deleteLowkey = "delete from LOW.TABLE_LOW where COLUMN_1=1";//Low删除数据1
	static final String insertForeignKey = "insert into HIGH.TABLE_HIGH(COLUMN_1,COLUMN_2) values(1,100)"; //插入一行数据表示存在外键
	static final String existForeignKey = "select * from HIGH.TABLE_HIGH where COLUMN_1 = 1";  //查看是否有外键存在
	static final String deleteForeignKey = "delete from HIGH.TABLE_HIGH where COLUMN_1 = 1";  //删除外键
	
	static int k = 0;
	
	public static void main(String[] args) {
		
		Low low = new Low() {
			public void run() {
				while(true) {
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						if(connectDB.executeQuery(deleteLowLock, conn, null).next()) { // null，3 --> 3 ,3
							System.out.println("-----user Low get The Lock----");
							boolean flag = connectDB.executeUpdate(deleteLowkey, conn, null); //能否成功删除
							// System.out.println(flag);

							if(flag) {
								System.out.println(k++ + "low用户得到：1");
								connectDB.executeUpdate(insertLowkey, conn, null); //数据删了之后还得新建数据
								result += 1;
							}else {
								System.out.println(k++ + "low用户得到：0");
								result += 0;
							}

							if((result.length()%8==0) && result.endsWith("00001010")) { //result必须是8位的倍数，如果结尾是00001010两次，就是high那边已经传完了
								if(++endCount >= 2) { //如果有两个\n就跳出
									System.out.println("low用户得到的二进制串为："+result);
									System.out.println();
									System.out.println("转码过来就是：");
									binString2String(result);   //转码

									//释放锁
									connectDB.executeUpdate(insertLowLock, conn, null);
									connectDB.executeUpdate(insertForeignLock, conn, null);

									connectDB.closeAll();   //关闭所有连接
									return;
								}
							}

							//Low释放锁 state： 3， 3
							System.out.println("----user Low is releasing the lock----");
							connectDB.executeUpdate(insertLowLock, conn, null);
							connectDB.executeUpdate(insertForeignLock, conn, null);


						} else {
							System.out.println("----Low can not get the Lock; has no operation");
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				}//while
		}//run
			
	};
		
		High high = new High() {
			public void run() {
				while(true) {
					try {
						sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if(connectDB.executeQuery(existForeignLock, conn, null).next()) { // 3, 3 --> null, 3
							System.out.println("+++++user High get The Lock+++++");
								if(result.equals("")) { //如果最后字符串为空
									connectDB.executeUpdate(deleteForeignLock, conn, null);
									connectDB.closeAll();
									return;
								}
								if(result.charAt(0) == '0') { //要传递的字符是0
									System.out.println("high用户传递的字符是：0");
									if(connectDB.executeQuery(existForeignKey, conn, null).next()) { //如果外键有数据
									} else {
										//外键没数据
										connectDB.executeUpdate(insertForeignKey, conn, null);  //插入外键数据
									}
								} else  { //要传递的字符是1
									System.out.println("high用户传递的字符是：1");
									if(connectDB.executeQuery(existForeignKey, conn, null).next()) { // 如果外键存在需要把外键删除
										connectDB.executeUpdate(deleteForeignKey, conn, null); //删除表中存的外键标识
									}
								}
								result = result.substring(1);

							//High释放锁 state： null， 3
							System.out.println("++++user High is releasing the lock----");
							connectDB.executeUpdate(deleteForeignLock, conn, null);
						}else {
							System.out.println("++++High can not get the Lock; has no operation");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		low.start();
		
		high.start();
	}
}
