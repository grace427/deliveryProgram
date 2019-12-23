package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.userPostCodeVO;

public class userPostCodeDAO {

	// 주소 불러오기
	public ArrayList<userPostCodeVO> getPostCode() {
		ArrayList<userPostCodeVO> list = new ArrayList<userPostCodeVO>();
		String dml = "select * from postcodetbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		userPostCodeVO upcVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				upcVO = new userPostCodeVO(rs.getString(1), rs.getString(2));
				list.add(upcVO);
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
	
	//주소 검색
//	public ArrayList<userPostCodeVO> getPostCodeSearch(String memAddress) throws Exception {
//		ArrayList<userPostCodeVO> list = new ArrayList<userPostCodeVO>();
//		String dml = "select * from postcodetbl where memAddress like?";
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		userPostCodeVO retval = null;
//		try {
//			con = userDBUtil.getConnection();
//			pstmt = con.prepareStatement(dml);
//			pstmt.setString(1, "%"+memAddress+"%");
//			rs = pstmt.executeQuery();
//			
//			while (rs.next()) {
//				retval = new userPostCodeVO(rs.getString(1));
//				list.add(retval);
//			}
//		} catch (SQLException se) {
//			System.out.println(se);
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			try {
//				if (rs != null)
//					rs.close();
//				if (pstmt != null)
//					pstmt.close();
//				if (con != null)
//					con.close();
//			} catch (SQLException se) {
//			}
//		}
//		return list;
//	}

	public userPostCodeVO getPostCodeSearch(String memAddress) throws Exception {
//	ArrayList<userPostCodeVO> list = new ArrayList<userPostCodeVO>();
		userPostCodeVO list=new userPostCodeVO();
	String dml = "select * from postcodetbl where memAddress like?";

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	userPostCodeVO retval = null;
	try {
		con = DBUtil.getConnection();
		pstmt = con.prepareStatement(dml);
		pstmt.setString(1, "%"+memAddress+"%");
		rs = pstmt.executeQuery();
		
		while (rs.next()) {
			retval = new userPostCodeVO(rs.getString(1));
//			list.add(retval);
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

}
