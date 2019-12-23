package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.userMemberVO;

public class userFdPwDAO {
	public ArrayList<userMemberVO> getPassWord(String txtFdPwName, String txtFdPwPhone1, String txtFdPwPhone2,
			String txtFdPwPhone3) {
		ArrayList<userMemberVO> list = new ArrayList<userMemberVO>();
		String dml = "select * from memberTBL where memName=? and memPhone=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		userMemberVO umvo = null;

		try {
			con = DBUtil.getConnection();
			String name = txtFdPwName;
			String phone = txtFdPwPhone1 + txtFdPwPhone2 + txtFdPwPhone3;

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, name);
			pstmt.setString(2, phone);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				umvo = new userMemberVO(rs.getString(1), rs.getString(2));
				list.add(umvo);
				System.out.println("비밀번호 찾았다.");
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public userMemberVO getPassWordInfo(String memPhone) {
		userMemberVO list = new userMemberVO();
		String dml = "select  memName, memPW from memberTBL where memPhone=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		userMemberVO umvo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, memPhone);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list = new userMemberVO(rs.getString(1), rs.getString(2));
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
		System.out.println(list + "비이이이이이밀번호");
		return list;

	}
}
