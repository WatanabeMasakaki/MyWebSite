package ec2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ItemDataBeans;
import dao.ItemDAO;


/**
 * Servlet implementation class SerchResult
 */
@WebServlet("/SerchResult")
public class SerchResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SerchResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// セッションスコープが空(null)だったらログイン画面にリダイレクト
		session.getAttribute("userInfo");
          if(session.getAttribute("userInfo") == null) {
        	  response.sendRedirect("Login");
        	  return;
          }

          try {
  			   //商品情報を取得
  			   ArrayList<ItemDataBeans>itemList = ItemDAO.getRandItem(4);

  			   //リクエストスコープに商品情報をセット
  			   request.setAttribute("itemList", itemList);

  			   //セッションにsearchWordが入っていたら破棄する
  			   String searchWord = (String)session.getAttribute("searchWord");
  			   if(searchWord != null) {
  			        session.removeAttribute("searchWord");
  			     }

  			   //SerchResult.jspへフォワード
  			   RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/SerchResult.jsp");
  			   dispatcher.forward(request, response);

  		         } catch (Exception e) {
			     e.printStackTrace();
			     session.setAttribute("errorMessage", e.toString());
			     response.sendRedirect("Error");
		      }
	        }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String itemSerchWord = request.getParameter("itemSerchWord");

		// リクエストパラメータの入力項目を引数に渡して、Daoのメソッドを実行
		ItemDAO itemDao = new ItemDAO();
		List<ItemDataBeans>itemList = itemDao.getItemsByItemSerchWord(itemSerchWord);

		// リクエストスコープに商品検索結果情報をセット
		request.setAttribute("itemList", itemList);

		// 商品検索結果のjspにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemSerchResult.jsp");
		dispatcher.forward(request, response);

	}
}
