package model1;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect { // 세 번째 생성자 활용
	public BoardDAO(ServletContext app) { // web.xml에 있는 값 활용
		super(app); // con, stmt, pstmt, rs, close() 사용 가능
	}

	// 게시물 갯수 세기(목록에 출력할 게시물 가져오기 위함)
	// Map<key, value>
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0; // 결과(게시물 수)를 담을 변수

		// 게시물 수를 얻어오는 쿼리문 작성
		String query = "select count(*) from board"; // query == 검색값 없음ver
		if (map.get("SearchWord") != null) { // SearchWord가 있는 경우: query == 검색값 있음ver
			query += " WHERE " + map.get("searchField") + " " + " like '%" + map.get("searchWord") + "%'";
		}
		try {
			stmt = con.createStatement(); // 쿼리문 생성
			rs = stmt.executeQuery(query); // 쿼리 실행
			rs.next(); // 커서를 첫 번째 행으로 이동
			totalCount = rs.getInt(1);
		} catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount;
	}// selectCount()

	public List<BoardDTO> selectList(Map<String, Object> map) {
		// 결과(게시물 목록)을 담을 리스트 변수
		List<BoardDTO> bbs = new Vector<BoardDTO>(); // Vector: ArrayList와 달리 다중 스레드 방식으로 하나의 스레드가 실행 완료되어야 다른 스레드도 실행
														// 가능

		String query = "select * from board "; // 조건이 없을 때
		if (map.get("searchWord") != null) {
			query += " where " + map.get("searchField") + " " + " like '%" + map.get("searchWord") + "%' "; // 조건이 있을 때
																											// where문 추가
		}
		query += " order by num DESC "; // 내림차순 정렬

		try {
			stmt = con.createStatement(); // 쿼리문 생성
			rs = stmt.executeQuery(query); // 쿼리문 실행

			while (rs.next()) { // 결과를 순환하며...
				// 한 행(게시물 하나)의 내용을 DTO에 저장
				BoardDTO dto = new BoardDTO();

				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));

				bbs.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return bbs;
	}// selectList()
	
	public List<BoardDTO> selectListPage(Map<String, Object> map) {
		// 결과(게시물 목록)을 담을 리스트 변수
		List<BoardDTO> bbs = new Vector<BoardDTO>(); // Vector: ArrayList와 달리 다중 스레드 방식으로 하나의 스레드가 실행 완료되어야 다른 스레드도 실행
														// 가능

		String query = "select * from ("
				+ "	select Tb.*, rownum rNum from ("
				+ "	select * from board "; // 조건이 없을 때
		
		if (map.get("searchWord") != null) {
			query += " where " + map.get("searchField") + " " + " like '%" + map.get("searchWord") + "%' "; // 조건이 있을 때 where문 추가
		}
		query += " order by num desc" // 내림차순 정렬
			   + ") Tb "
			   + ") where rNum between ? and ?"; 

		try {
			pstmt = con.prepareStatement(query); // 쿼리문 생성
			pstmt.setString(1, map.get("start").toString()); // 쿼리문 실행
			pstmt.setString(2, map.get("end").toString());
			rs = pstmt.executeQuery(); //쿼리 실행
			
			while (rs.next()) { // 결과를 순환하며...
				// 한 행(게시물 하나)의 내용을 DTO에 저장
				BoardDTO dto = new BoardDTO();

				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));

				bbs.add(dto);
			}
		} catch (SQLException e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return bbs;
	}// selectListPage()

	public int insertWrite(BoardDTO dto) {
		int result = 0; // 업데이트 성공 시 값이 1로 담김

		try { // insert 쿼리문 작성
			String query = "insert into board ( " + " num, title, content, id, visitcount) values ( "
					+ " seq_board_num.nextval, ?, ?, ?, 0)";
			// pstmt: 동적 쿼리 실행(한 개씩, int) <-> stmt: 정적 쿼리 실행(전체, String)
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getId()); // session에서 가져옴
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("게시물 입력 예외 발생: insertWrite()");
			e.printStackTrace();
		}
		return result;
	}// insertWrite()

	public void updateVisitCount(String num) { // 게시물 클릭 시 조회수 증가
		String query = "update board set " + " visitcount = visitcount + 1 " + " where num=?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, num); // 인파라미터를 일련 번호로 설정
			pstmt.executeQuery(); // 쿼리 실행
		} catch (SQLException e) {
			System.out.println("게시물 조회수 증가 예외 발생: updateVisitCount()");
			e.printStackTrace();
		}
	}// updateVisitCount()

	public BoardDTO selectView(String num) { // 자세히 보기 (번호를 받아서 dto로 리턴)
		BoardDTO dto = new BoardDTO(); // dto 객체 생성(값이 없음)
		// inner join: Member M이 Board B 내부로 들어옴
		String query = "select b.*, m.name from member m inner join board b on m.id = b.id where num = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, num); // 인파라미터를 일련번호로 설정
			rs = pstmt.executeQuery(); // 쿼리 실행 후 결과가 표로 나옴

			if (rs.next()) {
				dto.setNum(rs.getString("num")); // rs표로 가져오기 때문에 열이름으로 구분 가능
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				dto.setName(rs.getString("name")); // dto 객체의 값 저장
			}
		} catch (SQLException e) {
			System.out.println("게시물 상세보기 예외 발생: selectView()");
			e.printStackTrace();
		}
		return dto;
	}// selectView()

	public int updateEdit(BoardDTO dto) {// 업데이트용 (dto를 받아 int로 리턴)
		int result = 0;

		try {
			String query = "update board set title = ?, content = ? where num = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getNum());

			result = pstmt.executeUpdate(); // 쿼리문 실행하여 결과를 int로 받음
		} catch (SQLException e) {
			System.out.println("게시물 수정 예외 발생: updateEdit()");
			e.printStackTrace();
		}
		return result;
	}// updateEdit()

	public int deletePost(BoardDTO dto) {
		int result = 0;
		try {
			String query = "delete from board where num = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, dto.getNum());

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("게시물 삭제 예외 발생: deletePost()");
			e.printStackTrace();
		}
		return result;
	} // deletePost()
}
// BoardDAO()
