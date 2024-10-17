// "추가" 버튼 클릭 시, form submit 일어나면 
// description 요소를 제외한 모든 요소에 값이 있는지 확인
// code 는 4자리이고 숫자여야 함, price는 3~10자리 숫자
document.querySelector("body div:nth-child(3) form").addEventListener("submit", (e) => {
	e.preventDefault();

	const form = e.target;

	// form 내부 요소지정
	const code = form.querySelector("#code");
	const title = form.querySelector("#title");
	const writer = form.querySelector("#writer");
	const price = form.querySelector("#price");

	const codeReg = /^[0-9]{4}$/;
	const textReg = /^[A-Za-z가-힇0-9]+/;
	const priceReg = /^[0-9]{3,10}$/;
	if (!codeReg.test(code.value)) {
		alert("도서코드는 4자리의 숫자만 입력 가능합니다");
		code.select();
		return;
	} else if (!textReg.test(title.value)) {
		alert("도서명을 입력바랍니다.")
		title.select();
		return;
	} else if (!textReg.test(writer.value)) {
		alert("도서 저자를 입력바랍니다.")
		writer.select();
		return;
	} else if (!priceReg.test(price.value)) {
		alert("가격은 숫자만 입력바랍니다.");
		price.select(); // focus + 입력값 존재 시, 블럭으로 잡아줌
		return;
	}

	// 이상 없는 경우 form 전송
	alert("추가되었습니다");
	form.submit();

})	