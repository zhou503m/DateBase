package yt.study.dameng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectDB {
	
	private static final String DRIVER = "dm.jdbc.driver.DmDriver";
	
	private static final String URL = "jdbc:dm://localhost:5236/LOW";
	
	private Connection conn = null;
	
	private PreparedStatement preStat = null;
	
	private ResultSet rSet = null;
	
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!+ ");
			e.printStackTrace();
		}
	}
	
	public ConnectDB() {}
	
	public Connection getConnect(String username, String password) {
		
		try {
			conn = DriverManager.getConnection(URL, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public ResultSet executeQuery(String sql, Connection conn, Object[] params) {
//		try {
//			preStat = conn.prepareStatement(sql);
//			
//			if(params != null) {
//				for (int i = 0; i < params.length; i++) {
//					preStat.setObject(i+1, params[i]);
//				}
//			}
//			
//			rSet = preStat.executeQuery();
//			
//		} catch (SQLException e) {
//			System.out.println("executeQuery错误");
//		}
//		
//		return rSet;
		
		
		try {
			Statement statement = conn.createStatement();
			
			if(params != null) {
				for (int i = 0; i < params.length; i++) {
					preStat.setObject(i+1, params[i]);
				}
			}
			
			rSet = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();//zm
		}
		
		return rSet;
		
	}
	
	public boolean executeUpdate(String sql, Connection conn, Object[] params) {
		
//		try {
//			preStat = conn.prepareStatement(sql);
//			
//			if(params != null) {
//				for (int i = 0; i < params.length; i++) {
//					preStat.setObject(i+1, params[i]);
//				}
//			}
//			
//			preStat.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();//zm
//			return false;
//		}
//		
//		return true;
		
		
		try {
			Statement statement = conn.createStatement();
			
			if(params != null) {
				for (int i = 0; i < params.length; i++) {
					preStat.setObject(i+1, params[i]);
				}
			}
			
			statement.execute(sql);
		} catch (SQLException e) {
			//e.printStackTrace();//zm
			return false;
		}
		
		return true;
	}
	
	public void closeAll() {
        if (rSet != null) {  
            try {  
                rSet.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
  
        if (preStat != null) {  
            try {  
                preStat.close();  
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
  
        if (conn != null) {  
            try {
                conn.close();
            } catch (SQLException e) {  
                System.out.println(e.getMessage());  
            }  
        }     
	}

}
