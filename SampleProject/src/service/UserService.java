package service;

import java.util.HashMap;
import java.util.Map;

import controller.Controller;
import dao.UserDao;
import util.ScanUtil;
import util.View;

public class UserService {

	//유저와 관련된 기능을 할 클래스
	//싱글톤 패턴으로 구성할 것임
	private UserService(){}
	private static UserService instance;
	public static UserService getInstance(){
		if(instance == null){
			instance = new UserService();
		}
		return instance;
	}
	
	
	private UserDao userDao = UserDao.getInstance();
	
	public int join(){
		System.out.println("=====================회원가입===================");
		System.out.print("아이디>");
		String userId = ScanUtil.nextLine();
		System.out.print("비밀번호>");
		String password = ScanUtil.nextLine();
		System.out.print("이름>");
		String userName = ScanUtil.nextLine();
		
		//아이디 중복 확인 생략
		//비밀번호 확인 생략
		//정규표현식(유효성 검사) 생략
		
		Map<String, Object> param = new HashMap<>();
		param.put("USER_ID", userId);
		param.put("PASSWORD", password);
		param.put("USER_NAME", userName);
		
		int result = userDao.insertUser(param);
		
		if(0 < result){
			System.out.println("회원가입 성공");
		}else{
			System.out.println("회원가입 실패");
		}
		
		return View.HOME;
	}

	public int login() {
		System.out.println("====================로그인====================");
		System.out.print("아이디>");
		String userId = ScanUtil.nextLine();
		String idInfo = userId;
		System.out.println(idInfo);
		System.out.print("비밀번호>");
		String password = ScanUtil.nextLine();
		
		Map<String, Object> user = userDao.selectUser(userId, password); //아이디와 비밀번호 맞는지 확인
		if(user == null){
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else{
			System.out.println("로그인 성공");
			Controller.LoginUser = user;
			Controller.LoginUserId = idInfo;
			return View.BOARD_LIST; //로그인 성공했으니까 메인 화면이 아니라 게시판으로 이동
		}
		return View.LOGIN; //로그인 실패하면 다시 로그인 화면으로 
		}
}
