package kr.chatting.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chatting.OChattingVO;
import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.member.OMemberDAO;

public class ChattingAction implements Action{

	@Override		//현재 페이지는 / 상품detail에서 연결되는 페이지임
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 확인
		//1. 구매자 회원번호(from_num)반환
		HttpSession session = request.getSession();
		Integer from_num = (Integer)session.getAttribute("user_num");	//구매자 회원번호 : from_num
			//로그인 되지 않은 경우
			if(from_num == null) {
				return "redirect:/member/loginForm.do";
			}
		
		//2. 상품번호(item_num) 반환
		int item_num = Integer.parseInt(request.getParameter("item_num"));	//채팅방들어가려면 requeset에 item_num받기
		
		
		//3. item객체에 해당 상품번호의 데이터 담기
		OItemDAO dao = OItemDAO.getInstance();
		OItemVO item = dao.getItem(item_num);
		
				//3. 상품판매자 회원번호 반환	
				//int to_num = dao.getItem(item_num).getMem_num();	//판매자 회원번호
		
		
		//4. request에 chat객체 데이터 저장
		request.setAttribute("item", item);
		return "/WEB-INF/views/chatting/chatting.jsp";	//xml에 입력하기
	}
}
