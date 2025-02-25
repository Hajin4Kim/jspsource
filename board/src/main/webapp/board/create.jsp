<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<div class="container-fluid">
	<h1 class="h3 mb-4 text-gray-800">Create</h1>

<%-- 
(인코딩타입)enctype="application/x-www-form-urlencoded"(기본Default) 
enctype="multipart/form-data" (첨부파일 있는 경우)
--%>
	<form action="/create.do" method="post" enctype="multipart/form-data">
	<!-- 글번호는 자동부여됨 -->
		<div class="form-group">
			<label for="title">제목</label> 
			<input type="text" class="form-control" id="title" name="title" required="required" />
		</div>
		<div class="form-group">
			<label for="content">내용</label> 
			<textarea rows="10" type="text" class="form-control" id="content" name="content" required="required"></textarea>
		</div>
		<div class="form-group">
			<label for="name">작성자</label> 
			<input type="text" class="form-control" id="name" name="name" required="required" />
		</div>
		<div class="form-group">
			<label for="attach">첨부파일</label> 
			<input type="file" class="form-control" id="attach" name="attach" />
		</div>
		<div class="form-group">
			<label for="password">비밀번호</label> 
			<input type="password" class="form-control" id="password" name="password" required="required" />
		</div>
		
		<input type="hidden" name="page" value="1" /> 
		<input type="hidden" name="amount" value="10" /> 
		<input type="hidden" name="criteria" value="" /> 
		<input type="hidden" name="keyword" value="" />
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>
</div>
<%@ include file="../include/footer.jsp"%>