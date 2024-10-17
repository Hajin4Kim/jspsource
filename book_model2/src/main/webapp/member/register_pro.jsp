<%@page import="dao.MemberDAO"%>
<%@page import="dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

//한글 인코딩 처리
request.setCharacterEncoding("utf-8");

	// 1. 이 페이지로 가져올 값(== 넘어오는 값)이 있는지
	MemberDTO insertDto = new MemberDTO();
	insertDto.setUserid(request.getParameter("userid"));
	insertDto.setPassword(request.getParameter("password"));
	insertDto.setName(request.getParameter("name"));
	
	// 2. DB 작업 (DAO)
	MemberDAO dao = new MemberDAO();
	int insertRow = dao.insert(insertDto);
	
	// 4. 이동
	// == 1 (회원가입 성공 -> login.jsp)
	// == 0 (실패 -> register.jsp)
	if (insertRow == 1) {
		response.sendRedirect("login.jsp");
	} else {
		response.sendRedirect("register.jsp");
	}
%>