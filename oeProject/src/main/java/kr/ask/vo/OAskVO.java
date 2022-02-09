package kr.ask.vo;

import java.sql.Date;

//1:1문의테이블(OAsk문의 | OAnswer답변)

public class OAskVO {
	
	private int ask_num; //질문글번호
	private int answer_num; //답변글번호
	private int mem_num; //회원번호
	private String title; //문의글 제목
	private int state; //답변상태(0:대기중/1답변완료/default 0)
	private int kind; //질문분류(0신고/1상품문의/2광고문의)
	private String content; //문의글내용
	private Date reg_date; //문의,답변글 등록일(default:sysdate)
	private Date modify_date; //문의,답변글 수정일
	
	public int getAsk_num() {
		return ask_num;
	}
	public void setAsk_num(int ask_num) {
		this.ask_num = ask_num;
	}
	public int getAnswer_num() {
		return answer_num;
	}
	public void setAnswer_num(int answer_num) {
		this.answer_num = answer_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public Date getModify_date() {
		return modify_date;
	}
	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}
}