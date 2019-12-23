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
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� ȸ�������� ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);			
			pstmt.setString(1, umvo.getMemName());
			pstmt.setString(2, umvo.getMemPhone());
			pstmt.setString(3, umvo.getPostCode());
			pstmt.setString(4, umvo.getMemAddress());
			pstmt.setString(5, umvo.getMemPW());

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close(); // �ڿ��ݳ�
				if (con != null)
					con.close(); // �ڿ��ݳ�
			} catch (SQLException e) {
			}
		}
		return count;
	}
	
	//update
	// ���� ���(update table�� set �ʵ�� = ���� ���� where ����)   
	   public userMemberVO getMemberUpdate(userMemberVO umvo, int no) throws Exception {
	   
	      // �� ������ ó���� ���� SQL ��
		   String dml = "update memberTBL set " + " memName=?, memPhone=?, memAddress=?, postCode=?, memPW=?, memJoinDate=?, where memID=?";

		   Connection con = null;
	      PreparedStatement pstmt = null;
	            
	      try {
	         System.out.println("������Ʈ ����2");
	         // �� DBUtil�̶�� Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
	          con = DBUtil.getConnection(); 

	          // �� ������ �л� ������ �����ϱ� ���Ͽ� SQL������ ����
	          pstmt = con.prepareStatement(dml);
	          pstmt.setString(1, umvo.getMemName());
	          pstmt.setString(2, umvo.getMemPhone());
	          pstmt.setString(3, umvo.getMemAddress());
	          pstmt.setString(4, umvo.getPostCode());
	          pstmt.setString(5, umvo.getMemPW());
	          pstmt.setDate(6, umvo.getMemJoinDate());      
	          pstmt.setInt(7, umvo.getMemID());
	          // �� SQL���� ������ ó�� ����� ����
	          System.out.println("������Ʈ ����2");
	          int i = pstmt.executeUpdate();

	          if (i == 1) {
	             userMainController.alertDisplay(1, "���� �Ϸ�", umvo.getMemID()+"�����Ǿ����ϴ�", "���� ����");
	              } else {
	            	  userMainController.alertDisplay(1, "���� ����", umvo.getMemID()+"������ ���� �ʾҽ��ϴ�", "���� ����!!!!!!!");
	                 return null;
	              }
	            } catch (SQLException e) {
	               System.out.println("e=[" + e + "]");
	            } catch (Exception e) {
	               System.out.println("e=[" + e + "]");
	            } finally {
	               try {
	                  // �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
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
				System.out.println("�޸Ӿ��̵� ã�� ���� �̰Ŵ� ���������� ȸ������ �ҷ��ö��");
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

	
	
//	// ȸ������ -�˻� : ȸ����ȣ // �Ⱦ�����Ǽ�, ���� �Ǽ� ����ؼ� ����ϱ�
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
	

	//��ü������
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

		System.out.println(list+"ȸ�����̺�");
		return list;


	}
	


}
