package yt.study.dameng;

import java.sql.SQLException;

public class Start {
	static final String synInsertSql = "insert into HIGH.SYN(COLUMN_1) values(120)";  //锁
	//static final String synDeleteSql = "delete from HIGH.SYN where COLUMN_1=110";//释放锁_old
	
	static final String updateHighkey = "update HIGH.SYN set COLUMN_1=120 where COLUMN_1=110";//释放锁_new
	static final String updateHighkey1 = "update HIGH.SYN set COLUMN_1=110 where COLUMN_1=120";//抢占锁
	static final String synInsertLowSql = "insert into HIGH.SYN(COLUMN_1) values(100)";  //low插入的标记100
	static final String synInsertHighSql = "insert into HIGH.SYN(COLUMN_1) values(200)"; //high插入的标记200
	static final String synDeleteLowSql = "delete from HIGH.SYN where COLUMN_1 = 100";
	static final String synDeleteHighSql = "delete from HIGH.SYN where COLUMN_1 = 200";
	static final String synQueryLowSql = "select * from HIGH.SYN where COLUMN_1 = 100";
	static final String synQueryHighSql = "select * from HIGH.SYN where COLUMN_1 = 200";
	
	//static final String createLowTable = "create table LOW.TABLE_LOW(COLUMN_1 int primary key)";  //创建表
	static final String insertLowkey = "insert into LOW.TABLE_LOW(COLUMN_1) values(1)";
	
//	static final String dropLowTable = "drop table LOW.TABLE_LOW";
	static final String deleteLowkey = "delete from LOW.TABLE_LOW where COLUMN_1=1";
	//static final String dropHighTable = "drop table HIGH.TABLE_HIGH";
	static final String createForeignKey = "ALTER TABLE HIGH.TABLE_HIGH ADD constraint CONS_HIGH FOREIGN KEY(COLUMN_1)  REFERENCES LOW.TABLE_LOW(COLUMN_1)";
	static final String dropForeignKey = "ALTER TABLE HIGH.TABLE_HIGH DROP constraint CONS_HIGH";
	
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
					System.out.println("-------" + currentThread());
				if(connectDB.executeUpdate(updateHighkey1, conn, null)) { //抢占锁
					//System.out.println("++++++++++++++++++++++++++");
					try {
						if((connectDB.executeUpdate(synInsertLowSql, conn, null)) && (connectDB.executeQuery(synQueryHighSql, conn, null).next())) { //判断表中是否有high的标记，如果有说明high已经做好准备开始发送数据了
                            boolean flag = connectDB.executeUpdate(deleteLowkey, conn, null); //能否成功删除
                            System.out.println(flag);

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

                                    connectDB.executeUpdate(updateHighkey, conn, null);      //释放锁
                                    connectDB.closeAll();   //关闭所有连接
                                    return;
                                }
                            }

                            connectDB.executeUpdate(updateHighkey, conn, null);      //释放锁
                            connectDB.executeUpdate(synDeleteHighSql, conn, null);


                        } else {
                            //connectDB.executeUpdate(synDeleteHighSql, conn, null);  //如果插入成功，则说明high还开始传数据，这个时候需要把这个标记删掉，不然high就插入不了了
                            connectDB.executeUpdate(updateHighkey, conn, null);      //释放锁

                        }
					} catch (SQLException e) {
						e.printStackTrace();
					}
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
					System.out.println("++++++++" + currentThread());
				if(connectDB.executeUpdate(updateHighkey1, conn, null)) { //抢占锁
					try {
						if(connectDB.executeQuery(synQueryLowSql, conn, null).next() && connectDB.executeUpdate(synInsertHighSql, conn, null)) { //判断表中是否有low的标记，如果有说明low已经做好准备开始接受数据了
							if(result.equals("")) { //如果最后字符串为空
								connectDB.executeUpdate(synDeleteLowSql, conn, null); //删除low的标记
								connectDB.executeUpdate(synDeleteHighSql, conn, null); //删除high的标记
								connectDB.executeUpdate(updateHighkey, conn, null); //释放锁，返回
								connectDB.closeAll();
								return;
							}
							
							if(result.charAt(0) == '0') { //要传递的字符是0
								System.out.println("high用户传递的字符是：0");
								if(connectDB.executeQuery(existForeignKey, conn, null).next()) { //如果外键存在就不需要再创建外键操作
									//System.out.println("f exist");
								} else {
									//外键不存在
//									connectDB.executeUpdate(createForeignKey, conn, null);  //插入外键
    								connectDB.executeUpdate(insertForeignKey, conn, null);  //插入外键的标示
									//System.out.println(connectDB.executeUpdate(createForeignKey, conn, null));
									//System.out.println("f donot exist");
									//System.out.println(connectDB.executeUpdate(insertForeignKey, conn, null));
								}
							} else  { //要传递的字符是1
								System.out.println("high用户传递的字符是：1");
								if(connectDB.executeQuery(existForeignKey, conn, null).next()) { // 如果外键存在需要把外键删除
									//connectDB.executeUpdate(dropForeignKey, conn, null);   //删除外键操作
									connectDB.executeUpdate(deleteForeignKey, conn, null); //删除表中存的外键标识
								}
							}
							result = result.substring(1);
							//connectDB.executeUpdate(synInsertHighSql, conn, null); //插入high用户的标记说明我已经传递了一位数据了
							connectDB.executeUpdate(synDeleteLowSql, conn, null);  //删除low的标记
							connectDB.executeUpdate(updateHighkey, conn, null); //释放锁，sleep
						} else {
							//connectDB.executeUpdate(synDeleteLowSql, conn, null);//zm
							connectDB.executeUpdate(updateHighkey, conn, null); //释放锁
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} 
			}
			}
		};
		
		low.start();
		
		high.start();
	}
}
