<%@page import="utils.JSFunction"%>
<%@page import="model1.BoardDAO"%>
<%@page import="model1.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- dto, dao 활용하여 jdbc 기록 -->
<%
String title = request.getParameter("title"); //form값 받기
String content = request.getParameter("content");

BoardDTO dto = new BoardDTO(); //form값을 DTO객체에 저장
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserId").toString()); //로그인하여 세션(객체)에 있는 id 저장

BoardDAO dao = new BoardDAO(application); //jdbc 연결 객체 생성
int iResult = dao.insertWrite(dto); //insertWrite메서드에 dto값 전달하여 결과를 int로 받음

/* int iResult = 0; //페이징 테스트100 더미 데이터
for (int i = 1; i <= 100; i++) {
	dto.setTitle(title + "-" + i); //제목 글자에 -1 ~ 100까지 붙음
	iResult = dao.insertWrite(dto); //insert query 실행
} */

dao.close();

if (iResult == 1) { //전달 성공
	response.sendRedirect("List.jsp"); //목록 보기로 이동
} else {
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out); //뒤로 가기로 다시 시도?
}
%>