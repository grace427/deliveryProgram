package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.InboundVO;
import model.OutboundVO;

public class DeliveryDAO {

	// ������Ʈ by cost
	public ArrayList<OutboundVO> getLineChart() throws Exception {
		ArrayList<OutboundVO> list = new ArrayList<OutboundVO>();
		String dml = "select bookDate, sum(cost) as sumCost from outboundTBL group by bookDate";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OutboundVO retval = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			// pstmt.setString(1, "%"+cbMonth+"%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new OutboundVO(rs.getDate(1), rs.getInt(2));
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

	}

	// ����Ʈ by ����, ��/����
	public int getBarChart(String city, String payMethod) throws Exception {
		String dml = "select count(*) as cityCount from outboundTBL where receiverAddress like ?" + "and payMethod=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, "%" + city + "%");
			pstmt.setString(2, payMethod);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}

	// ���� ��Ʈ by ǰ��
	public int getPieChart(String pkgType) throws Exception {
		String dml = "select count(*) as pkgCount from outboundTBL where pkgType = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, pkgType);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
	
	// ���� ��ư Ŭ�� �̺�Ʈ
	public OutboundVO getApprovalUpdate(OutboundVO ovo, int no) throws Exception {
		String dml = "update outboundtbl set " + " approval=?  where trkNum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, "����");
			pstmt.setInt(2, ovo.getTrkNum());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Utility.alertDisplay(1, "���� �Ϸ�", "���� �Ϸ� �Ǿ����ϴ�.", "Tracking No. " + ovo.getTrkNum() + " ���� �Ϸ�");
			} else {
				Utility.alertDisplay(1, "���� ����", "���ο� �����߽��ϴ�.", "Tracking no. " + ovo.getTrkNum() + "���� ����");
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
		return ovo;
	}

	// �Ⱦ� ����� �� ���� ������ΰ� ã��(approval�� ����Ʈ(0))
	public ArrayList<OutboundVO> getPickupPkgCheck() throws Exception {
		ArrayList<OutboundVO> list = new ArrayList<OutboundVO>();
		String dml = "select * from outboundTBL where approval=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		OutboundVO retval = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, "�̽���");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new OutboundVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10),
						rs.getTime(11), rs.getString(12), rs.getInt(13), rs.getString(14));
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

	}

	// inbound���� �����ȣ �˻�
	public InboundVO getInboundPkgCheck(int trkNum) throws Exception {
		InboundVO list = null;
		String dml = "select * from inboundTBL where trkNum = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, trkNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list = new InboundVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
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
	} // end of getInboundPkgCheck

	// outbound���� �����ȣ �˻�
	public OutboundVO getOutboundPkgCheck(int trkNum) throws Exception {
		OutboundVO list = null;
		String dml = "select * from outboundTBL where trkNum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, trkNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list = new OutboundVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10),
						rs.getTime(11), rs.getString(12), rs.getInt(13), rs.getString(14));
				// list.add(retval);
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
	} // end of getOutboundPkgCheck

	// inbound ��ü
	public ArrayList<InboundVO> getInboundTotal() {
		ArrayList<InboundVO> list = new ArrayList<InboundVO>();
		String dml = "select * from inboundtbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // ResultSet�̶�, �����ͺ��̽� ���� �ӽ÷� �����ϴ� ��Ҹ� �����ϴ� ��ü
		InboundVO inboundVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // sql���� select�� ����. executeQuerty�� �ݵ�� ���ϰ��� �޾ƾ� �Ѵ�
			while (rs.next()) { // sql ���̺��� �� �׸��� �ϳ��� �����´�
				inboundVO = new InboundVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
				list.add(inboundVO); // arraylist�� �� �پ� �߰��Ѵ�
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
	}// end of getInboundTotal

	// outbound ��ü
	public ArrayList<OutboundVO> getOutboundTotal() {
		ArrayList<OutboundVO> list = new ArrayList<OutboundVO>();
		String dml = "select * from outboundtbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		OutboundVO outboundVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); 
			while (rs.next()) { 
				outboundVO = new OutboundVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10),
						rs.getTime(11), rs.getString(12), rs.getInt(13), rs.getString(14));
				list.add(outboundVO); 
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
	}// end of getOutboundTotal
	
	// �����ȸ�Ҷ� �����, ��߽ð�, ������(�ּ�) ���ϱ�
	public OutboundVO getOutboundTrackingCheck(int trkNum) throws Exception {
		OutboundVO list = null;
		String dml = "select * from outboundTBL where trkNum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, trkNum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list = new OutboundVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getDate(10),
						rs.getTime(11), rs.getString(12), rs.getInt(13), rs.getString(14));
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
	} // end of getOutboundTrackingCheck

}
