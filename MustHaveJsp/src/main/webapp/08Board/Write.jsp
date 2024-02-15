<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="IsLoggedIn.jsp"%> <!-- 로그인 확인 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	function validateForm(form) { //validateForm 호출 시 매개값으로 Form을 받음
		if (form.title.value == "") { // .value: JS의 값 자체를 가져오는 속성
			alert("제목을 입력하세요.");
			form.title.focus(); //커서를 title로 이동
			return false; //결과로 false 리턴
		}
		if (form.content.value == "") {
			alert("내용을 입력하세요");
			form.content.focus();
			return false;
		}
	}
</script>
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="../Common/Link.jsp" />
	<h2>회원제 게시판 - 글쓰기(Write)</h2>
	<form name="writeFrm" method="post" action="WriteProcess.jsp" onsubmit=""> <!-- onsubmit: 속성값이 T면 submit시 실행 -->
		<table border="1" width="90%">
			<tr> <!-- table row -->
				<td>제목</td>
				<td><input type="text" name="title" style="width: 90%;"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
				<textarea name="content" style="display:inline-block; width: 90%; height: 100px;"></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"> <!-- 열합침 -->
					<button type="submit">작성 완료</button>
					<button type="reset">다시 입력</button>
					<button type="button" onclick="location.href='List.jsp';">목록
						보기</button> <!-- 클릭 시 List.jsp로 이동 -->
				</td>
			</tr>
		</table>
	</form>
</body>
</html>