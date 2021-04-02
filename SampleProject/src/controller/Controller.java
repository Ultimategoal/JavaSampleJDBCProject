package controller;

import java.util.Map;

import service.BoardService;
import service.UserService;
import util.ScanUtil;
import util.View;

public class Controller {

	public static void main(String[] args) {
		/*
		 * 발표순서 : 조 소개 > 주제 소개 > 주제 선정 배경 > 메뉴 구조 > 시연 > 소감
		 * 발표인원 : 발표자 1명, ppt 및 시연 도우미 1명
		 * 
		 * Controller : 화면 이동
		 * Service : 화면 기능
		 * Dao : 쿼리작성
		 */
		//main 메서드에서는 static이 붙어 있기 때문에 전역변수 만들기가 어려움
		
		new Controller().start(); //객체 생성
	}
	
	public static Map<String, Object> LoginUser;
	public static String LoginUserId;
	
	private UserService userService = UserService.getInstance();
	private BoardService boardService = BoardService.getInstance();

	private void start() {
		int view = View.HOME;
		
		while(true){ //맨 처음 home화면 보여주게 함
			switch(view){
				case View.HOME : view = home(); break;
				case View.LOGIN : view = userService.login(); break;
				case View.JOIN : view = userService.join(); break;
				case View.BOARD_LIST : view = boardService.boardList(); break;
				
			}
		}
		
	}

	private int home() {
		System.out.println("--------------------------------------------");
		System.out.println("1.로그인\t2.회원가입\t0.프로그램 종료");
		System.out.println("--------------------------------------------");
		System.out.print("번호 입력>");
		
		int input = ScanUtil.nextInt();
		
		switch(input){
		case 1: //로그인
			return View.LOGIN; //다음 화면으로 리턴
//			break; 리턴하면 종료되기 때문에 break가 필요없기 때문에 컴파일 에러 뜸
		case 2: //회원가입
			return View.JOIN;
		case 0:
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);
		}
		return View.HOME; //1,2,0 제외한 다른걸 선택했을 때 return까지 내려오게 되므로 다시 home 화면으로 돌아오게 하기 위해서 return View.HOME;을 써줌
	}

}
