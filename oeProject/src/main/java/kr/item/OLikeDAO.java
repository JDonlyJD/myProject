package kr.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;

public class OLikeDAO {
   private static OLikeDAO instance = new OLikeDAO();
   
   public static OLikeDAO getInstance() {
      return instance;
   }
   private OLikeDAO() {}
   
   
   
   
   //찜하기 등록 
   public void insertLike(OLikeVO like)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션 할당 
         conn = DBUtil.getConnection();
         //SQL문 작성
         sql = "INSERT INTO olike (like_num, item_num, mem_num) VALUES (olike_seq.nextval,?,?)";
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         pstmt.setInt(1, like.getItem_num());
         pstmt.setInt(2, like.getMem_num());
         //SQL문 실행
         pstmt.executeUpdate();
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리 
         DBUtil.executeClose(null, pstmt, conn);
      }
   }
   
   
   
   
   //찜하기 목록
   public List<OLikeVO> getListLike(int mem_num)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      List<OLikeVO> list = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션을 할당
         conn = DBUtil.getConnection();
         //SQL문 작성
         sql = "SELECT * FROM olike l JOIN oitem i "
            + "ON l.item_num = i.item_num WHERE l.mem_num = ? "
            + "ORDER BY i.item_num ASC";
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         pstmt.setInt(1, mem_num);
         //SQL문을 실행해서 결과행들을 ResultSet에 담음
         rs = pstmt.executeQuery();
         
         list = new ArrayList<OLikeVO>();
         while(rs.next()) {
            OLikeVO like = new OLikeVO();
            like.setLike_num(rs.getInt("like_num"));
            like.setItem_num(rs.getInt("item_num"));
            like.setMem_num(rs.getInt("mem_num"));
            
            OItemVO item = new OItemVO();
            item.setTitle(rs.getString("title"));
            item.setPrice(rs.getInt("price"));
            item.setFilename(rs.getString("filename"));
            item.setState(rs.getInt("state"));
            
            //OItemVO를 OLikeVO에 저장
            like.setItem(item);
            
            list.add(like);
         }
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return list;
   }
   
   
   
   
   
   //찜하기 상세
   public OLikeVO getLike(OLikeVO like)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      OLikeVO likeSaved = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션을 할당
         conn = DBUtil.getConnection();
         //SQL문 작성
         sql = "SELECT * FROM olike WHERE item_num=? AND mem_num=?";
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         pstmt.setInt(1, like.getItem_num());
         pstmt.setInt(2, like.getMem_num());
         //SQL문을 실행해서 결과행을 ResultSet에 담음
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            likeSaved  = new OLikeVO();
            likeSaved.setItem_num(rs.getInt("item_num"));
            likeSaved.setMem_num(rs.getInt("mem_num"));
         }
         
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
         DBUtil.executeClose(rs, pstmt, conn);
      }   
      return likeSaved;
   }
   
   
   
   
   //찜하기 삭제
   public void deleteLike(int like_num)throws Exception{
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      
      try {
         //커넥션풀로부터 커넥션 할당
         conn = DBUtil.getConnection();
         //SQL문 작성
         sql = "DELETE FROM olike WHERE like_num=?";
         //PreparedStatement 객체 생성
         pstmt = conn.prepareStatement(sql);
         //?에 데이터 바인딩
         pstmt.setInt(1, like_num);
         //SQL문 실행
         pstmt.executeUpdate();
      }catch(Exception e) {
         throw new Exception(e);
      }finally {
         //자원정리
    	  DBUtil.getConnection();
      }
   }   
}
   

