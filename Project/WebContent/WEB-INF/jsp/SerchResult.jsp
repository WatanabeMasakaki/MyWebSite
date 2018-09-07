<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
<link  rel="stylesheet" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品検索画面</title>
</head>

<body>
<nav class="navbar navbar-dark bg-dark">
  <form class="form-inline mt-2 mt-md-0">
    <h3 class="text-light bg-dark"><strong>商品検索結果　</strong></h3>
  </form>
  <form class="form-inline mt-2 mt-md-0">
    <h6 class="text-light bg-dark">${userInfo.name}様</h6>
    <input class="btn btn-outline-primary btn-sm" type="button" value="ログイン" onClick="location.href='Login4.html'">
    <input class="btn btn-outline-primary btn-sm" type="button" value="ログアウト" onClick="location.href='Login4.html'">
    <input class="btn btn-outline-secondary btn-sm" type="button" value="新規登録" onClick="location.href='SignUp6.html'">
    <input class="btn btn-outline-success btn-sm" type="button" value="ユーザー一覧" onClick="location.href='UserList7.html'">
    <input class="btn-outline-warning btn-sm" type="button" value="カートを確認" onClick="location.href='cart11.html'">
    <input class="btn btn-outline-info btn-sm" type="button" value="購入履歴" onClick="location.href='userbuydate13.html'">
    <input class="btn btn-outline-danger btn-sm"  type="button" value="マスタデータ管理" onClick="location.href='masterdate.html'">
  </form>
</nav>

<div class="card bg-light mb-3 shadow" style="max-width: 40rem; margin-top: 80px; margin-bottom: 40px; margin-right: auto; margin-left: auto; text-align: left;">
 <div class="float-right">
  <div class="col">
    <h6>商品検索</h6>
    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="商品名を入力">
  </div>
 </div>
 <div class="card-body" style="text-align:center">
  <form method="post" action="SerchResult">
    <button type="submit" class="btn btn-primary" name= "itemSerchWord" id="itemSerchWord" >　　　商品検索　　　</button>
  </form>
 </div>
</div>

<div class="text-center style6">
 <h2> おすすめ商品</h2>
</div>
	<div class="container">
		<div class="row center">
			<h5 class=" col s12 light">おすすめ商品</h5>
		</div>
		<div class="section">
			<!--   おすすめ商品   -->
			<div class="row">
				<c:forEach var="item" items="${itemList}">
				<div class="col s12 m3">
					<div class="card">
						<div class="card-image">
							<a href="Item?item_id=${item.id}"><img src="img/${item.fileName}"></a>
						</div>
						<div class="card-content">
							<span class="card-title">${item.name}</span>
							<p>${item.price}円</p>
							<form method="get" action="item.html" id="login">
                              <button type="submit" class="btn btn-primary" >　詳細を見る　</button>
                            </form>
						</div>
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
	</div>

</body>

</html>