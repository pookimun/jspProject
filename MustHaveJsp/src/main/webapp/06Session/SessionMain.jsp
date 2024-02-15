<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); //날짜 표시 형식(M:월, m:분)

long creationTime = session.getCreationTime(); //최초 요청(세션 생성) 시작: session상위 클래스?의 getCreationTime메서드를 사용하여 long으로 생성
String creationTimeStr = dateFormat.format(new Date(creationTime)); //dateFormat으로 String으로 만듦

long lastTime = session.getLastAccessedTime(); //마지막 요청 시각
String lastTimeStr = dateFormat.format(new Date(lastTime));
%> <!-- 세션 정보 생성 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Session 설정 확인</h2>
	<ul>
	<li>세션 유지 시간(프로젝트): <%= session.getMaxInactiveInterval() %></li>
	<li>세션 아이디(현재 id): <%= session.getId() %></li>
	<li>최초 요청 시각: <%= creationTimeStr %></li>
	<li>마지막 요청 시각: <%=lastTimeStr %></li>
	</ul>
</body>
</html>