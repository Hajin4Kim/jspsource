// 목록 버튼 클릭 시, list로 이동(location)하게
// 버튼 타입이 button 인 경우, .addEventListener("click"
document.querySelector(".btn-primary").addEventListener("click", () => {
	location.href= "/list.do?keyword="+keyword;
})

// 삭제 버튼 클릭 시, 
document.querySelector(".btn-danger").addEventListener("click", () => {
	location.href= "/delete.do?code="+code+"&keyword="+keyword;
})

// 수정 버튼 클릭 시, price 의 값이 숫자가 들어있는지 확인 => 안들어와있으면 경고메시지 + 제출불가
// 버튼 타입이 submit 인 경우,"form" - .addEventListener("submit"
/* document.querySelector("form") 이런식으로 걸면, 
첫번째 form 이 옴 (modify.jsp 에서 
맨 첫번째 form 은 header.jsp 를 include 한 것이기 때문에) 
제대로 원하는 열결을 하지 못한다. */
document.querySelector("body div:nth-child(3) form").addEventListener("submit", (e) => {
	e.preventDefault();
	
	const price = document.querySelector("#price");
	// 100 (3자리) - 1000000000(10자리)
	// * : 0~무한대
	const regEx = /^[0-9]{3,10}$/;
	if(!regEx.test(price.value)){
		alert("숫자만 입력하여 다시 작성 바랍니다.");
		//price.focus();
		price.select(); // focus + 입력값 존재 시, 블럭으로 잡아줌
		return;
	}
	// 이상 없는 경우 form 전송
	e.target.submit();
	
})