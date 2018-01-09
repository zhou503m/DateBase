package yt.study.dameng.zm;

import yt.study.dameng.ConnectDB;

import java.sql.Connection;
import java.sql.SQLException;

public class ForeignKeyTest {
    /**
     * TABLE_HIGH 的COLUMN_1如果引用了LOW.TABLE_LOW做外键的话  需要TABLE_LOW表里有相应的数据才能插入
     */

    static final String insertLowkey = "insert into LOW.TABLE_LOW(COLUMN_1) values(1)";//低用户插入数据
    static final String insertForeignKey = "insert into HIGH.TABLE_HIGH(COLUMN_1,COLUMN_2) values(1,100)";//插入外键数据
    static final String existForeignKey = "select * from HIGH.TABLE_HIGH where COLUMN_1 = 1";  //查看是否有外键存在
    static final String deleteForeignKey = "delete from HIGH.TABLE_HIGH where COLUMN_1 = 1";  //删除外键
    static final String deleteLowkey = "delete from LOW.TABLE_LOW where COLUMN_1=1";//低用户删除数据

    public static void main(String[] args) throws SQLException {
        /**
         * HIGH用户
         */
        String userName = "HIGH";
        String passWord = "123456789";
        ConnectDB connectDB = new ConnectDB();
        Connection connection = connectDB.getConnect(userName, passWord);
        if (connection == null) {
            System.out.println("HIGH connection is null");
            return;
        }else {
            System.out.println("HIGH connection successfully");
        }
        /**
         * LOW用户
         */
        String userName1 = "LOW";
        String passWord1 = "123456789";
        ConnectDB connectDB1 = new ConnectDB();
        Connection connection1 = connectDB1.getConnect(userName1, passWord1);
        if (connection1 == null) {
            System.out.println("LOW connection is null");
            return;
        }else {
            System.out.println("LOW connection successfully");
        }
        //低用户插入数据
        boolean flag = connectDB1.executeUpdate(insertLowkey, connection1, null);
        if (flag) {
            System.out.println("user LOW insert data successfully");
        } else {
            System.out.println("user LOW insert data not successfully");
        }


        //插入外键数据
        boolean flag1 = connectDB.executeUpdate(insertForeignKey, connection, null);
        if (flag1) {
            System.out.println("insert ForeignKey successfully");
        } else {
            System.out.println("insert ForeignKey not successfully");
        }

        //查看是否有外键存在
        if(connectDB.executeQuery(existForeignKey, connection, null).next()) {
            System.out.println("ForeignKey exist");
        } else {
            System.out.println("ForeignKey donot exist");
        }

        //删除外键数据
        boolean flag2 = connectDB.executeUpdate(deleteForeignKey, connection, null);
        if (flag2) {
            System.out.println("delete ForeignKey successfully");
        } else {
            System.out.println("delete ForeignKey not successfully");
        }
        //LOW用户删除原始数据
        boolean flag3 = connectDB1.executeUpdate(deleteLowkey, connection1, null);
        if (flag3) {
            System.out.println("user LOW delete data successfully");
        } else {
            System.out.println("user LOW delete data not successfully");
        }


    }
}
