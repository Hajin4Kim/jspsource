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
import dto.PageDTO;
import dto.SearchDTO;

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
	public List<BoardDTO> getList(SearchDTO searchDTO) {
		List<BoardDTO> list = new ArrayList<>();
		// pagination 시작번호
		int start = searchDTO.getPage() * searchDTO.getAmount();
		// pagination 끝번호
		int end = (searchDTO.getPage() -1) * searchDTO.getAmount();
				
		try {
			con = getConnection();
			// 최신글순으로
			// pagination
			// http://localhost:8090/list.do?criteria=&keyword=&page=1&amount=10
			String sql = "SELECT bno, name, title, readcnt, regdate, re_lev ";
			sql +="FROM (SELECT rownum rnum, BNO, NAME, TITLE, READCNT, REGDATE, RE_LEV ";
			sql +="FROM (SELECT BNO, NAME, TITLE, READCNT, REGDATE, RE_LEV FROM BOARD ";
			
				if(!searchDTO.getCriteria().isBlank()) {
					// 검색어 기능 추가
					// 검색할 옵션 getCriteria
					sql	+= "WHERE " +searchDTO.getCriteria() +" LIKE ? ";
					sql	+= "ORDER BY RE_REF DESC, RE_SEQ ASC) ";
					sql += " WHERE rownum <= ?) ";
					sql += "WHERE rnum > ?";
							
					pstmt = con.prepareStatement(sql);
					// sql 구문? 해결 (없으면 건너뛰기)
					pstmt.setString(1, "%"+searchDTO.getKeyword()+"%"); // 검색 키워드 가져오기
					pstmt.setInt(2, start);
					pstmt.setInt(3, end);
				
				}else {
					sql	+= "ORDER BY RE_REF DESC, RE_SEQ ASC) ";
					sql += " WHERE rownum <= ?) ";
					sql += "WHERE rnum > ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, start);
					pstmt.setInt(2, end);
				}
					
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

				// 댓글에 필요한 정보 담기(re_ref, re_lev, re_seq)
				dto.setRe_ref(rs.getInt("re_ref"));
				dto.setRe_lev(rs.getInt("re_lev"));
				dto.setRe_seq(rs.getInt("re_seq"));

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
		} finally {
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
		} finally {
			close(con, pstmt);
		}
		return updateRow;
	}

	// 댓글추가 insert 기능
	public int replyInsert(BoardDTO replyDto) {
		int insertRow = 0;

		// 부모글 정보 가져오기
		int bno = replyDto.getBno();
		int reRef = replyDto.getRe_ref();
		int reLev = replyDto.getRe_lev();
		int reSeq = replyDto.getRe_seq();

		try {
			con = getConnection();

			// 업데이트
			String sql = "UPDATE BOARD SET RE_SEQ = RE_SEQ +1 WHERE RE_REF = ? AND RE_SEQ > ?";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setInt(1, reRef);
			pstmt.setInt(2, reSeq);

			pstmt.executeUpdate();

			// 업데이트 후 insert
			sql = "INSERT INTO BOARD(BNO, NAME, PASSWORD, TITLE, CONTENT, RE_REF, RE_LEV, RE_SEQ) ";
			sql += "VALUES(board_seq.nextval, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			// sql 구문? 해결
			pstmt.setString(1, replyDto.getName());
			pstmt.setString(2, replyDto.getPassword());
			pstmt.setString(3, replyDto.getTitle());
			pstmt.setString(4, replyDto.getContent());
			pstmt.setInt(5, reRef);
			pstmt.setInt(6, reLev + 1);
			pstmt.setInt(7, reSeq + 1);

			insertRow = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con, pstmt, rs);
		}
		return insertRow;
	}
	
	// 전체 게시물 갯수 리턴 메소드 (Pagination)
	public int getTotalRows(){
		int total = 0;
		
		try {
			con = getConnection();
			String sql = "SELECT COUNT(*) FROM BOARD";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				total = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(con, pstmt, rs);
		}
		return total;
	}
	
	
	
	
	
	
	
	
	
	
	

}
