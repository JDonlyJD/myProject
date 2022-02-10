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
	public static OMemberDAO getnInstance() {
		return instance;
	}
	private OMemberDAO() {}	
	
	// 찬미
	// [메서드1. 회원가입 : inseretMember() : 로그인 + 아이디 중복체크]
	// [메서드2. ID중복체크 및 로그인 처리 :  checkMember() : 로그인 + 아이디 중복체크]
	
	
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
				member.setMem_id(rs.getString("mem_id"));			//id
				member.setMem_auth(rs.getInt("mem_auth"));			//등급
				member.setMem_nick(rs.getString("mem_nick"));		//닉네임
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
	
	// 민정
	// [메서드7. 비밀번호 수정 : ]
	// [메서드8. 프로필사진 수정 : ]
	
	// 진주
	// [메서드9. 회원탈퇴(회원정보 삭제) : ]
		
	
	
	// [관리자-메서드8. 총 회원 수 : ]
	// [관리자-메서드9. 회원 목록 : ]
	// [관리자-메서드10. 회원정보 수정 : ]
	// [관리자-메서드11. 회원정보 삭제 : ]	
}
