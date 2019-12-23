package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.userDeliChkVO;

//inboundTBL

public class userDeliCheckDAO {

	public int getDeliveryChk(userDeliChkVO udcvo) {

		String dml = "insert into inboundTBL"
				+ "(trkNum, pkgType, weight, memID, sendName, sendAddress, sendPhone, postCode, startDate, startTime)"
				+ "values" + "(null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� ȸ�������� ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, udcvo.getPkgType());
			pstmt.setDouble(2, udcvo.getWeight());
			pstmt.setInt(3, udcvo.getMemID());
			pstmt.setString(4, udcvo.getSendName());
			pstmt.setString(5, udcvo.getSendAddress());
			pstmt.setString(6, udcvo.getSendPhone());
			pstmt.setString(7, udcvo.getPostCode());
			pstmt.setString(8, udcvo.getStartDate());
			pstmt.setString(9, udcvo.getStartTime());
		
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

		return 0;

	}
	
	public ArrayList<userDeliChkVO> getDeliveryTotal() {
		ArrayList<userDeliChkVO> list=new ArrayList<userDeliChkVO>();
		String dml="select * from inboundTBL";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		userDeliChkVO dcVO=null;
		
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				dcVO = new userDeliChkVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10));
				System.out.println("inbound ã�Ҵ�.");
				list.add(dcVO);
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
