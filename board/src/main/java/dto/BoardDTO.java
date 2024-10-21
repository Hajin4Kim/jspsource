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
	private int re_ref;
	private int re_lev;
	private int re_seq;
	private int readcnt;
	private Date regdate;
	
}
