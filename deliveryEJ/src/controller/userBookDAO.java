package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.userBookVO;

//outboundTBL

public class userBookDAO {

	public int getBook(userBookVO ubvo) {
		int memID = 0;
		try {
			memID = getMemCheck(ubvo.getReceiverPhone());
			System.out.println("*****ȸ�����̵�********"+memID+"***********");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String dml = "insert into outboundTBL"
				+ "( pkgType, weight, memID, receiverName, receiverAddress, postCode, receiverPhone, payMethod, bookDate, bookTime, bookSpot, cost, approval)"
				+ "values" + "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0.0, 0)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� ȸ�������� ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, ubvo.getPkgType());
			pstmt.setDouble(2, ubvo.getWeight());
			pstmt.setInt(3, memID);
			pstmt.setString(4, ubvo.getReceiverName());
			pstmt.setString(5, ubvo.getReceiverAddress());
			pstmt.setString(6, ubvo.getPostCode());
			pstmt.setString(7, ubvo.getReceiverPhone());
			pstmt.setString(8, ubvo.getPayMethod());
			pstmt.setString(9, ubvo.getBookDate());
			pstmt.setString(10, ubvo.getBookTime());
			pstmt.setString(11, ubvo.getBookSpot());
			

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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

	/*
	 * memberTBL�� ȸ����ȣ�� �����ͼ� �� ȸ����ȣ�� ����ó�� outboundTBL�� ����ó�� �����Ÿ� ã�´�.
	 */
	public int getMemCheck(String receiverPhone) throws Exception {
		int phone = 0;
		String dml = "select memID from membertbl where memPhone = ?";
		System.out.println("�� ��ȣ*************"+receiverPhone);
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		StudentVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, receiverPhone);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				phone = rs.getInt(1);
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
		System.out.println(phone+"w��ȭ�ä���");
		return phone;
	}

	public ArrayList<userBookVO> getBookTotal() {
		ArrayList<userBookVO> list = new ArrayList<userBookVO>();
		String dml = "select * from outboundTBL";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		userBookVO bookVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				bookVO = new userBookVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getString(11), rs.getString(12), rs.getDouble(13), rs.getInt(14));
				System.out.println("outbound ã�Ҵ�.");
				list.add(bookVO);
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
