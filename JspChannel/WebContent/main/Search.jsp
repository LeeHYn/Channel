<%@page import="com.JspChannel.channel.MVCBoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="../main/keps.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/css/swiper.min.css">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/css/swiper.min.css">
    <!-- font awesome -->
    <!-- Custom style -->


    <title>Hello, world!</title>
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
              <li class="nav-item"><a class="nav-link active"
                  aria-current="page" href="../member/SubChannel.do">구독 채널</a></li>
              <li class="nav-item"><a class="nav-link active"
                aria-current="page" href="../member/ImChannel.do">주요 채널</a></li>
              <li class="nav-item"><a class="nav-link active"
                aria-current="page" href="../member/Search.do">검색</a> 
              </li>
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
          </ul>
          </div>
      </div>
      </nav> 
      <div class = " pb-5 mb-5 row justify-content-center col-lg-12 col-md-12">
			<form method="post" class="row justify-content-center container col-12" action="../member/Search.do">
				<div class="col-8">
					<input type="search" class = "w-100"
						placeholder="검색어 입력" aria-label="Search" name="keyword">
				</div>
				<div class="col-4">
					<button type="submit" class = "btn btn-primary">검색</button>
				</div>
			</form>
	<div>
   	    <table class="table table-dark table-hover" border="1">
        <tr align="center">
            <th>채널명</th>
            <th>관리자</th>
        </tr>

  <!-- 게시물이 있을 때 boardLists배열 안에 있는걸 가져오기 위해서 변수 row를 선언하여사용-->
        
        <c:forEach items="${ searchList }"  var="row" varStatus="loop">
        <tr align="center">
		
			<td><a href="../member/Channel.do?idx=${ row.ch_num }">${ row.ch_name }</a></td>  <!-- 작성자 -->
            <td>${ row.user_id }</td>  <!-- 작성자 -->
			
        </tr>
        </c:forEach>     
 

    </table>
		</div>
		</div>
		</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/js/swiper.min.js"></script>
<script src="../main/keps.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

</body>
</html>