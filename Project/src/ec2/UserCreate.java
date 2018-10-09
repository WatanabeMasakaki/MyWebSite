package ec2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;

/**
 * Servlet implementation class UserCreate
 */
@WebServlet("/UserCreate")
public class UserCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッションスコープが空(null)だったらログイン画面にリダイレクト
		HttpSession session = request.getSession();
		session.getAttribute("userInfo");
          if(session.getAttribute("userInfo") == null) {
        	  response.sendRedirect("Login");
        	  return;
          }

  		// 新規登録のjspにフォワード
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserCreate.jsp");
    	dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
	    String loginid = request.getParameter("loginid");
		String password = request.getParameter("password");
		String passwordck = request.getParameter("passwordck");
		String username = request.getParameter("username");
		String birthdate = request.getParameter("birthdate");

		int result = 0; //初期化
		//パスワードとパスワード(確認)の入力内容が異なる場合
		//入力項目に１つでも未入力のものがある場合
		 if(!password.equals(passwordck) || password.equals("") || passwordck.equals("")
				  || username.equals("") || birthdate.equals("") || loginid.equals("")) {

		   //リクエストスコープにエラーメッセージをセット
		   request.setAttribute("errMsg", "入力された内容は正しくありません。");

		   // 新規登録jspにフォワード
		   RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserCreate.jsp");
		   dispatcher.forward(request, response);
		   return;
	     } else {
	   	  // リクエストパラメータの入力項目を引数に渡して、Daoのメソッドを実行
	   	  UserDao userDao = new UserDao();
	   	  result = userDao.findByUserSignupInfo(loginid,password,username,birthdate);
	     }

		  //既に登録されているログインIDが入力された場合
		  if (result == 0) {
		  //リクエストスコープにエラーメッセージをセット
		  request.setAttribute("errMsg", "入力された内容は正しくありません。");

		  // 新規登録jspにフォワード
		  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/UserCreate.jsp");
		  dispatcher.forward(request, response);
		  }
		  /** 登録成功の場合 **/
		  // ユーザ一覧のサーブレットにリダイレクト
	      response.sendRedirect("UserList");
    }
}
