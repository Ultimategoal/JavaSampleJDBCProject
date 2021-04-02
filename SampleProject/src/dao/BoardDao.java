package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Controller;
import service.UserService;
import util.JDBCUtil;
import util.ScanUtil;

public class BoardDao {

	private BoardDao(){}
	private static BoardDao instance;
	public static BoardDao getInstance(){
		if(instance == null){
			instance = new BoardDao();
		}
		return instance;
	}
	
	private JDBCUtil jdbc = JDBCUtil.getInstance();
	private UserService userService = UserService.getInstance();
	
	public List<Map<String, Object>> selectBoardList(){
		String sql = "SELECT A.BOARD_NO, A.TITLE, A.CONTENT, B.USER_NAME, A.REG_DATE"
				+ " FROM TB_JDBC_BOARD A"
				+ " LEFT OUTER JOIN TB_JDBC_USER B"
				+ " ON A.USER_ID = B.USER_ID"
				+ " ORDER BY A.BOARD_NO DESC";
		
		return jdbc.selectList(sql);
	}
	
	public int insertBoardList(){
		String sql = "INSERT INTO TB_JDBC_BOARD"
				+" VALUES((SELECT NVL(MAX(BOARD_NO),0) + 1 FROM TB_JDBC_BOARD),?, ?, ?, SYSDATE)";
		
		List<Object> param = new ArrayList<>();
		System.out.println("제목을 입력해주세요");
		Object input1 = ScanUtil.nextLine();
		param.add(input1);
		System.out.println("내용을 입력해주세요");
		Object input2 = ScanUtil.nextLine();
		param.add(input2);
		param.add(Controller.LoginUserId);
		return jdbc.update(sql, param);
		
	}
}
