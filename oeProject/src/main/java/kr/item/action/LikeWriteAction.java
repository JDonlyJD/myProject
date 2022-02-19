package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.item.OLikeDAO;
import kr.item.OLikeVO;

public class LikeWriteAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	   
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num == null) {//로그인이 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      
      //로그인이 되어 있는 경우
      request.setCharacterEncoding("utf-8");
      
      OLikeVO like = new OLikeVO();
      like.setItem_num(Integer.parseInt(request.getParameter("oitem_num")));
      like.setMem_num(user_num);
      
      OLikeDAO dao = OLikeDAO.getInstance();
      OLikeVO likeItem = dao.getLike(like);
      
      if(likeItem == null) {//같은 회원번호, 같은 상품번호로 등록한 정보 미존재
         dao.insertLike(like);
         
      }else {//같은 회원번호, 같은 상품번호로 등록한 정보 존재
    	request.setAttribute("notice_msg", "이미 찜목록에 추가하신 상품입니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/item/itemDetail.do");
		return "/WEB-INF/views/common/alert_singleView.jsp";	//forward방식으로 request에 데이터를 담음)
      }      
      return "redirect:/item/likeList.do";
   }
}