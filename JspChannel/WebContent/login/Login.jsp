<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
   
    <link href="../plugin/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- font awesome -->
    <!-- Custom style -->
    <link rel="stylesheet" href="../plugin/bootstrap/css/style.css" media="screen" title="no title" charset="utf-8">
    <link rel="stylesheet" href="missing.css">

    <title>Hello, world!</title>
  </head>
  <nav>
    <nav class="navbar navbar-expand-lg navbar-light bg-light ">
      <div class="container">
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
              <form class="d-flex">
                <button class="btn btn-outline-success" type="submit">로그인</button>
            </form>
          </div>
      </div>
      </nav>
    
      <div class="modal modal-signin position-static d-block bg-secondary py-5" tabindex="-1" role="dialog" id="modalSignin">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-5 shadow">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <!-- <h5 class="modal-title">Modal title</h5> -->
              <h2 class="fw-bold mb-0">Sign up for free</h2>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
      
            <div class="modal-body p-5 pt-0">
              <form action="../member/LoginProc" method="post">
                <div class="form-floating mb-3">
                  <input type="text" class="form-control rounded-4" name="id" id="floatingInput" placeholder="name@example.com">
                  <label for="floatingInput">Id</label>
                </div>
                <div class="form-floating mb-3">
                  <input type="password" class="form-control rounded-4" name="pw" id="floatingPassword" placeholder="Password">
                  <label for="floatingPassword">Password</label>
                </div>
                <button class="w-100 mb-2 btn btn-lg rounded-4 btn-primary" type="submit">Sign up</button>
                
                <hr class="my-4">
                
                <small><a href="../login/SingUp.jsp">회원가입  </a></small>
              </form>
            </div>
          </div>
        </div>
      </div>
    


    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

</body>
</html>