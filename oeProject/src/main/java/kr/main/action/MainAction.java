package kr.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.category.OCategoryDAO;
import kr.category.OCategoryVO;
import kr.controller.Action;
import kr.item.OItemDAO;
import kr.item.OItemVO;

public class MainAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//상품정보 읽기
		OItemDAO dao = OItemDAO.getInstance();
		List<OItemVO> itemList = dao.getListItem(1, 8, null, null);	
			//state값 안줬음(DAO구성도 state값없음)
		
		request.setAttribute("itemList", itemList);
		
		//카테고리드롭다운 
		OCategoryDAO dao2 = OCategoryDAO.getInstance();
		List<OCategoryVO> list = null;
		list = dao2.getListCateMenu();
		request.setAttribute("cateName_list", list);
		

		
		//JSP 경로 반환
		return "/WEB-INF/views/main/main.jsp";
	}
}
