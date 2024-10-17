<%@page import="dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

// 순서) id 입력 후 id확인클릭 -> 스크립트 -> fetch(checkid_pro.jsp) userid값을같이 보냄 -> checkid_pro 에서 userid 받은걸로 DAO 작업
	String userid = request.getParameter("userid");
	System.out.println(userid);

	MemberDAO dao = new MemberDAO();
	boolean dupId = dao.dupId(userid);
	
	if(dupId){// true = 중복아이디없음
		out.print("true");
	}else{ // false = duplicated id
		out.print("false");
	}

%>