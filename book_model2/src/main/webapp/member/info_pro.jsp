<%@page import="dao.MemberDAO"%>
<%@page import="dto.ChangeDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// == 0 (info.jsp)	/ ==1 (세션해제 & login.jsp)


	// 1.
	ChangeDTO changeDto = new ChangeDTO();	
	changeDto.setUserid(request.getParameter("userid"));
	changeDto.setCurrentPassword(request.getParameter("current_password"));
	changeDto.setChangePassword(request.getParameter("change_password"));

	// 2.
	MemberDAO dao = new MemberDAO();
	int updateRow = dao.update(changeDto);
	
	
	// 3.
	if (updateRow == 1){
		//로그아웃 : 세션 정보 제거
		session.invalidate(); // 전체 모든 세션 날라감
		response.sendRedirect("login.jsp");
	}else{
		response.sendRedirect("info.jsp");
	}
	



%>