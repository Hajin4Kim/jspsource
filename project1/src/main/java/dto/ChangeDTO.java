package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ChangeDTO {
	private String userid;
	private String currentPassword; // java 에서는 _ 안쓰므로, snake case로 변경
	private String changePassword;
}
