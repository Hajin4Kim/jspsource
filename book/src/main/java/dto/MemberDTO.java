package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor @AllArgsConstructor
@ToString @Getter @Setter
public class MemberDTO {
	private String userid;
	private String name;
	private String password;
}
