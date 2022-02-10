package kr.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.member.OMemberDAO;
import kr.member.OMemberVO;
import kr.util.DBUtil;

public class OMemberDAO {

	//싱글턴 패턴
	private static OMemberDAO instance = new OMemberDAO();
	public static OMemberDAO getInstance() {
		return instance;
	}
	private OMemberDAO() {}	
	
		// 찬미
		// [메서드1. 회원가입 : inseretMember() : 로그인 + 아이디 중복체크]
		public void insertMember(OMemberVO member)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			ResultSet rs = null;
			String sql = null;
			int num = 0; //시퀀스 번호 저장
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//★ 오토 커밋 해제(sql문이 2개 이상일때)
				conn.setAutoCommit(false);
				
				//회원번호(mem_num) 생성
				sql = "SELECT omember_seq.nextval FROM dual"; //동일한 시퀀스를 구하기 위해 별도의 sql문
				//PreparedStatement객체 생성
				pstmt = conn.prepareStatement(sql);
				//sql문을 실행시켜 결과행을 rs담음
				rs = pstmt.executeQuery();
				if(rs.next()) {
					num = rs.getInt(1);
				}
				
				//omember테이블에 데이터 저장(auth값은 디폴트값 1로 들어가므로 생략o)
				sql = "INSERT INTO omember (mem_num,mem_id) VALUES (?,?)";
				//PreparedStatement객체 생성
				pstmt2 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt2.setInt(1, num);
				pstmt2.setString(2, member.getMem_id());
				//sql문 실행
				pstmt2.executeUpdate();
				
				//omember_detail 테이블에 데이터 저장
				sql = "INSERT INTO omember_detail (mem_num,mem_id,mem_nick,mem_pw,mem_phone,mem_email,"
						+ "mem_zipcode,mem_addr,mem_addr2) VALUES (?,?,?,?,?,?,?,?,?)";
				//PreparedStatement객체 생성
				pstmt3 = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt3.setInt(1, num);
				pstmt3.setString(2, member.getMem_id());
				pstmt3.setString(3, member.getMem_nick());
				pstmt3.setString(4, member.getMem_pw());
				pstmt3.setString(5, member.getMem_phone());
				pstmt3.setString(6, member.getMem_email());
				pstmt3.setString(7, member.getMem_zipcode());
				pstmt3.setString(8, member.getMem_addr());
				pstmt3.setString(9, member.getMem_addr2());
				
				//sql문 실행
				pstmt3.executeUpdate();
				
				//★ sql문 실행시 모두 성공하면 commit
				conn.commit();
				
			}catch(Exception e) {
				//★ sql문이 하나라도 실패하면 롤백(rollback)작업
				conn.rollback();
				
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(null, pstmt3, null);
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(rs, pstmt, conn);
			}
		}
		
		// [메서드2. ID중복체크 및 로그인 처리 :  checkMember() : 로그인 + 아이디 중복체크]
		public OMemberVO checkMember(String mem_id)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			OMemberVO member = null;
			String sql = null;
			
			try {
				//1.커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				
				//2.sql문 작성
				//omember와 omember_detail테이블을 조인. omember의 누락된 데이터가 보여야
				//id중복 체크 가능
				sql = "SELECT * FROM omember m LEFT OUTER JOIN omember_detail d "
						+ "ON m.mem_num=d.mem_num WHERE m.mem_id=?";
				
				//3.PreparedStatement객체 생성
				pstmt = conn.prepareStatement(sql);
				
				//4.?에 데이터 바인딩
				pstmt.setString(1, member.getMem_id());
				
				//5.sql문을 실행시켜 결과행을 rs에 담아 반환
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					member = new OMemberVO();
					
					member.setMem_num(rs.getInt("mem_num"));
					member.setMem_id(rs.getString("mem_id"));
					member.setMem_nick(rs.getString("mem_nick")); //nick = name이름
					member.setMem_auth(rs.getInt("mem_auth"));
					member.setMem_pw(rs.getString("mem_pw"));
					member.setMem_photo(rs.getString("mem_photo"));
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				//자원정리
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return member;
		}
	
	// 다원
	// [메서드3. ID찾기 : findIdMember()]
	/*
	public OmemberVO findIdMember(String Input_memid, String Input_memnick, String Input_mememail)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OMemberVO member = null;
		String sql = null;
		
		try {
			//1. 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//2. SQL문 작성 (테이블 2개를 JOIN해서 데이터를 읽어와야한다.)
			sql = "SELECT * FROM omember m JOIN omember_detail d ON m.mem_num=d.mem_num"
					+ " WHERE m.mem_num=?";
			//3. pstmt객체생성 + ?에 데이터바인딩
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			//4. SQL문을 실행해서 결과행을 ResultSet에 담음 + VO객체 생성 후 rs에 담긴 데이터를 VO에 넣어주기
			rs = pstmt.executeQuery();
			if(rs.next()) {	//mem_num의 행은 1개 => if
				member = new OMemberVO();
				member.setMem_num(rs.getInt("mem_num"));			//회원번호
				member.setMem_id(rs.getString("mem_id"));			//id
				member.setMem_auth(rs.getInt("mem_auth"));			//등급
				member.setMem_nick(rs.getString("mem_nick"));		//아이디
				member.setMem_pw(rs.getString("mem_pw"));			//비밀번호
				member.setMem_phone(rs.getString("mem_phone"));		//연락처
				member.setMem_photo(rs.getString("mem_photo"));		//사진파일명
				member.setMem_addr(rs.getString("mem_addr"));		//주소
				member.setMem_addr2(rs.getString("mem_addr2"));		//나머지주소
				member.setMem_zipcode(rs.getString("mem_zipcode"));	//우편번호
				member.setMem_email(rs.getString("mem_email"));		//이메일
				member.setMem_date(rs.getDate("mem_date"));			//가입일
				member.setMem_modifydate(rs.getDate("mem_modifydate"));	//변경일
			}		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return member;
	}
		
		
		
	public boolean findIdMember(String Input_memnick, String Input_memphone) {
		if(mem_nick.equals(Input_memnick) && mem_phone.equals(Input_memphone)) {
			return true;
				//**Q. String타입으로 return mem_id를 반환해줄까 ?
		}
		return false; 
	}
	*/
	// [메서드4. PW찾기 : findPwMember() ]
	

	// [메서드5. 회원 상세정보 : getMember() ]	
	public OMemberVO getMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OMemberVO member = null;
		String sql = null;
		
		try {
			//1. 커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//2. SQL문 작성 (테이블 2개를 JOIN해서 데이터를 읽어와야한다.)
			sql = "SELECT * FROM omember m JOIN omember_detail d ON m.mem_num=d.mem_num"
					+ " WHERE m.mem_num=?";
			//3. pstmt객체생성 + ?에 데이터바인딩
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			//4. SQL문을 실행해서 결과행을 ResultSet에 담음 + VO객체 생성 후 rs에 담긴 데이터를 VO에 넣어주기
			rs = pstmt.executeQuery();
			if(rs.next()) {	//mem_num의 행은 1개 => if
				member = new OMemberVO();
				member.setMem_num(rs.getInt("mem_num"));			//회원번호
				member.setMem_id(rs.getString("mem_id"));			//아이디
				member.setMem_auth(rs.getInt("mem_auth"));			//등급
				member.setMem_nick(rs.getString("mem_nick"));		//이름
				member.setMem_pw(rs.getString("mem_pw"));			//비밀번호
				member.setMem_phone(rs.getString("mem_phone"));		//연락처
				member.setMem_photo(rs.getString("mem_photo"));		//사진파일명
				member.setMem_addr(rs.getString("mem_addr"));		//주소
				member.setMem_addr2(rs.getString("mem_addr2"));		//나머지주소
				member.setMem_zipcode(rs.getString("mem_zipcode"));	//우편번호
				member.setMem_email(rs.getString("mem_email"));		//이메일
				member.setMem_date(rs.getDate("mem_date"));			//가입일
				member.setMem_modifydate(rs.getDate("mem_modifydate"));	//변경일
			}		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return member;
	}
	
	// 진주
	// [메서드6. 회원정보 수정 : updateMember()]
	public void updateMember(OMemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			sql = "UPDATE omember_detail SET mem_nick=?, mem_phone=?, mem_email=?,"
					+ "mem_zipcode=?, mem_addr=?, mem_addr2=? ,mem_modify_date=SYSDATE "
					+ "WHERE mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, member.getMem_nick());
			pstmt.setString(2, member.getMem_phone());
			pstmt.setString(3, member.getMem_email());
			pstmt.setString(4, member.getMem_zipcode());
			pstmt.setString(5, member.getMem_addr());
			pstmt.setString(6, member.getMem_addr2());
			pstmt.setInt(7, member.getMem_num());

			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	// 민정
	// [메서드7. 비밀번호 수정 : ]
	// [메서드8. 프로필사진 수정 : ]
	
	// 진주
	// [메서드9. 회원탈퇴(회원정보 삭제) : ]
	public void deleteMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;

		try {
			//conn 
			conn = DBUtil.getConnection();
			//auto commit 해제 
			conn.setAutoCommit(false);

			//omember의 auth 값 변경
			sql ="UPDATE omember SET mem_auth=0 WHERE mem_num=?";
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			//sql 실행
			pstmt.executeUpdate();

			//zmember_detail의 레코드 삭제
			sql = "DELETE FROM omember_detail WHERE mem_num=?";
			//pstmt 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setInt(1, mem_num);
			//sql 실행
			pstmt2.executeUpdate();

			//모든 SQL문의 실행이 성공하면 commit
			conn.commit();

		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, conn);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}		

	
	// [관리자-메서드8. 총 회원 수 : ]
	// [관리자-메서드9. 회원 목록 : ]
	// [관리자-메서드10. 회원정보 수정 : ]
	// [관리자-메서드11. 회원정보 삭제 : ]	
}
