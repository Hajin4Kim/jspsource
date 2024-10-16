package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.BookDTO;
import dto.MemberDTO;

public class MemberDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	static {
		// 드라이버 로드
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "c##java";
		String password = "12345";

		return DriverManager.getConnection(url, user, password);
	}

	public void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close(Connection con, PreparedStatement pstmt) {
		try {
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CRUD 메소드

	// R (Read) - 전체조회, 특정(pk) 조건 조회, 제목 조회
	// 조회 메소드 작성하기

	// TODO: 리턴 타입 : 무조건 List<~~DTO> 또는 ~~DTO
	// List<~~DTO> : WHERE 절 없는 경우, WHERE 절이 PK 가 아니면.
	// ~~DTO: WHERE 절이 PK 인 경우.

	// 전달인자(parameter) : ()안에 어떻게 작성할 것인가?
	// sql 구문의 ? 보고 결정
	// read_pro.jsp 에 code 값 보내기

	public MemberDTO isLogin(MemberDTO loginDto) {

		MemberDTO dto = null;
		try {
			con = getConnection();
			String sql = "SELECT * FROM MEMBERTBL WHERE USERID = ? AND PASSWORD = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, loginDto.getUserid());
			pstmt.setString(2, loginDto.getPassword());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto = new MemberDTO();
				dto.setUserid((rs.getString("userid")));
				dto.setPassword((rs.getString("password")));
				dto.setName((rs.getString("name")));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return dto;
	}

	// C(Create) - 삽입 (회원가입)
	// 삽입 메소드 작성하기
	// 리턴타입 : int
	public int insert(MemberDTO insertDto) {
		
		int insertRow = 0;
		try {
			con = getConnection();
			String sql = "INSERT INTO MEMBERTBL(USERID, NAME, PASSWORD) VALUES(?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, insertDto.getUserid());
			pstmt.setString(2, insertDto.getName());
			pstmt.setString(3, insertDto.getPassword());
			
			insertRow = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt);
		}
		return insertRow;
	}

	public boolean dupId(String userid) {

		try {
			con = getConnection();
			String sql = "SELECT * FROM MEMBERTBL WHERE USERID = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 이미 id  있음. 의 의미코드줄
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return true;// 중복아이디 없음
	}
	
	// U(Update) - 수정
	// 수정 메소드 작성하기
	// 리턴타입 : int

	// D(Delete) - 삭제
	// 삭제 메소드 작성하기
	// 리턴타입 : int

}
