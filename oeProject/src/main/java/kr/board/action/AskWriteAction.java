package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.ask.OAskDAO;
import kr.ask.OAskVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class AskWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인x
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우
		MultipartRequest multi = FileUtil.createFile(request);
		OAskVO ask = new OAskVO();
		/*ask.setTitle(multi.getParameter("title"));
		ask.setContent(multi.getParameter("content"));
		ask.setIp(request.getRemoteAddr());
		ask.setFilename(multi.getFilesystemName("filename"));
		ask.setMem_num(user_num);
		
		OAskDAO dao = OAskDAO.getInstance();
		dao.insertBoard(board);
		*/
		return "/WEB-INF/views/board/askWrite.jsp";
	}

}
