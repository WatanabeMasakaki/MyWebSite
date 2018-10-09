package ec2;

import java.io.IOException;
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
 * Servlet implementation class ItemMaster4
 */
@WebServlet("/ItemMaster4")
public class ItemMaster4 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemMaster4() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

		// セッションスコープ(userId)に保存されたユーザー情報が管理者以外("1"以外)だったら、NotMasterPage画面にリダイレクト//追加
		int userId = (int) session.getAttribute("userId");

        if(!(userId == 1)) {
       	response.sendRedirect("NotMasterPage");
       	return;
        }
		///////////////////////////////////////////////////////////////////////////////

			// ログイン時に取得したユーザーIDをセッションから取得
			//int userId = (int) session.getAttribute("userId");消追加
			ItemDAO itemDao = new ItemDAO();
			List<ItemDataBeans> itemList = itemDao.getAllItemData();

		    // リクエストスコープにユーザ一覧情報をセット
	 		request.setAttribute("itemList", itemList);

			// ItemMaster4のjspにフォワード
	  		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemMaster4.jsp");
	  		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String itemname = request.getParameter("itemname");
		String itemdetail = request.getParameter("itemdetail");
		String itemprice = request.getParameter("itemprice");
		String itemid = request.getParameter("itemid");

		//未入力の項目がある場合
		if(itemname.equals("") || itemdetail.equals("") || itemprice.equals("")) {

			  //リクエストスコープにエラーメッセージをセット
			  request.setAttribute("errMsg", "入力された内容は正しくありません。");

			  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemMaster4.jsp");
		      dispatcher.forward(request, response);
			  return;
		}else{
			// リクエストパラメータの入力項目を引数に渡して、Daoのメソッドを実行
			ItemDAO ItemDAO = new ItemDAO();
			ItemDAO.findByItemUpdateInfo(itemname,itemdetail,itemprice,itemid);
		}

		// ユーザ一覧のサーブレットにリダイレクト
		response.sendRedirect("ItemMaster4");
	}
}
