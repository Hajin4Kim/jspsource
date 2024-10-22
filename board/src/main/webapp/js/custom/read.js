// actionForm action = "/list.do"로 수정 후 submit

const actionForm = document.querySelector("#actionForm");

/* 
modify,jsp 수정, 삭제 목록 버튼 이벤트
*/
// read.jsp 에서 수정버튼 클릭 시,
// actionForm action = "/modify.do"로 수정 후 submit
const modifyBtn = document.querySelector("#readForm .btn-info");
if (modifyBtn) {
	modifyBtn.addEventListener("click", () => {
		// 값이 있다면 실행
		actionForm.action = "/modify.do";
		actionForm.submit();
	});
}

// 목록 버튼 클릭 시,
const listBtn = document.querySelector("#readForm .btn-success");
if (listBtn) {
	listBtn.addEventListener("click", () => {
		// actionForm bno 요소 제거
		actionForm.querySelector("[name='bno']").remove();
		actionForm.action = "/list.do";
		actionForm.submit();
	});
}

// 삭제 버튼 클릭 시, #readForm 의 action="/delete.do" 로 변경
const removeBtn = document.querySelector(".btn-danger");
if (removeBtn) {
	removeBtn.addEventListener("click", () => {
		readForm.action = "/delete.do";
		readForm.submit();
	});
}

// modify.jsp 에서 수정 클릭시(submit)
// readForm 안에 password,title,content 값이 있는지 확인하고 
// 값이 존재 : submit / 없다면 : message
const readForm = document.querySelector("#readForm");
readForm.addEventListener("submit", (e) => {
	e.preventDefault();

	const title = readForm.querySelector("#title");
	const content = readForm.querySelector("#content");
	const password = readForm.querySelector("#password");

	if (title.value === "") {
		alert("제목을 입력하세요");
		title.focus();
		return;

	} else if (content.value === "") {
		alert("내용을 입력하세요");
		content.focus();
		return;
	} else if (password.value === "") {
		alert("비밀번호를 입력하세요");
		password.focus();
		return;
	}
	// 실행
	readForm.submit();

});


