package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.OMemberVO;
import kr.member.OMemberDAO;

public class JoinAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
				
		//자바빈(VO)객체 생성
		OMemberVO member = new OMemberVO();
		member.setMem_id(request.getParameter("mem_id"));
		member.setMem_nick(request.getParameter("mem_nick"));
		member.setMem_pw(request.getParameter("mem_pw"));
		member.setMem_phone(request.getParameter("mem_phone"));
		member.setMem_email(request.getParameter("mem_email"));
		member.setMem_zipcode(request.getParameter("mem_zipcode"));
		member.setMem_addr(request.getParameter("mem_addr"));
		member.setMem_addr2(request.getParameter("mem_addr2"));
				
		OMemberDAO dao = OMemberDAO.getInstance();
		dao.insertMember(member);
				
		//JSP경로 반환
		return "/WEB-INF/views/member/join.jsp";
	}
  
}
