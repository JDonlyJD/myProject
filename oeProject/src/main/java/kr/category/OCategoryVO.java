package kr.category;

public class OCategoryVO {
	private int cate_num; //카테고리 번호
	private int cate_name; //카테고리 이름
	private int cate_status; //(0미사용, 1사용)
	
	public int getCate_num() {
		return cate_num;
	}
	public void setCate_num(int cate_num) {
		this.cate_num = cate_num;
	}
	public int getCate_name() {
		return cate_name;
	}
	public void setCate_name(int cate_name) {
		this.cate_name = cate_name;
	}
	public int getCate_status() {
		return cate_status;
	}
	public void setCate_status(int cate_status) {
		this.cate_status = cate_status;
	}
}
