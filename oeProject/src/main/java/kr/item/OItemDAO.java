package kr.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.item.OItemVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

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
			sql = "INSERT INTO oitem(item_num, mem_num, cate_num, title, price, "
				+ "filename, content) VALUES (oitem_seq.nextval,?,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, item.getMem_num());
			pstmt.setInt(2, item.getCate_num());
			pstmt.setString(3, item.getTitle());
			pstmt.setInt(4, item.getPrice());
			pstmt.setString(5, item.getFilename());
			pstmt.setString(6, item.getContent());
			
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//판매상품 글 수정(update)
	   public void updateItem(OItemVO item)throws Exception{
		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 String sql = null;
		 String sub_sql = "";
		 int cnt = 0;
		  
		 try {
			 //커넥션풀로부터 커넥션 할당
			 conn = DBUtil.getConnection();
			 
			 if(item.getFilename() != null) {
				 sub_sql = ",filename=?";
			 }
			 sql = "UPDATE oitem SET cate_num=?, title=?, price=?, content=?, modify_date=SYSDATE" 
				     + sub_sql + " WHERE item_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, item.getCate_num());
			pstmt.setString(++cnt, item.getTitle());
			pstmt.setInt(++cnt, item.getPrice());
			pstmt.setString(++cnt, item.getContent());
			 if(item.getFilename()!=null) {
				pstmt.setString(++cnt, item.getFilename());
			 }
			pstmt.setInt(++cnt, item.getItem_num());
			 
			 //SQL문 실행
			 pstmt.executeUpdate();
			 
		 }catch(Exception e) {
			 throw new Exception(e);
		 }finally {
			 //자원정리
			 DBUtil.executeClose(null, pstmt, conn);
		 }
	 }	
	   
	   //파일삭제(deletefile)
		public void deleteFile(int item_num)throws Exception{
			Connection conn = null;
			 PreparedStatement pstmt = null;
			 String sql = null;
			 
			try {
				 //커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE oitem SET filename ='' WHERE item_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				
				pstmt.setInt(1, item_num);
				
				//SQL문 실행
				pstmt.executeUpdate();			 
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		
		//판매 상품 삭제(delete)
		 public void deleteItem(int item_num)throws Exception{
			 Connection conn = null;
			 PreparedStatement pstmt = null;
			 //PreparedStatement pstmt2 = null; (reply)
			 String sql = null;
			 
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//오토커밋 해제
				conn.setAutoCommit(false);
				 
	/*			//댓글 삭제
				sql = "DELETE FROM oitem_reply WHERE item_num=?";
				//PreparedStatement 객체 생성
				 pstmt = conn.prepareStatement(sql);
				 //?에 데이터 바인딩
				 pstmt.setInt(1, item_num); 
				 //SQL문 실행
	*/			
				//부모글 삭제 
				sql = "DELETE FROM oitem WHERE item_num=?";
				//PrepardStatement 객체 생성
				 pstmt = conn.prepareStatement(sql); //댓글 삭제 주석풀면 pstmt2로 변경
				 //?에 데이터 바인딩
				 pstmt.setInt(1, item_num);
				 pstmt.executeUpdate();
				 
				 //정상적으로 모든 SQL문을 실행
				 conn.commit();
			 }catch(Exception e) {
				 //SQL문이 하나라도 실패하면
				 conn.rollback();
				 throw new Exception(e);
			 }finally {
				 //자원정리
				 DBUtil.executeClose(null, pstmt, conn);
				 //DBUtil.executeClose(null, pstmt2, conn);
				 //DBUtil.executeClose(null, pstmt, null);
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

	//민정
	//총 레코드 수 (검색 레코드 수) 
		//키워드를 통해서 검색을 할지 총갯수를 통해서 검색을 할지
		public int getListItemCount(String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				//커넥션 풀로부터 커넥션 할당받기
				conn = DBUtil.getConnection();
				
				//keyword를 통한 조건체크
				if(keyword!=null && !"".equals(keyword)) {
					//검색글 처리
					if(keyfield.equals("1")) sub_sql = "WHERE i.title LIKE ? OR i.content LIKE ?"; //글제목+내용
//					if(keyfield.equals("1")) sub_sql = "WHERE i.title LIKE ?"; //글제목
//					else if(keyfield.equals("2")) sub_sql = "WHERE m.id LIKE ?"; //
//					else if(keyfield.equals("3")) sub_sql = "WHERE b.content LIKE ?";
				}
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM oitem i JOIN omember m USING(mem_num) " + sub_sql;
//				sql = "SELECT COUNT(*) FROM oitem i RIGHT OUTER JOIN omember m USING(mem_num) " + sub_sql;
				
				//PreparedStatement객체 생성
				pstmt = conn.prepareStatement(sql);
				
				if(keyword!= null && !"".equals(keyword)) {
					pstmt.setString(1, "%"+keyword+"%");
					pstmt.setString(2, "%"+keyword+"%");
				}
				//SQL문을 실행하고 결과행을 resultSet에 담음
				rs = pstmt.executeQuery();
				if (rs.next()) {
					count = rs.getInt(1);
				}
				
			} catch (Exception e) {
				throw new Exception();
				
			} finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return count;
		}
		
		
	//민정 판매목록
	//글 목록
		public List<OItemVO> getListItem(int startRow, int endRow, String keyfield, String keyword) throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<OItemVO> list = null;
			String sql = null;
			String sub_sql = "";
			int cnt = 0;
			
			try {
				//커넥션 풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				//위와 동일한 검색에 대한 처리
				//keyword를 통한 조건체크
				if(keyword!=null && !"".equals(keyword)) {
					if(keyfield.equals("1")) sub_sql = "WHERE i.title LIKE ? OR i.content LIKE ?"; //글제목+내용
//					if(keyfield.equals("1")) sub_sql = "WHERE b.title LIKE ?";
//					else if(keyfield.equals("2")) sub_sql = "WHERE m.id LIKE ?";
//					else if(keyfield.equals("3")) sub_sql = "WHERE b.content LIKE ?";
				}
				
				//SQL문 작성
				sql = "SELECT * FROM(SELECT a.*, rownum rnum FROM "
						+ "(SELECT * FROM oitem i JOIN omember m USING(mem_num) "
						+ sub_sql + "ORDER BY i.state DESC)a) "
						+ "WHERE rnum >= ? AND rnum <= ?";
				
				//PreparedStatement객체 생성
				pstmt = conn.prepareStatement(sql);
				//조건에 따라 ? 가 생기므로 조건체크
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%"+keyword+"%");
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
				pstmt.setInt(++cnt, startRow);
				pstmt.setInt(++cnt, endRow);
				
				//SQL문을 실행해서 결과행들을 ResultSet에 담음
				rs = pstmt.executeQuery();
				list = new ArrayList<OItemVO>();
				while(rs.next()) { //다명시하지않고 표시할 것만 넣겠음
					OItemVO item = new OItemVO();
					item.setItem_num(rs.getInt("item_num")); //상품번호
					item.setTitle(StringUtil.useNoHtml(rs.getString("title")));//HTML태그를 허용하지 않음
					item.setState(rs.getInt("state")); //판매상태(0판매중/1예약중/2판매완료) (default 0)
					item.setPrice(rs.getInt("price")); //상품가격
					item.setReg_date(rs.getDate("reg_date")); //상품등록일
					//board.setMem_num(rs.getInt("mem_num")); 
					//item.setHit(rs.getInt("hit"));
					
					//BoardVO 를 ArrayList에 저장
					list.add(item);
				}
				
			} catch (Exception e) {
				throw new Exception(e);
				
			} finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}

	//다원
	//상품Detail 부분(1) 조회수 증가메서드
	public void updateReadcount(int item_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
			
		try {
			//1. 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//2. SQL문 작성
			sql = "UPDATE OITEM SET hit=hit+1 WHERE item_num=?";
			
			//3. pstmt객체 생성
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터바인딩
			pstmt.setInt(1, item_num);
			//5. SQL문 실행
			pstmt.executeUpdate();			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}	
	
	//다원
	//상품Detail부분(2) 글상세 메서드 
	public OItemVO getItem(int item_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OItemVO item = null;
		String sql = null;
		
		try {
			//1. 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//2. SQL문 작성			
			sql = "SELECT * FROM OITEM i JOIN OMEMBER m ON i.mem_num=m.mem_num WHERE i.item_num=?";
			
			//3. PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터바인딩
			pstmt.setInt(1, item_num);
			
			//5. SQL문을 실행해서 결과행을 ResultSet에 담음
			rs = pstmt.executeQuery();	
			if(rs.next()) {
				item = new OItemVO();
				item.setMem_num(rs.getInt("mem_num"));//넣었음!!!!!!!!
				item.setItem_num(rs.getInt("item_num"));		//상품번호
				item.setMem_id(rs.getString("mem_id"));			//회원아이디
				item.setHit(rs.getInt("hit"));					//조회수
				item.setReg_date(rs.getDate("reg_date"));		//등록일	
				item.setCate_num(rs.getInt("cate_num"));		//카테고리
				item.setState(rs.getInt("state"));				//판매상태
				item.setTitle(rs.getString("title"));			//판매글 제목
				item.setContent(rs.getString("content"));		//판매글 내용
				item.setPrice(rs.getInt("price"));				//가격
				item.setFilename(rs.getString("filename"));		//파일명
				
				/*
				MultipartRequest multi = FileUtil.createFile(request);
				OItemVO item = new OItemVO();
				item.setCate_num(Integer.parseInt(multi.getParameter("cate")));
				item.setMem_num(user_num);   //회원번호
				item.setTitle(multi.getParameter("title"));
				item.setPrice(Integer.parseInt(multi.getParameter("price")));
				item.setFilename(multi.getFilesystemName("filename"));
				item.setContent(multi.getParameter("content"));
				*/
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return item;
	}	
	
	//[ 댓글메서드1. 댓글등록 : insertReplyItem() ]
	public void insertReplyItem(OItemReplyVO itemReply)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//1. 커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			//2. sql문 작성			
			sql = "INSERT INTO oitem_reply(re_num, item_num, mem_num,re_content,re_ip)"
					+ "VALUES(oreply_seq.nextval, ?,?,?,?)";
			//3. pstmt객체 생성
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터바인딩
			pstmt.setInt(1, itemReply.getItem_num());
			pstmt.setInt(2, itemReply.getMem_num());
			pstmt.setString(3, itemReply.getRe_content());
			pstmt.setString(4, itemReply.getRe_ip());

			//5. sql문 실행
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//[ 댓글 메서드2. 댓글 갯수 : getReplyItemCount() ]
	public int getReplyItemCount(int item_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//1. ConnectionPool로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//2. SQL문 작성
			sql = "SELECT COUNT(*) FROM OITEM_REPLY i JOIN OMEMBER o USING(mem_num)"
					+ " WHERE i.item_num=?";
			//3. PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터바인딩
			pstmt.setInt(1, item_num);
			//5. sql문을 실행해서 결과행을 ResultSet에 담음
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
	
	//[ 댓글 메서드3. 댓글 목록 : getListReplyItem() ]
	public List<OItemReplyVO> getListReplyItem(int startRow, int endRow, int item_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<OItemReplyVO> list = null;
		String sql = null;
		
		try {
			//1. ConnectionPool로부터 커넥션할당
			conn = DBUtil.getConnection();
			//2. sql문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT r.re_num, TO_CHAR(r.re_date,'YYYY-MM-DD HH24:MI:SS') re_date,"
					+ "TO_CHAR(r.re_modifydate,'YYYY-MM-DD HH24:MI:SS') re_modifydate,"
					+ "r.re_content,r.item_num,mem_num,m.mem_id FROM oitem_reply r "	
										//mem_num은 USING을 쓸거기때문에 테이블알리아스(b.mem_num)을 쓰면 안됨 / ON을 쓸 때는 알리아스 넣어줘야함
					+ "JOIN omember m USING(mem_num) WHERE r.item_num=? "
					+ "ORDER BY r.re_num DESC)a) "
					+ "WHERE rnum>=? AND rnum<= ?";
			//3. psmt객체 생성
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터바인딩
			pstmt.setInt(1, item_num);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			//5. sql문장을 수행해서 결과행들을 ResultSet에 담음
			rs = pstmt.executeQuery();
			list = new ArrayList<OItemReplyVO>();
			while(rs.next()) {
				OItemReplyVO reply = new OItemReplyVO();
				reply.setRe_num(rs.getInt("re_num"));
				
				//날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
				reply.setRe_date(DurationFromNow.getTimeDiffLabel(rs.getString("re_date")));
				if(rs.getString("re_modifydate")!=null) {	//수정날짜가 null이 아닐경우 변환작업 시작
					reply.setRe_modifydate(DurationFromNow.getTimeDiffLabel(rs.getString("re_modifydate")));
				}
				reply.setRe_content(StringUtil.useBrNoHtml(rs.getString("re_content")));	//댓글내용에 html태그인정하지 않음
				reply.setItem_num(rs.getInt("item_num"));
				reply.setMem_num(rs.getInt("mem_num"));
				reply.setId(rs.getString("mem_id"));
				
				list.add(reply);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return list;
	}	
	
	//[댓글 메서드4. 댓글 상세 : getReplyItem() ]
	public OItemReplyVO getReplyItem(int re_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OItemReplyVO reply = null;
		String sql = null;
		
		try {
			//1. 커넥션풀에서 Connection 할당
			conn = DBUtil.getConnection();
			//2. SQL문 작성
			sql = "SELECT * FROM OItem_reply r JOIN OMEMBER USING(mem_num) WHERE re_num=?";
			//3. pstmt객체 생성
			pstmt = conn.prepareStatement(sql);
			//4. ?에 데이터 바인딩
			pstmt.setInt(1, re_num);
			//5. sql문을 실행해서 결과해서 ResultSet에 데이터 담기
			rs = pstmt.executeQuery();
			if(rs.next()) {
				reply = new OItemReplyVO();
				reply.setRe_num(rs.getInt("re_num"));
				reply.setItem_num(rs.getInt("item_num"));
				reply.setMem_num(rs.getInt("mem_num"));
				reply.setId(rs.getString("mem_id"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return reply;
	}	
	//[댓글 메서드5. 댓글 수정 : updateReplyItem() ]

	//[댓글 메서드6. 댓글 삭제 : deleteReplyItem() ]
	
	
	
	
}

