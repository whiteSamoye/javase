package cn.samoye.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.dbutils.DbUtils;
import org.junit.Test;

/**
 * 
 * @author samoye
 *
 */
public class _DbUtils {
	private String url = "jdbc:mysql://localhost:3306/test";
	private String password = "root";
	private String user = "root";
	@Test
	public void getConn(){
		boolean flag = DbUtils.loadDriver("com.mysql.jdbc.Driver");
		if(flag){
//			DbUtils.
//			Connection conn = DriverManager.getConnection(url, user, password);
		}
	}
}
