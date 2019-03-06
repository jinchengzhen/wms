package common.jdbc;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import common.toSQL.SQLElement.ColumElement_N;
import common.toSQL.SQLStatement.SelectStatement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
* @ClassName: CommonDao
* @Description: TODO
* @author Administrator
* @date 2018年11月12日
 */
public class CommonDao {
	private static final Logger logger = Logger.getLogger(CommonDao.class);
	
	private static CommonDao dao;
	
	private static String postgresqlIP;// = PropertyUtil.getProperty("postgresqlIP");//"jdbc:postgresql://127.0.0.1:5432/"
	private static String postgresqlPort;// = PropertyUtil.getProperty("postgresqlPort");//5432
	private static String databaseName;// = PropertyUtil.getProperty("databaseName");;//"jcz"
	//用户名
	private static String userName;// = PropertyUtil.getProperty("userName");;//"postgres"
	//用户密码
	private static String Password;// = PropertyUtil.getProperty("Password");;//"123456"
	//数据库链接对象
	private java.sql.Connection conn;
	//数据库命令执行对象
	public PreparedStatement pstmt;
	//数据库返回结果
	private java.sql.ResultSet rs;
	
	private CommonDao() {
		
	}
	public synchronized static CommonDao getInstance() {
		if(dao == null) {
			dao = new CommonDao();
		}
		return dao;
	}
	//静态代码块
		static{
		    //1、加载驱动
		    try {
		        Class.forName("org.postgresql.Driver");
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		    }
		}
		
	//初始化数据库连接参数
	public static void SQLDBInit(Map<String,String> map) {
		postgresqlIP = map.get("postgresqlIP");
		postgresqlPort = map.get("postgresqlPort");
		databaseName = map.get("databaseName");
		userName = map.get("userName");
		Password = map.get("Password");
	}
	
	//2、创建连接
    private void getConnection(){
        if(conn == null){
            try {
            	String url = "jdbc:postgresql://"+postgresqlIP+":"+postgresqlPort+"/"+databaseName;
                conn = DriverManager.getConnection(url, userName, Password);
            } catch (SQLException e) {
            	logger.error(e.getMessage());
            }
        }
    }
    //关闭连接
    private void close(){        
	    try {
	        if(rs!=null){
	            rs.close();
	            rs = null;
	        }
	        if(pstmt!=null){
	            pstmt.close();
	            pstmt = null;
	        }
	        if(conn!=null){
	            conn.close();
	            conn = null;
	        }
	    } catch (SQLException e) {
	        // TODO Auto-generated catch block
	    e.printStackTrace();
	    }
	
	}
	/**
	 * @author jincz
	 * @description 返回map
	 * @param select
	 * @return  List<Map<String,Object>>
	 * @date 2018年10月19日下午8:36:57
	 */
	public List<Map<String,Object>> selectToMap(SelectStatement select){
		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		getConnection();	
		List<ColumElement_N> columlist = select.getColumlist();
		try {
			String sql = select.toSQLStatement();
			logger.info("准备执行的sql： "+sql);
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Map<String,Object> map = null;
				if(select.isOrder()) {
					map = new LinkedHashMap<String,Object>();
				}else {
					map = new HashMap<String,Object>();
				}
				int i = 1; 
				for(ColumElement_N colum:columlist) {
					colum.setFlag(true);
					map.put(colum.getFirstName(),rs.getObject(i) == null ? "":rs.getObject(i) );
					i++;
				}
            	result.add(map);
            }
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally{
            close();
		}
		return result;
	}
	/**
	 * @author jincz
	 * @description 返回json数组
	 * @param select
	 * @return  JSONArray
	 * @date 2018年10月19日下午8:37:23
	 */
	public  JSONArray selectToJson(SelectStatement select){
		JSONArray result = new JSONArray();
		getConnection();	
		List<ColumElement_N> columlist = select.getColumlist();
		try {
			String sql = select.toSQLStatement();
			logger.info("准备执行的sql： "+sql);
			pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				JSONObject jsonObj = new JSONObject();
				int i = 1; 
				for(ColumElement_N colum:columlist) {
					colum.setFlag(true);
					jsonObj.put(colum.getFirstName(),rs.getObject(i) == null ? "":rs.getObject(i) );
					i++;
				}
            	result.add(jsonObj);
            }
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally{
            close();
		}
		return result;
	}
	/**
	 * @author jincz
	 * @description 返回object数组
	 * @param sql
	 * @return  List<Object[]>
	 * @date 2018年10月19日下午8:48:49
	 */
	public List<Object[]> selectToObj(String sql){
		List<Object[]> result = new ArrayList<Object[]>();
		getConnection();	
		try {
			logger.info("准备执行的sql： "+sql);
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData() ; 
			int columnCount = rsmd.getColumnCount();
			while(rs.next()){
				Object[] array = new Object[columnCount];
				for(int i = 0;i<columnCount;i++) {
					array[i] = rs.getObject(i+1) == null ? "":rs.getObject(i+1);
				}
            	result.add(array);
            }
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally{
            close();
		}
		return result;
	}
	//单条sql执行
	public boolean excute(String sql) {
		getConnection();	
		boolean result = false;
		try {
			int num = 0;
			logger.info("准备执行的sql： "+sql);
			pstmt = conn.prepareStatement(sql);
			num = pstmt.executeUpdate();
			if(num > 0) {
				result = true;
			}
		}catch (SQLException e) {
			logger.error(e.getMessage());
		} finally{
            close();
		}
		return result;
	}
	/**
	* @Title: routineExcute
	* @Description: TODO事务,多条sql执行
	* @param    
	* @return boolean    
	* @throws
	 */
	
	public boolean routineExcute(String...sqls) {
		getConnection();	
		try {
			conn.setAutoCommit(false);
			for(String sql : sqls) {
				pstmt = conn.prepareStatement(sql);
				logger.info("准备执行的sql： "+sql);
				pstmt.executeUpdate();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch(Exception e) {
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
            close();
		}
		return true;
	}
}
