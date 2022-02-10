package kr.member;

import kr.member.OMemberDAO;

public class OMemberDAO {

	//싱글턴 패턴
	private static OMemberDAO instance = new OMemberDAO();
	public static OMemberDAO getnInstance() {
		return instance;
	}
	private OMemberDAO() {}	
	
	//1. 찬미
	// [메서드1. 회원가입 : inseretMember() : 로그인 + 아이디 중복체크]
	// [메서드2. ID중복체크 및 로그인 처리 :  checkMember() : 로그인 + 아이디 중복체크]
	
	//2. 다원
	// [메서드3. 회원 상세정보 : getMember() ]	
	// [메서드4. 회원정보 수정 : updateMember()]
	
	//3. 민정
	// [메서드5. 비밀번호 수정 : ]
	// [메서드6. 프로필사진 수정 : ]
	
	//4. 진주
	// [메서드7. 회원탈퇴(회원정보 삭제) : ]
		
	
	
	// [관리자-메서드8. 총 회원 수 : ]
	// [관리자-메서드9. 회원 목록 : ]
	// [관리자-메서드10. 회원정보 수정 : ]
	// [관리자-메서드11. 회원정보 삭제 : ]	
}
