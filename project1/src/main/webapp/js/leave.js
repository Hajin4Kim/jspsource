/**
 * 
 */

// 회원탈퇴 클릭 시 form submit 중지
// confirm("정말로 탈퇴하시겠습니까?")

document.querySelector("form").addEventListener("submit", (e) => {
	e.preventDefault();

	// // 확인 ;true/ 취소 ;false 로 값이 온다.
	 // const result = confirm("정말로 탈퇴하시겠습니까?");
	 // console.log(result);
	 
	 // // 확인인 경우, form 다시 submit 진행
	 if(confirm("정말로 탈퇴하시겠습니까?")){
		e.target.submit();
	 }
	 
})
