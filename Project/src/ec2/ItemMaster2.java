package ec2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.ItemDAO;

/**
 * Servlet implementation class ItemMaster2
 */
@WebServlet("/ItemMaster2")
@MultipartConfig(location="/Users/masagk7/MyWebSite/Project/WebContent/img", maxFileSize=1048576)
public class ItemMaster2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemMaster2() {
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

		// セッションスコープ(userId)に保存されたユーザー情報が管理者以外("1"以外)だったら、NotMasterPage画面にリダイレクト//追加
		int userId = (int) session.getAttribute("userId");

        if(!(userId == 1)) {
       	response.sendRedirect("NotMasterPage");
       	return;
        }

  		// ItemMaster2のjspにフォワード
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemMaster2.jsp");
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
//		String gazoufile = request.getParameter("gazoufile");
		Part part = request.getPart("gazoufile");
		String name = this.getFileName(part);
        part.write(name);
       // response.sendRedirect("jsp/ItemMaster2.jsp");

		int result = 0; //初期化
		//入力項目に１つでも未入力のものがある場合
		 if(itemname.equals("") || itemdetail.equals("")
				  || itemprice.equals("") || part.equals("")) {

		   //リクエストスコープにエラーメッセージをセット
		   request.setAttribute("errMsg", "未入力項目があります！。");

		   // ItemMaster2jspにフォワード
		   RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemMaster2.jsp");
		   dispatcher.forward(request, response);
		   return;
	     } else {
	   	  // リクエストパラメータの入力項目を引数に渡して、Daoのメソッドを実行
	   	  ItemDAO itemDao = new ItemDAO();
	   	  result = itemDao.findByItemSignupInfo(itemname,itemdetail,itemprice,name);
	     }

		  //既に登録されているログインIDが入力された場合
		  if (result == 0) {
		  //リクエストスコープにエラーメッセージをセット
		  request.setAttribute("errMsg", "入力された内容は正しくありません。");

		  // ItemMaster2jspにフォワード
		  RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/ItemMaster2.jsp");
		  dispatcher.forward(request, response);
		  }
		  /** 登録成功の場合 **/
		  // ユーザ一覧のサーブレットにリダイレクト
	      response.sendRedirect("SerchResult");
	}

    private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }

}
