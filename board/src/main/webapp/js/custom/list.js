// 제목 클릭 시 a 태그 기능 중지 e.preventDefault();
// href 에 있는 번호 가져오기 후, actionForm 안 bno value로 삽입

const actionForm = document.querySelector("#actionForm");

// 제목의 a 태그가 여러 개 있는 경우, (방법2가지)
// 1. 개별로 처리하는 법
/*
 const allA = document.querySelectorAll(".table a");
allA.forEach("a", ()=>{
	a.addEventListener("click", (e)=>{
		e.preventDefault();
		// href 값 가져오기
	})
	
})
*/
// 2. 부모에게 전달되는 이벤트 버블링
document.querySelector("tbody").addEventListener("click", (e) => {
	// a 태그 기능 중지
	e.preventDefault();

	// 이벤트 대상 알아보기
	console.log(e.target); // <a href="5" class="text-decoration-none text-reset">board 작성</a>
	console.log(e.target.href); // http://localhost:8090/5
	console.log(e.target.getAttribute("href"));

	const bno = e.target.getAttribute("href");
	actionForm.querySelector("[name='bno']").value = bno;

	// 삽입 후 확인
	console.log(actionForm.innerHTML); // <input type="hidden" name="bno" value="4">

	// actionForm submit
	// action : /cntupdate.do 로 변경
	actionForm.action = "/cntupdate.do";
	actionForm.submit();


});

// 검색 기능
// select 옵션값 중 null 선택하거나, 옵션 선택 후 키워드 입력 없이 검색 이벤트 실행하면 form Submit 중지
// 이후 적절한 메세지 띄우기

const searchForm = document.querySelector("#searchForm");
const searchBtn = document.querySelector("#searchForm .btn-warning");

const criteriaSelect = document.querySelector("[name='criteria']");
const keywordInput = document.querySelector("[name='keyword']");

searchBtn.addEventListener("click", (e) => {
	e.preventDefault(); // 기본 제출 동작 중지

	const selectedCriteria = criteriaSelect.value;
	const keyword = keywordInput.value.trim();

	// 1. select에서 'n' (null 옵션)이 선택된 경우
	// 2. 키워드가 입력되지 않은 경우
	if (selectedCriteria === "n") {
		alert("검색 조건을 선택한 후 검색 바랍니다.");
	} else if (keyword === "") {
		alert("키워드를 입력 후 검색 바랍니다.");
	} else {
		// 위 조건을 만족하지 않으면 폼 제출
		searchForm.submit();
	}
});

// Pagination 기능
// 1 2 3, ... 숫자페이지 버튼 누를 때, 이전, 다음 버튼 누를 때 actionForm submit
// a태그 가 갖고있는 href 값 가져와서 actionForm 의 page 요소값으로 대체

const pagination = document.querySelector(".pagination");
console.log(pagination);
// 각 페이지 링크에 클릭 이벤트 리스너 추가
pagination.addEventListener("click", (e) => {
	e.preventDefault();
	
	
	actionForm.querySelector("[name='bno']").remove();
	// a 태그의 href 값 (페이지 번호)
	actionForm.querySelector("[name='page']").value = e.target.getAttribute("href");
	actionForm.action = "/list.do";
	actionForm.submit();  // 폼 제출
})









