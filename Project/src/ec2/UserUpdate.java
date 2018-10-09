package ec2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.User;
import dao.UserDao;

/**
 * Servlet implementation class UserUpdate
 */
@WebServlet("/UserUpdate")
public class UserUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// URLからGETパラメータとしてIDを受け取る
	    String id = request.getParameter("id");

	    // 確認用：idをコンソールに出力
	 	System.out.println(id);

	    // TODO  未実装：idを引数にして、idに紐づくユーザ情報を出力する
	 	UserDao userDao = new UserDao();
	    int id2 = Integer.parseInt(id);
	    User user = userDao.findByUserDetailInfo(id2);

	 // TODO  未実装：ユーザ情報をリクエストスコープにセットしてjspにフォワード
	    request.setAttribute("user",user );
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String password = request.getParameter("password");
		String passwordck = request.getParameter("passwordck");
		String username = request.getParameter("username");
		String birthdate = request.getParameter("birthdate");
		String id = request.getParameter("id"); //getParameter()はString型しか受け付けない。

		//int result = 0; //初期化
		//パスワードとパスワード(確認)の入力内容が異なる場合
		//パスワード以外に未入力の項目がある場合
		if(!password.equals(passwordck) || username.equals("") || birthdate.equals("")) {

			  //リクエストスコープにエラーメッセージをセット
			  request.setAttribute("errMsg", "入力された内容は正しくありません。");

			  // ユーザー情報更新jspにフォワード
			  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserUpdate.jsp");
		      dispatcher.forward(request, response);
			  return;
		}else{
			// リクエストパラメータの入力項目を引数に渡して、Daoのメソッドを実行
			UserDao userDao = new UserDao();
			userDao.findByUserUpdateInfo(password,username,birthdate,id);
		}

		// ユーザ一覧のサーブレットにリダイレクト
		response.sendRedirect("UserList");
	}
}
