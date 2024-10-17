package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterAction implements Action {
	
	private String path;

	public RegisterAction(String path) {
		super();
		this.path = path;
	}

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1. 값 가져오기
		// 2. Service 호출
		// 3. (CRUD 중)Read 일 때만 => 그러므로 여기선 안함
		// 4. ActionForward 객체
		
		
		return new ActionForward(path, true);
	}

}
