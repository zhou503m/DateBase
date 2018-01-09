package yt.study.dameng.zm;

import yt.study.dameng.ConnectDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryHighTable {

    static final String synQuery = "select * from HIGH.HIGH_TABLE";

    public static void main(String[] args) throws SQLException {
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
        //查询数据
        ResultSet resultSet = connectDB.executeQuery(synQuery, connection, null);
        if (resultSet.next()) {
            System.out.println("HIGH.HIGH_TABLE has data");
            System.out.println(resultSet.getInt(1));
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }
        }else {
            System.out.println("HIGH.HIGH_TABLE has no data ");
        }
    }
}
