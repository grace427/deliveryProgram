package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.userMemberVO;

public class userMemberDAO {

	//insert
	public int getMemberRegister(userMemberVO umvo) {

		String dml = "insert into memberTBL " + "(memID, memName, memPhone, postCode, memAddress, memPW, memJoinDate)"
				+ " values " + "(null, ?, ?, ?, ?, ?, now())";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 회원정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);			
			pstmt.setString(1, umvo.getMemName());
			pstmt.setString(2, umvo.getMemPhone());
			pstmt.setString(3, umvo.getPostCode());
			pstmt.setString(4, umvo.getMemAddress());
			pstmt.setString(5, umvo.getMemPW());

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close(); // 자원반납
				if (con != null)
					con.close(); // 자원반납
			} catch (SQLException e) {
			}
		}
		return count;
	}
	
	//update
	// 수정 기능(update table명 set 필드명 = 수정 내용 where 조건)   
	   public userMemberVO getMemberUpdate(userMemberVO umvo, int no) throws Exception {
	   
	      // ② 데이터 처리를 위한 SQL 문
		   String dml = "update memberTBL set " + " memName=?, memPhone=?, memAddress=?, postCode=?, memPW=?, memJoinDate=?, where memID=?";

		   Connection con = null;
	      PreparedStatement pstmt = null;
	            
	      try {
	         System.out.println("업데이트 에러2");
	         // ③ DBUtil이라는 클래스의 getConnection( )메서드로 데이터베이스와 연결
	          con = DBUtil.getConnection(); 

	          // ④ 수정한 학생 정보를 수정하기 위하여 SQL문장을 생성
	          pstmt = con.prepareStatement(dml);
	          pstmt.setString(1, umvo.getMemName());
	          pstmt.setString(2, umvo.getMemPhone());
	          pstmt.setString(3, umvo.getMemAddress());
	          pstmt.setString(4, umvo.getPostCode());
	          pstmt.setString(5, umvo.getMemPW());
	          pstmt.setDate(6, umvo.getMemJoinDate());      
	          pstmt.setInt(7, umvo.getMemID());
	          // ⑤ SQL문을 수행후 처리 결과를 얻어옴
	          System.out.println("업데이트 에러2");
	          int i = pstmt.executeUpdate();

	          if (i == 1) {
	             userMainController.alertDisplay(1, "수정 완료", umvo.getMemID()+"수정되었습니다", "수정 성공");
	              } else {
	            	  userMainController.alertDisplay(1, "수정 실패", umvo.getMemID()+"수정이 되지 않았습니다", "수정 실패!!!!!!!");
	                 return null;
	              }
	            } catch (SQLException e) {
	               System.out.println("e=[" + e + "]");
	            } catch (Exception e) {
	               System.out.println("e=[" + e + "]");
	            } finally {
	               try {
	                  // ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
	                  if (pstmt != null)
	                     pstmt.close();
	                  if (con != null)
	                     con.close();
	               } catch (SQLException e) {
	               }
	            }
	            return umvo;
	         } // end of getMemberUpdate
	   

	
	public userMemberVO getMemberCheck(String memPhone) throws Exception {
		userMemberVO list = new userMemberVO();
		String dml = "select * from membertbl where memPhone = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//userMemberVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1,memPhone);
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				list = new userMemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				System.out.println("메머아이디 찾음 지금 이거는 마이페이지 회원정보 불러올라고");
//				list.add(retval);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}

	
	
//	// 회원관리 -검색 : 회원번호 // 픽업예약건수, 수령 건수 계산해서 출력하기
//		public ArrayList<userMemberVO> getMemberIDCheck(String memID) throws Exception {
//			 ArrayList<userMemberVO> list = new ArrayList<userMemberVO>();
//			 String dml = "select * from memberTBL where meberID like?";
//			 
//			 Connection con = null;
//		     PreparedStatement pstmt = null;
//		     ResultSet rs = null;
//		     userMemberVO memberVO = null;
//
//		     try {
//		        con = userDBUtil.getConnection();
//		        pstmt = con.prepareStatement(dml);
//		        pstmt.setString(1, "%"+memID+"%");
//		        rs = pstmt.executeQuery();
//		        while (rs.next()) {
//		        	memberVO = new userMemberVO(rs.getInt(1), rs.getString(2), rs.getString(3),
//		        		  	rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)); 
//		          list.add(memberVO);
//		         }
//		      } catch (SQLException se) {
//		         System.out.println(se);
//		      } catch (Exception e) {
//		         System.out.println(e);
//		      } finally {
//		         try {
//		            if (rs != null)
//		               rs.close();
//		            if (pstmt != null)
//		               pstmt.close();
//		            if (con != null)
//		               con.close();
//		         } catch (SQLException se) {
//		         }
//		      }
//		      return list;	
//		}  // end of getMemberIDCheck
	

	//전체리스스
	public ArrayList<userMemberVO> getMemberTotal() {
		ArrayList<userMemberVO> list = new ArrayList<userMemberVO>();
		String dml = "select * from memberTBL";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		userMemberVO memberVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memberVO = new userMemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getDate(7));
				list.add(memberVO);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}

		System.out.println(list+"회원테이블");
		return list;


	}
	


}
