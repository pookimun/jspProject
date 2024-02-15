<%@page import="utils.JSFunction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if (session.getAttribute("UserId") == null) {
	JSFunction.alertLocation("로그인 후 이용해 주십시오.", "../06Session/LoginForm.jsp", out); /* 매개값url, msg, out(html코드 출력) */
	return; /* ?돌아오라는거? */
}
%>
