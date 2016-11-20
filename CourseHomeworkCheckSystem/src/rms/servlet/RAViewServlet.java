package rms.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import newproject.RAService;
import newproject.model.RA;
import newproject.service.impl.RAServiceImpl;
import rms.servlet.business.RAListBean;

/**
 * Servlet implementation class RAViewServlet
 */
@WebServlet("/RAViewServlet")
public class RAViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RAService raService = new RAServiceImpl();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RAViewServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session==null){
			response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
		}
		else{
			String userId = String.valueOf(session.getAttribute("userId"));
			if(userId.equals("null")){
				session = null;
				response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
			}
			else{
				List<RA> raList = raService.retrieveRA(userId);
				RAListBean raListBean = new RAListBean();
				raListBean.setRaList(raList);
				request.setAttribute("raList", raListBean);
				
				ServletContext context = getServletContext();
				context.getRequestDispatcher("/jsp/qualityManager/addRisk.jsp").forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
