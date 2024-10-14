<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%-- 1 ~ 10 출력하기 --%>
<%
	for(int i =1; i<11; i++){
		out.print(i+"<br>");
	}

	int a = 3, b = 0;
%>
<p>사칙연산</p>
<!-- 커서에 있는 줄 복사붙여넣기: ctrl+alt+아래쪽화살표 -->
<p>a + b = <%=a+b %></p>
<p>a - b = <%=a-b %></p>
<p>a * b = <%=a*b %></p>
<p>a / b = <%=a/b %></p>
</body>
</html>