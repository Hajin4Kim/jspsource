// 목록 버튼 클릭 시, list로 이동(location)하게
// 버튼 타입이 button 인 경우, .addEventListener("click"
document.querySelector(".btn-primary").addEventListener("click", () => {
	location.href= "/list.do";
})

// 버튼 타입이 submit 인 경우, .addEventListener("submit"
/*
document.querySelector(".btn-success").addEventListener("submit", (e) => {
	e.preventDefault();
})
*/
