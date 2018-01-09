package yt.study.dameng.zm;

import yt.study.dameng.ConnectDB;

import java.sql.Connection;

public class LowTest {
    /**
     * user LOW test
     * 1. insert HIGH.SYN
     * 2. update HIGH.SYN
     * 管理用户LOW右键  对象权限里面选择HIGH下面的SYN表   选择insert、update
     */

    static final String synInsertLowSql = "insert into HIGH.SYN(COLUMN_1) values(10)";
    static final String updateHighkey = "update HIGH.SYN set COLUMN_1=12 where COLUMN_1=10";


    public static void main(String[] args) {
        String userName = "LOW";
        String passWord = "123456789";
        ConnectDB connectDB = new ConnectDB();
        Connection connection = connectDB.getConnect(userName, passWord);
        if (connection == null) {
            System.out.println("LOW connection is null");
            return;
        }else {
            System.out.println("LOW connection successfully");
        }

         //插入数据
        boolean flag = connectDB.executeUpdate(synInsertLowSql, connection, null);
        if (flag) {
            System.out.println("insert successfully");
        } else {
            System.out.println("insert not successfully");
        }

        //更新数据
        boolean flag1 = connectDB.executeUpdate(updateHighkey, connection, null);
        if (flag1) {
            System.out.println("update successfully");
        } else {
            System.out.println("update not successfully");
        }

    }
}
