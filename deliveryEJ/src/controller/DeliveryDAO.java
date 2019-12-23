package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.InboundVO;
import model.OutboundVO;

public class DeliveryDAO {

	// 라인차트 by cost
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

	// 바차트 by 도시, 선/착불
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

	// 파이 차트 by 품목별
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
	
	// 승인 버튼 클릭 이벤트
	public OutboundVO getApprovalUpdate(OutboundVO ovo, int no) throws Exception {
		String dml = "update outboundtbl set " + " approval=?  where trkNum = ?";
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = DBUtil.getConnection();
			
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, "승인");
			pstmt.setInt(2, ovo.getTrkNum());

			int i = pstmt.executeUpdate();

			if (i == 1) {
				Utility.alertDisplay(1, "승인 완료", "승인 완료 되었습니다.", "Tracking No. " + ovo.getTrkNum() + " 승인 완료");
			} else {
				Utility.alertDisplay(1, "승인 실패", "승인에 실패했습니다.", "Tracking no. " + ovo.getTrkNum() + "승인 실패");
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
		return ovo;
	}

	// 픽업 예약건 중 승인 대기중인건 찾기(approval이 디폴트(0))
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
			pstmt.setString(1, "미승인");
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

	// inbound에서 송장번호 검색
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

	// outbound에서 송장번호 검색
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

	// inbound 전체
	public ArrayList<InboundVO> getInboundTotal() {
		ArrayList<InboundVO> list = new ArrayList<InboundVO>();
		String dml = "select * from inboundtbl";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; // ResultSet이란, 데이터베이스 값을 임시로 저장하는 장소를 제공하는 객체
		InboundVO inboundVO = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery(); // sql에서 select와 같다. executeQuerty는 반드시 리턴값을 받아야 한다
			while (rs.next()) { // sql 테이블에서 각 항목을 하나씩 가져온다
				inboundVO = new InboundVO(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10));
				list.add(inboundVO); // arraylist에 한 줄씩 추가한다
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

	// outbound 전체
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
	
	// 배송조회할때 출발일, 출발시간, 출발장소(주소) 구하기
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
