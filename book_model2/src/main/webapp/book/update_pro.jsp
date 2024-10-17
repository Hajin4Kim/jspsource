<%@page import="dto.BookDTO"%>
<%@page import="dao.BookDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%

// 한글 인코딩 처리
request.setCharacterEncoding("utf-8");

//~~_pro.jsp 작성법
// 1. 이 페이지로 가져올 값(== 넘어오는 값)이 있는지 확인
// 		시작하는 페이지에서 form 이 존재하는 경우 또는 a 태그 ? 다음에 넘어오는지 ==> 넘어옴

// 수정 페이지 작성 시, PK(code) 는 무조건 갖고오기 + 수정할 칼럼(price) 가져오기
BookDTO updateDto = new BookDTO();
updateDto.setCode(Integer.parseInt(request.getParameter("code")));
updateDto.setPrice(Integer.parseInt(request.getParameter("price")));
updateDto.setDescription(request.getParameter("description"));

// 2. DB 작업 (DAO)
BookDAO dao = new BookDAO();
int updateRow = dao.update(updateDto);

// 3. 결과값을 공유(수정, 삭제, 삽입 => X 없음)

// 4. 가져온 값 전달 -> 페이지 이동(client 가 볼 페이지)
//TODO: 3 번 안하는 경우 => (결과값을 공유할 것 없는 경우 => response.sendRedirect)
// 0 이 리턴되면 modify_pro.jsp
// 1 이 리턴되면 list_pro.jsp
if (updateRow == 0) {
	response.sendRedirect("modify_pro.jsp?code="+updateDto.getCode()); // code 같이 안보내면 500 에러
} else {
	response.sendRedirect("list_pro.jsp");
}
%>