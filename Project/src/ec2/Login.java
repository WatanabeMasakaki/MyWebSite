package ec2;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;// フォワード
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import dao.UserDao;

/**
 * Servlet implementation class login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// ログインセッションがある場合、商品一覧画面(doget)にリダイレクトさせる
	    HttpSession session = request.getSession();
		session.getAttribute("userInfo");
		  if(session.getAttribute("userInfo") != null) {
		    response.sendRedirect("SerchResult");
		    return;
		  }
		 //login.jspへフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");
		// HttpSessionインスタンスの取得
		HttpSession session = request.getSession();

	try {
		// リクエストパラメータの入力項目を取得
		String loginid = request.getParameter("loginid");
		String password = request.getParameter("password");

		//ユーザーIDを取得(購入履歴(BuyDateHistory)で確認で使う)
		int userId = UserDao.getUserId(loginid, password);
		session.setAttribute("userId", userId);

		// リクエストパラメータの入力項目を引数に渡して、Daoのメソッドを実行
		UserDao userDao = new UserDao();
		User user = userDao.findByLoginInfo(loginid, password);

		/** テーブルに該当のデータが見つからなかった場合 **/
		if (user == null) {
			// リクエストスコープにエラーメッセージをセット
			request.setAttribute("errMsg", "ログインに失敗しました。");

			// ログインjspにフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		}
		/** テーブルに該当のデータが見つかった場合 **/
		// セッションスコープにユーザの情報をセット
		session = request.getSession();
		session.setAttribute("userInfo", user);
		// 商品一覧のサーブレットにリダイレクト
		response.sendRedirect("SerchResult");

   }catch (SQLException e) {
		// TODO 自動生成された catch ブロック
		e.printStackTrace();
	}
  }
}
