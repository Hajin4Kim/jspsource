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
	public List<BoardDTO> getList(){
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		
		try {
			con = getConnection();
			String sql = "SELECT bno, name, title, readcnt, regdate FROM board ORDER BY BNO DESC";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결 (없으면 건너뛰기)
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			// dto 에 컬럼 별로 담고(set)(get) list 에 추가
				BoardDTO dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setName(rs.getString("name"));
//				dto.setPassword(rs.getString("password"));
				dto.setTitle(rs.getString("title"));
//				dto.setContent(rs.getString("content"));
//				dto.setAttach(rs.getString("attach"));
//				dto.setRe_ref(rs.getInt("re_ref"));
//				dto.setRe_lev(rs.getInt("re_lev"));
//				dto.setRe_seq(rs.getInt("re_seq"));
				dto.setReadcnt(rs.getInt("readcnt"));
				dto.setRegdate(rs.getDate("regdate"));
				
				list.add(dto);
								
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
			
			if(rs.next()) {
				dto = new BoardDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setAttach(rs.getString("attach"));
				dto.setName(rs.getString("name"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
		}finally {
			close(con, pstmt);
		}
		return updateRow;
	}
	
	// delete 기능
	


}
