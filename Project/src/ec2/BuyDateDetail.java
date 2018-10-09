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

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * Servlet implementation class BuyDateDetail
 */
@WebServlet("/BuyDateDetail")
public class BuyDateDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyDateDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッション開始
		HttpSession session = request.getSession();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			BuyDataBeans bdb2 = BuyDAO.getBuyDataBeansByBuyId(id);
			ArrayList<ItemDataBeans> bdb3 = BuyDetailDAO.getItemDataBeansListByBuyId(id);

			String validationMessage = (String) Ec2Helper.cutSessionAttribute(session, "validationMessage");

			request.setAttribute("validationMessage", validationMessage);
			request.setAttribute("bdb2", bdb2);
			request.setAttribute("bdb3", bdb3);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/BuyDateDetail.jsp");
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
	}
}
