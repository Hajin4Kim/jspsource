<%@page import="dto.BookDTO"%>
<%@page import="java.util.List"%>
<%@page import="dao.BookDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	BookDAO dao = new BookDAO();
    List<BookDTO> list = dao.getList();
    
    // 이동방식
    // response.sendRedirect()
    // ~~Jsp, Servelt 마다 다름~~.forward()
    
    // 어떤 특정 값(객체)을 다른 페이지(jsp, servlet / not HTML)들과 공유하기 위한 목적.
    // 1. session(로그인,장바구니) 		session.setAttribute("list", list);		+ 페이지 이동(이동방식제한조건없음BUT 대부분sendRedirect())
   	// 2. request 		request.setAttribute("list", list);		+ 페이지 이동(이동방식제한조건O => forward()) => 사용, 공유할 수 있는 페이지도 제한됨
    request.setAttribute("list", list);
    
    pageContext.forward("list.jsp");
    
    
%>