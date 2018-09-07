<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<link  rel="stylesheet" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EC2ログイン画面</title>
</head>
<body>


<c:if test="${errMsg != null}" >
  <div class="alert alert-danger" role="alert">${errMsg}</div>
</c:if>

<form method="post" action="Login" id="login">
<div class="form-group" style="max-width: 30rem; margin-top: 340px; margin-bottom: 50px; margin-right: auto; margin-left: auto; text-align: left;">
<h1 class="hello">ログイン画面</h1>
  <div class="col">
    <label for="exampleInputEmail1">ログインID</label>
    <input type="text" name="loginid" class="form-control" id="exampleInputEmail1" placeholder="ログインIDを入力">
    <small class="text-muted">あなたのログインIDは他の誰とも共有しません。</small>
  </div>
</div>
 <div class="col" style="max-width: 30rem; margin-top: 50px; margin-bottom: 50px; margin-right: auto; margin-left: auto; text-align: left;">
  <div class="form-group">
    <label for="exampleInputPassword1">パスワード</label>
    <input type="password" name="password" class="form-control" id="exampleInputPassword2" placeholder="パスワード">
  </div>
 </div>
 <div class="text-center">
    <button type="submit" class="btn btn-primary" >ログイン</button>
 </div>
</form>

</body>
</html>