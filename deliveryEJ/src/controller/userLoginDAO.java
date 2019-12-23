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
			// ③ DBUtil 클래스의 getConnection( )메서드로 데이터베이스와 연결
			con = DBUtil.getConnection();

			// ④ 입력받은 회원정보를 처리하기 위하여 SQL문장을 생성
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, txtLoginPhone);
			pstmt.setString(2, txtPw);

			// ⑤ SQL문을 수행후 처리 결과를 얻어옴
			rs = pstmt.executeQuery();

			while (rs.next()) {
				umvo = new userMemberVO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6));
				list.add(umvo);
				System.out.println("찾았다.");
			}

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// ⑥ 데이터베이스와의 연결에 사용되었던 오브젝트를 해제
				if (pstmt != null)
					pstmt.close(); // 자원반납
				if (con != null)
					con.close(); // 자원반납
			} catch (SQLException e) {
			}
		}
		return list;
	}

}
