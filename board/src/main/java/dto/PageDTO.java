package dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PageDTO {
	private int startPage; // 1~10, 11~20, ... 페이지 버튼
	private int endPage; // 1~10, 11~20, ... 페이지 버튼
	private boolean prev; // 이전 페이지 버튼
	private boolean next; // 다음 페이지 버튼
	
	private SearchDTO searchDTO;
	private int total; // 전체 게시물 수
	
	public PageDTO(SearchDTO searchDTO, int total) {
		this.searchDTO = searchDTO;
		this.total = total;
		endPage = (int)(Math.ceil(searchDTO.getPage() / 10.0)) * 10; // 무작위로 10을 뽑아냄
		startPage = endPage - 9; // 만약 endpage =10  -> startpage = 1
		
		// 진짜 endPage => 만약endPage 로만 쓰면, 게시물이 50개인 경우 10페이지까지 만들어냄;; realEnd가 5페이지까지 만들도록 계산 
		int realEnd = (int)(Math.ceil((total / 1.0) / searchDTO.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
}
