// 목록 클릭 시, actionForm submit
const actionForm = document.querySelector("#actionForm");

// 목록 버튼 클릭 시,
const listBtn = document.querySelector("#replyForm .btn-success");
if (listBtn) {
	listBtn.addEventListener("click", () => {
		
		//actionForm.action = "/list.do";
		actionForm.submit();
	});
}
