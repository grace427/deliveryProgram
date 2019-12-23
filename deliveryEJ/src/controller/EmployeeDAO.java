package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.EmployeeVO;

public class EmployeeDAO {
	

	// 직원 등록
	public int getEmployeeregister(EmployeeVO evo) throws Exception {
		String dml = "insert into employeetbl "
				+ "(empID, empName, empPhone, empCity, empImage, empHireDate)" + " values "
				+ "(null, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml); 
			pstmt.setString(1, evo.getEmpName());
			pstmt.setString(2, evo.getEmpPhone());
			pstmt.setString(3, evo.getEmpCity());
			pstmt.setString(4, evo.getEmpImage());
			pstmt.setString(5, evo.getEmpHireDate());
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try { 
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// 직원 삭제
	public void getEmployeeDelete(int no) throws Exception {
		String dml = "delete from employeetbl where empID= ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);
			int i = pstmt.executeUpdate();
			if (i == 1) {
				Utility.alertDisplay(5, "직원정보 삭제", "직원 삭제 완료", "직원 삭제 성공");
			} else {
				Utility.alertDisplay(1, "직원정보 삭제", "직원 삭제 미완료", "직원 삭제 실패");
			}
		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {

			}
		}
	}


	
		// 데이터베이스에서 직원정보 가져오기
		public ArrayList<EmployeeVO> getEmployeeTotal() {
			ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();
			String dml = "select * from employeeTBL";

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null; 
			EmployeeVO employeeVO = null;

			try {
				con = DBUtil.getConnection();
				pstmt = con.prepareStatement(dml);
				rs = pstmt.executeQuery(); 
				while (rs.next()) { 
					employeeVO = new EmployeeVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5), rs.getString(6));
					list.add(employeeVO); 
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
		}// end of getEmployeeTotal
		
		// 직원관리 -검색 : 직원번호
		public EmployeeVO getEmployeeIDCheck(int empID) throws Exception {
			 EmployeeVO list = new EmployeeVO();
			 String dml = "select * from employeeTBL where empID=?";
			 
			 Connection con = null;
		     PreparedStatement pstmt = null;
		     ResultSet rs = null;
		 	
		     try {
		        con = DBUtil.getConnection();
		        pstmt = con.prepareStatement(dml);
		        pstmt.setInt(1, empID);
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		          list = new EmployeeVO(rs.getInt(1), rs.getString(2), rs.getString(3),
		        		  	rs.getString(4), rs.getString(5), rs.getString(6)); 
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
		}  // end of getEmployeeIDCheck
		
		// 직원관리 -검색 : 이름
		public ArrayList<EmployeeVO> getEmployeeNameCheck(String empName) throws Exception {
			 ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();
			 String dml = "select * from EmployeeTBL where empName like ?";
			 
			 Connection con = null;
		     PreparedStatement pstmt = null;
		     ResultSet rs = null;
		     EmployeeVO retval = null;

		     try {
		        con = DBUtil.getConnection();
		        pstmt = con.prepareStatement(dml);
		        pstmt.setString(1, "%"+empName+"%");
		        rs = pstmt.executeQuery();
		        while (rs.next()) {
		          retval = new EmployeeVO(rs.getInt(1), rs.getString(2), rs.getString(3),
		        		  	rs.getString(4), rs.getString(5), rs.getString(6));
		          list.add(retval);
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
		} // end of getEmployeeNameCheck
}
