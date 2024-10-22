package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import dto.BoardDTO;

public class BoardDAO {
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
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/oracle");
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;

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

	// 전체 리스트 가져오기
	public List<BoardDTO> getList() {
		List<BoardDTO> list = new ArrayList<>();

		try {
			con = getConnection();
			String sql = "SELECT bno, name, title, readcnt, regdate, re_lev FROM board ORDER BY RE_REF DESC, RE_SEQ ASC";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결 (없으면 건너뛰기)
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// dto 에 컬럼 별로 담고(set)(get) list 에 추가
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setReadcnt(rs.getInt("readcnt"));
				dto.setRegdate(rs.getDate("regdate"));
				dto.setRe_lev(rs.getInt("re_lev"));

				list.add(dto);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return list;
	}

	// read 기능
	public BoardDTO read(int bno) {
		BoardDTO dto = null;

		try {
			con = getConnection();
			String sql = "SELECT * FROM board WHERE bno = ?";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setAttach(rs.getString("attach"));
				dto.setName(rs.getString("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return dto;
	}

	// modify(update) 기능
	public int update(BoardDTO updateDto) { // set과 get 이 2개 이상이면 DTO
		int updateRow = 0;

		try {
			con = getConnection();
			String sql = "UPDATE BOARD SET TITLE= ?, CONTENT= ? WHERE BNO = ? AND PASSWORD = ?";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setString(1, updateDto.getTitle());
			pstmt.setString(2, updateDto.getContent());
			pstmt.setInt(3, updateDto.getBno());
			pstmt.setString(4, updateDto.getPassword());

			updateRow = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return updateRow;
	}

	// delete 기능
	public int delete(BoardDTO deleteDto) {

		int deleteRow = 0;

		try {
			con = getConnection();
			String sql = "DELETE FROM BOARD WHERE bno = ? AND password = ?";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setInt(1, deleteDto.getBno());
			pstmt.setString(2, deleteDto.getPassword());
			
			deleteRow = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt);
		}
		return deleteRow;

	}
	
	// create insert 기능
	public int insert(BoardDTO insertDto) {
		int insertRow = 0;
		int bno = 0;
		
		try {
			con = getConnection();
			
			////// DB 자동 발행되는 bno 번호 가져오기
//			String sql = "SELECT board_seq.nextval FROM DUAL";
//			pstmt = con.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			if(rs.next()) {
//				bno = rs.getInt(1);
//			}
//			
//			sql = "INSERT INTO BOARD(BNO, NAME, PASSWORD, TITLE, CONTENT, RE_REF, RE_LEV, RE_SEQ) ";
//			sql += "VALUES(?, ?, ?, ?, ?, ?, 0,0)";
//			pstmt = con.prepareStatement(sql);
//			// sql 구문? 해결
//			pstmt.setInt(1, bno);
//			pstmt.setString(2, insertDto.getName());
//			pstmt.setString(3, insertDto.getPassword());
//			pstmt.setString(4, insertDto.getTitle());
//			pstmt.setString(5, insertDto.getContent());
//			pstmt.setInt(6, bno);
			
			String sql = "INSERT INTO BOARD(BNO, NAME, PASSWORD, TITLE, CONTENT, ATTACH, RE_REF, RE_LEV, RE_SEQ) ";
			sql += "VALUES(board_seq.nextval, ?, ?, ?, ?, ?, board_seq.currval, 0,0)";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setString(1, insertDto.getName());
			pstmt.setString(2, insertDto.getPassword());
			pstmt.setString(3, insertDto.getTitle());
			pstmt.setString(4, insertDto.getContent());
			pstmt.setString(5, insertDto.getAttach()); // 첨부파일 추가
			
			insertRow = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt, rs);
		}
		return insertRow;
	}
	
	// 조회수 업데이트(증가) 기능
	public int updateReadCnt(int bno) {
		int updateRow = 0;
		
		try {
			con = getConnection();
			String sql = "UPDATE BOARD SET READCNT = READCNT + 1 WHERE bno = ?";
			pstmt = con.prepareStatement(sql);
			// SQL 구문 ? 해결
			pstmt.setInt(1, bno);
			
			updateRow = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt);
		}
		return updateRow;
	}
	

}
