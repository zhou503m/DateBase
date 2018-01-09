package yt.study.dameng.zm;

import yt.study.dameng.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 打印表SYN的数据
 * 默认表已经存在
 */
public class QuarySYNTable {

    static final String synQuery = "select * from HIGH.SYN"; //查询所有的数据
    static final String insertKey = "insert into HIGH.SYN(COLUMN_1) values(300)"; //插入数据
    static final String deleteAllData = "delete from HIGH.SYN";

    public static void main(String[] args) throws SQLException {
        String userName = "SYSDBA";
        String passWord = "SYSDBA";
        ConnectDB connectDB = new ConnectDB();
        Connection connection = connectDB.getConnect(userName, passWord);
        if (connection == null) {
            System.out.println("SYSDBA connection is null");
            return;
        }else {
            System.out.println("SYSDBA connection successfully");
        }

//        //插入数据
//        boolean flag = connectDB.executeUpdate(insertKey, connection, null);
//        if (flag) {
//            System.out.println("insert successfully");
//        } else {
//            System.out.println("insert not successfully");
//        }

//        //删除数据
//        boolean flag1 = connectDB.executeUpdate(deleteAllData, connection, null);
//        if (flag1) {
//            System.out.println("delete successfully");
//        } else {
//            System.out.println("delete not successfully");
//        }

        //查询数据
        ResultSet resultSet = connectDB.executeQuery(synQuery, connection, null);
        if (resultSet.next()) {
            System.out.println("SYN has data");
            System.out.println(resultSet.getInt(1));
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }else {
            System.out.println("SYN has no data ");
        }
    }
}
