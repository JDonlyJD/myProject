package kr.item.vo;

import java.sql.Date;

public class OItemVO {
	private int item_num;//상품번호
	private int mem_num;//회원번호
	private int cate_num;//카테고리 번호
	private String title;//글제목
	private int price;//상품가격
	private int state;//판매상태(0판매중/1예약중/2판매완료) (default 0)
	private String content;//상품글내용
	private String filename;//상품사진이름
	private int hit;//상품등록글 조회수(default 0)
	private Date reg_date;//상품등록일
	private Date modify_date;//상품수정일
	
	//oitem_order
	private int order_num;//구매번호
//	private int item_num;//상품번호
//	private int mem_num;//구매자 회원번호
//	private Date reg_date;//상품구매일
	
	//oitem_favorite
	private int like_num;//구매번호
//	private int item_num;//상품번호
//	private int mem_num;//구매자 회원번호
	
	//otiem_reply
	private int re_num;//댓글번호
//	private int item_num;//상품번호
//	private int mem_num;//회원번호
//	private String content;//댓글 내용
//	private Date reg_date;//댓글 등록일
//	private Date modify_date;//댓글 수정일
	
	
	
	
}
