package ec2;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class item
 */
@WebServlet("/ItemSearchResult")
public class ItemSerchResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final static int pageMaxItemCount = 8; //1ページに表示する商品の数

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemSerchResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response); //doPostで書いた内容を繰り返す（JSPの<a/a>タグは必ずdoGetに遷移するようになっている）

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

	 try {
		// リクエストパラメータの入力項目を取得
		String itemSerchWord = request.getParameter("itemSerchWord");

		//表示ページ番号 未指定の場合 1ページ目を表示
		int pageNum = Integer.parseInt(request.getParameter("pageNum") == null ? "1" : request.getParameter("pageNum"));
		// 新たに検索されたキーワードをセッションに格納する
		session.setAttribute("itemSerchWord", itemSerchWord);

		// 商品リストを取得 ページ表示分のみ
		ItemDAO itemDao = new ItemDAO();
		ArrayList<ItemDataBeans> searchResultItemList = ItemDAO.getItemsByItemName(itemSerchWord, pageNum, pageMaxItemCount);

		// 検索ワードに対しての総ページ数を取得
		double itemCount = ItemDAO.getItemCount(itemSerchWord);
		int pageMax = (int) Math.ceil(itemCount / pageMaxItemCount);

		//総アイテム数
		request.setAttribute("itemCount", (int) itemCount);

		// 総ページ数
		request.setAttribute("pageMax", pageMax);

		// 表示ページ
		request.setAttribute("pageNum", pageNum);

		//検索商品リスト
		request.setAttribute("itemList", searchResultItemList);

		// 商品検索結果のjspにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemSerchResult.jsp");
		dispatcher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}
}


