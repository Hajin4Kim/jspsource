package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
// 생성자를 하나라도 더 만들면, Default 생성자는 없어짐 => @AllArgsConstructor 를 추가했다면 @NoArgsConstructor도 추가해줘야한다. 
@Data
public class SearchDTO {
	private String criteria;
	private String keyword;
	
	// pagination 정보
	private int page;
	private int amount;
	
	// 생성자
	public SearchDTO(int page, int amount) {
		super();
		this.page = page;
		this.amount = amount;
	}
	
	
}
