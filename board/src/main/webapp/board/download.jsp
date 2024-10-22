<%@page import="java.io.BufferedOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

	//1. 클라이언트가 요청한 파일명 가져오기
	String fileName = request.getParameter("fileName");

	//2. 서버의 업로드 폴더에 가서 클라이언트가 요청한 파일 읽은 후 보내기	
	//2-1. 서버 경로 생성
		//c:\\upload\\70e21c5e-f63c-4669-8238-725b9e8999f8_pexels-gochrisgoxyz-1643409.jpg
	String saveDir = "c:\\upload";
	String downPath = saveDir + File.separator + fileName;
	
	//2-2. 파일 읽기
	FileInputStream fis = new FileInputStream(downPath);
	
	//2-3. 읽은 파일(이미지,텍스트,동영상,...) 브라우저 헤더에 붙여서 보내기
	response.setContentType("application/octet-stream"); //브라우저에게 컨텐트타입 제공(MIME 타입)
	// 한글깨짐 방지 파일명 인코딩설정
	fileName = new String (fileName.getBytes("utf-8"), "iso-8859-1");
	
	int start = fileName.lastIndexOf("_"); // uuid 와 원 파일명 ("_") 기준으로 나누기
	String oriName = fileName.substring(start + 1); // 실제 원 파일명
	
	// 웹페이지에서 다운로드 버튼(또는 a 태그로 만들어진 첨부파일) 누르면 나오는 
	// 파일탐색기 내에 다른이름으로 저장 시, 나오는 파일 이름:: 고양이 (2).jfif
	response.setHeader("content-disposition", "attachment; filename=" + oriName); 
	
	// jsp 때문에 사용하는 코드
	out.clear();
	out = pageContext.pushBody();
	
	BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
	
	// 버퍼에서 읽은 파일
	int numRead = 0;
	byte b[]= new byte[4096];
	while((numRead = fis.read(b))!= -1){
		bos.write(b);
	}
	bos.flush();
	bos.close();
	fis.close();
	
%>