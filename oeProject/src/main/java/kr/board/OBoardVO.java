package kr.board;

import java.sql.Date;

//1:1문의테이블(OAsk문의 | OAnswer답변)

public class OBoardVO {
	
	private int board_num; //질문글번호
	private String title; //문의글 제목
	private String content; //문의글내용
	private int hit;		//조회수
	private Date reg_date; //문의,답변글 등록일(default:sysdate)
	private Date modify_date; //문의,답변글 수정일
	private String filename;	//파일명
	private String ip;			//ip주소
	private int mem_num; //회원번호
	private String id;	

	private int state; //답변상태(0:대기중/1답변완료/default 0)
	private int kind; //질문분류(0신고/1상품문의/2광고문의)
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
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
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	
}