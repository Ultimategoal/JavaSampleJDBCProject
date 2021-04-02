package service;

import java.util.List;
import java.util.Map;

import controller.Controller;
import util.ScanUtil;
import util.View;
import dao.BoardDao;

public class BoardService {

	//마찬가지로 싱글톤 패턴으로 만들것임
	private BoardService(){}
	private static BoardService instance;
	public static BoardService getInstance(){
		if(instance == null){
			instance = new BoardService();
		}
		return instance;
	}
	
	private BoardDao boardDao = BoardDao.getInstance();
	
	public int boardList(){
		List<Map<String, Object>> boardList = boardDao.selectBoardList(); //게시판 목록을 가져왔음
		
		System.out.println("=======================================");
		System.out.println("번호\t제목\t작성자\t작성일");
		System.out.println("---------------------------------------");
		for(Map<String, Object> board : boardList){
			System.out.println(board.get("BOARD_NO")
					+ "\t" + board.get("TITLE")
					+ "\t" + board.get("USER_NAME")
					+ "\t" + board.get("REG_DATE"));
		}
		System.out.println("=======================================");
		System.out.println("1.조회\t2.등록\t0.로그아웃");
		System.out.print("입력>");
		int input = ScanUtil.nextInt();
		
		switch(input){
		case 1: //조회
			boardDao.selectBoardList();
			break;
		case 2: //등록
			boardDao.insertBoardList();
			break;
		case 0: //로그아웃
			Controller.LoginUser = null;
			Controller.LoginUserId = null;
			return View.HOME; //로그아웃 했으니까 다시 홈화면으로
		}
		
		return View.BOARD_LIST; //잘못입력했을 때 다시 보드리스트로
	}
}
