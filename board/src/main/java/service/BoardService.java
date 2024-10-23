package service;

import java.util.List;

import dto.BoardDTO;
import dto.PageDTO;
import dto.SearchDTO;


// CRUD 호출
public interface BoardService {
	List<BoardDTO> listAll(SearchDTO searchDTO);
	BoardDTO getRow(int bno);
	boolean update(BoardDTO updateDto);
	boolean delete(BoardDTO deleteDto);
	boolean create(BoardDTO insertDto);
	
	boolean hitUpdate(int bno); // 조회수 증가
	
	// 댓글
	boolean reply(BoardDTO replyDto);
	// 페이지네이션
	int getTotalRows();
	
}
