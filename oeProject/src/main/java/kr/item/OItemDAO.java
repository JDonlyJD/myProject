package kr.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.item.OItemVO;
import kr.util.DBUtil;

public class OItemDAO {
	//싱글턴 패턴
	private static OItemDAO instance = new OItemDAO();
	
	public static OItemDAO getInstance() {
		return instance;
	}
	private OItemDAO() {}
	
	//판매상품등록
	public void insertItem(OItemVO item)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO oitem(item_num, cate_num, title, price, "
				+ "filename, content, state) VALUES (oitem_seq.nextval,?,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item.getCate_num());
			pstmt.setString(2, item.getTitle());
			pstmt.setInt(3, item.getPrice());
			pstmt.setString(4, item.getFilename());
			pstmt.setString(5, item.getContent());
			pstmt.setInt(6, item.getState());
			
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	/*
	//전체 상품 갯수/검색 상품 갯수
	public int getItemCount(String keyfield, String keyword, int status)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검새 처리
				if(keyfield.equals("1")) sub_sql = "AND name LIKE ?";
				else if(keyfield.equals("2")) sub_sql = "AND detail LIKE ?";	
			}
			
			//SQL문 작성
			sql ="SELECT COUNT(*) FROM zitem WHERE status > ? " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%" + keyword + "%");
			}
			//SQL문 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	//판매상품 목록/검색 목록
		public List<OItemVO> getListItem(int startRow, int endRow, 
								int state)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OItemVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql ="SELECT * FROM (SELECT a.*, rownum rnum FROM"
					+ "(SELECT * FROM oitem WHERE state > ? " + sub_sql
					+ " ORDER BY item_num DESC)a) WHERE rnum >= ? AND rnum <= ?";	
					
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(1, state);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);
				//SQL문 실행해서 결과행을 ResultSet에 담음
				rs = pstmt.executeQuery();

				list = new ArrayList<OItemVO>();
				while(rs.next()) {
					OItemVO item = new OItemVO();
					item.setItem_num(rs.getInt("item_num"));
					item.setName(rs.getString("name"));
					item.setPrice(rs.getInt("price"));
					item.setQuantity(rs.getInt("quantity"));
					item.setPhoto1(rs.getString("photo1"));
					item.setPhoto2(rs.getString("photo2"));
					item.setReg_date(rs.getDate("reg_date"));
					item.setModify_date(rs.getDate("modify_date"));
					item.setStatus(rs.getInt("status"));
					
					//자바빈(VO)를 ArrayList에 저장
					list.add(item);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
*/		
}
