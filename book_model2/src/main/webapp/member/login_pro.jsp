<%@page import="dto.MemberDTO"%>
<%@page import="dao.MemberDAO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

// ~~_pro.jsp 작성법
// 1. 이 페이지로 가져올 값(== 넘어오는 값)이 있는지 확인
// 		시작하는 페이지에서 form 이 존재하는 경우 또는 a 태그 ? 다음에 넘어오는지 ==> 넘어옴
MemberDTO loginDto = new MemberDTO();
loginDto.setUserid(request.getParameter("userid"));
loginDto.setPassword(request.getParameter("password"));

// 2. DB 작업 (DAO)
MemberDAO dao = new MemberDAO();
MemberDTO dto = dao.isLogin(loginDto); // DB select 추출된 결과 ==> DB 확인 끝난 DTO

// 3. 결과값을 공유 (session.setAttribute())
// 4. 가져온 값 전달 -> 페이지 이동(client 가 볼 페이지)
// null == login.jsp
// !null == list.jsp
if (dto != null) {
	// 서버 쪽에서 정보 보관
	session.setAttribute("loginDto", dto); 
	response.sendRedirect("/book/list_pro.jsp");
} else {
	response.sendRedirect("login.jsp");
}

%>