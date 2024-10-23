package dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
//@AllArgsConstructor


//@Data == @Getter, @Setter, @EqualsAndHashCode, @ToString @NoArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BoardDTO {

	private int bno;
	private String name;
	private String password;
	private String title;
	private String content;
	private String attach;
	private int re_ref; // 이건 DB에서 쓰는 변수명, 앞으로 자바는 CamelCase로 바꾸기
	private int re_lev; // reLev
	private int re_seq;
	private int readcnt;
	private Date regdate;
	
}
