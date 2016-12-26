package cn.samoye.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import cn.samoye.bean.Student;
/**
 * 需要的jar	mysql数据库驱动包
 * @author samoye
 *
 */
public class JdbcTest {
	private String url = "jdbc:mysql://localhost:3306/test";
	private String password = "root";
	private String user = "root";
	@Test
	public void testDatabaseConn() throws Exception {
		JdbcTest test = new JdbcTest();
		test.getDataConnByDriver(url, user, password);
		test.getDataConnByDriverManager(url, user, password);
		test.getDataConnByDriverManagerAndForName(url, user, password);
		
		
	}
	/**
	 * 1.通过java.sql.Driver获取数据库连接
	 */
	public Connection getDataConnByDriver(String url,String user,String password){
		Connection connect = null;
		try {
			Driver driver = new com.mysql.jdbc.Driver();
			Properties prop = new Properties();
			prop.setProperty("user", user);
			prop.setProperty("password", password);
			connect = driver.connect(url, prop);
			
		} catch (SQLException e) {
			System.out.println("数据库连接异常");
			e.printStackTrace();
		}finally {
			if(connect != null){
				try {
					connect.close();
					System.out.println("连接关闭成功");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("getDataConnByDriver"+connect);
		return connect;
	}
	
	/**
	 * 通过向DriverManager注册驱动,并且从DriverManager中获取连接对象
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public Connection getDataConnByDriverManager(String url,String user,String password){
		Connection conn = null;
		
		try {
			Driver driver = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			System.out.println("数据库连接 异常");
			e.printStackTrace();
		}finally {
			if(conn != null){
				try {
					conn.close();
					System.out.println("连接关闭成功");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("getDataConnByDriverManager"+conn);
		return conn;
	}
	/**
	 * 通过加载驱动包类对象的方式,加载驱动,并且从DriverManager中获取连接
	 * @param url
	 * @param user
	 * @param password
	 * @return
	 */
	public Connection getDataConnByDriverManagerAndForName(String url,String user,String password){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn != null){
				try {
					conn.close();
					System.out.println("连接关闭成功");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("getDataConnByDriverManagerAndForName"+conn);
		return conn;
	}
	public static Connection getConn(String url,String user,String password){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeJdbcObj(ResultSet rs,Statement stm,Connection conn){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				System.out.println("ResultSet资源关闭失败!");
				e.printStackTrace();
			}
		}
		if(stm != null){
			try {
				stm.close();
			} catch (SQLException e) {
				System.out.println("Statement资源关闭失败!");
				e.printStackTrace();
			}
		}
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Connection资源关闭失败!");
				e.printStackTrace();
			}
		}
	}
	
//静态sql  start
	//测试statement插入  
	@Test
	public void testStatementUpdate() throws Exception {
		JdbcTest jtest = new JdbcTest();
		Connection conn = JdbcTest.getConn(url, user, password);
		//insert
		/**
		String sql = "insert into t_test values(2,'samoye2',23);";
		int resCode = jtest.statementUpdate(conn, sql);
		System.out.println("resCode: "+resCode);
		*/
		//update
		/**
		String sql = "update t_test set name = 'samoye1' where id = 1";
		int result = jtest.statementUpdate(conn, sql);
		System.out.println("更新成功");
		*/
		//delete
		String sql = "delete from t_test where id=1";
		int result = jtest.statementUpdate(conn, sql);
		System.out.println("删除了"+result+"记录");
		
	}
	
	public int statementUpdate(Connection conn,String sql){
		Statement stm = null;
		int resCode = 0;
		try {
			 stm = conn.createStatement();
			resCode = stm.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTest.closeJdbcObj(null, stm, conn);
		}
		return resCode;
	}
	
	//测试statementQuery
	@Test
	public void testStatementQuery() throws Exception {
		JdbcTest jtest = new JdbcTest();
		Connection conn = JdbcTest.getConn(url, user, password);
		
		//select
		String sql = "select * from t_test;";
		ArrayList<Student> qList = jtest.statementQuery(conn, sql);
		for (Student student : qList) {
			System.out.println(student);
		}
	}
	
	public ArrayList<Student> statementQuery(Connection conn,String sql){
		ResultSet rs = null;
		Statement stm = null;
		ArrayList<Student> result = new ArrayList<Student>() ;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				
				Student student = new Student(id, name, age);
				result.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTest.closeJdbcObj(rs, stm, conn);
		}
		return result;
	}
	
//静态sql  end	

//预编译sql start
	@Test
	public void testPStatementUpdate() throws Exception {
		Connection conn = JdbcTest.getConn(url, user, password);
		JdbcTest jtest = new JdbcTest();
		String sql = "insert into t_test values(?,?,?)";
		int addRes = jtest.pStatementUpdate(conn, sql, 4, "xiaosa", 25);
		System.out.println("addRes: "+addRes);
		
	}
	public int pStatementUpdate(Connection conn,String sql,Integer id,String name,Integer age){
		PreparedStatement pstm = null;
		int result = 0;
		try {
			pstm = conn.prepareStatement(sql);
			if(id != null){
				pstm.setInt(1, id);
			}
			if(name != null && name != ""){
				pstm.setString(2, name);
			}
			if(age != null){
				pstm.setInt(3, age);
			}
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTest.closeJdbcObj(null, pstm, conn);
		}
		return result;
	}
	
	/**
	 * 注意模糊查询时,不可以写成:
	 * 	String sql = "select * from t_test where name  like '%?%';";
	 * @throws Exception
	 */
	@Test
	public void testPStatementQuery() throws Exception {
		Connection conn = JdbcTest.getConn(url, user, password);
		JdbcTest jtest = new JdbcTest();
		String sql = "select * from t_test where name  like ?;";
		List<Student> list = jtest.pStatementQuery(conn, sql);
		for (Student stu : list) {
			System.out.println(stu);
		}
	}
	public List<Student> pStatementQuery(Connection conn,String sql){
		ResultSet rs = null;
		PreparedStatement pstm = null;
		List<Student> result = new ArrayList<Student>();
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "%samoy%");
			rs = pstm.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				Student stu = new Student(id, name, age);
				result.add(stu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTest.closeJdbcObj(rs, pstm, conn);
		}
		return result;
	}
//预编译sql end
	
//callStatement 开始
	@Test
	public void test() throws Exception {
		
	}
//callStatement结束
	
//批处理sql start
	/**
	 * 测试批处理
	 * @throws Exception
	 */
	@Test
	public void testPach() throws Exception {
		JdbcTest jtest = new JdbcTest();
		Connection conn = JdbcTest.getConn(url, user, password);
		String sql = "insert into t_test values(?,?,?)";
		
		List<Student> stuList = new ArrayList<Student>();
		for(int i=2;i<102;i++){
			stuList.add(new Student(i,"samoye"+i,22));
		}
		jtest.batch(conn, sql,stuList);
	}
	public void batch(Connection conn,String sql,List<Student> stuList){
		PreparedStatement pstm = null;
		try {
			pstm = conn.prepareStatement(sql);
			for (Student stu : stuList) {
				pstm.setInt(1, stu.getId());
				pstm.setString(2, stu.getName());
				pstm.setInt(3, stu.getAge());
				pstm.addBatch();
				if(stuList.size() % 10 == 0){
					int[] batch = pstm.executeBatch();
					
					pstm.clearBatch();
					for (int i : batch) {
						System.out.println("--->"+i);
					}
				}
//				pstm.add
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcTest.closeJdbcObj(null, pstm, conn);
		}
	}
//批处理sqlend
	
//事务 start
	@Test
	public void testTransaction() throws Exception {
		Connection conn = JdbcTest.getConn(url, user, password);
//		conn.
	}
//事务 end
	
	
}
