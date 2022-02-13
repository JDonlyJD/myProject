package kr.item.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.FileUtil;

public class ItemUpdateAction implements Action{

   @Override
   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      HttpSession session = request.getSession();
      Integer user_num = (Integer)session.getAttribute("user_num");
      if(user_num==null) {//로그인이 되지 않은 경우
         return "redirect:/member/loginForm.do";
      }
      
      MultipartRequest multi = FileUtil.createFile(request);
      int item_num = Integer.parseInt(multi.getParameter("item_num"));
      String filename = multi.getFilesystemName("filename");
      
      OItemDAO dao = OItemDAO.getInstance();
      //수정전 데이터
      OItemVO db_item = dao.getItem(item_num);
      if(user_num!=db_item.getMem_num()) {//로그인한 회원번호와 작성자 회원번호가 불일치
         FileUtil.removeFile(request, filename);//업로드된 파일이 있으면 파일 삭제
         return "/WEB-INF/views/common/notice.jsp";
      }
      //로그인한 회원번호와 작성자 회원번호가 일치
      OItemVO item = new OItemVO();
      
      item.setMem_num(user_num);
      item.setCate_num(Integer.parseInt(multi.getParameter("cate")));
      item.setTitle(multi.getParameter("title"));
      item.setPrice(Integer.parseInt(multi.getParameter("price")));
      item.setContent(multi.getParameter("content"));
      item.setFilename(multi.getFilesystemName("filename"));
      
      //판매 상품 수정
      dao.updateItem(item);
      
      //전송된 파일이 있을 경우 이전 파일 삭제
      if(filename != null) {
    	  FileUtil.removeFile(request, db_item.getFilename());
      }
 
      return "redirect:/item/itemDetail.do?item_num=" + item_num;
   }

}