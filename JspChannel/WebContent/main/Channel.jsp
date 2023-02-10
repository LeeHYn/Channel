<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">

<!-- font awesome -->
<!-- Custom style -->

</head>

<div class="container">
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="../member/Main">GameJoy</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<ul class="navbar-nav me-auto mb-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link active"
                  aria-current="page" href="../member/SubChannel.do">구독 채널</a></li>
						<li class="nav-item"><a class="nav-link active"
                aria-current="page" href="../member/ImChannel.do">주요 채널</a></li>
              <li class="nav-item"><a class="nav-link active"
                aria-current="page" href="../member/Search.do">검색</a> 
					</ul>
					<c:if test="${login != 1}">
						<li class="nav-item mx-lg-3 mx-0 mt-1 mt-lg-0">
							<button type="button" class="btn btn-primary"
								onclick="location.href='../member/Login';">로그인</button>
						</li>
					</c:if>
					<c:if test="${login == 1}">
						<li class="nav-item mx-lg-3 mx-0 mt-1 mt-lg-0">
							<button type="button" class="btn btn-primary"
								onclick="location.href='../member/Logout';">로그아웃</button>
						</li>
					</c:if>
			</div>
		</div>
	</nav>


	<article class="contain-fluid board-article">

		<div class="board-title">
		
			<c:if test="${channeluser != 1}">
				<li class="nav-item mx-lg-3 mx-0 mt-1 mt-lg-0">
					
						<span><a href="../member/Subscribe.do?index=${ index }">구독</a></span>
				</li>
			</c:if>
			

			
			<c:if test="${channeluser == 1}">
				<li class="nav-item mx-lg-3 mx-0 mt-1 mt-lg-0">
					<span><a href="../member/SubscribeCancle.do?index=${ index }">구독취소</a></span>
				</li>
			</c:if>
			<div class="grid">
				<div class="g-col-6">

					<table class="table table-dark table-hover">
						<c:forEach items="${ boardList3}" var="row" varStatus="loop">
							<tr>
								<td>${ row.ch_name }</td>
								<td>${ row.user_id }</td>
								<td>${ row.cs }</td>
							</tr>
						</c:forEach>
					</table>

				</div>
			</div>
			<div class = " pb-5 mb-5 row justify-content-center col-lg-8 col-md-12">
			<form method="post" class="row justify-content-center container col-12" action="../member/Channel.do?idx=${ index }">
				<div class="col-8">
					<input type="search" class = "w-100"
						placeholder="검색어 입력" aria-label="Search" name="keyword">
				</div>
				<div class="col-4">
					<button type="submit" class = "btn btn-primary">검색</button>
				</div>
			</form>
		</div>
			<div class="btns-board">
				<div class="float-right"></div>
				<div class="float-left">
					<button class="btn btn-outline-success"><a href="../member/PostWrite?idx=${ index }">글쓰기</a></button>
				</div>
			</div>
			<div class="list-table">
				<table class="table table-dark table-hover">
					<tr>
						<th width="10%">번호</th>
						<th width="50%">제목</th>
						<th width="15%">아이디</th>
						<th width="10%">작성일</th>
						<th width="15%">방문수</th>
					</tr>
					<c:forEach items="${ searchList }" var="row">
						<tr>
							<td width="10%">  ${ row.post_num}</td>
							<td width="50%"><a href="../member/View.do?post_num=${row.post_num }">${ row.post_title }</a></td>
							<td w idth="15%">${ row.user_id }</td>
							<td width="10%">${ row.first_day }</td>
							<td width="15%">${ row.vcount }</td>
						
                    </tr>
        </c:forEach>
                  </table>
                </div>

	</article>
			<div class="col-12 container row justify-content-center shadow-lg rounded bg-body">
				<c:if test="${ page > pageCount }">
					<div class="col-1"><a href="../member/Channel.do?page=${ start - 1 }&keyword=${keyword}&idx=${ index }">이전</a></div>
				</c:if>
				<c:forEach var="i" begin ="${ start }" end="${ end }" step="1">
				<c:if test="${ i == page }">
					<div class="col-1"><b>${ page }</b></div>
				</c:if>
				<c:if test="${ i != page }">
					<div class="col-1"><a href="../member/Channel.do?page=${ i }&keyword=${keyword}&idx=${ index }">${ i }</a></div>
				</c:if>
				</c:forEach>
				<c:if test="${ end < pages }">
					<div class="col-1"><a href="../member/Channel.do?page=${ end + 1 }&keyword=${keyword}&idx=${ index }">다음</a></div>
				</c:if>
			</div>
    </div>
    <br>
    <br>
    <div>
    
    </div>

        <script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

    </body>
</html>