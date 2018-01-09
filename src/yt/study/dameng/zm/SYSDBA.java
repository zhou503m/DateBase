package yt.study.dameng.zm;

import yt.study.dameng.*;

import java.sql.Connection;
import java.sql.ResultSet;

public class SYSDBA {
    static final String createLowTable = "create table LOW.TABLE_LOW(COLUMN_1 int primary key)";//用户LOW创建一个TABLE_LOW的表
    static final String createHighTable = "create table HIGH.TABLE_HIGH(CONS_HIGH int primary key)";//用户HIGH创建一个TABLE_HIGH的表
    static final String createForeignKey =
            "ALTER TABLE HIGH.TABLE_HIGH ADD constraint CONS_HIGH FOREIGN KEY(COLUMN_1)  REFERENCES LOW.TABLE_LOW(COLUMN_1)";//将HIGH的CONS_HIGH变成LOW的外键



    public static void main(String[] args) {
        String userName = "SYSDBA";
        String passWord = "SYSDBA";
        ConnectDB connectDB = new ConnectDB();
        Connection connection = connectDB.getConnect(userName, passWord);
        if (connection == null) {
            System.out.println("SYSDBA connection is null");
        }else {
            System.out.println("SYSDBA connection successfully");
        }
        //运行sql语句
        boolean isCreateSuc = connectDB.executeUpdate(createForeignKey, connection, null);
        if (isCreateSuc) {
            System.out.println("create table successfully");
        }else {
            System.out.println("create table fiald");
        }

    }
}
