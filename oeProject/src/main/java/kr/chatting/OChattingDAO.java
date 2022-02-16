package kr.chatting;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import kr.item.OItemDAO;
import kr.item.OItemVO;
import kr.member.OMemberVO;
import kr.util.DBUtil;
import kr.util.DurationFromNow;
import kr.util.StringUtil;

public class OChattingDAO {
  
   //싱글턴 패턴
   private static OChattingDAO instance = new OChattingDAO();
   
   public static OChattingDAO getInstance() {
      return instance;
   }
   private OChattingDAO() {}
   
   
   //------------------댓글(Reply)_DAO------------------
   
      //[ 댓글 메서드1. 댓글등록 : insertReplyItem() ]
      //[ 댓글 메서드2. 댓글 갯수 : getReplyItemCount() ]
      //[ 댓글 메서드3. 댓글 목록 : getListReplyItem() ]
      //[ 댓글 메서드4. 댓글 상세 : getReplyItem() ]
      //[ 댓글 메서드5. 댓글 수정 : updateReplyItem() ]   -- 필요없음   
      //[ 댓글 메서드6. 댓글 삭제 : deleteReplyItem() ]   -- 필요없음
   
   //--------------------------------------------------
   
   // [채팅메서드1. 채팅테이블에 채팅등록 INSERT : insertChat() ]
         public void insertChat(OChattingVO chat)throws Exception{   //인자 어떻게 들어갈지 생각해보기
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            String sql = null;

            try {
               //커넥션풀로부터 커넥션 할당
               conn = DBUtil.getConnection();
               
               //sql문 작성 : omember_detail 테이블에 데이터 저장
               sql = "INSERT INTO OChatting "
                     + "(chat_num,to_num,from_num,content,item_num) "
                     + "VALUES (ochatting_seq.nextval,?,?,?,?)";
               
               //PreparedStatement객체 생성
               pstmt = conn.prepareStatement(sql);
               
               //?에 데이터 바인딩
               pstmt.setInt(1, chat.getTo_num());         //to_num : 판매자 회원번호
               pstmt.setInt(2, chat.getFrom_num());      //from_num : 구매자 회원번호
               pstmt.setString(3, chat.getContent());      //content : 메시지내용
               pstmt.setInt(4, chat.getItem_num());      //item_num : 상품번호
               
               //sql문 실행
               pstmt.executeUpdate();
               
            }catch(Exception e) {   
               throw new Exception(e);
            }finally {
               DBUtil.executeClose(rs, pstmt, conn);
            }
         }
         
         
   // [채팅메서드2. 채팅메시지 갯수 : getCountChat() ]
   public int getCountChat(int item_num)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;
      int count = 0;
      
      try {
         //1. ConnectionPool로부터 커넥션 할당
         conn = DBUtil.getConnection();
         //2. SQL문 작성
         sql = "SELECT COUNT(*) FROM OChatting WHERE item_num=?";
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
   
   
   //[ 채팅메서드3. 채팅 목록 : getListChatItem() ]
   public List<OChattingVO> getListChatItem(int startRow, int endRow, int item_num)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<OChattingVO> list = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션할당
         conn = DBUtil.getConnection();
         //sql문 작성
         sql = "SELECT * FROM OCHATTING c JOIN omember m ON c.from_num=m.mem_num WHERE c.item_num=? ORDER BY c.chat_num";
         
         //pstmt객체 생성
         pstmt = conn.prepareStatement(sql);
         
         //?에 데이터 바인딩
         pstmt.setInt(1, item_num);
         
         //sql문을 실행해서 결과해서 ResultSet에 데이터 담기
         rs = pstmt.executeQuery();
         list = new ArrayList<OChattingVO>();
         while(rs.next()) {
            OChattingVO chat = new OChattingVO();
            chat.setChat_num(rs.getInt("chat_num"));
            
            //날짜 -> 1분전, 1시간전, 1일전 형식의 문자열로 변환
            chat.setReg_date(DurationFromNow.getTimeDiffLabel(rs.getString("reg_date")));
            chat.setContent(rs.getString("content"));
            chat.setItem_num(rs.getInt("item_num"));
            chat.setFrom_num(rs.getInt("from_num"));
            chat.setTo_num(rs.getInt("to_num"));
            chat.setMem_id(rs.getString("mem_id"));
            
            
            list.add(chat);
         }
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return list;
   }
   
   
   
}