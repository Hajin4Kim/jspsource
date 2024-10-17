<%@page import="dto.BookDTO"%>
<%@page import="dao.BookDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// ~~_pro.jsp 작성법
	// 1. 이 페이지로 가져올 값(== 넘어오는 값)이 있는지 확인
	// 		시작하는 페이지에서 form 이 존재하는 경우 또는 a 태그 ? 다음에 넘어오는지 ==> 넘어옴
	int code = Integer.parseInt(request.getParameter("code"));
	
	// 2. DB 작업 (DAO)
	BookDAO dao = new BookDAO();
	BookDTO dto = dao.getRow(code);
	
	// 3. 결과값을 공유
	request.setAttribute("dto", dto);
	
	// 4. 가져온 값 전달 -> 페이지 이동(client 가 볼 페이지)
	pageContext.forward("read.jsp");

%>