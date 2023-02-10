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
   
		<form name="writeFrm" method="post" enctype="multipart/form-data"
      action="../member/CreatechProc" onsubmit="return validateForm(this);">
	<table class="table table-dark table-hover" border="1" width="90%">
        <td>채널 이름</td>
        <td>
            <input type="text" name="ch_name" style="width:90%;" />
        </td>
    </tr>
    <tr>
        <td>채널 이미지</td>
        <td>
		<input type="file" id="chooseFile" name="ch_image"
		onchange="loadFile(this)"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <input type="submit"></input>
            <input type="reset"></input>
            <input type="button" value="목록 바로가기" onclick="location.href='../member/Main';"></input>
        </td>
    </tr>
</table>    
</form>

</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/4.5.1/js/swiper.min.js"></script>
<script src="../main/keps.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

</body>
</html>