package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class UserDao {

	//서비스와 마찬가지로 싱글톤패턴으로 만들것임
	
	private UserDao(){}
	private static UserDao instance;
	public static UserDao getInstance(){
		if(instance == null){
			instance = new UserDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	
	public int insertUser(Map<String, Object> param){
		String sql = "INSERT INTO TB_JDBC_USER (USER_ID, PASSWORD, USER_NAME) "
				+ "VALUES (?, ?, ?)";
		
		List<Object> p = new ArrayList<>();
		p.add(param.get("USER_ID"));
		p.add(param.get("PASSWORD"));
		p.add(param.get("USER_NAME"));
		
		return jdbc.update(sql, p); //얘는 리스트라서 위에 있는 ?들은 map이라서 map 다시  list에 담아서 넣어야 됨
		//메서드 int로 만든 이유가 리턴 값이 인트이기 때문에 int로 만듬
		
	}

	public Map<String, Object> selectUser(String userId, String password) {
		String sql = "SELECT USER_ID, PASSWORD, USER_NAME"
				+ " FROM TB_JDBC_USER"
				+ " WHERE USER_ID = ?"
				+ " AND PASSWORD = ?"; //쿼리 주의 사항이 줄 한칸 띄었을 때 "앞에 반드시 띄어쓰기가 있어야 함
		
		List<Object> param = new ArrayList<>();
		param.add(userId);
		param.add(password);
		
		return jdbc.selectOne(sql, param);
	}
}
