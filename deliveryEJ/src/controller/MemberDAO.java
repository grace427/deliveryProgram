package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import model.MemberVO;

public class MemberDAO {

	public ArrayList<String> getColumnName() {
		ArrayList<String> columnName = new ArrayList<String>();

		String sql = "select * from memberTBL";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			for (int i = 1; i <= cols; i++) {
				columnName.add(rsmd.getColumnName(i));
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
		return columnName;
	}

	// �����͸� �Է�(insert)
	public int getMemberRegiste(MemberVO mvo) throws Exception {
		String dml = "insert into memberTBL " + "(memID, memName, memPhone, memAddress, postCode, memPW, memJoinDate)"
				+ " values " + "(null, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, mvo.getMemName());
			pstmt.setString(2, mvo.getMemPhone());
			pstmt.setString(3, mvo.getMemAddress());
			pstmt.setString(4, mvo.getPostCode());
			pstmt.setString(5, mvo.getMemPW());
			pstmt.setString(6, mvo.getMemJoinDate());
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
	} // end of getMemberRegiste

	// �ּҷ� �����ȣ ã��
	public String getPostcode(String address) throws Exception {
		String dml = "select postCode from membertbl where memAddress like ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String postcode = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, "%" + address + "%");
			rs = pstmt.executeQuery();
			postcode = rs.getString(0);
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
		return postcode;
	}

	// ȸ������ -�˻� : ȸ����ȣ
	public MemberVO getMemberIDCheck(int memID) throws Exception {
		MemberVO list = new MemberVO();
		String dml = "select * from memberTBL where memID = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, memID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list = new MemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
			}

			if (list == null) {
				Utility.alertDisplay(1, "���� ����", "���ο� �����߽��ϴ�.", "ȸ����ȣ" + memID + "�� ã�� �� �����ϴ�.");
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
	} // end of getMemberIDCheck

	// ȸ������ -�˻� : �̸�
	public ArrayList<MemberVO> getMemberNameCheck(String memName) throws Exception {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		String dml = "select * from memberTBL where memName like ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO retval = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, "%" + memName + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				retval = new MemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7));
				list.add(retval);
			}
			if (list == null) {
				Utility.alertDisplay(1, "���� ����", "���ο� �����߽��ϴ�.", "666666666");
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
	} // end of getMemberNameCheck

	// ���� ���(update table�� set �ʵ�� = ���� ���� where ����)
	public MemberVO getMemberUpdate(MemberVO mvo, int no) throws Exception {
		String dml = "update memberTBL set "
				+ " memName=?, memPhone=?, memAddress=?, postCode=?, memPW=?, memJoinDate=?, where memID=?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, mvo.getMemName());
			pstmt.setString(2, mvo.getMemPhone());
			pstmt.setString(3, mvo.getMemAddress());
			pstmt.setString(4, mvo.getPostCode());
			pstmt.setString(5, mvo.getMemPW());
			pstmt.setString(6, mvo.getMemJoinDate());
			pstmt.setInt(7, mvo.getMemID());
			int i = pstmt.executeUpdate();

			if (i == 1) {
				Utility.alertDisplay(1, "���� �Ϸ�", mvo.getMemID() + "�����Ǿ����ϴ�", "���� ����");
			} else {
				Utility.alertDisplay(1, "���� ����", mvo.getMemID() + "������ ���� �ʾҽ��ϴ�", "���� ����!!!!!!!");
				return null;
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
		return mvo;
	} // end of getMemberUpdate

	// ���� ���(delete)
	public void getMemberDelete(int no) throws Exception {
		String dml = "delete from memberTBL where memID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setInt(1, no);
			int i = pstmt.executeUpdate();
			if (i == 1) {
				Utility.alertDisplay(5, "���� ����", "ȸ�� ���� �Ϸ�", "ȸ�� ���� ����");
			} else {
				Utility.alertDisplay(1, "���� ����", "ȸ�� ���� �̿Ϸ�", "ȸ�� ���� ����");
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

	// �����ͺ��̽����� ȸ������ �� �ҷ�����
	public ArrayList<MemberVO> getMemberTotal() {
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		String dml = "select * from membertbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		MemberVO memberVO = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			System.out.println("DAO �ȿ� �ִ� �����Ż");
			rs = pstmt.executeQuery(); 
			while (rs.next()) { 
				memberVO = new MemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7));
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
		return list;
	}// end of getMemberTotal

} // end of MemberDAO
