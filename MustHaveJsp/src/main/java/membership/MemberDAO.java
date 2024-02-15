package membership;

import java.sql.SQLException;
import common.JDBConnect;

public class MemberDAO extends JDBConnect { //jdbc 연결하여 crud
	public MemberDAO (String driver, String url, String id, String pw) {
		super(driver, url, id, pw); //객체를 new로 생성할 때 jdbc 연결용 매개값 전달
	}//MemberDAO 객체 생성할 때 오라클 접속 완료
	
	public MemberDTO getMemberDTO(String uid, String upass) {
		MemberDTO dto = new MemberDTO();
		String query = "select * from member where id = ? and pass = ?"; //쿼리문 템플릿
		try {
			//쿼리 실행
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, uid);
			pstmt.setString(2, upass);
			rs = pstmt.executeQuery();
			//결과 처리
			if (rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString("pass"));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("getMemberDTO() 메서드 오류. 확인요망");
			e.printStackTrace();
		} //쿼리문 실행
		return dto; //결과 리턴용 객체
	}//getMemberDTO()
	
	
}//MemberDAO
