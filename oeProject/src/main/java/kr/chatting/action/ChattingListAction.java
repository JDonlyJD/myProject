package kr.chatting.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.chatting.OChattingDAO;
import kr.chatting.OChattingVO;
import kr.controller.Action;
import kr.util.PagingUtil;

public class ChattingListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//1. 로그인확인
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		//로그인되지 않은경우
		Map<String,Object> mapAjax = new HashMap<String,Object>();	
		if(user_num == null) {
			mapAjax.put("result","logout");

			//로그인된 경우	
		}else {
			//2. 전송된 데이터 인코딩처리
			request.setCharacterEncoding("utf-8");

			//3. pageNum 초기화
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";

			//4. count값 구하기
			int item_num = Integer.parseInt(request.getParameter("item_num"));
			OChattingDAO dao = OChattingDAO.getInstance();
			int count = dao.getCountChat(item_num);

			//5. PagingUtil 객체 생성 
			int rowCount=10;	//한 페이지에 10개의 레코드가 보이도록 할 것임
			PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,rowCount,1,null);
			// PagingUtil을 통해서 startRownum과 endRownum을 구함.

			//6. list 객체 생성
			List<OChattingVO> list = null;
			if(count > 0) {
				list = dao.getListChatItem(page.getStartCount(), page.getEndCount(), item_num);
			}else {	//count>0이 아닐경우 출력할 데이터가 없어서 null로 처리됨 -> so, 빈배열형태로 출력할 수 있도록 처리
				list = Collections.emptyList(); // list가 없을 땐 빈 배열형태로 보냄(이렇게 처리안하면 null로보내짐)
			}

			//7. HashMap에 json으로 만들 문구 넣어주기				
			mapAjax.put("count", count);
			mapAjax.put("rowCount", rowCount);
			mapAjax.put("list", list);
			mapAjax.put("user_num", user_num);

			//8. JSON 데이터로 반환	
			ObjectMapper mapper = new ObjectMapper();	
			String ajaxData = mapper.writeValueAsString(mapAjax);

			//9. request에 저장
			request.setAttribute("ajaxData", ajaxData);
		}
		//10. return 주소 반환 (ajax연결을 해줄 jsp로 연결시킴)
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
