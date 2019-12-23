package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.userMemberVO;

public class userLoginDAO {
	public ArrayList<userMemberVO> getLogin(String txtLoginPhone, String txtPw) {

		ArrayList<userMemberVO> list = new ArrayList<userMemberVO>();
		String dml = "select * from memberTBL where memPhone=? and memPW=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		userMemberVO umvo = null;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� ȸ�������� ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, txtLoginPhone);
			pstmt.setString(2, txtPw);

			// �� SQL���� ������ ó�� ����� ����
			rs = pstmt.executeQuery();

			while (rs.next()) {
				umvo = new userMemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				list.add(umvo);
				System.out.println("ã�Ҵ�.");
			}

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
		return list;
	}

}
