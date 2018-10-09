package ec2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ItemDataBeans;
import dao.ItemDAO;

/**
 * Servlet implementation class MasterItemDelete
 */
@WebServlet("/MasterItemDelete")
public class MasterItemDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MasterItemDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// URLからGETパラメータとしてIDを受け取る
	    String id = request.getParameter("id");

	 	ItemDAO ItemDAO = new ItemDAO();
	 	int id2 = Integer.parseInt(id);
	 	ItemDataBeans item = ItemDAO.findByItemDetailInfo(id2);

	    request.setAttribute("item",item );
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/MasterItemDelete.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを指定
		request.setCharacterEncoding("UTF-8");

		// リクエストパラメータの入力項目を取得
		String id = request.getParameter("id2");

		// リクエストパラメータの入力項目を引数に渡して、Daoのメソッドを実行
		ItemDAO ItemDAO = new ItemDAO();
		ItemDAO.findByItemDeleteInfo(id);

		// ユーザ一覧のサーブレットにリダイレクト
		response.sendRedirect("ItemMaster5");
	}
}
