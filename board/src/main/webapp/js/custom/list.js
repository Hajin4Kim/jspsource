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
document.querySelector("tbody").addEventListener("click", (e)=> {
	// a 태그 기능 중지
	e.preventDefault();
	
	// 이벤트 대상 알아보기
	console.log(e.target); // <a href="5" class="text-decoration-none text-reset">board 작성</a>
	console.log(e.target.href); // http://localhost:8090/5
	console.log(e.target.getAttribute("href")); 
	
	const bno = e.target.getAttribute("href");
	actionForm.querySelector("[name='bno']").value= bno;
	
	// 삽입 후 확인
	console.log(actionForm.innerHTML); // <input type="hidden" name="bno" value="4">

	// actionForm submit
	// action : /cntupdate.do 로 변경
	actionForm.action = "/cntupdate.do";
	actionForm.submit();
	
	
});

