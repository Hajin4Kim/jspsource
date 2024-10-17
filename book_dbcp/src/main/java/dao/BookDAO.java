package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import dto.BookDTO;

public class BookDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;

//	static {
//		// 드라이버 로드
//		try {
//			Class.forName("oracle.jdbc.OracleDriver");
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	public Connection getConnection() {
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/oracle");
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}return con;

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
	public BookDTO getRow(int code) {
		BookDTO dto = null;
		try {

			con = getConnection();
			String sql = "SELECT * FROM BOOKTBL WHERE CODE = ?";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();

			// WHERE 절에 PK 가 사용된 경우(하나만추출되므로), if절 사용
			if (rs.next()) {
				dto = new BookDTO();
				dto.setCode(rs.getInt("code"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setPrice(rs.getInt("price"));
				dto.setDescription(rs.getString("description"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return dto;
	}

	public List<BookDTO> getList() {
		List<BookDTO> list = new ArrayList<BookDTO>();

		try {
			con = getConnection();
			String sql = "SELECT * FROM booktbl ORDER BY CODE";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결 (없으면 건너뛰기)
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// dto 에 컬럼 별로 담고(set)(get) list 에 추가
				BookDTO dto = new BookDTO();
				dto.setCode(rs.getInt("code"));
				dto.setTitle(rs.getString("title"));
				dto.setWriter(rs.getString("writer"));
				dto.setPrice(rs.getInt("price"));

				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	}

	// U(Update) - 수정
	// 수정 메소드 작성하기
	// 리턴타입 : int
	public int update(BookDTO updateDto) {

		int updateRow = 0;
		try {

			con = getConnection();
			String sql = "UPDATE BOOKTBL SET PRICE = ?, description= ? WHERE code = ?";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결 (? 갯수가 2개 이상이면 무조건 DTO)
			pstmt.setInt(1, updateDto.getPrice());
			pstmt.setString(2, updateDto.getDescription());
			pstmt.setInt(3, updateDto.getCode());
			rs = pstmt.executeQuery();
			
			updateRow = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return updateRow;
	}

	// D(Delete) - 삭제
	// 삭제 메소드 작성하기
	// 리턴타입 : int
	public int delete(int code) {
		
		int deleteRow = 0;
		try {
			con = getConnection();
			String sql = "DELETE FROM BOOKTBL WHERE code = ?";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setInt(1, code);
			deleteRow = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt);
		}
		return deleteRow;
	}
	
	// C(Create) - 삽입
	// 삽입 메소드 작성하기
	// 리턴타입 : int
	public int insert(BookDTO insertDto) {
		
		int insertRow = 0;
		
		try {
			con = getConnection();
			String sql = "INSERT INTO booktbl(code, title, writer, price, description) values(?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setInt(1, insertDto.getCode());
			pstmt.setString(2, insertDto.getTitle());
			pstmt.setString(3, insertDto.getWriter());
			pstmt.setInt(4, insertDto.getPrice());
			pstmt.setString(5, insertDto.getDescription());
			// rs 는 select 구문에서만 사용
			insertRow = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt);
		}
		return insertRow;
		
	}
		
	
	
	
	
	
	
	
	
	
}
