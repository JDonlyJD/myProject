package kr.item.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.util.PagingUtil;

public class SaleListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
				
		//count를 바꿔야 페이징처리를 할 수 있으니까
		OItemDAO dao = OItemDAO.getInstance();
//		int count = dao.getListItemCount(keyfield, keyword);
		
		//페이지 처리
		//keyfield와 keyword, currentPage, count, rowCount, pageCount, url 을 넘겨준다
//		PagingUtil page = new PagingUtil(keyfield, keyword, Integer.parseInt(pageNum), count, 20,10,"list.do");
		
		List<OItemVO> list = null;
		//정보를 읽어오고
//		if(count >0) {
//			list = dao.getListItem(page.getStartCount(), page.getEndCount(), keyfield, keyword);
//		}
		
		//정보를 넘겨주고
//		request.setAttribute("count", count);
//		request.setAttribute("list", list);
//		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		//JSP경로반환
		return "/WEB-INF/views/item/saleList.jsp";
		
	}

}
