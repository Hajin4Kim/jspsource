package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ChangeDTO;
import dto.MemberDTO;

// ~~DAO : DB 서버와 연동해서 DB 작업 담당하는 클래스
// 1. 드라이버 로드
// 2. Connection (DB 서버의) 얻기
// 3. Connection 통해 CRUD(insert, read, update, delete) 작업실행

// Statement : SQL 의 DDL, DML 실행 시 사용(정적 SQL문 실행 시 사용 = DELETE FROM usertbl WHERE id=1)
//				동적 SQL 구문 사용 시 SQL injection(DB 서버 공격) 발생할 수 있음
// PreparedStatement : Statement 와 기능동일 BUT (동적 SQL문 실행 시 사용 = DELETE FROM usertbl WHERE id=?)
//						? 로 값을 넣기 때문에 편리성, 보안성이 좋다.

// ResultSet : SELECT 결과가 담기는 객체 ( == SELECT 구문 사용시에만 필요한 요소임)

public class MemberDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 연결문자열 => DriverManager
	public Connection getConnection() throws SQLException {
		// localhost (== 127.0.0.1) : 오라클 서버 ip 주소
		// 1521 : 오라클 기본포트
		// xe : 오라클 Express Edition 설치 시 기본 설치되는 DB명
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "c##java";
		String password = "12345";

		return DriverManager.getConnection(url, user, password);
	}

	// 자원해제
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

	// CRUD 메소드
	// public int create(String userid, String name, String password, int age,
	// String email) {
	public int create(MemberDTO insertDto) {
		int result = 0;
		try {

			// Connection 얻기
			con = getConnection();

			// sql 구문 작성 후 db 서버로 보내기
			String sql = "INSERT INTO USERTBL(userid,name,password,age,email) " + " VALUES(?, ?, ?, ?, ?)";

			pstmt = con.prepareStatement(sql);

			// ? 해결
			// pstmt.setString(1, userid);
			// pstmt.setString(2, name);
			// pstmt.setString(3, password);
			// pstmt.setInt(4, age);
			// pstmt.setString(5, email);

			pstmt.setString(1, insertDto.getUserid());
			pstmt.setString(2, insertDto.getName());
			pstmt.setString(3, insertDto.getPassword());
			pstmt.setInt(4, insertDto.getAge());
			pstmt.setString(5, insertDto.getEmail());

			// sql 구문 실행(DML 구문: insert, delete, update 실행결과는 숫자)
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return result;

	}

	public List<MemberDTO> read() {
		// 전체 회원 조회
		List<MemberDTO> list = new ArrayList<>();
		try {

			con = getConnection();

			String sql = "SELECT userid,name,age,email FROM USERTBL";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) { // 행 가져옴 rs.next()
				// 컬럼에서 값을 꺼내서 DTO 에 담기
				MemberDTO dto = new MemberDTO();
				dto.setUserid(rs.getString("userid"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				dto.setEmail(rs.getString("email"));

				// list 에 추가하는 작업
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;

	}

	public MemberDTO login(MemberDTO dto) {

		MemberDTO loginDTO = null;
		try {
			// 커넥션 얻기
			con = getConnection();

			// sql 구문 작성 후 DB 서버로 보내기
			String sql = "SELECT USERID, NAME FROM USERTBL WHERE USERID = ? AND PASSWORD = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getUserid());
			pstmt.setString(2, dto.getPassword());

			rs = pstmt.executeQuery();

			// rs 에 담긴 내용을 ~~DTO 로 무조건 옮기기
			if (rs.next()) { // 가로행 하나
				loginDTO = new MemberDTO();
				// String userid = rs.getString(1); // 열1
				// String name = rs.getString(2); // 열2
				loginDTO.setUserid(rs.getString(1));
				loginDTO.setName(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return loginDTO;

	}

	public int update(ChangeDTO changeDTO) {

		int updateRow = 0;

		// 비밀번호 변경
		try {
			con = getConnection();
			String sql = "UPDATE USERTBL SET PASSWORD = ? WHERE USERID = ? AND PASSWORD = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, changeDTO.getChangePassword());
			pstmt.setString(2, changeDTO.getUserid());
			pstmt.setString(3, changeDTO.getCurrentPassword());

			updateRow = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return updateRow;
	}

	public int delete(String userid, String password) {
		
		// 회원삭제
		int deleteRow = 0;
		try {
			
			con = getConnection();
			String sql = "DELETE FROM USERTBL WHERE USERID = ? AND PASSWORD = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, password);
			
			deleteRow = pstmt.executeUpdate();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt);
		}
		return deleteRow;
		
	}

}
