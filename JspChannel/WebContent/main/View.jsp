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
                aria-current="page" href="#">검색</a> 
              </li>
          </ul>
          </div>
      </div>
      </nav> 
  
	<table class="table table-dark table-hover" border="1" width="90%">
       <c:forEach items="${ postboard }" var="item">
        <td>제목</td>
        <td>
			${ item.post_title }
        </td>
    </tr>
    <tr>
     <td>방문수</td>
        <td>
			${ item.vcount }
        </td>
    </tr>
    <tr>
        <td>내용</td>
        <td>
            <textarea style="width:90%;height:100px;" readonly="readonly" > ${ item.post_contents }</textarea>
        </td>
    </tr>
    <tr>
        <td>첨부 파일</td>
        <td>
        <img alt="첨부 파일" src="${ item.post_file }" class="w-100">
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <input type="button" value="목록 바로가기" onclick="location.href='../member/channel.do';"></input>
        </td>
    </tr>
    </c:forEach>
</table>    

</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/js/swiper.min.js"></script>
<script src="../main/keps.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

</body>
</html>