package yt.study.dameng;

public class ReadData_2 {
	static final String dropForeignKey = "ALTER TABLE HIGH.TABLE_HIGH DROP constraint CONS_HIGH";
	
	
	static final String deleteForeignKey = "delete from HIGH.TABLE_HIGH where COLUMN_1 = 1";  //删除外键
	static final String insertForeignKey = "insert into HIGH.TABLE_HIGH(COLUMN_1,COLUMN_2) values(1,100)";
	static final String insertSynKey = "insert into HIGH.SYN(COLUMN_1) values(120)";
	static final String insertSynKey2 = "insert into HIGH.SYN(COLUMN_1) values(100)";
	static final String deleteForeignlowKey = "delete from Low.TABLE_LOW where COLUMN_1 = 1";
	
	static final String deletehigh = "delete from HIGH.TABLE_HIGH";
	static final String deletehighkey = "delete from HIGH.SYN where COLUMN_1=110";
	static final String deletelow = "delete from LOW.TABLE_LOW";
	static final String insertLOWKEY = "insert into LOW.TABLE_LOW(COLUMN_1) values(1)";
	
	static final String createForeignKey = "ALTER TABLE HIGH.TABLE_HIGH ADD constraint CONS_HIGH FOREIGN KEY(COLUMN_1)  REFERENCES LOW.TABLE_LOW(COLUMN_1)";
	public static void main (String[] args) {
		High high = new High() {
			public void run() {
//				System.out.println(connectDB.executeUpdate(deleteForeignKey, conn, null));
//				System.out.println(connectDB.executeUpdate(insertForeignKey, conn, null));
				System.out.println(connectDB.executeUpdate(deletehigh, conn, null));
				System.out.println(connectDB.executeUpdate(insertSynKey, conn, null));
				//System.out.println(connectDB.executeUpdate(insertSynKey2, conn, null));
				//System.out.println(connectDB.executeUpdate(deletehighkey, conn, null));
			}
		};
		high.start();
		Low low = new Low() {
			public void run() {
				//System.out.println(connectDB.executeUpdate(deletelow, conn, null));
				//System.out.println(connectDB.executeUpdate(deleteForeignlowKey, conn, null));
//				System.out.println(connectDB.executeUpdate(insertForeignKey, conn, null));
				//System.out.println(connectDB.executeUpdate(dropForeignKey, conn, null));
			}
		};
		low.start();
	}

}
