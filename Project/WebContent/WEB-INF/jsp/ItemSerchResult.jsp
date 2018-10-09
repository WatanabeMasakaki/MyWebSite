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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome-animation/0.0.10/font-awesome-animation.css" type="text/css" media="all" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品検索結果</title>
</head>
<body>
<nav class="navbar navbar-dark bg-dark">
  <form class="form-inline mt-2 mt-md-0">
    <h3 class="text-light bg-dark"><strong>商品検索結果　</strong></h3>
  </form>
  <form class="form-inline mt-2 mt-md-0">
    <h4 class="text-light bg-dark">${userInfo.name}様　</h4>
    <i class="fas fa-sign-out-alt backcolor1 fa-2x fa-border faa-bounce animated-hover" type="button" data-toggle="tooltip" data-placement="bottom" title="ログアウト" onClick="location.href='Logout'"></i>
    <i class="fas fa-user-plus backcolor1 fa-2x fa-border faa-bounce animated-hover" type="button" 　data-toggle="tooltip" data-placement="bottom" title="ユーザー新規登録"onClick="location.href='UserCreate'"></i>
    <i class="fas fa-users backcolor1 fa-2x fa-border faa-bounce animated-hover" type="button" 　data-toggle="tooltip" data-placement="bottom" title="ユーザーリスト"onClick="location.href='UserList'"></i>
    <i class="fas fa-shopping-cart backcolor1 fa-2x fa-border faa-bounce animated-hover" type="button" 　data-toggle="tooltip" data-placement="bottom" title="カートの中身"onClick="location.href='Cart'"></i>
    <i class="fas fa-history backcolor1 fa-2x fa-border faa-bounce animated-hover" type="button" 　data-toggle="tooltip" data-placement="bottom" title="購入履歴"onClick="location.href='BuyDateHistory'"></i>

    <div class="dropdown">

     <i class="fas fa-user-cog backcolor1 fa-2x fa-border dropdown-toggle" role="button" data-toggle="dropdown" type="button"　data-toggle="tooltip" data-placement="bottom" title="マスタデータ"></i>
  <!-- ドロップメニューの設定 -->
     <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
       <a class="dropdown-item" href="ItemMaster1">商品マスタ一覧参照</a>
       <a class="dropdown-item" href="ItemMaster2">商品マスタ新規登録</a>
       <a class="dropdown-item" href="ItemMaster3">商品マスタ情報参照</a>
       <a class="dropdown-item" href="ItemMaster4">商品マスタ情報更新</a>
       <a class="dropdown-item" href="ItemMaster5">商品マスタ情報削除</a>
     </div>
    </div>
  </form>
</nav>

<div class="card bg-light mb-3 shadow" style="max-width: 40rem; margin-top: 80px; margin-bottom: 40px; margin-right: auto; margin-left: auto; text-align: left;">
 <div class="float-right">
  <div class="col">
    <h6>商品検索</h6>
    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="商品名を入力">
  </div>
 </div>
 <div class="card-body" style="text-align:center;">
  <form method="post" action="SerchResult">
    <button type="submit" class="btn btn-primary" name= "itemSerchWord" id="itemSerchWord" >　　　商品検索　　　</button>
  </form>
 </div>
</div>

	<div class="container" style="text-align:center;">
		<div class="row">
			<h5 class=" col s12 light">検索結果 ${itemCount}件</h5>
		</div>
		<div class="section">
			<!--   検索結果   -->
			<div class="row">
			  <c:forEach var="item" items="${itemList}">
				<div class="col-3 s12 m3">
					<div class="card shadow card-size3 border border-secondary">
						<div class="card-image">
							<a href="Item?item_id=${item.id}"><img src="img/${item.fileName}"></a>
						</div>
						<div class="card-content">
							<span class="card-title">${item.name}</span>
							 <p>${item.price}円</p>
                             <input type="button" value="　詳細を見る　" class="btn btn-primary gazou-size2" onClick="location.href='Item?itemid=${item.id}'"></input>
						</div>
					</div>
				</div>
			  </c:forEach>
			</div>
		</div>
</div>

<nav aria-label="ページ送り">
  <ul class="pagination justify-content-center">
    <li class="page-item"><a class="page-link" href="ItemSearchResult?itemSerchWord=${itemSerchWord}&pageNum=${pageNum - 1}">前へ</a></li>
     <c:forEach begin="${(pageNum - 5) > 0 ? pageNum - 5 : 1}" end="${(pageNum + 5) > pageMax ? pageMax : pageNum + 5}" step="1" varStatus="status">
	   <li class="page-item"<c:if test="${pageNum == status.index }"> class="active" </c:if>><a class="page-link" href="ItemSearchResult?itemSerchWord=${itemSerchWord}&pageNum=${status.index}">${status.index}</a></li>
	 </c:forEach>
    <li class="page-item"><a class="page-link" href="ItemSearchResult?itemSerchWord=${itemSerchWord}&pageNum=${pageNum + 1}">次へ</a></li>
  </ul>
</nav>

</body>
</html>