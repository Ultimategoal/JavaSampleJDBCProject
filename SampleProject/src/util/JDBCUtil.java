package util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {

	//싱글톤 패턴이라는 디자인패턴으로 구성할 클래스
	//싱글톤 패턴 : 인스턴스의 생성을 제한하여 하나의 인스턴스만 사용하는 디자인 패턴
	private JDBCUtil(){ //생성자 앞에 private을 붙이면 객체 생성을 하지 못함, 호출이 불가능하기 때문임
		
	}
	
	private static JDBCUtil instance; //객체를 저장할 변수
	
	public static JDBCUtil getInstance(){ //객체를 생성해서 빌려주는 메서드
		if(instance == null){
			instance = new JDBCUtil();
		}
		return instance; //null일 때, 처음에만 생성하고 그 이후에는 위에 객체를 저장해서 리턴해서 돌려줌
	} //클래스 내에서 객체 생성해서 빌려줌, 다른 클래스에서 이 클래스의 객체가 필요하다고 하면 메서드 호출해서 빌려주면 됨
	
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "PC25";
	String password = "java";

	
	java.sql.Connection con = null;	//연결된 정보
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm");
	
	/*
	 * Map<String, Object> selectOne(String sql), 조회 결과가 한줄인 경우에 사용
	 * Map<String, Object> selectOne(String sql, List<Object> param), String sql>쿼리, param > ?에 들어갈 값
	 * List<Map<String, Object>> selectList(String sql), 조회 결과가 여러줄인 경우에 사용
	 * List<Map<String, Object>> selectList(String sql, List<Object> param)
	 * int update(String sql), select 외에 insert,delete,update등을 쓸 때 사용
	 * int update(String sql, List<Object> param)
	 * 이렇게 6개의 메서드가 필요함
	 */
	//리턴해서 다른 클래스에서도 사용해야 하기 때문에 public사용
	
	public Map<String, Object> selectOne(String sql){
//		Map<String, Object> row = new HashMap<>(); 이렇게 하는게 아니라 null로 넣는게 나음
		Map<String, Object> row = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			if(rs.next()){ //--.rs.next()를 하면 첫번째 줄을 바라봄, if써준 이유는 한 줄도 없을 경우 false로 빠져나오기 위함임
				row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++){
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null) try{rs.close();} catch(Exception e){}
			if(ps != null) try{ps.close();} catch(Exception e){}
			if(con != null) try{con.close();} catch(Exception e){}
		}
		
		return row;
	}
	
	public Map<String, Object> selectOne(String sql, List<Object> param){
		Map<String, Object> row = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			
			for(int i = 0; i < param.size(); i++){ //param이 ArrayList이기 때문에 여러 값이 있을 수 있어서 for문을 돌려야함
				ps.setObject(i + 1, param.get(i));
			}
			
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			if(rs.next()){
				row = new HashMap<>();
				for(int i = 1; i <= columnCount; i++){
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null) try{rs.close();} catch(Exception e){}
			if(ps != null) try{ps.close();} catch(Exception e){}
			if(con != null) try{con.close();} catch(Exception e){}
		}
		
		return row;
	}
	
	public List<Map<String, Object>> selectList(String sql){
		List<Map<String, Object>> list = new ArrayList<>();
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while(rs.next()){
				Map<String, Object> row = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++){
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null) try{rs.close();} catch(Exception e){}
			if(ps != null) try{ps.close();} catch(Exception e){}
			if(con != null) try{con.close();} catch(Exception e){}
		}
		
		return list;
	}
	
	public List<Map<String, Object>> selectList(String sql, List<Object> param){
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);
			for(int i = 0; i < param.size(); i++){ //param이 ArrayList이기 때문에 여러 값이 있을 수 있어서 for문을 돌려야함
				ps.setObject(i + 1, param.get(i));
			}
			
			rs = ps.executeQuery();
			
			java.util.Date date = rs.getDate("reg_date");
			String regDate = format.format(date);
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while(rs.next()){
				Map<String, Object> row = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++){
					row.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(row);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null) try{rs.close();} catch(Exception e){}
			if(ps != null) try{ps.close();} catch(Exception e){}
			if(con != null) try{con.close();} catch(Exception e){}
		}
		
		return list; //여기서 리턴하려면 try/catch 밖에서 변수를 만들어야함
	}
	public int update(String sql){
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(ps != null) try{ps.close();} catch(Exception e){}
			if(con != null) try{con.close();} catch(Exception e){}
		}
		
		return result;
	}
	
	public int update(String sql, List<Object> param){
		
		int result = 0;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);
			for(int i = 0; i < param.size(); i++){
				ps.setObject(i + 1, param.get(i));
			}
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(ps != null) try{ps.close();} catch(Exception e){}
			if(con != null) try{con.close();} catch(Exception e){}
		}
		
		return result;
	}
	
	
}
