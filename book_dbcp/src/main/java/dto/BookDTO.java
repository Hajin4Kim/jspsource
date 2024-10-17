package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class BookDTO {
	// 테이블과 동일한 모양
	private int code; // Number => int / Long
	private String title; // Varchar2 => String // Date => LocalDate
	private String writer;
	private int price;
	private String description;
}
